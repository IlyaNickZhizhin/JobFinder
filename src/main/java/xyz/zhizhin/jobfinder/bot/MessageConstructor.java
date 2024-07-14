package xyz.zhizhin.jobfinder.bot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import xyz.zhizhin.jobfinder.dictionariesHHapi.Currency;

import java.util.ArrayList;
import java.util.List;

import static xyz.zhizhin.jobfinder.bot.UserState.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageConstructor {

    private final TelegramClient client;
    private final UserStateStorage userStateStorage;


    public Integer askName(Update update) {
        return sendMessage(update, "Enter your Name");
    }

    public Integer askSurname(Update update) {
        return sendMessage(update, "Enter your Surname");
    }

    public Integer askMiddleName(Update update) {
        return sendMessage(update, "Enter your Middle name");
    }

    public Integer askSex(Update update) {
        return sendMessage(update, "Enter your gender, please use Man, Woman or other ONE word witch will be understandable for AI");
    }

    public Integer askAge(Update update) {
        return sendMessage(update, "Enter your full years age, please use only numbers");
    }

    public Integer askAdditionalText(Update update) {
        return sendMessage(update, "Enter additional text, for example, a link to a portfolio or LinkedIn with a short description, for example, \"You can view my works by following the link https:\\\\your-link-here\"");
    }

    public Integer askExpirience(Update update) {
        return sendMessage(update, "Enter your full years of your experience, please use only numbers");
    }

    public Integer askSetSkills(Update update) {
        return sendMessage(update, "Enter a comma-separated list of your skills so that the bot can compare it with a list of requirements in vacancies, for example, \"Drawing, Personnel management, category B driver's license\"");
    }

    public Integer askFindStrategy(Update update) {
        return sendMessage(update, "Заглушка");
    }

    public Integer askCheck(Update update) {
        return sendMessage(update, "Enter your check Y or N");
    }

    public void sendError(Long chatId) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text("Something incorrect, lets try again")
                .build();
        try  {
            client.execute(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void next(Long userId, Update update) {
        UserState state = userStateStorage.getUserStates().get(userId).getFirst();
        switch (state) {
            case ASKING_NAME : {
                deleteMessage(update.getMessage().getChatId(), userStateStorage.getUserStates().get(userId).getSecond());
                userStateStorage.getUserStates().put(userId, Pair.of(ASKING_SURNAME, askSurname(update)));
                break;
            }
            case ASKING_SURNAME : {
                deleteMessage(update.getMessage().getChatId(), userStateStorage.getUserStates().get(userId).getSecond());
                userStateStorage.getUserStates().put(userId, Pair.of(ASKING_MIDDLE_NAME, askMiddleName(update)));
                break;
            }
            case ASKING_MIDDLE_NAME : {
                deleteMessage(update.getMessage().getChatId(), userStateStorage.getUserStates().get(userId).getSecond());
                userStateStorage.getUserStates().put(userId, Pair.of(ASKING_GENDER, askSex(update)));
                break;
            }
            case ASKING_GENDER: {
                deleteMessage(update.getMessage().getChatId(), userStateStorage.getUserStates().get(userId).getSecond());
                userStateStorage.getUserStates().put(userId, Pair.of(ASKING_AGE, askAge(update)));
                break;
            }
            case ASKING_AGE : {
                deleteMessage(update.getMessage().getChatId(), userStateStorage.getUserStates().get(userId).getSecond());
                userStateStorage.getUserStates().put(userId, Pair.of(ASKING_ADDITIONAL_TEXT,askAdditionalText(update)));
                break;
            }
            case ASKING_ADDITIONAL_TEXT : {
                deleteMessage(update.getMessage().getChatId(), userStateStorage.getUserStates().get(userId).getSecond());
                userStateStorage.getUserStates().put(userId, Pair.of(ASKING_EXPERIENCE, askExpirience(update)));
                break;
            }
            case ASKING_EXPERIENCE : {
                deleteMessage(update.getMessage().getChatId(), userStateStorage.getUserStates().get(userId).getSecond());
                userStateStorage.getUserStates().put(userId, Pair.of(ASKING_SET_SKILLS, askSetSkills(update)));
                break;
            }
            case ASKING_SET_SKILLS : {
                deleteMessage(update.getMessage().getChatId(), userStateStorage.getUserStates().get(userId).getSecond());
                userStateStorage.getUserStates().put(userId, Pair.of(ASKING_CHECK, askFindStrategy(update)));
                break;
            }
            case ASKING_CHECK : {
                deleteMessage(update.getMessage().getChatId(), userStateStorage.getUserStates().get(userId).getSecond());
                userStateStorage.getUserStates().remove(userId);
                askCheck(update);
                break;
            }
        }
    }

    private Integer sendMessage(Update update, String text) {
        long chatId = update.getMessage().getChatId();
        updateCard(update);
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .parseMode("Markdown")
                .build();
        try  {
            Message response = client.execute(message);
            return response.getMessageId();
        } catch (TelegramApiException e) {
            log.error(e.getMessage(), e);
            return -1;
        }
    }

    private void deleteMessage(Long chatId, Integer messageId) {
        DeleteMessage deleteMessage = DeleteMessage.builder()
                .chatId(chatId)
                .messageId(messageId)
                .build();
        try {
            client.execute(deleteMessage);
        } catch (TelegramApiException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void updateCard(Update update) {
        Long userId = update.getMessage().getFrom().getId();
        int cardMessage;
        try {
            cardMessage = Integer.parseInt(userStateStorage.getUserAnswer().get(userId).get(CARD_ID));
            EditMessageText editMessage = EditMessageText.builder()
                    .chatId(update.getMessage().getChatId())
                    .messageId(cardMessage)
                    .parseMode("Markdown")
                    .text(createCard(userId))
                    .build();
            try {
                client.execute(editMessage);
            } catch (TelegramApiException e) {
                log.error(e.getMessage(), e);
            }
        } catch (NumberFormatException ignored) {}
    }

    public void generateCard(Update update) {
        Long userId = update.getMessage().getFrom().getId();
        userStateStorage.getUserAnswer().get(userId).put(CARD_ID, String.valueOf(sendMessage(update, createCard(userId))));
    }

    private String createCard(Long userId) {
        StringBuffer card = new StringBuffer();
        card.append("**")
                .append(ASKING_NAME.toString().split("_")[1])
                .append(":** ")
                .append(userStateStorage.getUserAnswer().get(userId).get(ASKING_SURNAME)==null ? " " : userStateStorage.getUserAnswer().get(userId).get(ASKING_SURNAME))
                .append(" ")
                .append(userStateStorage.getUserAnswer().get(userId).get(ASKING_NAME)==null  ? " " : userStateStorage.getUserAnswer().get(userId).get(ASKING_NAME))
                .append(" ")
                .append(userStateStorage.getUserAnswer().get(userId).get(ASKING_MIDDLE_NAME)==null ? " " : userStateStorage.getUserAnswer().get(userId).get(ASKING_MIDDLE_NAME))
                .append("\n**")
                .append(ASKING_GENDER.toString().split("_")[1])
                .append(":** ")
                .append(userStateStorage.getUserAnswer().get(userId).get(ASKING_GENDER)==null  ? " " : userStateStorage.getUserAnswer().get(userId).get(ASKING_GENDER))
                .append("\n**")
                .append(ASKING_AGE.toString().split("_")[1])
                .append(":** ")
                .append(userStateStorage.getUserAnswer().get(userId).get(ASKING_AGE)==null  ? " " : userStateStorage.getUserAnswer().get(userId).get(ASKING_AGE))
                .append("\n**")
                .append(ASKING_EXPERIENCE.toString().split("_")[1])
                .append(":** ")
                .append(userStateStorage.getUserAnswer().get(userId).get(ASKING_EXPERIENCE)==null  ? " " : userStateStorage.getUserAnswer().get(userId).get(ASKING_EXPERIENCE))
                .append("\n**")
                .append(ASKING_SET_SKILLS.toString().split("_")[2])
                .append(":** ")
                .append(userStateStorage.getUserAnswer().get(userId).get(ASKING_SET_SKILLS)==null  ? " " : userStateStorage.getUserAnswer().get(userId).get(ASKING_SET_SKILLS))
                .append("\n**")
                .append(ASKING_ADDITIONAL_TEXT.toString().split("_")[1])
                .append(":** ")
                .append(userStateStorage.getUserAnswer().get(userId).get(ASKING_ADDITIONAL_TEXT)==null  ? " " : userStateStorage.getUserAnswer().get(userId).get(ASKING_ADDITIONAL_TEXT))
                .append("\n**")
                .append(ASKING_FIND_STRATEGY.toString().split("_")[2])
                .append(":** ")
                .append(userStateStorage.getUserAnswer().get(userId).get(ASKING_FIND_STRATEGY)==null ? " " : userStateStorage.getUserAnswer().get(userId).get(ASKING_FIND_STRATEGY));
        return card.toString();
    }

    private InlineKeyboardMarkup createKeyboard(Long userId) {
        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        for (Currency currency: Currency.values()) {
            row.add(InlineKeyboardButton.builder().text(currency.getName()).callbackData("currency" + currency.getCode()).build());
        }
        keyboardRows.add(row);
        return InlineKeyboardMarkup.builder().build();
    }
}

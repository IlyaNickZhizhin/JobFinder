package xyz.zhizhin.jobfinder.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;


@Component
public class Bot extends AbilityBot {

    @Value("${telegram-bot.creatorId}")
    private Long creatorId;
    private final BotController controller;
    private final UserStateStorage storage;
    private final MessageConstructor constructor;
    private final MessageTextValidator validator;



    public Bot(TelegramClient telegramClient, @Value("${telegram-bot.name}") String botUsername,
               BotController controller,
               MessageConstructor constructor,
               UserStateStorage storage,
               MessageTextValidator validator) {
        super(telegramClient, botUsername);
        this.controller = controller;
        this.constructor = constructor;
        this.storage = storage;
        this.validator = validator;
    }


    @Override
    public long creatorId() {
        return creatorId;
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Long userId = update.getMessage().getFrom().getId();
            String text = update.getMessage().getText();
            Pair<UserState, Integer> state = storage.getUserStates().get(userId);
            if (state != null && validator.validate(update)) {
                storage.getUserAnswer().get(userId).put(state.getFirst(), text);
                constructor.next(userId, update);
                return;
            }
            switch (text){
                case "/sign_up" : {
                    controller.singnUn(update);
                    break;
                }
                case "/sign_in" : {
                    controller.singnIn(update);
                    break;
                }
                case "/change" : {
                    controller.change(update);
                    break;
                }
                case "/go" : {
                    controller.go(update);
                    break;
                }
                case "/stop" : {
                    controller.stop(update);
                    break;
                }
                case "/delete" : {
                    controller.delete(update);
                    break;
                }
                default: {
                    controller.defaultMethod(update);
                    break;
                }
            }
        }
    }
}

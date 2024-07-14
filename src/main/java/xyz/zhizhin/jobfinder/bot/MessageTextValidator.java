package xyz.zhizhin.jobfinder.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class MessageTextValidator {

    private final UserStateStorage storage;

    public boolean validate(Update update) {
        switch (storage.getUserStates().get(update.getMessage().getFrom().getId()).getFirst()) {
            case ASKING_GENDER, ASKING_NAME, ASKING_SURNAME, ASKING_MIDDLE_NAME -> {
                return validateGender(update.getMessage().getText());
            }
            case ASKING_AGE, ASKING_EXPERIENCE -> {
                return validateYears(update.getMessage().getText());
            }
            default -> {
                return true;
            }
        }

    }


    private boolean validateGender(String text) {
        return text.split(" ").length == 1;
    }

    private boolean validateYears(String text) {
        try {
            Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

}

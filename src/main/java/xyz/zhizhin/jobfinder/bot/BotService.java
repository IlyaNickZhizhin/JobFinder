package xyz.zhizhin.jobfinder.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import xyz.zhizhin.jobfinder.controller.HeadHunterUserController;
import xyz.zhizhin.jobfinder.model.HeadHunterUser;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class BotService {

    private final HeadHunterUserController userController;
    private final MessageConstructor constructor;
    private final UserStateStorage storage;

    public void signUp(Update update) {
        HeadHunterUser user = new HeadHunterUser();
        user.setId(update.getMessage().getFrom().getId());
        user.setTelegramName(update.getMessage().getFrom().getUserName());
        storage.getUserAnswer().put(user.getId(), new HashMap<>());
        constructor.generateCard(update);
        storage.getUserStates().put(user.getId(), Pair.of(UserState.ASKING_NAME, constructor.askName(update)));
    }

    public void defaultMethod(Update update) {
        constructor.sendError(update.getMessage().getChatId());
    }
}

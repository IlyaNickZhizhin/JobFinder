package xyz.zhizhin.jobfinder.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Update;

@Controller
@RequiredArgsConstructor
public class BotController {

    private final BotService botService;

    public void singnUn(Update update) {
        botService.signUp(update);
    }

    public void singnIn(Update update) {
    }

    public void change(Update update) {
    }

    public void go(Update update) {
    }

    public void stop(Update update) {
    }

    public void delete(Update update) {
    }

    public void defaultMethod(Update update) {
        botService.defaultMethod(update);
    }
}

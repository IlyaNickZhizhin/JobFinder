package xyz.zhizhin.jobfinder.bot;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.time.Duration;

@Component
@ComponentScan("xyz.zhizhin.jobfinder.bot")
@Slf4j
public class BotConfig {

    @Bean
    public TelegramClient telegramClient(@Value("${telegram-bot.token}") String botToken) {
        OkHttpClient client = new OkHttpClient.Builder()
                .callTimeout(Duration.ofSeconds(10))
                .readTimeout(Duration.ofSeconds(10))
                .connectTimeout(Duration.ofSeconds(10))
                .writeTimeout(Duration.ofSeconds(10))
                .build();
        return new OkHttpTelegramClient(client, botToken);
    }

    @Bean
    public TelegramBotsLongPollingApplication startApp(@Value("${telegram-bot.token}") String botToken, Bot bot) {
        TelegramBotsLongPollingApplication app = new TelegramBotsLongPollingApplication();
        try {
            app.registerBot(botToken, bot);
            log.info("Bot started");
            return app;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

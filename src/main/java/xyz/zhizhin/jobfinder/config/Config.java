package xyz.zhizhin.jobfinder.config;

import okhttp3.OkHttpClient;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openai.api.common.OpenAiApiClientErrorException;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.WebHandler;

@Configuration
public class Config {

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }


    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder
                .defaultSystem("Ты карьерный консультант, получающий стандартное сопроводительное письмо пользователя, описание вакансии, пользователя, и дающий только один вариант наиболее верного сопроводительного письма для данной вакансии не более 100 слов, сохраняя стиль оригинального письма, без вступления и пояснений")
                .build();
    }

}

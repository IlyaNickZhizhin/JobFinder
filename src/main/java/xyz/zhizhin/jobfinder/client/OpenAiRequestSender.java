package xyz.zhizhin.jobfinder.client;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.RestController;
import xyz.zhizhin.jobfinder.model.HeadHunterUser;
import xyz.zhizhin.jobfinder.model.HeadHunterVacancy;
import xyz.zhizhin.jobfinder.model.HeadHunterVacancyResponse;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class OpenAiRequestSender {

    private final ChatClient chatClient;
    private final String DISCLAIMER = "Данное сообщение написано приложением при помощи чата GPT" +
            " автор понимает некоторую не этичность данного действия, но на это его подтолкнули крайние обстоятельства," +
            " надеемся на Ваше понимание";

    public String prepareResponseLetter(HeadHunterVacancyResponse vacancyResponse,
                                        HeadHunterVacancy vacancy,
                                        HeadHunterUser headHunterUser) {
        StringBuilder responce = new StringBuilder(chatClient.prompt().user(buildResponseQuestion(vacancyResponse, vacancy, headHunterUser)).call().content());
        return responce.append("\n").append(headHunterUser.getAdditionalText()).append("\n").append(DISCLAIMER).append("\n").toString();
    }

    private String buildResponseQuestion(HeadHunterVacancyResponse vacancyResponse,
                                         HeadHunterVacancy vacancy,
                                         HeadHunterUser headHunterUser) {
        StringBuilder response = new StringBuilder();
        response.append("Данные пользователя: \"");
        response.append(headHunterUser.getName()).append(" ")
                .append(headHunterUser.getMiddleName()).append(" ")
                .append(headHunterUser.getSurname()).append(", ")
                .append(headHunterUser.getGender()).append(", ")
                .append(headHunterUser.getAge()).append(" лет. Опыт: ")
                .append(headHunterUser.getExpirience())
                .append(" лет")
                .append("\"\n");
        response.append("Набор навыков: \"")
                .append(headHunterUser.getSetSkills())
                .append("\"\n");
        response.append("Компания: \"")
                .append(vacancy.getCompanyName())
                .append("\"\n");
        response.append("Описание вакансии: \"")
                .append(vacancy.getDescription())
                .append("\"\n");
        response.append("Стандартное сопроводительное письмо: \"")
                .append(vacancyResponse.getUserResponse())
                .append("\"\n");
        return response.toString();
    }

}

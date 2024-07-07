package xyz.zhizhin.jobfinder.integration;

import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.zhizhin.jobfinder.client.HeadHunterRequestSender;
import xyz.zhizhin.jobfinder.client.OpenAiRequestSender;
import xyz.zhizhin.jobfinder.config.Config;
import xyz.zhizhin.jobfinder.dictionariesHHapi.*;
import xyz.zhizhin.jobfinder.mapper.JSONItemToHeadHunterVacancy;
import xyz.zhizhin.jobfinder.model.FindStrategy;
import xyz.zhizhin.jobfinder.model.HeadHunterUser;
import xyz.zhizhin.jobfinder.model.HeadHunterVacancy;
import xyz.zhizhin.jobfinder.model.HeadHunterVacancyResponse;
import xyz.zhizhin.jobfinder.repository.HeadHunterVacancyRepository;
import xyz.zhizhin.jobfinder.service.FindJobService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class tryApp {

    OkHttpClient okHttpClient = new OkHttpClient();
    Config config = new Config();
    HeadHunterRequestSender requestSender = new HeadHunterRequestSender(okHttpClient);
    JSONItemToHeadHunterVacancy jhv = new JSONItemToHeadHunterVacancy();

    @Autowired
    OpenAiRequestSender requestSenderAI;

    HeadHunterVacancyRepository headHunterVacancyRepository = Mockito.mock(HeadHunterVacancyRepository.class);;

    FindJobService findJobService = new FindJobService(requestSender, headHunterVacancyRepository, jhv);


    private FindStrategy findStrategy;
    private HeadHunterUser headHunterUser = new HeadHunterUser();
    private HeadHunterVacancyResponse headHunterVacancyResponse = new HeadHunterVacancyResponse();

    @BeforeEach
    public void init() {
        findStrategy = new FindStrategy();
        findStrategy.setId(0);
        findStrategy.setPhrase("Java");
        findStrategy.setCurrency(Currency.USD);
        findStrategy.setSearchField(SearchField.NAME);
        findStrategy.setExperience(Experience.JUNIOR);
        findStrategy.setEmployment(Employment.FULL);
        findStrategy.setSchedule(Schedule.FULL_DAY);
        findStrategy.setOnlyWithSalaryStatus(true);
        findStrategy.setPeriod(1);
        findStrategy.setSalary(500);
        findStrategy.setVacancySearchOrder(VacancySearchOrder.PUBLICATION_TIME);
        headHunterUser.setFindStrategy(findStrategy);
        headHunterUser.setName("Илья");
        headHunterUser.setMiddleName("Николаевич");
        headHunterUser.setSurname("Жижин");
        headHunterUser.setAge(37);
        headHunterUser.setExpirience(2);
        headHunterUser.setSetSkills("Java, Spring, Hibernate");
        headHunterUser.setSetSkills("мужчина");
        headHunterUser.setAdditionalText("GitHub: https://github.com/IlyaNickZhizhin");
        headHunterVacancyResponse.setUserResponse("Привет! Смотрите какой я охренительный девелопер, заинтересован работать на Вас, за вашу нищенскую зарплату!");
    }

    @Test
    public void test() throws IOException {
        List<HeadHunterVacancy> headHunterVacancyList = new ArrayList<>();
        when(headHunterVacancyRepository.findById(findStrategy.getSalary())).thenReturn(Optional.ofNullable(null));
        when(headHunterVacancyRepository.saveAll(anyList())).thenAnswer(i->i.getArguments()[0]);
        headHunterVacancyList = findJobService.findJobs(headHunterUser);
        for (HeadHunterVacancy vacancy : headHunterVacancyList) {
            System.out.println(vacancy);
            System.out.println(requestSenderAI.prepareResponseLetter(headHunterVacancyResponse, vacancy, headHunterUser));
        }

    }

}

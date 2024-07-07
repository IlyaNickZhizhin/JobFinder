package xyz.zhizhin.jobfinder.service;

import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import xyz.zhizhin.jobfinder.client.HeadHunterRequestSender;
import xyz.zhizhin.jobfinder.mapper.JSONItemToHeadHunterVacancy;
import xyz.zhizhin.jobfinder.model.FindStrategy;
import xyz.zhizhin.jobfinder.model.HeadHunterUser;
import xyz.zhizhin.jobfinder.model.HeadHunterVacancy;
import xyz.zhizhin.jobfinder.repository.HeadHunterVacancyRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FindJobService {

    private final HeadHunterRequestSender headHunterRequestSender;
    private final HeadHunterVacancyRepository repository;
    private final JSONItemToHeadHunterVacancy mapper;

    public List<HeadHunterVacancy> findJobs(HeadHunterUser user) {
        FindStrategy findStrategy = user.getFindStrategy();
        if (findStrategy == null) {
            throw new RuntimeException("Find strategy not found");
        }
        List<HeadHunterVacancy> vacancies = new ArrayList<>();
        JSONArray items = headHunterRequestSender.getVacancyJSONArray(findStrategy);
        if (items.isEmpty()) {
            return vacancies;
        }
        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            HeadHunterVacancy vacancy;
            Long id = item.getLong("id");
            vacancy = repository.findById(id)
                    .orElse(new HeadHunterVacancy());
            if (vacancy.getId() == null) {
                item = items.getJSONObject(i);
                vacancy = mapper.itemToHeadHunterVacancy(item);
                Pair<String, String> nameAndDescription = headHunterRequestSender.getCompanyAndDescription(vacancy.getId());
                vacancy.setCompanyName(nameAndDescription.getFirst());
                vacancy.setDescription(nameAndDescription.getSecond());
            }
            vacancies.add(vacancy);
        }
        return repository.saveAll(vacancies);
    }


}

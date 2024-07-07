package xyz.zhizhin.jobfinder.client;

import lombok.RequiredArgsConstructor;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import xyz.zhizhin.jobfinder.model.FindStrategy;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class HeadHunterRequestSender {

    private final OkHttpClient client;

    public JSONArray getVacancyJSONArray(FindStrategy findStrategy) {
        if (findStrategy == null) {
            throw new RuntimeException("Find strategy not found");
        }
        Request findVacanciesRequest = new Request.Builder()
                .url(prepareUrlForFindVacancy(findStrategy))
                .build();
        try (Response response = client.newCall(findVacanciesRequest).execute()) {
            String responseBody = response.body().string();
            return new JSONObject(responseBody).getJSONArray("items");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };

    public Pair<String, String> getCompanyAndDescription(Long id){
        Request request = new Request.Builder()
                .url(prepareUrlForFindVacancyDescription(id))
                .build();
        try (Response responseDescription = client.newCall(request).execute()) {
            String responseVacancyBody = responseDescription.body().string();
            JSONObject vacancyJson = new JSONObject(responseVacancyBody);
            return Pair.of(vacancyJson.getJSONObject("employer").getString("name"), vacancyJson.getString("description").replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " "));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpUrl prepareUrlForFindVacancy (FindStrategy findStrategy) {
        return  new HttpUrl.Builder()
                .scheme("https")
                .host("api.hh.ru")
                .addPathSegment("vacancies")
                .addQueryParameter("page", String.valueOf(0))
                .addQueryParameter("per_page", String.valueOf(10))
                .addQueryParameter("text", findStrategy.getPhrase())
                .addQueryParameter("salary", String.valueOf(findStrategy.getSalary()))
                .addQueryParameter("currency_code", findStrategy.getCurrency().getCode())
                .addQueryParameter("search_field", findStrategy.getSearchField().getId())
                .addQueryParameter("experience", findStrategy.getExperience().getId())
                .addQueryParameter("employment", findStrategy.getEmployment().getId())
                .addQueryParameter("schedule", findStrategy.getSchedule().getId())
                .addQueryParameter("only_with_salary", String.valueOf(findStrategy.isOnlyWithSalaryStatus()))
                .addQueryParameter("period", String.valueOf(findStrategy.getPeriod()))
                .addQueryParameter("order_by", findStrategy.getVacancySearchOrder().getId())
                .build();
    }

    private HttpUrl prepareUrlForFindVacancyDescription (Long vacancyId) {
        return  new HttpUrl.Builder()
                .scheme("https")
                .host("api.hh.ru")
                .addPathSegment("vacancies")
                .addPathSegment(vacancyId.toString())
                .build();
    }

}

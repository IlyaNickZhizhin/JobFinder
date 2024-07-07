package xyz.zhizhin.jobfinder.mapper;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import xyz.zhizhin.jobfinder.dictionariesHHapi.Currency;
import xyz.zhizhin.jobfinder.dictionariesHHapi.Employment;
import xyz.zhizhin.jobfinder.dictionariesHHapi.Experience;
import xyz.zhizhin.jobfinder.dictionariesHHapi.Schedule;
import xyz.zhizhin.jobfinder.model.HeadHunterVacancy;

@Component
public class JSONItemToHeadHunterVacancy {

    public HeadHunterVacancy itemToHeadHunterVacancy(JSONObject item) {
        HeadHunterVacancy vacancy = new HeadHunterVacancy();
        vacancy.setId(item.getLong("id"));
        JSONObject salary = getSafeJSONObject(item,"salary");
        vacancy.setCurrency(Currency.valueOf(getSafeString(salary, "currency")));
        vacancy.setSalaryFrom(getSafeInt(salary, "from"));
        vacancy.setSalaryTo(getSafeInt(salary, "to"));
        vacancy.setEmployment(Employment.valueOf(item.getJSONObject("employment").getString("id").toUpperCase()));
        vacancy.setAskedExperience(
                Experience.getById(
                        getSafeString(getSafeJSONObject(item, "experience"), "id").toLowerCase()));
        vacancy.setSchedule(
                Schedule.getById(
                        getSafeString(getSafeJSONObject(item, "schedule"), "id").toUpperCase()));
        return vacancy;
    }

    private JSONObject getSafeJSONObject(JSONObject item, String key){
        try {
            return item.getJSONObject(key);
        } catch (JSONException e) {
            return new JSONObject();
        }
    }

    private String getSafeString(JSONObject item, String key){
        try {
            return item.getString(key);
        } catch (JSONException e) {
            return "Значение не найдено";
        }
    }

    private Integer getSafeInt(JSONObject item, String key){
        try {
            return item.getInt(key);
        } catch (JSONException e) {
            return null;
        }
    }

}

package Sites;

import Utility.Collector;
import Utility.Site;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import javax.swing.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HeadHunter implements Site {

    private String search_value = "https://api.hh.ru/vacancies";
    private String answer = "";
    private String log;

    public String getJsonVacanciesFromRequest(String request){ // send request and get json
        try{
            URL url = new URL(request);
            return IOUtils.toString(url, StandardCharsets.UTF_8);
        }catch (Exception e){
            log = e.getMessage();
        }
        return "Не удалось получить ответ\n";
    }

    void useFilter(){ //  use user filters
        if(Collector.getKeyWords() != null ){
            search_value = addOrReplacementParameterURL(search_value, "text", Collector.getKeyWords());
        }
        search_value = addOrReplacementParameterURL(search_value, "only_with_salary", "true");
        search_value = addOrReplacementParameterURL(search_value, "per_page", "100");
    }

    void fillAnswer(String raw_json_vacancies){ // create list vacancies
        JSONObject json = new JSONObject(raw_json_vacancies);
        int pages = json.getInt("pages");
        int per_page = json.getInt("per_page");
        for(int i = 0, k = 1; i < pages; i++){
            for(int j = 0; j < per_page; j++, k++){
                answer += k+")"+" "+ makeVacancyString(json.getJSONArray("items").getJSONObject(j));
            }
            search_value = addOrReplacementParameterURL(search_value, "pages", ""+i);
            json = new JSONObject(getJsonVacanciesFromRequest(search_value));
        }
    }

    String makeVacancyString(JSONObject json){
        String vacancy = "";
        vacancy += json.optString("name", "не указано"); //name
        vacancy += "\t"+json.getJSONObject("salary").optString("from", "не указано");   // salary min
        vacancy += "\t"+json.getJSONObject("salary").optString("to", "не указано");   // salary max
        vacancy += "\t"+json.getJSONObject("salary").optString("currency", "не указано"); // currency
        vacancy += "\t"+"<a href=\""+json.getString("alternate_url")+"\">"+json.getString("alternate_url")+"</a>";    // link
        return vacancy.strip() + "<br>";
    }

    @Override
    public final String getParseData(){ // main scenario
        useFilter();
        fillAnswer(getJsonVacanciesFromRequest(search_value));
        log += "Список вакансий с HeadHunter успешно загружен\n";
        return answer;
    }

    public String getLog() {
        return log;
    }
}

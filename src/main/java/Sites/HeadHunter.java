package Sites;

import Utility.Collector;
import Utility.Site;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HeadHunter implements Site {

    private String search_value = "https://api.hh.ru/vacancies";
    private String answer = "";
    public String getJsonVacanciesFromRequest(String request) throws IOException {
        URL url = new URL(request);
        return IOUtils.toString(url, StandardCharsets.UTF_8);
    }

    void useFilter(){
        if(Collector.getKeyWords() != null ){
            search_value = addOrReplacementParameterURL(search_value, "text", Collector.getKeyWords());
        }
        search_value = addOrReplacementParameterURL(search_value, "only_with_salary", "true");
        search_value = addOrReplacementParameterURL(search_value, "per_page", "100");
    }

    void fillAnswer(String raw_json_vacancies) throws IOException {
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
        vacancy += "\t"+json.getJSONObject("salary").optString("from", "не указано");   // salary
        vacancy += "\t"+json.getJSONObject("salary").optString("to", "не указано");   // salary
        vacancy += "\t"+json.getJSONObject("salary").optString("currency", "не указано"); // currency
        vacancy += "\t"+json.getString("alternate_url");    // link
        return vacancy.strip() + "\n";
    }

    @Override
    public final String getParseData() throws IOException {
        useFilter();
        fillAnswer(getJsonVacanciesFromRequest(search_value));
        System.out.println(answer);
        return answer;
    }

}

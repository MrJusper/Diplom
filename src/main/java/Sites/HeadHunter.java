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
    public String getJsonVacancies() throws IOException {
        URL url = new URL(search_value);
        return IOUtils.toString(url, StandardCharsets.UTF_8);
    }

    void useFilter(){
        if(Collector.getKeyWords() != null ){
            search_value = addOrReplacementParameterURL(search_value, "text", Collector.getKeyWords());
        }
        search_value = addOrReplacementParameterURL(search_value, "only_with_salary", "true");
        search_value = addOrReplacementParameterURL(search_value, "per_page", "100");
        System.out.println(search_value);
    }

    void fillAnswer(String raw_json_vacancies){
        JSONObject json = new JSONObject(raw_json_vacancies);
        answer = makeVacancyString(json.getJSONArray("items").getJSONObject(0));
    }

    String makeVacancyString(JSONObject json){
        String vacancy = "";
        vacancy += json.getString("name"); //name
        vacancy += "\t"+json.getJSONObject("salary").getInt("from");   // salary
        vacancy += "\t"+json.getJSONObject("salary").getInt("to");   // salary
        vacancy += "\t"+json.getJSONObject("salary").getString("currency"); // currency
        vacancy += "\t"+json.getString("alternate_url");    // link
        return vacancy.strip();
    }

    @Override
    public String getParseData() throws IOException {
        useFilter();
        fillAnswer(getJsonVacancies());
        System.out.println(answer);
        return answer;
    }

}

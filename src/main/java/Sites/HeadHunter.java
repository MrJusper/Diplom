package Sites;

import Utility.Collector;
import Utility.Site;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HeadHunter implements Site {

    private String search_value = "https://api.hh.ru/vacancies?only_with_salary=true&per_page=100&";
    private String answer = "";
    public String getJsonVacancies() throws IOException {
        URL url = new URL(search_value);
        return IOUtils.toString(url, StandardCharsets.UTF_8);
    }

    void useFilter(){
        if(Collector.getKeyWords() != null ){
            String[] arrWords = Collector.getKeyWords().split(" ");
            StringBuilder addWords = new StringBuilder("text=");
            addWords.append(arrWords[0]);
            for(int i = 1;i < arrWords.length;i++){
                addWords.append("+").append(arrWords[i]);
            }
            search_value += addWords;
        }
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

    String addOrReplacementParametrURL(String url){

        return null;
    }
}

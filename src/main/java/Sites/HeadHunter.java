package Sites;

import Utility.Collector;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class HeadHunter {

    private String search_value = "https://api.hh.ru/vacancies?";
    public void getVacancies() throws IOException {
        getData();
        URL url = new URL(search_value);
        String json = IOUtils.toString(url, StandardCharsets.UTF_8);
        System.out.println(json);
    }

    void getData(){
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

}

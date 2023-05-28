package Sites;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;

public class ZarplataRU {
    public static void getVacancies() throws IOException {
        URL url = new URL("https://api.zarplata.ru/vacancies");
        String jsonStr = IOUtils.toString(url);
        System.out.println(jsonStr);
    }
}
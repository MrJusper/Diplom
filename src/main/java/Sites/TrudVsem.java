package Sites;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;

public class TrudVsem {

    public static void getVacancies() throws IOException {
        URL url = new URL("http://opendata.trudvsem.ru/api/v1/vacancies?text=инженер");
        String jsonStr = IOUtils.toString(url);
        System.out.println(jsonStr);
    }

}

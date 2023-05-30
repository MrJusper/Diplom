package Utility;

import java.io.IOException;

public interface Site {
    String getParseData() throws IOException;

    default String addOrReplacementParameterURL(String url, String param, String value){
        value = value.strip().replaceAll(" ","+");
        if(!(url.contains("?"))){
            url += "?" + param + "=" + value;
            return url;
        }
        String[] parameters = url.split("\\?")[1].split("&"); // for_example parameters[0] is page=4

        for(int i = 0; i < parameters.length; i++){ // Replace
            if(parameters[i].startsWith(param)){
                parameters[i] = param + "=" + value;
                url = url.split("\\?")[0] + "?" + String.join("&",parameters);
                return url;
            }
        }

        url = url.split("\\?")[0] + "?" + String.join("&",parameters) + "&" + param + "=" + value;
        return url;
    }
}

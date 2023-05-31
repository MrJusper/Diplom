import Sites.*;
import Utility.Site;
import Utility.UserInterface;
// Необходим для запуска ПО, + только через него будет происходить обращения к конкретным классам
// для обработки ответов от сайтов, и отправки запросов

public class Main {
    public static void main(String[] args){

        Site hhru = new HeadHunter();
        UserInterface IOUserInterface = new UserInterface();
        try{
       //     hhru.getParseData();
           // TrudVsem.getVacancies();
          //  ZarplataRU.getVacancies();
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }
}

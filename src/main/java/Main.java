import Sites.*;
import Utility.UserInterface;
// Необходим для запуска ПО, + только через него будет происходить обращения к конкретным классам
// для обработки ответов от сайтов, и отправки запросов

public class Main {
    public static void main(String[] args){

        HeadHunter hhru = new HeadHunter();
        UserInterface IOUserInterface = new UserInterface();
        try{
            hhru.getVacancies();
           // TrudVsem.getVacancies();
          //  ZarplataRU.getVacancies();
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }
}

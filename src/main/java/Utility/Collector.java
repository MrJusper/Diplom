package Utility;

// Будет содержать данные введёные пользователем

public class Collector {
    private static String keyWords;
    String City;
    String minSalary;
    String maxSalary;
    String distant;

    public static String getKeyWords() {
        return keyWords;
    }

    public static void setKeyWords(String keyWords) {
        Collector.keyWords = keyWords;
    }

}

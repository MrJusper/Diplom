package Utility;

// Будет содержать данные введёные пользователем

public class Collector {
    private static String keyWords;
    private static String minSalary;
    private String country;

    public static String getKeyWords() {
        return keyWords;
    }

    public static void setKeyWords(String keyWords) {
        Collector.keyWords = keyWords;
    }

    public static String getMinSalary() {
        return minSalary;
    }

    public static void setMinSalary(String minSalary) {
        Collector.minSalary = minSalary;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}

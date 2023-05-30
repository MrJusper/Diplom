package Utility;

// Визуальное представление кнопок и ползунков для сбора информации по интересующим вакансиям

import javax.swing.*;

public class UserInterface {

    public UserInterface(){

        Collector.setKeyWords("Мэнеджер по продажам косметики");
        makeScreen();
    }

    void makeScreen(){
        JFrame frame = new JFrame("Denis");
        frame.setVisible(true);
    }
}

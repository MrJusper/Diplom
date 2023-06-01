package Utility;

// Визуальное представление кнопок и ползунков для сбора информации по интересующим вакансиям

//        gbc.fill = GridBagConstraints.BOTH;
//        gbc.gridx = 1; pos x
//        gbc.gridy = 0; pos y
//        gbc.ipadx = 150; x size pixel
//        gbc.ipady = 150; y size pixel
//        gbc.gridwidth = 3; column size
//        gbc.gridheight = 3; row size
//      gbc.insets = new Insets(5,5,5,5); down left right top (pixels)

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.Timer;

public class UserInterface {
    ArrayList<Site> sites = new ArrayList<>();
    public UserInterface(Site[] sites){
        Collections.addAll(this.sites, sites);
        makeScreen();
    }

    void makeScreen(){
        JFrame frame = new JFrame("ВКР - ПО АГРЕГАТОР ДЛЯ ПОИСКА РАБОТЫ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagConstraints gbc = new GridBagConstraints();
        GridBagLayout grid = new GridBagLayout();
        frame.setResizable(false);
        frame.setLayout(grid);


        JLabel labelForSearch = new JLabel("Поиск:");
        labelForSearch.setHorizontalAlignment(JLabel.CENTER);
        int insets = 10;
        gbc.insets = new Insets(insets,insets,insets,insets);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(labelForSearch, gbc);

        JTextField forKeyWords = new JTextField();
        gbc.insets = new Insets(insets,insets,insets,insets);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        frame.add(forKeyWords, gbc);
        gbc.gridwidth = 1;

        JTextPane logs = new JTextPane();
        logs.setEditable(false);
        gbc.insets = new Insets(0,insets,insets,insets);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        logs.setText("Тут шаги выполнения\n");
        logs.setPreferredSize(new Dimension(0,500));
        frame.add(logs, gbc);

        JTextPane Output = new JTextPane();
        Output.setEditable(false);
        gbc.insets = new Insets(insets,0,insets,insets);
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridwidth = 0;
        gbc.gridheight = 3;
        Output.setText("Тут будут фигуранты\n");
        Output.setPreferredSize(new Dimension(500,500));
        frame.add(Output, gbc);

        JButton btn1 = new JButton("Искать");
        gbc.insets = new Insets(0,insets,insets,insets);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        frame.add(btn1, gbc);
        ActionListener actionListener = e -> {
            btn1.setEnabled(false);
            logs.setText(logs.getText()+"Поиск начался\n");
            SwingWorker<String, Void> searchVacancies = new SwingWorker<String, Void>() { // heavy work
                @Override
                public String doInBackground() {
                    Timer timer = new Timer();

                    timer.scheduleAtFixedRate(new TimerTask() {

                        Date date = new Date();
                        @Override
                        public void run() {
                            logs.setText(logs.getText()+"Прошло секунд: "+ ((new Date().getTime() - date.getTime()) /1000)+"\n");;
                        }
                    }, 1000, 1000);

                    String answer = "";
                    for(Site site : sites){
                        answer += site.getParseData();
                    }
                    Output.setText(answer);
                    logs.setText(logs.getText()+"Поиск закончился\n");
                    timer.cancel();
                    btn1.setEnabled(true);
                    return answer;
                }
            };
            searchVacancies.execute();
        };
        btn1.addActionListener(actionListener);

        JButton btn2 = new JButton("Сохранить");
        gbc.insets = new Insets(0,0,insets,0);
        gbc.gridx = 1;
        gbc.gridy = 1;
        frame.add(btn2, gbc);
        btn2.addActionListener(e -> logs.setText(logs.getText()+"Считай сохранил\n"));

        JButton btn3 = new JButton("Удалить");
        gbc.insets = new Insets(0,insets,insets,insets);
        gbc.gridx = 2;
        gbc.gridy = 1;
        frame.add(btn3, gbc);
        btn3.addActionListener(e -> logs.setText(logs.getText()+"Считай удалил\n"));


        frame.pack();
        frame.setVisible(true);
    }
}




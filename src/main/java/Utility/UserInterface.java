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
import java.io.File;
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
        gbc.insets = new Insets(insets,0,insets,insets);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        frame.add(forKeyWords, gbc);
        gbc.gridwidth = 1;

        Dimension dimensionLogs = new Dimension(200,500);
        JTextPane logs = new JTextPane();
        JScrollPane jspLogs = new JScrollPane(logs);
        logs.setEditable(false);
        jspLogs.setPreferredSize(dimensionLogs);
        gbc.insets = new Insets(0,insets,insets,insets);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        logs.setText("Тут шаги выполнения\n");
        logs.setPreferredSize(dimensionLogs);
        frame.add(jspLogs, gbc);

        Dimension dimensionTextVacancies = new Dimension(900,600);
        JTextPane Output = new JTextPane();
        JScrollPane jsp = new JScrollPane(Output);
        Output.setEditable(false);
        jsp.setPreferredSize(dimensionTextVacancies);
        gbc.insets = new Insets(insets,0,insets,insets);
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridwidth = 0;
        gbc.gridheight = 3;
        Output.setText("Тут будут фигуранты\n");
        Output.setPreferredSize(dimensionTextVacancies);
        frame.add(jsp, gbc);

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

        JButton btn2 = new JButton("Сохранить"); // BUTTON SAVE
        gbc.insets = new Insets(0,0,insets,0);
        gbc.gridx = 1;
        gbc.gridy = 1;
        frame.add(btn2, gbc);
        btn2.addActionListener(e -> {
            logs.setText(logs.getText()+"Считай сохранил\n");
            JDialog Save_frame = new JDialog(frame, "ПРОЦЕДУРА СОХРАНЕНИЯ");
            Save_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            GridBagConstraints Save_gbc = new GridBagConstraints();
            GridBagLayout Save_grid = new GridBagLayout();
            Save_frame.setResizable(false);
            Save_frame.setLayout(Save_grid);

            JLabel Save_labelForSearch = new JLabel("Укажите название файла:");
            Save_labelForSearch.setHorizontalAlignment(JLabel.CENTER);
            Save_gbc.insets = new Insets(insets,insets,insets,insets);
            Save_gbc.fill = GridBagConstraints.BOTH;
            Save_gbc.gridx = 0;
            Save_gbc.gridy = 0;
            Save_frame.add(Save_labelForSearch, Save_gbc);

            JTextField Save_forKeyWords = new JTextField();
            Save_gbc.insets = new Insets(insets,0,insets,0);
            Save_gbc.gridx = 1;
            Save_gbc.gridy = 0;
            Save_gbc.gridwidth = 2;
            Save_forKeyWords.setPreferredSize(new Dimension( 250,16));
            Save_frame.add(Save_forKeyWords, Save_gbc);
            Save_gbc.gridwidth = 1;

            JButton Save_btn1 = new JButton("Сохранить");
            Save_gbc.insets = new Insets(insets,insets,insets,insets);
            Save_gbc.gridx = 3;
            Save_gbc.gridy = 0;
            Save_gbc.gridheight = 1;
            Save_gbc.gridwidth = 1;
            Save_frame.add(Save_btn1, Save_gbc);

            Save_frame.pack();
            Save_frame.setLocationRelativeTo(null);
            Save_frame.setVisible(true);
        });

        JButton btn3 = new JButton("Удалить");
        gbc.insets = new Insets(0,insets,insets,insets);
        gbc.gridx = 2;
        gbc.gridy = 1;
        frame.add(btn3, gbc);
        btn3.addActionListener(e -> {
            logs.setText(logs.getText()+"Считай удалил\n");
            JDialog Delete_frame = new JDialog(frame, "ПРОЦЕДУРА УДАЛЕНИЯ");
            Delete_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            GridBagConstraints Delete_gbc = new GridBagConstraints();
            GridBagLayout Delete_grid = new GridBagLayout();
            Delete_frame.setResizable(false);
            Delete_frame.setLayout(Delete_grid);

            String directory = "../сохранённые_вакансии";
            new File(directory).mkdir();
            File[] listOfFiles = new File("../сохранённые_вакансии").listFiles();
            ArrayList<String> list = new ArrayList<>();
            for (File file : listOfFiles) {
                list.add(file.getName());
            }
            JList Saved_files = new JList(list.toArray(new String[0]));
            Delete_gbc.insets = new Insets(insets,insets,0,insets);
            Delete_gbc.fill = GridBagConstraints.BOTH;
            Delete_gbc.gridx = 0;
            Delete_gbc.gridy = 0;
            Delete_gbc.gridheight = 1;
            Delete_gbc.gridwidth = 2;
            Saved_files.setPreferredSize(new Dimension(400,400));
            Delete_frame.add(Saved_files,Delete_gbc);

            JButton Save_btn1 = new JButton("Удалить");
            Delete_gbc.insets = new Insets(insets,0,insets,insets);
            Delete_gbc.gridx = 1;
            Delete_gbc.gridy = 1;
            Delete_gbc.gridheight = 1;
            Delete_gbc.gridwidth = 1;
            Delete_frame.add(Save_btn1, Delete_gbc);

            JLabel Save_labelForSearch = new JLabel("Выберите файл для удаления");
            Save_labelForSearch.setHorizontalAlignment(JLabel.CENTER);
            Delete_gbc.insets = new Insets(insets,insets,insets,insets);
            Delete_gbc.gridx = 0;
            Delete_gbc.gridy = 1;
            Delete_frame.add(Save_labelForSearch, Delete_gbc);

            Delete_frame.pack();
            Delete_frame.setLocationRelativeTo(null);
            Delete_frame.setVisible(true);
        });


        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}




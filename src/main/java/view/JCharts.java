package view;

import javax.swing.*;
import java.awt.*;

public class JCharts extends JPanel {

    private Chart chartDay;
    private Chart chartWeek;
    private Chart chartMonth;

    /**
     * Classe que contrueix el panell de les estadístiques
     * @param dia array amb les dades de les ultimes 24 hores
     * @param setmana array amb les dades dels ultims 7 dies
     * @param mes array amb les dades dels ultims 30 dies
     */
    public JCharts(int[]dia, int[]setmana, int []mes){

        JTabbedPane jtPane = new JTabbedPane();

        chartDay = new Chart(dia, "DIA");
        jtPane.addTab("Dia",chartDay);

        chartWeek = new Chart(setmana, "SETMANA");
        jtPane.addTab("Setmana",chartWeek);

        chartMonth =  new Chart(mes, "MES");
        jtPane.addTab("Mes",chartMonth);


        this.add(jtPane, BorderLayout.CENTER);

        //this.add(new JLabel("Aqui trobem estadístiques diaries, setmanals i mensuals sobre " +
        //      "els matches dels usuaris"), BorderLayout.SOUTH);
    }

    public void update(int[] day,int [] week, int[] month ){
        chartDay.update(day, "DIA");
        chartMonth.update(week, "SETMANA");
        chartWeek.update(month, "MES");
    }

}


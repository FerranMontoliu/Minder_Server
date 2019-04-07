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

        //Crear i afegir estadistiques del dia
        chartDay = new Chart(dia, "DIA");
        jtPane.addTab("Dia",chartDay);

        //Crear i afegir estadistiques de la setmana
        chartWeek = new Chart(setmana, "SETMANA");
        jtPane.addTab("Setmana",chartWeek);

        //Crear i afegir estadistiques del mes
        chartMonth =  new Chart(mes, "MES");
        jtPane.addTab("Mes",chartMonth);


        this.add(jtPane, BorderLayout.CENTER);

        //this.add(new JLabel("Aqui trobem estadístiques diaries, setmanals i mensuals sobre " +
        //      "els matches dels usuaris"), BorderLayout.SOUTH);
    }

    /**
     * Funcio per a actualitzar la informacio de les gràfiques
     * @param day
     * @param week
     * @param month
     */
    public void update(int[] day,int [] week, int[] month ){
        chartDay.update(day, "DIA");
        chartMonth.update(week, "SETMANA");
        chartWeek.update(month, "MES");
    }

}


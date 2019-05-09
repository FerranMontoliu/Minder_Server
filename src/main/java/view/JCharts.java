package view;

import javax.swing.*;
import java.awt.*;

/**
 * Classe que contrueix el panell de les gràfiques DIA/SETMANA/MES
 * Dia array amb les dades de les ultimes 24 hores
 * Setmana array amb les dades dels ultims 7 dies
 * Mes array amb les dades dels ultims 30 dies
 */
public class JCharts extends JPanel {

    private Chart chartDay;
    private Chart chartWeek;
    private Chart chartMonth;

    /**
     * Constructor del panell d'estadistiques
     * @param dia array de matches de l'ultim dia
     * @param setmana array de matches de l'ultima setmana
     * @param mes array de matches de l'ultim mes
     */
    public JCharts(int[]dia, int[]setmana, int []mes){
        JTabbedPane jtPane = new JTabbedPane();

        //Crear i afegir estadistiques del dia
        chartDay = new Chart(dia);
        jtPane.addTab("Today",chartDay);

        //Crear i afegir estadistiques de la setmana
        chartWeek = new Chart(setmana);
        jtPane.addTab("This week",chartWeek);

        //Crear i afegir estadistiques del mes
        chartMonth =  new Chart(mes);
        jtPane.addTab("This Month",chartMonth);


        this.add(jtPane, BorderLayout.CENTER);

        //this.add(new JLabel("Aqui trobem estadístiques diaries, setmanals i mensuals sobre " +
        //      "els matches dels usuaris"), BorderLayout.SOUTH);
    }

    /**
     * Funcio per a actualitzar la informacio de les gràfiques
     * @param day array de matches de l'ultim dia
     * @param week array de matches de l'ultima setmana
     * @param month array de matches de l'ultim mes
     */
    public void update(int[] day,int [] week, int[] month ){
        chartDay.update(day, "Day");
        chartMonth.update(week, "Week");
        chartWeek.update(month, "Month");
    }

}


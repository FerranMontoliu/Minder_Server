package view;
import model.entity.User;

import javax.swing.*;

public class Window extends JFrame{

    private JCharts jCharts;

    public Window(int[] day, int[] week, int[] month, User[]users){

        //Iniciar la finestra
        setTitle("Server");
        setSize(800, 600);
        setResizable(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //indica que fara quan s'apreti la X al tancar. Si no es posa, estanca la finestra pero no es para el programa
        setLocationRelativeTo(null); //indica on apareix la finestra al obrirse. NULL sera al mig

        //Crear el jtabbed pane principal
        JTabbedPane jtPane = new JTabbedPane();


        //Panell d'Acceptats/Visualitzar
        JAccepted jpAcceptats = new JAccepted(5,7);
        jtPane.addTab("Acceptats/Visualitzats",jpAcceptats );


        //Panell de Registrar
        JRegister jRegister = new JRegister();
        jtPane.addTab("Registrar",jRegister );


        //Panell d'estadístiques
        jCharts = new JCharts(day, week, month);
        jtPane.addTab("Estadistiques",jCharts );

        //Panell Top 5
        JTop jTop = new JTop(users);
        jtPane.addTab("Top 5",jTop);
        //Afegir al panell principal el JTabbedPane
        getContentPane().add(jtPane);

        //Actualitzar els atributs


    }

    public void update(){
        int[] week = {2,2,4,5,7,8,9,6,3,2,4,8,9,7,5,4,2,2,9,6,5,9,0,1,2};
        int[] month = {3,22,2,4,5,7,5};
        int[] day = {2,2,4,5,7,8,9,6,3,2,4,8,9,7,5,4,2,2,1,5,3,9,8,6,12,3,4,56,7,8,1,12};
        jCharts.update(day, week, month);


    }


}

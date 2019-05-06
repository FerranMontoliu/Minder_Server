package view;
import controller.Controller;
import model.entity.User;

import javax.swing.*;
import java.util.ArrayList;

public class WindowServer extends JFrame{

    private JCharts jCharts;
    private JRegister jRegister;
    private JTop jTop;

    public WindowServer(int[] day, int[] week, int[] month, ArrayList<User> users){

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
        jtPane.addTab("Accepted/Viewed",jpAcceptats );

        //Panell de Registrar
        jRegister = new JRegister();
        jtPane.addTab("Sign Up",jRegister );

        //Panell d'estadístiques
        jCharts = new JCharts(day, week, month);
        jtPane.addTab("Statistics",jCharts );

        //Panell Top 5
        jTop = new JTop(users);
        jtPane.addTab("Top 5",jTop);

        //Afegir al panell principal el JTabbedPane
        getContentPane().add(jtPane);

        //Actualitzar els atributs


    }



    public void updateWindow(int[]week, int[]month, int[]day, ArrayList<User> top5){
        jCharts.update(day, week, month);
        jTop.updateTop5(top5);

    }


    public JRegister getRegister(){
        return jRegister;
    }

    public void registerController(Controller c) {
        jRegister.registerController(c.getCr());
    }
}

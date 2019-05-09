package view;
import controller.Controller;
import model.entity.User;

import javax.swing.*;
import java.util.ArrayList;

public class WindowServer extends JFrame{

    private JCharts jCharts;
    private JRegister jRegister;
    private JTop jTop;

    /**
     * Constructor de la finestra
     * @param week array de matches de l'ultima setmana
     * @param month array de matches de l'ultim mes
     * @param day array de matches de l'ultim dia
     * @param users arraylist d'usuaris ordenats per numero de matches
     */
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


    }


    /**
     * Funcio que actualitza la finestra del servidor
     * @param week array de matches de l'ultima setmana
     * @param month array de matches de l'ultim mes
     * @param day array de matches de l'ultim dia
     * @param top5 arraylist d'usuaris ordenats per numero de matches
     */
    public void updateWindow(int[]week, int[]month, int[]day, ArrayList<User> top5){
        jCharts.updateData(day, week, month);
        jTop.updateTop5(top5);

    }

    /**
     * Getter el panell de registre
     * @return retorna el JRegister
     */
    public JRegister getRegister(){
        return jRegister;
    }

    /**
     * Funcio que enregistra el controlador a la vista
     * @param c controlador
     */
    public void registerController(Controller c) {
        jRegister.registerController(c.getCr());
    }
}

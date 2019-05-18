package view;
import controller.Controller;
import model.entity.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Classe que implementa la finestra del servidor, la qual extén de JFrame
 */
public class WindowServer extends JFrame{

    private JCharts jCharts;
    private JRegister jRegister;
    private JTop jTop;
    private JAccepted jAcceptats;
    private BarChart jBarChart;

    /**
     * Constructor de la finestra
     */
    public WindowServer(){
        //Parametres randoms per crear l'estructura que no s'arribaran a mostrar.
        //tot just despres al tenir la bbdd es carregaran els valors reals.
        User u = new User("name", 18, true, "test@example.com", "Password1", 20, 21);
        int[] day = {2,2,4,5,7,8,9,6,3,2,4,8,9,7,5,4,2,2,9,6,5,9,0};
        int[] week = {3,22,2,4,5,7,5};
        int[] month = {2,2,4,5,7,8,9,6,3,2,4,8,9,7,5,4,2,2,1,5,3,9,8,6,12,3,4,7,8,1};
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            users.add(u);
        }

        //Iniciar la finestra
        setTitle("Server");
        setSize(800, 600);
        setResizable(true);
        setMinimumSize(new Dimension(550, 600));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //indica que fara quan s'apreti la X al tancar. Si no es posa, estanca la finestra pero no es para el programa
        setLocationRelativeTo(null); //indica on apareix la finestra al obrirse. NULL sera al mig

        //Crear el jtabbed pane principal
        JTabbedPane jtPane = new JTabbedPane();

        //Panell d'Acceptats/Visualitzar
         jAcceptats = new JAccepted(5,7);
        jtPane.addTab("Accepted/Viewed", jAcceptats);

        //Panell de Registrar
        jRegister = new JRegister();
        jtPane.addTab("Sign Up", jRegister);

        //Panell d'estadístiques
        jCharts = new JCharts(day, week, month);
        jtPane.addTab("Statistics", jCharts);

        //Panell Top 5
        jTop = new JTop(users);
        jtPane.addTab("Top 5", jTop);

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

    /**
     * Metode que actualitza les barres.
     *
     * @param numberOfViews Nombre d'usuaris visulitzats.
     * @param numberOfLikes Nombre d'usuaris acceptats.
     */
    public void updateBars(int numberOfViews, int numberOfLikes) {
       jAcceptats.redrawGraphs(numberOfViews, numberOfLikes);
       jAcceptats.repaint();
    }
}

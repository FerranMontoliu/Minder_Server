package controller;

import model.database.dao.MatchDAO;
import model.entity.User;
import view.JRegister;
import view.WindowServer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Classe que implementa el controlador general de la vista del servidor
 */
public class Controller implements ActionListener {

    private WindowServer view;
    private ControllerRegister cr;

    /**
     * Constructor del controlador
     * @param view Vista del servidor
     */
    public Controller(WindowServer view){
        this.view = view;
        cr = new ControllerRegister(view);
    }

    /**
     * Getter del controlador de la vista.
     * @return Retorna el controlador.
     */
    public ControllerRegister getCr() {
        return cr;
    }

    /**
     * Metode que regula l'acció quan l'usuari interacciona amb la vista
     * @param e Event que fa trigger.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO: AIXÒ ESTÀ BUIT AMICS!!
    }

    /**
     * Metode que actualitza les grafiques de dia-setmana-mes.
     */
    public void updateWindow() {
        MatchDAO ma = new MatchDAO();
        ArrayList<User> u = ma.getTopFiveMostMatchedUsers();
        view.updateWindow(ma.getLastMonthMatches(), ma.getLastWeekMatches(), ma.getLastDayMatches(), u);
    }
}

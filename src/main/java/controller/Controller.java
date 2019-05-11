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
    private JRegister jRegister;
    private ControllerRegister cr;

    /**
     * Mètode
     * @param view: Vista
     * @param jRegister: Panell per fer signup
     */
    public Controller(WindowServer view, JRegister jRegister){
        this.view = view;
        cr = new ControllerRegister(view);
        this.jRegister = jRegister;
    }

    /**
     * Mètode que retorna el Controlador
     * @return
     */
    public ControllerRegister getCr() {
        return cr;
    }

    /**
     * Mètode que regula l'acció quan l'usuari interacciona amb la vista
     * @param e: Event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
    }

    /**
     * Mètode que actualitza les gràfiques de dia-setmana-mes
     */
    public void updateWindow() {

        MatchDAO ma = new MatchDAO();

        ArrayList<User> u = ma.getTopFiveMostMatchedUsers();
        view.updateWindow(ma.getLastMonthMatches(),ma.getLastWeekMatches(), ma.getLastDayMatches(),u );

    }
}

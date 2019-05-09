package controller;

import model.database.dao.MatchDAO;
import model.entity.User;
import view.JRegister;
import view.WindowServer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Controller implements ActionListener {

    private WindowServer view;
    private JRegister jRegister;
    private ControllerRegister cr;


    public Controller(WindowServer view, JRegister jRegister){
        this.view = view;
        cr = new ControllerRegister(view);
        this.jRegister = jRegister;
    }


    public ControllerRegister getCr() {
        return cr;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean correct;

    }

    public void updateWindow() {

        MatchDAO ma = new MatchDAO();

        ArrayList<User> u = ma.getTopFiveMostMatchedUsers();
        view.updateWindow(ma.getLastMonthMatches(),ma.getLastWeekMatches(), ma.getLastDayMatches(),u );

    }
}

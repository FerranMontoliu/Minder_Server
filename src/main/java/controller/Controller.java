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
        int[] week = {2, 2, 4, 5, 7, 8, 9, 6, 3, 2, 4, 8, 9, 7, 5, 4, 2, 2, 9, 6, 5, 9, 0, 1, 2};
        int[] month = {3, 22, 2, 4, 5, 7, 5};
        int[] day = {2, 2, 4, 5, 7, 8, 9, 6, 3, 2, 4, 8, 9, 7, 5, 4, 2, 2, 1, 5, 3, 9, 8, 6, 12, 3, 4, 56, 7, 8, 1, 12};

        MatchDAO ma = new MatchDAO();

        ArrayList<User> u = ma.getTopFiveMostMatchedUsers();
        System.out.println(u.get(0).getUsername());
        view.updateWindow(week, month, day,u );

    }
}

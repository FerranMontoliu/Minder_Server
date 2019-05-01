package controller;

import view.JRegister;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {

    private Window view;
    private JRegister jRegister;
    private ControllerRegister cr;


    public Controller(Window view, JRegister jRegister){
        this.view = view;
        cr = new ControllerRegister((view.Window) view);
        this.jRegister = jRegister;

    }


    public ControllerRegister getCr() {
        return cr;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean correct;

    }
}

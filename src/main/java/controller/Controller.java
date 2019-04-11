package controller;

import view.JRegister;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {

    private Window view;
    private ControllerRegister cr;


    public Controller(Window view){
        this.view = view;
        cr = new ControllerRegister((view.Window) view);

    }


    public ControllerRegister getCr() {
        return cr;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

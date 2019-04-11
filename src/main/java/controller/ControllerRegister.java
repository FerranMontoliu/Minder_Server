package controller;

import view.JRegister;
import view.Window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerRegister implements ActionListener {
    private JRegister jRegister;


    public ControllerRegister (Window view){
        jRegister = view.getRegister();

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command){
            case "REGISTER":
                jRegister.removeRegister();
        }

    }
}

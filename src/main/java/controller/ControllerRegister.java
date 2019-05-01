package controller;

import view.JRegister;
import view.Window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerRegister implements ActionListener {
    private JRegister jRegister;
    private UserManager userManager;



    public ControllerRegister (Window view){
        userManager = new UserManager();
        jRegister = view.getRegister();

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        boolean correct = true;

        switch (command) {
            case "REGISTER":
                System.out.println("Register button");
                String a = jRegister.getAge();
                if(a.length() > 0) {
                    if (Integer.valueOf(a) < 18) { //Comprovació EDAT
                        correct = false;
                        System.out.println("TOO YOUNG");
                    }
                }else {
                    correct = false;
                }

                //Comprovació Mail
                correct = userManager.mailCorrectFormat(jRegister.getMail());

                //Comprovació Username

                if(jRegister.getUsername().length() == 0) {
                    correct = false;
                    System.out.println("USERNAME Invalid");
                }
                if(!(userManager.passwordCorrectFormat(jRegister.getPassword()))) {
                    System.out.println("Password is wrong");
                }

                if(correct) {

                }
                jRegister.removeRegister();

            case "PASSWORD":
                jRegister.changeViewPassword();
        }

    }
}

package controller;
import view.JRegister;
import view.WindowServer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerRegister implements ActionListener {
    private JRegister jRegister;
    private UserManager userManager;



    public ControllerRegister (WindowServer view){
        userManager = new UserManager();
        jRegister = view.getRegister();

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        boolean correct = true;

        switch (command) {
            case "REGISTER":
                correct = true;

                //Comprovació Username
                if(jRegister.getUsername().length() == 0) {
                    correct = false;
                }
                //Comprovació Age
                String a = jRegister.getAge();
                if(a.length() > 0 & a.matches(".*\\d.*")) {
                    if (Integer.valueOf(a) < 18) { //Comprovació EDAT
                        correct = false;
                    }
                }else {
                    correct = false;
                }

                //Comprovació Mail
                if(!(userManager.mailCorrectFormat(jRegister.getMail()))) {
                    correct = false;
                }
                //Comprovació Password
                if(!(userManager.passwordCorrectFormat(jRegister.getPassword()))) {
                    correct = false;
                }
                if(!(userManager.passwordConfirm(jRegister.getPassword(),jRegister.getConfirmPassword()))) {
                    correct = false;
                }

                //SignUp correcte, es pot passar a la base de dades per afegir-se
                if(correct) {
                    throwCorrectMessage();
                }
                else {
                    throwErrorMessage();
                }
                jRegister.removeRegister();

            case "PASSWORD":
                jRegister.changeViewPassword();
        }

    }
    public void throwErrorMessage() {
        jRegister.showMessage(false);
    }
    public void throwCorrectMessage() {
        jRegister.showMessage(true);
    }
}

package controller;
import model.UserChecker;
import model.database.dao.UserDAO;
import model.entity.User;
import view.JRegister;
import view.WindowServer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe que implementa el controlador de la vista del Servidor
 */
public class ControllerRegister implements ActionListener {
    private JRegister jRegister;
    private UserChecker userManager;
    private boolean correct;


    /**
     * Metode que crea el controlador
     * @param view : Vista del servidor
     */
    public ControllerRegister (WindowServer view) {
        userManager = new UserChecker();
        jRegister = view.getRegister();

    }

    /**
     * Metode usat quan l'usuari prem el botó de registrar-se des del server
     * @param e: Event quan es produeix una acció
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String command = e.getActionCommand();

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
                    if (Integer.valueOf(a) < 18) {
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
                if(!(userManager.checkAgeFilters(jRegister.getMinimumAge(),jRegister.getMaximumAge()))) {
                    correct = false;
                }
                //SignUp correcte, es pot passar a la base de dades per afegir-se
                if(correct) {
                    throwCorrectMessage();
                    jRegister.isPremium();
                    User u = new User(jRegister.getUsername(),Integer.valueOf(jRegister.getAge()),jRegister.isPremium(), jRegister.getMail(), encoder.encode(jRegister.getPassword()), jRegister.getMinimumAge(), jRegister.getMaximumAge());
                    UserDAO userDAO = new UserDAO();
                    userDAO.addUser(u);
                }
                else {
                    throwErrorMessage();
                }
                jRegister.removeRegister();

            case "PASSWORD": //Mostrar / No Mostrar Password
                jRegister.changeViewPassword();
        }

    }

    /**
     * Mètode que crida una funció de la vista per mostrar missatge d'error en el signup
     */
    private void throwErrorMessage() {
        jRegister.showMessage(false);
    }

    /**
     * Mètode que crida una funció de la vista per mostrar el missatge de signup correcte
     */
    private void throwCorrectMessage() {
        jRegister.showMessage(true);
    }
}
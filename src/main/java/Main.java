import model.User;
import network.Server;

import javax.swing.*;
import java.util.ArrayList;

public class Main {

    /**
     * Mètode principal del programa
     *
     * @param args Paràmetres d'entrada del programa.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ArrayList<User> model = new ArrayList<>();
            Server server = new Server(model);

            server.startServer();
        });
        //User u = new User("name", 18, true, "test@example.com", "Password1", "Password1");
        //String s = u.imageToBase64();
        //u.base64ToImage(s);
    }
}


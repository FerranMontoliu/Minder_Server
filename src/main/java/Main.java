import controller.Controller;
import model.entity.User;
import network.Server;
import view.WindowServer;

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

            WindowServer view = new WindowServer();
            Controller controller = new Controller(view);
            view.registerController(controller);
            controller.updateWindow();
            controller.updateBars();
            view.setVisible(true);

            ArrayList<User> model = new ArrayList<>();
            Server server = new Server(model, controller);
            server.startServer();
        });
    }
}

import controller.Controller;
import model.database.dao.LikeDAO;
import model.database.dao.MatchDAO;
import model.database.dao.UserDAO;
import model.database.dao.ViewDAO;
import model.entity.User;
import model.entity.UserMatches;
import network.Server;
import view.Window;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;

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

            //Tests de l'Anna:
            User u = new User("name", 18, true, "test@example.com", "Password1", 20, 21);
            int[] day = {2,2,4,5,7,8,9,6,3,2,4,8,9,7,5,4,2,2,9,6,5,9,0,1};
            int[] week = {3,22,2,4,5,7,5};
            int[] month = {2,2,4,5,7,8,9,6,3,2,4,8,9,7,5,4,2,2,1,5,3,9,8,6,12,3,4,7,8,1};
            User[] users = new User[5];
            for (int i = 0; i<5; i++){
                users[i] = u;
            }
            //Aquí acaben els tests de l'Anna.

            /*//Tests del Ferran:
            User test = new User(true, "Pol Espurnes", "19", false, "polsuk@gmail.com", "hola", "60","90", "penis", "something", true, true, "frozen", null, null, null, null, null);
            test.imageToBase64("data/image.jpg");
            System.out.println("Penis: " + test.getPhoto());
            UserDAO ud = new UserDAO();
            ud.updateInfoUser(test);
            //Fi.*/


            Window view = new Window(day, week, month, users);
            Controller controller = new Controller(view,view.getRegister());
            view.registerController(controller);
            view.setVisible(true);

            /*
            MatchDAO ma = new MatchDAO();
            LinkedList<UserMatches> us = ma.getTopFiveMostMatchedUsers();
            System.out.println(us.get(1).getName());
            System.out.println(us.get(2).getName());
            System.out.println(us.get(3).getName());
             */

        });
    }
}

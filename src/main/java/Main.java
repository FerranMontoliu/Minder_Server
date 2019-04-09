import model.database.dao.LikeDAO;
import model.database.dao.MatchDAO;
import model.database.dao.UserDAO;
import model.database.dao.ViewDAO;
import model.entity.User;
import network.Server;
import view.Window;

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

            //Brossa de l'Anna:
            User u = new User("name", "18", true, "test@example.com", "Password1");
            int[] day = {2,2,4,5,7,8,9,6,3,2,4,8,9,7,5,4,2,2,9,6,5,9,0,1};
            int[] week = {3,22,2,4,5,7,5};
            int[] month = {2,2,4,5,7,8,9,6,3,2,4,8,9,7,5,4,2,2,1,5,3,9,8,6,12,3,4,7,8,1};
            User[] users = new User[5];
            for (int i = 0; i<5; i++){
                users[i] = u;
            }
            //Aquí acaba la brossa de l'Anna.


            //Brossa del Ferran:
            Window view = new Window(day, week, month, users);
            view.setVisible(true);

            UserDAO pene = new UserDAO();
            pene.addUser(u);
            User us = new User(true, "name", "18", true, "test@example.com", "Password1", null, "Mencanta menjar polles enormes", true, false, "`", null, null, null, null, null);
            pene.updateInfoUser(us);
            pene.existsUser(us);

            MatchDAO nepe = new MatchDAO();
            nepe.addMatch(us, us);
            nepe.getUserMatches(us);
            nepe.deleteMatch(us, us);

            LikeDAO membre = new LikeDAO();
            membre.addLike(us, us);
            membre.getUserLikes(us);
            membre.getUserLikesMe(us);

            ViewDAO test = new ViewDAO();
            test.addViewed(us, us);
            //Aquí acaba la brossa del Ferran.
        });
    }
}


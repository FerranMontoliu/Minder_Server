package model.database.dao;

import model.database.DBConnector;
import model.entity.User;

import java.sql.ResultSet;

public class LikeDAO {
    public void getUserLikes(User u) {
        String query = "SELECT * FROM liked WHERE username_1 = '" + u.getUsername() + "';";
        ResultSet res = DBConnector.getInstance().selectQuery(query);
        System.out.println(res);
        //TODO: fer que retorni una llista d'usuaris.
    }

    public void getUserLikesMe(User u) {
        String query = "SELECT * FROM liked WHERE username_2 = '" + u.getUsername() + "';";
        ResultSet res = DBConnector.getInstance().selectQuery(query);
        System.out.println(res);
        //TODO: fer que retorni una llista d'usuaris.
    }

    /**
     * Mètode encarregat de registrar un nou like a un perfil a la base de dades.
     *
     * @param u1 Usuari que ha donat el like.
     * @param u2 Usuari al qual s'ha donat el like.
     */
    public void addLike(User u1, User u2) {
        String query = "INSERT INTO liked (username_1, username_2) VALUES ('" + u1.getUsername() + "', '" + u2.getUsername() + "')";
        DBConnector.getInstance().executeQuery(query);
    }
}

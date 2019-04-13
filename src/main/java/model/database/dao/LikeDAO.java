package model.database.dao;

import model.database.DBConnector;
import model.entity.LikesLoader;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LikeDAO {
    public LikesLoader getUserLikes(String u) {
        String query = "SELECT * FROM liked WHERE username_1 = '" + u + "';";
        ResultSet res = DBConnector.getInstance().selectQuery(query);
        LikesLoader ll = new LikesLoader();
        try {
            while(res.next()) {
                String likedUser = res.getString("username_2");
                ll.addLike(likedUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ll;
    }

    public LikesLoader getUserLikesMe(String u) {
        String query = "SELECT * FROM liked WHERE username_2 = '" + u + "';";
        ResultSet res = DBConnector.getInstance().selectQuery(query);
        LikesLoader ll = new LikesLoader();
        try {
            while(res.next()) {
                String likedUser = res.getString("username_1");
                ll.addLike(likedUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ll;
    }

    /**
     * Mètode encarregat de registrar un nou like a un perfil a la base de dades.
     *
     * @param u1 Usuari que ha donat el like.
     * @param u2 Usuari al qual s'ha donat el like.
     */
    public void addLike(String u1, String u2) {
        String query = "INSERT INTO liked (username_1, username_2, liked_date) VALUES ('" + u1 + "', '" + u2 + "', '" + "2019-04-10" + "')";
        DBConnector.getInstance().executeQuery(query);
    }
}

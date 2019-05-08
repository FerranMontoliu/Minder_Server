package model.database.dao;

import model.database.DBConnector;
import model.entity.LikesLoader;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LikeDAO {
    //TODO: eliminar??
    public LikesLoader getUserLikes(String u) {
        String query = "SELECT username_2 FROM liked WHERE username_1 = '" + u + "' AND liked_bool = '1';";
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

    //TODO: eliminar??
    public LikesLoader getUserLikesMe(String u) {
        String query = "SELECT username_1 FROM liked WHERE username_2 = '" + u + "' AND liked_bool = '1';";
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
     * Metode encarregat de registrar un nou like a un perfil a la base de dades.
     *
     * @param u1 Usuari que ha donat el like.
     * @param u2 Usuari al qual s'ha donat el like.
     */
    public void addLike(String u1, String u2) {
        String query = "INSERT INTO liked (username_1, username_2, liked_date, liked_bool) VALUES ('" + u1 + "', '" + u2 + "', CURDATE(), '1')";
        DBConnector.getInstance().executeQuery(query);
    }

    /**
     * Metode encarregat de registrar un nou dislike a un perfil a la base de dades.
     *
     * @param u1 Usuari que ha donat el dislike.
     * @param u2 Usuari al qual s'ha donat el dislike.
     */
    public void addDislike(String u1, String u2) {
        String query = "INSERT INTO liked (username_1, username_2, liked_date, liked_bool) VALUES ('" + u1 + "', '" + u2 + "', CURDATE(), '0')";
        DBConnector.getInstance().executeQuery(query);
    }

    /**
     * Funcio encarregada de comptar quants likes hi ha a la base de dades.
     *
     * @return Nombre de likes de la base de dades.
     */
    public int getNumberOfLikes() {
        String query = "SELECT COUNT(*) FROM liked WHERE liked_bool = '1'";
        ResultSet res = DBConnector.getInstance().selectQuery(query);
        int i = 0;
        try {
            res.next();
            i = res.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * Funcio encarregada de comptar quants likes hi ha a la base de dades.
     *
     * @return Nombre de visualitzacions de la base de dades.
     */
    public int getNumberOfViews() {
        String query = "SELECT COUNT(*) FROM liked";
        ResultSet res = DBConnector.getInstance().selectQuery(query);
        int i = 0;
        try {
            res.next();
            i = res.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }
}

package model.database.dao;

import model.database.DBConnector;
import model.entity.ViewLoader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ViewDAO {

    /**
     * Mètode encarregat de registrar una nova visualització d'un perfil a la base de dades.
     *
     * @param u1 Usuari que ha vist el perfil.
     * @param u2 Usuari del qual s'ha vist el perfil.
     */
    public void addViewed(String u1, String u2) {
        String query = "INSERT INTO views (username_1, username_2, view_date) VALUES ('" + u1 + "', '" + u2 + "', '" + "2019-04-10" + "')";
        DBConnector.getInstance().executeQuery(query);
    }

    public ViewLoader getViewed(String u) {
        String query = "SELECT * FROM views WHERE username_1 = '" + u + "';";
        ResultSet res = DBConnector.getInstance().selectQuery(query);
        ArrayList<String> likedUsers = new ArrayList<>();
        try {
            while(res.next()) {
                String likedUser = res.getString("username_2");
                likedUsers.add(likedUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ViewLoader vl = new ViewLoader(likedUsers);
        return vl;
    }
}

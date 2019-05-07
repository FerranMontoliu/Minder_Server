package model.database.dao;

import model.database.DBConnector;
import model.entity.ViewLoader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ViewDAO {

    public ViewLoader getViewed(String u) {
        String query = "SELECT username_2 FROM liked WHERE username_1 = '" + u + "';";
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

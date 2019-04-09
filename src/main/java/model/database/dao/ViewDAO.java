package model.database.dao;

import model.database.DBConnector;
import model.entity.User;

public class ViewDAO {
    public void addViewed(User u1, User u2) {
        String query = "INSERT INTO views (username_1, username_2, view_date) VALUES ('" + u1.getUsername() + "', '" + u2.getUsername() + "', '" + "2019-04-10" + "')";
        DBConnector.getInstance().executeQuery(query);
    }
}

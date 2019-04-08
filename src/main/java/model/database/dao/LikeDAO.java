package model.database.dao;

import model.database.DBConnector;
import model.entity.User;

public class LikeDAO {
    public void getUserLikes(User u) {
        String query = "SELECT * FROM liked WHERE username_1 = " + u.getUsername() + ";";
        DBConnector.getInstance().insertQuery(query);
    }

    public void getUserLikesMe(User u) {
        String query = "SELECT * FROM liked WHERE username_2 = " + u.getUsername() + ";";
        DBConnector.getInstance().insertQuery(query);
    }

    public void addLike(User u1, User u2) {
        String query = "INSERT INTO liked (username_1, username_2) VALUES ('" + u1.getUsername() + "', '" + u2.getUsername() + "')";
        DBConnector.getInstance().insertQuery(query);
    }
}

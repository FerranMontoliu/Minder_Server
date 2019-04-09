package model.database.dao;

import model.database.DBConnector;
import model.entity.User;

import java.sql.ResultSet;

public class MatchDAO {
    public void getUserMatches(User u) {
        String query = "SELECT * FROM matchs WHERE username_1 = '" + u.getUsername() + "' OR username_2 = '" + u.getUsername() + "';";
        ResultSet res = DBConnector.getInstance().selectQuery(query);
        System.out.println(res);
    }

    public void addMatch(User u1, User u2) {
        String query = "INSERT INTO matchs (username_1, username_2, match_date) VALUES ('" + u1.getUsername() + "', '" + u2.getUsername() + "', '" + "2019-04-10" + "')";
        DBConnector.getInstance().executeQuery(query);
    }

    public void deleteMatch(User u1, User u2) {
        String query = "DELETE FROM matchs WHERE (username_1 = '" + u1.getUsername() + "' AND username_2 = '" + u2.getUsername() + "') OR (username_1 = '" + u2.getUsername() + "' AND username_2 = '" + u1.getUsername() + "');";
        DBConnector.getInstance().executeQuery(query);
    }
}

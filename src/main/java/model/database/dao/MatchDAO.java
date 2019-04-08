package model.database.dao;

import model.database.DBConnector;
import model.entity.User;

public class MatchDAO {
    public void getUserMatches(User u) {
        String query = "SELECT * FROM matchs WHERE username_1 = " + u.getUsername() + " OR " + "username_2 = " + u.getUsername() + ";";
        DBConnector.getInstance().insertQuery(query);
    }

    public void addMatch(User u1, User u2) {
        String query = "INSERT INTO matchs (username_1, username_2, match_date) VALUES ('" + u1.getUsername() + "', '" + u2.getUsername() + "', '" + "data aqui" + "')";
        DBConnector.getInstance().insertQuery(query);
    }

    public void deleteMatch(User u1, User u2) {
        String query = "DELETE FROM matchs WHERE username_1 = " + u1.getUsername() + ", username_2 = " + u2.getUsername() + " OR " + "username_1 = " + u2.getUsername() + ", username_2 = " + u1.getUsername() + ";";
        DBConnector.getInstance().insertQuery(query);
    }
}

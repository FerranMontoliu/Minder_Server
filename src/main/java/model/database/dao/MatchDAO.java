package model.database.dao;

import model.database.DBConnector;
import model.entity.MatchLoader;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MatchDAO {

    public MatchLoader getUserMatches(String u) {
        String query = "SELECT * FROM matchs WHERE username_1 = '" + u + "';";
        ResultSet res = DBConnector.getInstance().selectQuery(query);
        MatchLoader ml = new MatchLoader();
        try {
            while(res.next()) {
                String matchedUser = res.getString("username_2");
                ml.addMatch(matchedUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        query = "SELECT * FROM matchs WHERE username_2 = '" + u + "';";
        res = DBConnector.getInstance().selectQuery(query);
        try {
            while(res.next()) {
                String matchedUser = res.getString("username_1");
                ml.addMatch(matchedUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ml;
    }

    /**
     * Metode encarregat d'afegir un like a la base de dades.
     *
     * @param u1 Usuari que conforma el match.
     * @param u2 Usuari que conforma el match.
     */
    public void addMatch(String u1, String u2) {
        String query = "INSERT INTO matchs (username_1, username_2, match_date) VALUES ('" + u1 + "', '" + u2 + "', '" + "2019-04-10" + "')";
        DBConnector.getInstance().executeQuery(query);
    }

    /**
     * Metode encarregat d'eliminar un match de la base de dades.
     *
     * @param u1 Usuari que conforma el match.
     * @param u2 Usuari que conforma el match.
     */
    public void deleteMatch(String u1, String u2) {
        String query = "DELETE FROM matchs WHERE (username_1 = '" + u1 + "' AND username_2 = '" + u2 + "') OR (username_1 = '" + u2 + "' AND username_2 = '" + u1 + "');";
        DBConnector.getInstance().executeQuery(query);
    }
}

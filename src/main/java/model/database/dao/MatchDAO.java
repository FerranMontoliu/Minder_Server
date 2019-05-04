package model.database.dao;

import model.database.DBConnector;
import model.entity.MatchLoader;
import model.entity.UserMatches;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

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
        String format = "MM-dd-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String date = simpleDateFormat.format(new Date());
        String query = "INSERT INTO matchs (username_1, username_2, match_date, viewed) VALUES ('" + u1 + "', '" + u2 + "', '" + date + "', '0')";
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

    /**
     * Funcio que retorna una llista amb els usuaris de la bbdd ordenats per nombre de matches
     * @return
     */
    public LinkedList<UserMatches> getTopFiveMostMatchedUsers() {
        //TODO: retornar els 5 users amb més matches (ASUMINT QUE EXISTEIXEN 5 USERS MÍNIM!).

        LinkedList<UserMatches> users = new LinkedList<>();
        String query = "SELECT * FROM users";
        ResultSet res = DBConnector.getInstance().selectQuery(query);
        ResultSet res1;
        int matches;

        try {
            while (res.next()) {
                String name = res.getString("username");

                query = "SELECT COUNT(*) FROM matchs WHERE username_1 = '" + name + "'";
                res1 = DBConnector.getInstance().selectQuery(query);

                res1.next();
                matches = res1.getInt(1);

                query = "SELECT COUNT(*) FROM matchs WHERE username_2 = '" + name + "'";


                res1 = DBConnector.getInstance().selectQuery(query);

                res1.next();
                matches+= res1.getInt(1);

                users.add(new UserMatches(name, matches));

            }

            users.add(new UserMatches("Anna", 7));

            int n = users.size();

            //No ordena bé
            for(int i = 0 ; i< n; i++){
                for (int j = 0; j< n-1-i; j++){
                    if(users.get(j).getMatches() > users.get(j+1).getMatches()){
                        UserMatches u = users.get(j);
                        users.remove(j);
                        users.add(j, users.get(j+1));
                        users.remove(j+1);
                        users.add(j+1, u);

                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public int getNumberOfMatches() {
        //TODO: SELECT COUNT bla bla
        return 0;
    }

    public int getUserNumberOfMatches(String username) {
        //TODO: SELECT COUNT bla bla
        return 0;
    }

    /**
     * IN PROGRESS! NO TOCAR
     * @param actualDate
     * @return
     */
    public int[] getLastDayMatches(String actualDate) {
        //TODO: SELECT COUNT bla bla
        return null;
    }

    public int[] getLastWeekMatches(String actualDate) {
        //TODO: SELECT COUNT bla bla
        return null;
    }

    public int[] getLastMonthMatches(String actualDate) {
        //TODO: SELECT COUNT bla bla
        return null;
    }

    public void viewedMatch(String username1, String username2) {
        //TODO: SET bla bla bla
    }
}

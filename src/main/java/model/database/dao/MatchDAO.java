package model.database.dao;

import model.database.DBConnector;
import model.entity.MatchLoader;
import model.entity.User;
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
    public LinkedList<User> getTopFiveMostMatchedUsers() {
        //TODO: retornar els 5 users amb més matches (ASUMINT QUE EXISTEIXEN 5 USERS MÍNIM!).

        LinkedList<User> users = new LinkedList<>();
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

                users.add(new User(name, matches));

            }

            users.add(new User("Anna", 7));

            int n = users.size();

            //No ordena bé
            for(int i = 0 ; i< n; i++){
                for (int j = 0; j< n-1-i; j++){
                    if(users.get(j).getMatches() > users.get(j+1).getMatches()){
                        User u = users.get(j);
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
     * Metode que retorna (modificar tipus de variable de retorn al gust) els noms dels usuaris que han fet match durant
     * el dia que s'executa aquesta query
     * @return
     */
    public int[] getLastDayMatches() throws SQLException {
        String username1, username2;
        String query = "SELECT username_1,username_2 " +
                "FROM matchs " +
                "WHERE DATE(match_date) = DATE(CURRENT_DATE)";
        ResultSet res = DBConnector.getInstance().selectQuery(query);
        while (res.next()) {
            username1 = res.getString("username_1");
            username2 = res.getString("username_2");
            System.out.println("Match between " + username1 + " and " + username2 + " today!");
        }
        return null;
    }

    /**
     * Metode que retorna (modificar tipus de variable de retorn al gust) els noms dels usuaris i data que han fet
     * match durant la setmana que s'executa aquesta query
     * @return
     */
    public int[] getLastWeekMatches() throws SQLException {
        //TODO fer proves depenent del dia al que estem i preguntar si ha de ser des de dilluns fins dia actual o 7 dies
        String username1, username2;
        String query = "SELECT * " +
                "FROM matchs " +
                "WHERE match_date " +
                "BETWEEN date_sub(now(),INTERVAL 1 WEEK) " +
                "AND now()";
        //O SINO: "SELECT * FROM matchs WHERE date_format(match_date, '%Y-%m-%d') = date_format(now(), '%Y-%m-%d')
        ResultSet res = DBConnector.getInstance().selectQuery(query);
        while (res.next()) {
            username1 = res.getString("username_1");
            username2 = res.getString("username_2");
            Date d = res.getDate("match_date");
            System.out.println("Match between " + username1 + " and " + username2 + " this week! (day: " + d +")");
        }

        return null;
    }

    /**
     * Metode que retorna (modificar tipus de variable de retorn al gust) els noms dels usuaris i data que han fet
     * match durant l'ultim mes des de que s'executa aquesta query
     * @return
     */
    public int[] getLastMonthMatches() throws SQLException {
        //TODO: preguntar si ha de ser des del dia 1 del mes actual o han de ser els ultims 30/31 dies
        String username1, username2;
        //query per ultims 30 dies
        String query ="SELECT * " +
                "FROM matchs " +
                "WHERE match_date " +
                "BETWEEN date_sub(now(),INTERVAL 1 MONTH) " +
                "AND now()";
        //query del mateix mes que l'actual
        String query2 = "SELECT * " +
                "FROM matchs " +
                "WHERE date_format(match_date, '%Y-%m') = date_format(now(), '%Y-%m')";
        ResultSet res = DBConnector.getInstance().selectQuery(query);
        while (res.next()) {
            username1 = res.getString("username_1");
            username2 = res.getString("username_2");
            Date d = res.getDate("match_date");
            System.out.println("Match between " + username1 + " and " + username2 + " this month! (day: " + d +")");
        }
        return null;
    }

    public void viewedMatch(String username1, String username2) {
        //TODO: SET bla bla bla
    }
}

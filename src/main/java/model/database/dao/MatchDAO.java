package model.database.dao;

import model.database.DBConnector;
import model.entity.MatchLoader;
import model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MatchDAO {

    /**
     * Funcio encarregada de retornar tots els matchs d'un usuari.
     *
     * @param u Nom de l'usuari del qual es volen obtenir els matchs.
     *
     * @return Retorna una llista amb els noms dels usuaris amb els qual ha fet match l'usuari que entra com a parametre.
     */
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
     * Funcio que retorna una llista amb els usuaris de la bbdd ordenats per nombre de matchs.
     *
     * @return Llista que conte els 5 users amb mes matchs de la bbdd.
     */
    public ArrayList<User> getTopFiveMostMatchedUsers() {
        //TODO: retornar els 5 users amb més matches (ASUMINT QUE EXISTEIXEN 5 USERS MÍNIM!).

        ArrayList<User> users = new ArrayList<>();
        String query = "SELECT username FROM users";
        ResultSet res = DBConnector.getInstance().selectQuery(query);
        ResultSet res1;
        int matches;

        try {
            while(res.next()) {
                String name = res.getString("username");
                matches = getUserNumberOfMatches(name);
                users.add(new User(name, matches));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                if(o1.getMatches() < o2.getMatches()){
                    return 1;
                }
                if(o1.getMatches() == o2.getMatches()){
                    return 0;
                }
                return -1;
            }
        });

        return users;
    }

    /**
     * Funcio que retorna el nombre de matchs que hi ha a la base de dades.
     *
     * @return Retorna el nombre de matchs que hi ha guardats a la base de dades.
     */
    public int getNumberOfMatches() {
        String query = "SELECT COUNT(*) FROM matchs";
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
     * Funcio que retorna el nombre de matchs d'un usuari.
     *
     * @param username Usuari del qual es vol obtenir el nombre de matchs.
     *
     * @return Retorna el nombre de matchs de l'usuari que entra com a parametre.
     */
    public int getUserNumberOfMatches(String username) {
        String query = "SELECT COUNT(*) FROM matchs WHERE username_1 = '" + username + "' OR username_2 = '" + username + "'";
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
     * Metode que retorna (modificar tipus de variable de retorn al gust) els noms dels usuaris que han fet match durant
     * el dia que s'executa aquesta query
     * @return
     */
    public int[] getLastDayMatches() throws SQLException {
        String username1, username2;
        String query = "SELECT username_1, username_2 " +
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

    /**
     * Funcio encarregada de marcar un match com a visualitzat en la base de dades.
     *
     * @param username1 User 1 que conforma el match.
     * @param username2 User 2 que conforma el match.
     */
    public void viewedMatch(String username1, String username2) {
        String query = "UPDATE matchs SET viewed = '1' " +
                "WHERE (username_1 = '" + username1 + "' AND username_2 = '" + username2 + "') " +
                "OR (username_1 = '" + username2 + "' AND username_2 = '" + username1 + "')";
        DBConnector.getInstance().executeQuery(query);
    }

    /**
     * Funcio encarregada de comprovar si un match existeix en la base de dades.
     *
     * @param receiver User 1 que conforma el xat.
     * @param sender User 2 que conforma el xat
     *
     * @return Retorna true si existeix match, false altrament.
     */
    public boolean existsMatch(String receiver, String sender) {
        String query = "SELECT COUNT(*) FROM matchs " +
                "WHERE (username_1 = '" + sender +"' AND username_2 = '" + receiver + "') " +
                "OR (username_1 = '" + receiver + "' AND username_2 = '" + sender + "')";
        ResultSet res = DBConnector.getInstance().selectQuery(query);
        int i = 0;
        try {
            res.next();
            i = res.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i > 0;
    }
}

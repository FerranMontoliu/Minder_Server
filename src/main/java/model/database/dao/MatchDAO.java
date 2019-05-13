package model.database.dao;

import model.database.DBConnector;
import model.entity.MatchLoader;
import model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
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
     * Metode que busca si a un usuari (Sender) li ha donat like anteriorment un altre usuari (receiver), i per tant saber si
     * hi ha d'haver match, en cas de que li hagi donat like anteriorment. Si no existeix cap relacio de viewed entre els dos o
     * l'anterior li ha donat dislike, no sera cap match.
     * @param receiver usuari que es mostra pel panell connect actualment
     * @param sender usuari actual que vol conectar amb altres usuaris
     * @return retorna true en cas que sigui un match i false quan no ho sigui
     */
    public boolean isMatch(String receiver, String sender){ //u2 ha de ser l'usuari associated i u1 el connect
        //Es mira si existeix alguna relacio de viewed en que el sender hagi estat vist per el receiver.
        String queryExists = "SELECT COUNT(*) FROM liked WHERE liked.username_1 = '" + receiver + "' AND liked.username_2 = '" + sender +"'";
        ResultSet resExists = DBConnector.getInstance().selectQuery(queryExists);
        String query;
        try {
            resExists.next();
            int e = resExists.getInt(1);
            //Si no hi ha hagut cap relacio, directament no hi haura match
            if (e == 0){
                return false;
            //si hi ha hagut relacio, el resultat dependra de si el receiver li va donar like o dislike al sender
            }else{
                query = "SELECT liked_bool FROM liked WHERE (username_1 = '"+ receiver +"' AND username_2 = '"+ sender +"')";
                ResultSet res = DBConnector.getInstance().selectQuery(query);

                boolean i;
                res.next();
                i = (res.getInt(1) == 1)? true:false;
                return i;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * Metode encarregat d'afegir un like a la base de dades.
     *
     * @param u1 Usuari que conforma el match.
     * @param u2 Usuari que conforma el match.
     */
    public void addMatch(String u1, String u2) {
        String query = "INSERT INTO matchs (username_1, username_2, match_date, viewed) VALUES ('" + u1 + "', '" + u2 + "', now(), '0')";
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
    public int[] getLastDayMatches() {

        int[] day = {2, 2, 4, 5, 7, 8, 9, 6, 3, 2, 4, 8, 9, 7, 5, 4, 2, 2, 9, 6, 5, 9, 0, 1};

        /*
        int[] dies = new int[24];

        for(int i = 0; i< 24; i++){
            String query = "SELECT COUNT(*) " +
                    "FROM matchs " +
                    "WHERE DAY(match_date) = DAY(DATE_ADD(NOW()) AND" +
                    "HOUR(match_date) = HOUR(DATE_ADD(NOW(), INTERVAL -" + Integer.toString( 24-i ) +" HOUR));";
            ResultSet res = DBConnector.getInstance().selectQuery(query);

            try {
                res.next();
                dies[i] = res.getInt(1);
                System.out.println(dies[i]);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

         */
        return day;
    }

    /**
     * Metode que retorna (modificar tipus de variable de retorn al gust) els noms dels usuaris i data que han fet
     * match durant la setmana que s'executa aquesta query
     * @return
     */
    public int[] getLastWeekMatches() {

        int[] dies = new int[7];

        for(int i = 0; i< 7; i++){
                String query = "SELECT COUNT(*) " +
                        "FROM matchs " +
                        "WHERE DAY(match_date) = DAY(DATE_ADD(NOW(), INTERVAL -" + Integer.toString( 6-i ) +" DAY));";
                ResultSet res = DBConnector.getInstance().selectQuery(query);

            try {
                res.next();
                dies[i] = res.getInt(1);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }


        return dies;
    }

    /**
     * Metode que retorna (modificar tipus de variable de retorn al gust) els noms dels usuaris i data que han fet
     * match durant l'ultim mes des de que s'executa aquesta query
     * @return
     */
    public int[] getLastMonthMatches() {

        int[] dies = new int[30];

        for(int i = 0; i< 30; i++){
            String query = "SELECT COUNT(*) " +
                    "FROM matchs " +
                    "WHERE DAY(match_date) = DAY(DATE_ADD(NOW(), INTERVAL -" + Integer.toString( 29-i ) +" DAY));";
            ResultSet res = DBConnector.getInstance().selectQuery(query);

            try {
                res.next();
                dies[i] = res.getInt(1);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }


        return dies;
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

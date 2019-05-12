package model.database.dao;

import model.database.DBConnector;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LikeDAO {

    /**
     * Metode encarregat de registrar un nou like a un perfil a la base de dades o be d'actualitzar la relacio de viewed
     * en cas que ja s'haguessin vist aquests dos usuaris anteriorment.
     * @param u1 Usuari que ha donat el like.
     * @param u2 Usuari al qual s'ha donat el like.
     */
    public void addLike(String u1, String u2) {
        //abans d'afegir un like mirem si ja ha vist l'usuari (li ha donat dislike anteriorment), i actualitzem el camp
        //liked_bool a '1'
        String queryExists = "SELECT COUNT(*) FROM liked WHERE liked.username_1 = '" + u1 + "' AND liked.username_2 = '" + u2 +"'";
        ResultSet resExists = DBConnector.getInstance().selectQuery(queryExists);
        String query;
        try {
            resExists.next();
            int i = resExists.getInt(1);
            if (i == 0){
                query = "INSERT INTO liked (username_1, username_2, liked_date, liked_bool) VALUES ('" + u1 + "', '" + u2 + "', now(), '1')";
            }else{
                query = "UPDATE liked SET liked_bool = '1', liked_date = now() WHERE liked.username_1 = '" + u1 + "' AND liked.username_2 = '" + u2 + "'";
            }
            DBConnector.getInstance().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Metode encarregat de registrar un nou dislike a un perfil a la base de dades o be d'actualitzar la relacio de viewed
     * en cas que ja s'haguessin vist aquests dos usuaris anteriorment.
     *
     * @param u1 Usuari que ha donat el dislike.
     * @param u2 Usuari al qual s'ha donat el dislike.
     */
    public void addDislike(String u1, String u2) {
        //abans d'afegir un dislike mirem si ja ha vist l'usuari (li ha donat dislike anteriorment), i actualitzem el camp
        //liked_bool a '0' un altre cop per a no crear una nova fila de dades entre dos usuaris existents
        String queryExists = "SELECT COUNT(*) FROM liked WHERE liked.username_1 = '" + u1 + "' AND liked.username_2 = '" + u2 +"'";
        ResultSet resExists = DBConnector.getInstance().selectQuery(queryExists);
        String query;
        try {
            resExists.next();
            int i = resExists.getInt(1);
            if (i == 0){
                query = "INSERT INTO liked (username_1, username_2, liked_date, liked_bool) VALUES ('" + u1 + "', '" + u2 + "', now(), '0')";
            }else{
                query = "UPDATE liked SET liked_bool = '0', liked_date = now() WHERE liked.username_1 = '" + u1 + "' AND liked.username_2 = '" + u2 + "'";
            }

            DBConnector.getInstance().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Funcio que comprova si ja s'ha conat dislike a un usuari anteriorment.
     *
     * @param username1 Usuari que dona el dislike.
     * @param username2 Usuari que rep el dislike.
     *
     * @return Retorna true si existeix, false altrament.
     */
    public boolean existsDislike(String username1, String username2) {
        String query = "SELECT COUNT(*) FROM liked WHERE username_1 = '" + username1 + "' AND username_2 = '" + username2 + "'";
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

    public void updateDislike(String username1, String username2) {
        String query = "UPDATE liked SET liked_date = now() WHERE username_1 = '" + username1 + "' AND username_2 = '" + username2 + "'";
        DBConnector.getInstance().executeQuery(query);
    }

    /**
     * Funcio encarregada de comptar quants likes hi ha a la base de dades.
     *
     * @return Nombre de likes de la base de dades.
     */
    public int getNumberOfLikes() {
        String query = "SELECT COUNT(*) FROM liked WHERE liked_bool = '1'";
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
     * Funcio encarregada de comptar quants likes hi ha a la base de dades.
     *
     * @return Nombre de visualitzacions de la base de dades.
     */
    public int getNumberOfViews() {
        String query = "SELECT COUNT(*) FROM liked";
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
}

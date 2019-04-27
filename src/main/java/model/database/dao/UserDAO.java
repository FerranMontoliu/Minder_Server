package model.database.dao;

import model.database.DBConnector;
import model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    /**
     * Mètode encarregat de registrar un nou usuari a la base de dades.
     *
     * @param u Usuari que es vol registrar.
     */
    public void addUser(User u) {
        int isPremium = u.isPremium()? 1: 0;
        int likesC = u.getLikesC()? 1: 0;
        int likesJava = u.getLikesJava()? 1: 0;
        String query = "INSERT INTO users(username, mail, completed, age, premium, password, photo, description, likes_java, likes_c, fav_song, hobbies, connected, minAge, maxAge) VALUES ('" + u.getUsername() + "', '" + u.getMail() + "', '0', '" + u.getAge() + "', '" + isPremium + "', '" + u.getPassword() + "', 'null', 'null', '" + likesJava + "', '" + likesC + "', 'null', 'null', '0', '18', '0')";
        DBConnector.getInstance().executeQuery(query);
    }

    /**
     * Mètode encarregat d'actualitzar la informació d'un usuari.
     *
     * @param u Usuari del qual es vol actualitzar la informació.
     */
    public void updateInfoUser(User u) {
        int likesC = u.getLikesC()? 1: 0;
        int likesJava = u.getLikesJava()? 1: 0;
        String query = "UPDATE users SET photo = '" + u.getPhoto() + "', description = '" + u.getDescription() + "', likes_java = '" + likesJava + "', likes_c = '" + likesC + "', fav_song = '" + u.getFavSong() + "', hobbies = '" + "u.getHobbies()" + "' WHERE users.username = '" + u.getUsername() + "'";
        DBConnector.getInstance().executeQuery(query);
    }

    /**
     * Funció que s'encarrega de comprovar si un usuari existeix o no en la base de dades.
     *
     * @param u Usuari del qual es vol comprovar l'existència.
     * @return Retorna true si existeix, false altrament.
     */
    public boolean existsUser(User u) {
        String query;
        if(u.getUsername() != null) {
            query = "SELECT COUNT(*) FROM users WHERE users.username = '" + u.getUsername() + "'";
        } else {
            query = "SELECT COUNT(*) FROM users WHERE users.mail = '" + u.getMail() + "'";
        }
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

    /**
     * Funció que retorna l'usuari complet de la base de dades que correspon a l'usuari incomplet que se li entra com a paràmetre.
     *
     * @param u Usuari del qual es vol extreure tota la info de la base de dades.
     * @return Usuari amb tota la seva informació de la base de dades.
     */
    public User getUser(User u) {
        String query;
        if(u.getUsername() != null) {
            query = "SELECT * FROM users WHERE users.username = '" + u.getUsername() + "'";
        } else {
            query = "SELECT * FROM users WHERE users.mail = '" + u.getMail() + "'";
        }
        ResultSet res = DBConnector.getInstance().selectQuery(query);
        String username = null, mail = null, password = null, photo = null, description = null, favSong = null, hobbiesString = null, minAge = null, maxAge = null;
        boolean completed = false, premium = false, likesJava = false, likesC = false;
        int age = 0;
        String[] hobbies = null;
        try {
            while(res.next()) {
                username = res.getString("username");
                mail = res.getString("mail");
                completed = res.getInt("completed") > 0;
                age = res.getInt("age");
                premium = res.getInt("premium") > 0;
                password = res.getString("password");
                minAge = res.getString("minAge");
                maxAge = res.getString("maxAge");
                photo = res.getString("photo");
                description = res.getString("description");
                likesJava = res.getInt("likes_java") > 0;
                likesC = res.getInt("likes_c") > 0;
                favSong = res.getString("fav_song");
                hobbiesString = res.getString("hobbies");
                hobbies = hobbiesString.split(",");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        User dbUser = new User(completed, username, Integer.toString(age), premium, mail, password, minAge, maxAge, photo, description, likesJava, likesC, favSong, hobbies, null, null, null, null);
        return dbUser;
    }

    public boolean isConnected(String username) {
        boolean connected = false;
        String query = "SELECT * FROM users WHERE users.username = '" + username + "'";
        ResultSet res = DBConnector.getInstance().selectQuery(query);
        try {
            res.next();
            connected = res.getInt("connected") > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connected;
    }

    public void userConnects(String username) {
        String query = "UPDATE users SET connected = '1' WHERE users.username = '" + username + "'";
        DBConnector.getInstance().executeQuery(query);
    }

    public void userDisconnects(String username) {
        String query = "UPDATE users SET connected = '0' WHERE users.username = '" + username + "'";
        DBConnector.getInstance().executeQuery(query);
    }

    public String getNextUser(String username, String minAge, String maxAge, boolean isPremium) {
        String query, user = null;
        if(isPremium) { //TODO: falta implementar què passa si el user és premium
            //Si maxAge es 0 implica que no hi ha filtre per edat.
            if(Integer.parseInt(maxAge) == 0) {
                //Retorna els usuaris que t'han donat like a tu:
                query = "SELECT * FROM liked WHERE liked.username_2 = '" + username + "'"; //TODO: falta implementar que tampoc els hagis vist
                //Retorna els usuaris que no t'han donat like a tu:
                //TODO: potser hauria de retornar tots els users que no he vist i tots els que m'han donat like i fer una poda.
            } else {
                //TODO: el mateix però aplicant el filtre BETWEEN.
            }
        } else {
            //Si maxAge es 0 implica que no hi ha filtre per edat.
            if(Integer.parseInt(maxAge) == 0) {
                query = "SELECT * FROM users";
                ResultSet res = DBConnector.getInstance().selectQuery(query);
                try {
                    res.next();
                    user = res.getString("username");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                query = "SELECT * FROM users WHERE users.age BETWEEN '" + minAge + "' AND '" + maxAge + "'";
                ResultSet res = DBConnector.getInstance().selectQuery(query);
                try {
                    res.next();
                    user = res.getString("username");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return user;
    }
}

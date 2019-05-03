package model.database.dao;

import model.database.DBConnector;
import model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    /**
     * Metode encarregat de registrar un nou usuari a la base de dades.
     *
     * @param u Usuari que es vol registrar.
     */
    public void addUser(User u) {
        int isPremium = u.isPremium()? 1: 0;
        int likesC = u.getLikesC()? 1: 0;
        int likesJava = u.getLikesJava()? 1: 0;
        int minAge = u.getMinAge();
        int maxAge = u.getMaxAge();
        String query = "INSERT INTO users(username, mail, completed, age, premium, password, photo, description, likes_java, likes_c, fav_song, hobbies, connected, minAge, maxAge) VALUES ('" + u.getUsername() + "', '" + u.getMail() + "', '0', '" + u.getAge() + "', '" + isPremium + "', '" + u.getPassword() + "', 'null', 'null', '" + likesJava + "', '" + likesC + "', 'null', 'null', '0', '" + minAge + "', '" + maxAge +"')";
        DBConnector.getInstance().executeQuery(query);
    }

    /**
     * Metode encarregat d'actualitzar la informacio d'un usuari.
     * Tambe activa el camp completed en cas que sigui necessari.
     * @param u Usuari del qual es vol actualitzar la informació.
     */
    public void updateInfoUser(User u) {
        int likesC = u.getLikesC()? 1: 0;
        int likesJava = u.getLikesJava()? 1: 0;
        String query = "UPDATE users SET photo = '" + u.getPhoto() + "', description = '" + u.getDescription() + "', likes_java = '" + likesJava + "', likes_c = '" + likesC + "', fav_song = '" + u.getFavSong() + "', hobbies = '" + u.getHobbies() + "' WHERE users.username = '" + u.getUsername() + "'";
        DBConnector.getInstance().executeQuery(query);
        User updatedUser = getUser(u);
        if(!updatedUser.isCompleted()){
            query = "UPDATE users SET completed = '1' WHERE users.username = '" + u.getUsername() + "'";
            DBConnector.getInstance().executeQuery(query);
        }
    }

    /**
     * Metode encarregat d'actualitzar les preferencies de compte d'un usuari. Aquestes
     * preferencies inclouen: password, isPremium i age filter
     * @param u Usuari del qual es vol actualitzar la informació.
     */
    public void updatePreferences(User u){
        int premium = u.isPremium()? 1: 0;
        String query = "UPDATE users SET minAge = '" + u.getMinAge() + "', maxAge = '"+ u.getMaxAge() + "', password = '" + u.getPassword() + "', premium = '" + premium + "' WHERE users.username = '" + u.getUsername() + "'";
        DBConnector.getInstance().executeQuery(query);
        User updatedUser = getUser(u);
        if(!updatedUser.isCompleted()){
            query = "UPDATE users SET completed = '1' WHERE users.username = '" + u.getUsername() + "'";
            DBConnector.getInstance().executeQuery(query);
        }
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
     * Funcio que retorna l'usuari complet de la base de dades que correspon a l'usuari incomplet que se li entra com a parametre.
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
        String username = null, mail = null, password = null, photo = null, description = null, favSong = null, hobbiesString = null;
        boolean completed = false, premium = false, likesJava = false, likesC = false;
        int age = 0, maxAge = 0, minAge = 0;
        String[] hobbies = null;
        try {
            while(res.next()) {
                username = res.getString("username");
                mail = res.getString("mail");
                completed = res.getInt("completed") > 0;
                age = res.getInt("age");
                premium = res.getInt("premium") > 0;
                password = res.getString("password");
                minAge = res.getInt("minAge");
                maxAge = res.getInt("maxAge");
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
        User dbUser = new User(completed, username, age, premium, mail, password, minAge, maxAge, photo, description, likesJava, likesC, favSong, hobbies, null, null, null, null);
        return dbUser;
    }

    public boolean isConnected(String username) {
        boolean connected = false;
        String query = "SELECT connected FROM users WHERE users.username = '" + username + "'";
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
    //TODO: Ferran, tot just cridar aquesta funcio peta amb el missatge: "java.lang.NullPointerException
    //TODO	                                                              at model.database.dao.UserDAO.getNextUser(UserDAO.java:174)"
    //He provat de cridar-la havent borrat els usuaris vistos de la bbdd pero igualment peta.
    public String getNextUser(String username, int minAge, int maxAge, boolean isPremium, boolean likesCb, boolean likesJavab) {
        String query, user = null;
        int likesC = likesCb? 1: 0;
        int likesJava = likesJavab? 1: 0;
        if(isPremium) {
            //Si maxAge es 0 implica que no hi ha filtre per edat.
            if(maxAge == 0) {
                //Retorna els usuaris que t'han donat like a tu:
                query = "SELECT u.username FROM users as u, views as v, liked ad l WHERE (u.likes_c = '" + likesC + "' OR u.likes_java = '" + likesJava + "') AND (l.username_2 = '" + username + "') AND NOT EXISTS (v.username_1 = '" + username + "' AND v.username_2 = 'u.username') LIMIT 1";
                ResultSet res = DBConnector.getInstance().selectQuery(query);
                try {
                    //Si no ha trobat cap user, et retorna el següent user que no hagis vist:
                    if(!res.next()) {
                        query = "SELECT u.username FROM users as u, views as v WHERE (u.likes_c = '" + likesC + "' OR u.likes_java = '" + likesJava + "') AND NOT EXISTS (v.username_1 = '" + username + "' AND v.username_2 = 'u.username') LIMIT 1";
                        res = DBConnector.getInstance().selectQuery(query);
                        res.next();
                    }
                    user = res.getString("username");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                //Retorna els usuaris que t'han donat like a tu:
                query = "SELECT u.username FROM users as u, views as v, liked ad l WHERE (u.likes_c = '" + likesC + "' OR u.likes_java = '" + likesJava + "') AND (l.username_2 = '" + username + "') AND (u.likes_c = '" + likesC + "' OR u.likes_java = '" + likesJava + "') AND NOT EXISTS (v.username_1 = '" + username + "' AND v.username_2 = 'u.username') LIMIT 1";
                ResultSet res = DBConnector.getInstance().selectQuery(query);
                try {
                    //Si no ha trobat cap user, et retorna el següent user que no hagis vist:
                    if(!res.next()) { //TODO: Aqui peta
                        query = "SELECT u.username FROM users as u, views as v WHERE (u.likes_c = '" + likesC + "' OR u.likes_java = '" + likesJava + "') AND (u.age BETWEEN '" + minAge + "' AND '" + maxAge + "') AND NOT EXISTS (v.username_1 = '" + username + "' AND v.username_2 = 'u.username') LIMIT 1";
                        res = DBConnector.getInstance().selectQuery(query);
                        res.next();
                    }
                    user = res.getString("username");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            //Si maxAge es 0 implica que no hi ha filtre per edat.
            if(maxAge == 0) {
                query = "SELECT u.username FROM users as u " +
                        "WHERE (u.username NOT IN(SELECT v.username_2 FROM views as v WHERE v.username_1 = '" + username + "'))" +
                        "AND (u.likes_c = '" + likesC + "' OR u.likes_java = '" + likesJava + "') LIMIT 1;";
                ResultSet res = DBConnector.getInstance().selectQuery(query);
                try {
                    res.next();
                    user = res.getString("username");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                query = "SELECT u.username FROM users as u, views as v WHERE (u.likes_c = '" + likesC + "' OR u.likes_java = '" + likesJava + "') AND (u.age BETWEEN '" + minAge + "' AND '" + maxAge + "') AND NOT EXISTS (v.username_1 = '" + username + "' AND v.username_2 = 'u.username') LIMIT 1";
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

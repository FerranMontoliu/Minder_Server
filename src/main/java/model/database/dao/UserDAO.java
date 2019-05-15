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
        String query = "INSERT INTO users(username, mail, completed, age, premium, password, photo, description, likes_java, likes_c, fav_song, hobbies, connected, minAge, maxAge) VALUES ('" + u.getUsername() + "', '" + u.getMail() + "', '0', '" + u.getAge() + "', '" + isPremium + "', '" + u.getPassword() + "', 'null', 'null', '" + likesJava + "', '" + likesC + "', 'null', 'null', '0', '" + u.getMinAge() + "', '" + u.getMaxAge() +"')";
        DBConnector.getInstance().executeQuery(query);
    }

    /**
     * Metode encarregat d'actualitzar la informacio d'un usuari. Tambe activa el camp completed en cas que sigui necessari.
     *
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
     * Metode encarregat d'actualitzar les preferencies de compte d'un usuari. Aquestes preferencies inclouen: password, isPremium i age filter.
     *
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
     *
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
     *
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
        return (new User(completed, username, age, premium, mail, password, minAge, maxAge, photo, description, likesJava, likesC, favSong, hobbies, null, null, null, null));
    }

    /**
     * Funcio que retorna l'usuari parcial de la base de dades que correspon al String que li entra com a parametre.
     *
     * @param user Usuari del qual es vol extreure la info de la base de dades.
     *
     * @return Usuari amb la informacio necessaria per a mostrarse en la pestanya de connect.
     */
    public User getConnectUser(String user) {
        String query = "SELECT * FROM users WHERE users.username = '" + user + "'";
        ResultSet res = DBConnector.getInstance().selectQuery(query);
        String username = null, photo = null, description = null, favSong = null, hobbiesString = null;
        boolean likesJava = false, likesC = false;
        int age = 0;
        String[] hobbies = null;
        try {
            while(res.next()) {
                username = res.getString("username");
                age = res.getInt("age");
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
        return (new User(false, username, age, false, null, null, 0, 0, photo, description, likesJava, likesC, favSong, hobbies, null, null, null, null));
    }

    /**
     * Funcio encarregada de mirar si un usuari esta connectat o no.
     *
     * @param username Usuari del qual volem veure si esta connectat.
     *
     * @return Retorna true si esta connectat, false altrament.
     */
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

    /**
     * Funcio encarregada de marcar a un usuari com a connectat.
     *
     * @param username Usuari que es vol connectar.
     */
    public void userConnects(String username) {
        String query = "UPDATE users SET connected = '1' WHERE users.username = '" + username + "'";
        DBConnector.getInstance().executeQuery(query);
    }

    /**
     * Funcio encarregada de marcar a un usuari com a desconnectat.
     *
     * @param username Usuari que es vol desconnectar.
     */
    public void userDisconnects(String username) {
        String query = "UPDATE users SET connected = '0' WHERE users.username = '" + username + "'";
        DBConnector.getInstance().executeQuery(query);
    }

    /**
     * Funcio encarregada de retornar el seguent usuari a mostrar en la pantalla de connect.
     *
     * @param username Nom de l'usuari que esta a la pantalla de connect.
     * @param minAge Edat minima del filtre per edat de l'usuari.
     * @param maxAge Edat maxima del filtre per edat de l'usuari (0 si no en te).
     * @param isPremium Indica si l'usuari es Premium (true) o Normal (false).
     * @param likesCb Indica si a l'usuari li agrada C (true) o no (false).
     * @param likesJavab Indica si a l'usuari li agrada Java (true) o no (false).
     *
     * @return Retorna el seguent usuari a mostrar en la pantalla de connect (o null si no queden users a mostrar).
     */
    public String getNextUser(String username, int minAge, int maxAge, boolean isPremium, boolean likesCb, boolean likesJavab) {
        String user = null;
        int likesC = likesCb? 1: 0;
        int likesJava = likesJavab? 1: 0;
        if(isPremium) {
            //Si maxAge es 0 implica que no hi ha filtre per edat.
            if(maxAge == 0) {
                user = getNextUserPremiumNoFilter(username, likesC, likesJava);
            } else {
                user = getNextUserPremiumFilter(username, likesC, likesJava, minAge, maxAge);
            }
        } else {
            //Si maxAge es 0 implica que no hi ha filtre per edat.
            if(maxAge == 0) {
                user = getNextUserNoPremiumNoFilter(username, likesC, likesJava);
            } else {
                user = getNextUserNoPremiumFilter(username, likesC, likesJava, minAge, maxAge);
            }
        }
        return user;
    }

    /**
     * Funcio encarregada de retornar el seguent usuari a mostrar en la pantalla de connect en cas de no ser Premium i no tenir filtre per edat.
     *
     * @param username Nom de l'usuari que esta a la pantalla de connect.
     * @param likesC Indica si a l'usuari li agrada C (1) o no (0).
     * @param likesJava Indica si a l'usuari li agrada Java (1) o no (0).
     *
     * @return Retorna el seguent usuari a mostrar en la pantalla de connect (o null si no queden users a mostrar).
     */
    private String getNextUserNoPremiumNoFilter(String username, int likesC, int likesJava) {
        String user = null;
        String query = "SELECT u.username FROM users as u " +
                "WHERE (u.username NOT IN(SELECT l.username_2 FROM liked as l WHERE l.username_1 = '" + username + "')) " +
                "AND ((u.likes_c = '" + likesC + "' AND u.likes_c = '1') OR (u.likes_java = '" + likesJava + "' AND u.likes_java = '1')) " +
                "AND (u.username <> '" + username + "') " +
                "LIMIT 1;";
        ResultSet res = DBConnector.getInstance().selectQuery(query);
        try {
            if(!res.next()) {
                query = "SELECT u.username FROM users as u, liked as l " +
                        "WHERE (l.username_1 = '" + username +
                        "') AND (l.username_2 = u.username) AND " +
                        "(l.liked_bool = '0') " +
                        "AND ((u.likes_c = '" + likesC + "' AND u.likes_c = '1') OR (u.likes_java = '" + likesJava + "' AND u.likes_java = '1')) " +
                        "ORDER BY l.liked_date " +
                        "LIMIT 1;";
                res = DBConnector.getInstance().selectQuery(query);
                if(res.next()) {
                    user = res.getString("username");
                }
            } else {
                user = res.getString("username");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Funcio encarregada de retornar el seguent usuari a mostrar en la pantalla de connect en cas de no ser Premium i tenir filtre per edat.
     *
     * @param username Nom de l'usuari que esta a la pantalla de connect.
     * @param likesC Indica si a l'usuari li agrada C (1) o no (0).
     * @param likesJava Indica si a l'usuari li agrada Java (1) o no (0).
     * @param minAge Edat minima del filtre per edat de l'usuari.
     * @param maxAge Edat maxima del filtre per edat de l'usuari.
     *
     * @return Retorna el seguent usuari a mostrar en la pantalla de connect (o null si no queden users a mostrar).
     */
    private String getNextUserNoPremiumFilter(String username, int likesC, int likesJava, int minAge, int maxAge) {
        String user = null;
        String query = "SELECT u.username FROM users as u " +
                "WHERE (u.username NOT IN(SELECT l.username_2 FROM liked as l WHERE l.username_1 = '" + username + "')) " +
                "AND ((u.likes_c = '" + likesC + "' AND u.likes_c = '1') OR (u.likes_java = '" + likesJava + "' AND u.likes_java = '1')) " +
                "AND (u.username <> '" + username + "') " +
                "AND (u.age BETWEEN '" + minAge + "' AND '" + maxAge + "')" +
                "LIMIT 1;";
        ResultSet res = DBConnector.getInstance().selectQuery(query);
        try {
            if(!res.next()) {
                query = "SELECT u.username FROM users as u, liked as l " +
                        "WHERE (l.username_1 = '" + username +
                        "') AND (l.username_2 = u.username) AND " +
                        "(l.liked_bool = '0') " +
                        "AND ((u.likes_c = '" + likesC + "' AND u.likes_c = '1') OR (u.likes_java = '" + likesJava + "' AND u.likes_java = '1')) " +
                        "AND (u.username <> '" + username + "') " +
                        "AND (u.age BETWEEN '" + minAge + "' AND '" + maxAge + "') " +
                        "LIMIT 1;";
                res = DBConnector.getInstance().selectQuery(query);
                if(res.next()) {
                    user = res.getString("username");
                }
            } else {
                user = res.getString("username");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Funcio encarregada de retornar el seguent usuari a mostrar en la pantalla de connect en cas de ser Premium i no tenir filtre per edat.
     *
     * @param username Nom de l'usuari que esta a la pantalla de connect.
     * @param likesC Indica si a l'usuari li agrada C (1) o no (0).
     * @param likesJava Indica si a l'usuari li agrada Java (1) o no (0).
     *
     * @return Retorna el seguent usuari a mostrar en la pantalla de connect (o null si no queden users a mostrar).
     */
    private String getNextUserPremiumNoFilter(String username, int likesC, int likesJava) {
        String user = null;
        String query = "SELECT u.username FROM users as u " +
                "WHERE (u.username NOT IN(SELECT l.username_2 FROM liked as l WHERE l.username_1 = '" + username + "')) " +
                "AND ((u.likes_c = '" + likesC + "' AND u.likes_c = '1') OR (u.likes_java = '" + likesJava + "' AND u.likes_java = '1')) " +
                "AND (u.username <> '" + username + "') " +
                "AND (u.username IN(SELECT l.username_1 FROM liked as l WHERE l.username_2 = '" + username + "')) " +
                "LIMIT 1;";
        ResultSet res = DBConnector.getInstance().selectQuery(query);
        try {
            if(!res.next()) {
                user = getNextUserNoPremiumNoFilter(username, likesC, likesJava);
            } else {
                user = res.getString("username");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Funcio encarregada de retornar el seguent usuari a mostrar en la pantalla de connect en cas de ser Premium i tenir filtre per edat.
     *
     * @param username Nom de l'usuari que esta a la pantalla de connect.
     * @param likesC Indica si a l'usuari li agrada C (1) o no (0).
     * @param likesJava Indica si a l'usuari li agrada Java (1) o no (0).
     * @param minAge Edat minima del filtre per edat de l'usuari.
     * @param maxAge Edat maxima del filtre per edat de l'usuari.
     *
     * @return Retorna el seguent usuari a mostrar en la pantalla de connect (o null si no queden users a mostrar).
     */
    private String getNextUserPremiumFilter(String username, int likesC, int likesJava, int minAge, int maxAge) {
        String user = null;
        String query = "SELECT u.username FROM users as u " +
                "WHERE (u.username NOT IN(SELECT l.username_2 FROM liked as l WHERE l.username_1 = '" + username + "')) " +
                "AND ((u.likes_c = '" + likesC + "' AND u.likes_c = '1') OR (u.likes_java = '" + likesJava + "' AND u.likes_java = '1')) " +
                "AND (u.username <> '" + username + "') " +
                "AND (u.age BETWEEN '" + minAge + "' AND '" + maxAge + "') " +
                "AND (u.username IN(SELECT l.username_1 FROM liked as l WHERE l.username_2 = '" + username + "')) " +
                "LIMIT 1;";
        ResultSet res = DBConnector.getInstance().selectQuery(query);
        try {
            if(!res.next()) {
                user = getNextUserNoPremiumFilter(username, likesC, likesJava, minAge, maxAge);
            } else {
                user = res.getString("username");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}

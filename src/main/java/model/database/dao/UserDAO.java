package model.database.dao;

import model.database.DBConnector;
import model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public void addUser(User u) {
        int isPremium = u.isPremium()? 1: 0;
        int likesC = u.getLikesC()? 1: 0;
        int likesJava = u.getLikesJava()? 1: 0;
        String query = "INSERT INTO users(username, mail, completed, age, premium, password, photo, description, likes_java, likes_c, fav_song, hobbies) VALUES ('" + u.getUsername() + "', '" + u.getMail() + "', '0', '" + u.getAge() + "', '" + isPremium + "', '" + u.getPassword() + "', 'null', 'null', '" + likesJava + "', '" + likesC + "', 'null', 'null')";
        DBConnector.getInstance().executeQuery(query);
    }

    public void updateInfoUser(User u) {
        int likesC = u.getLikesC()? 1: 0;
        int likesJava = u.getLikesJava()? 1: 0;
        String query = "UPDATE users SET photo = '" + "string de la foto aqui" + "', description = '" + u.getDescription() + "', likes_java = '" + likesJava + "', likes_c = '" + likesC + "', fav_song = '" + u.getFavSong() + "', hobbies = '" + "u.getHobbies()" + "' WHERE users.username = '" + u.getUsername() + "'";
        DBConnector.getInstance().executeQuery(query);
    }

    public boolean existsUser(User u) {
        String query = "SELECT COUNT(*) FROM users WHERE users.username = '" + u.getUsername() + "'";
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

    public User getUser(User u) {
        String query = "SELECT * FROM users WHERE users.username = '" + u.getUsername() + "'";
        ResultSet res = DBConnector.getInstance().selectQuery(query);
        String username = null, mail = null, password = null, photo = null, description = null, favSong = null, hobbiesString = null;
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
        User dbUser = new User(completed, username, Integer.toString(age), premium, mail, password, photo, description, likesJava, likesC, favSong, hobbies, null, null, null, null);
        return dbUser;
    }
}

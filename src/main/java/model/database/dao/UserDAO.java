package model.database.dao;

import model.database.DBConnector;
import model.entity.User;

public class UserDAO {
    public void addUser(User u) {
        String query = "INSERT INTO users(username, mail, completed, age, premium, password, salt, photo, description, likes_java, likes_c, fav_song, hobbies, user_id) VALUES ('" + u.getUsername() + "', '" + u.getMail() + "', 'false', '" + u.getAge() + "', '" + u.isPremium() + "', '" + u.getPassword() + "', '" + u.getSalt() + "', 'null', 'null', '" + u.getLikesJava() + "', '" + u.getLikesC() + "', 'null', 'null', NULL)";
        DBConnector.getInstance().insertQuery(query);
    }

    public void deleteUser(User u) {

    }

    public void updateUser(User u) {

    }

    public void existsUser(User u) {

    }
}

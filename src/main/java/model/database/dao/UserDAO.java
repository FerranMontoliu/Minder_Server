package model.database.dao;

import model.database.DBConnector;
import model.entity.User;

public class UserDAO {
    public void addUser(User u) {
        String query = "INSERT INTO users(username, mail, completed, age, premium, password, salt, photo, description, likes_java, likes_c, fav_song, hobbies) VALUES ('" + u.getUsername() + "', '" + u.getMail() + "', 'false', '" + u.getAge() + "', '" + u.isPremium() + "', '" + u.getPassword() + "', '" + u.getSalt() + "', 'null', 'null', '" + u.getLikesJava() + "', '" + u.getLikesC() + "', 'null', 'null')";
        DBConnector.getInstance().insertQuery(query);
    }

    public void updateInfoUser(User u) {
        String query = "UPDATE users SET description = '" + u.getDescription() + "', photo = '" + "string de la foto aqui" + "', likes_java = '" + u.getLikesJava() + "', likes_c = '" + u.getLikesC() + "', fav_song = '" + u.getFavSong() + "', hobbies = '" + u.getHobbies() + "' WHERE username = " + u.getUsername() + ";";
        DBConnector.getInstance().insertQuery(query);
    }

    public void existsUser(User u) {

    }
}

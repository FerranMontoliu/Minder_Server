package model.database.dao;

import model.database.DBConnector;
import model.entity.Chat;
import model.entity.Message;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

public class ChatDAO {
    public Chat loadChat(String u1, String u2) {
        String query = "SELECT * FROM chats WHERE username_1 = '" + u1 + "' AND username_2 = '" + u2 + "';";
        ResultSet res = DBConnector.getInstance().selectQuery(query);
        LinkedList<Message> xat = new LinkedList<>();
        String source, destination, message, date;
        String pattern = "MM/dd/yyyy HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        try {
            while(res.next()) {
                source = res.getString("username_1");
                destination = res.getString("username_2");
                message = res.getString("message");
                Date d = res.getDate("message_date");
                date = df.format(d);
                Message m = new Message(source, message, destination, date);
                xat.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Chat c = new Chat(xat);
        return c;
    }

    public void sendMessage(String u1, String u2, Message m) {
        String query = "INSERT INTO chats (username_1, username_2, message_date, message) VALUES ('" + u1 + "', '" + u2 + "', '" + "2019-04-10" + "', '" + m + "')";
        DBConnector.getInstance().executeQuery(query);
    }
}

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
        String query = "SELECT * FROM chats WHERE username_1 = '" + u1 + "' AND username_2 = '" + u2 + "' OR username_1 = '" + u2 + "' AND username_2 = '" + u1 + "';";
        ResultSet res = DBConnector.getInstance().selectQuery(query);
        LinkedList<Message> xat = new LinkedList<>();
        String source, destination, message, date;
        String pattern = "MM/dd/yyyy HH:mm";
        DateFormat df = new SimpleDateFormat(pattern);
        try {
            while(res.next()) {
                source = res.getString("username_1");
                destination = res.getString("username_2");
                message = res.getString("message");
                String d = res.getString("message_date");
                Message m = new Message(source, message, destination, d);
                xat.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Chat c = new Chat(xat);
        return c;
    }

    public void sendMessage(Message m) {
        System.out.println("Abans de la del send");
        String query = "INSERT INTO chats (username_1, username_2, message, message_date) VALUES ('" + m.getSource() + "', '" + m.getDestination() + "', '" + m.getText() + "', '" + m.getTime() + "')";
        DBConnector.getInstance().executeQuery(query);
    }
}

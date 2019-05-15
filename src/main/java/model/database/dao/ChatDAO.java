package model.database.dao;

import model.database.DBConnector;
import model.entity.Chat;
import model.entity.Message;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

public class ChatDAO {

    /**
     * Funcio encarregada de carregar tot el xat entre dos usuaris.
     *
     * @param u1 Usuari 1 que conforma el xat.
     * @param u2 Usuari 2 que conforma el xat.
     *
     * @return Retorna el xat senxer entre els dos usuaris.
     */
    public Chat loadChat(String u1, String u2) {
        String query = "SELECT * FROM chats WHERE (username_1 = '" + u1 + "' AND username_2 = '" + u2 + "') OR (username_1 = '" + u2 + "' AND username_2 = '" + u1 + "');";
        ResultSet res = DBConnector.getInstance().selectQuery(query);
        LinkedList<Message> xat = new LinkedList<>();
        try {
            while(res.next()) {
                String source = res.getString("username_1");
                String destination = res.getString("username_2");
                String message = res.getString("message");
                String d = res.getString("message_date");
                Message m = new Message(source, message, destination, d);
                xat.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (new Chat(xat));
    }

    /**
     * Funcio encarregada d'enviar un missatge (guardar-lo en la base de dades).
     *
     * @param m Missatge que es guarda a la base de dades.
     */
    public void sendMessage(Message m) {
        String query = "INSERT INTO chats (username_1, username_2, message, message_date) VALUES ('" + m.getSource() + "', '" + m.getDestination() + "', '" + m.getText() + "', '" + m.getTime() + "')";
        DBConnector.getInstance().executeQuery(query);
    }

    /**
     * Funcio encarregada d'eliminar tots els missatges entre dos usuaris (eliminar-los de la base de dades).
     *
     * @param u1 Usuari 1 que conforma el xat.
     * @param u2 Usuari 2 que conforma el xat.
     */
    public void deleteMessages(String u1, String u2){
        String query = "DELETE FROM chats WHERE (username_1 = '" + u1 +"' AND username_2 = '" + u2 + "') OR (username_1 = '" + u2 + "' AND username_2 = '" + u1 + "')";
        DBConnector.getInstance().executeQuery(query);
    }
}

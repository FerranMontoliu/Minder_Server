package model.entity;

import java.io.Serializable;
import java.util.LinkedList;

public class Chat implements Serializable {
    private LinkedList<Message> messages;

    public Chat(LinkedList<Message> messages) {
        this.messages = messages;
    }

    public LinkedList<Message> getMessages() {
        return messages;
    }

    public void setMessages(LinkedList<Message> messages) {
        this.messages = messages;
    }

    public String getNewMessage() {
        return messages.getLast().toString();
    }
}

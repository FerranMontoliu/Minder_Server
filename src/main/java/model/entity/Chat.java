package model.entity;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Classe que representa un chat que conte una llista de missatges.
 */
public class Chat implements Serializable {
    private LinkedList<Message> messages;

    /**
     * Constructor de la classe xat.
     *
     * @param messages Llista de missatges que formen el xat.
     */
    public Chat(LinkedList<Message> messages) {
        this.messages = messages;
    }

    /**
     * Getter de la llista de missatges que formen el xat.
     *
     * @return Llista de missatges que formen el xat.
     */
    public LinkedList<Message> getMessages() {
        return messages;
    }

    /**
     * Funcio que retorna l'ultim missatge de la llista.
     *
     * @return Retorna l'ultim usuari de la llista de missatges.
     */
    public String getNewMessage() {
        return messages.getLast().toString();
    }
}
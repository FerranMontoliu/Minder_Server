package model.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe que representa un missatge de chat.
 */
public class Message implements Serializable {
    private String source;
    private String text;
    private String destination;
    private String time;

    /**
     * Constructor de la classe Message del server.
     *
     * @param source Persona que envia el missatge.
     * @param text Contingut del missatge.
     * @param destination Persona que rep el missatge.
     * @param time Data i hora en que s'envia el missatge.
     */
    public Message(String source, String text, String destination, String time) {
        this.source = source;
        this.text = text;
        this.destination = destination;
        this.time = time;
    }

    /**
     * Constructor de la classe Message del client.
     *
     * @param user Persona que envia el missatge.
     * @param text Contingut del missatge.
     * @param destination Persona que rep el missatge.
     */
    public Message (String user, String text, String destination) {
        this.source = user;
        this.text = text;
        this.destination = destination;
        this.time = generateMessageTime();
    }

    /**
     * Funcio encarregada de generar la data i la hora actual.
     *
     * @return String que conte la data i la hora actuals.
     */
    private String generateMessageTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        return simpleDateFormat.format(new Date());
    }

    /**
     * Getter de l'origen del missatge.
     *
     * @return Nom de la persona que envia el missatge.
     */
    public String getSource() {
        return source;
    }

    /**
     * Getter del contingut del missatge.
     *
     * @return Text que conte el missatge.
     */
    public String getText() {
        return text;
    }

    /**
     * Getter del destinatari del missatge.
     *
     * @return Nom de la persona que rep el missatge.
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Getter del temps en el qual s'envia el missatge.
     *
     * @return Temps en el qual s'envia el missatge.
     */
    public String getTime() {
        return time;
    }

    /**
     * Funcio toString personalitzada per a poder mostrar un missatge en el xat.
     *
     * @return String que conte el missatge formatejat per a mostrar.
     */
    @Override
    public String toString() {
        return "(" + time + ") "+ source + ": " + text;
    }
}
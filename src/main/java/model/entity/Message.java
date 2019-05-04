package model.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message implements Serializable {
    private String source;
    private String text;
    private String destination;
    private String time;

    public Message(String source, String text, String destination, String time) {
        this.source = source;
        this.text = text;
        this.destination = destination;
        this.time = time;
    }

    public Message (String user, String text, String destination) {
        this.source = user;
        this.text = text;
        this.destination = destination;
        this.time = generateMessageTime();
    }

    private String generateMessageTime() {
        String format = "MM/dd/yyyy HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date());
    }

    public String getSource() {
        return source;
    }

    public String getText () {
        return text;
    }

    public String getDestination() {
        return destination;
    }

    public String getTime() { return  time; }

    @Override
    public String toString () {
        return "(" + time + ") "+ source + ": " + text;
    }
}


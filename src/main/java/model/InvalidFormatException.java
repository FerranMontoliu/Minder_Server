package model;

public class InvalidFormatException extends Exception{

    /**
     * Constructor de la Exception.
     * @param message Missatge d'error.
     */
    public InvalidFormatException(String message){
        super(message);
    }
}

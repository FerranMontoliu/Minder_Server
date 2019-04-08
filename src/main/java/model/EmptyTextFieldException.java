package model;

/**
 * Classe que representa la Exception que es llenca quan s'intenta llegir d'una entrada de dades buida.
 */
public class EmptyTextFieldException extends Exception {

    /**
     * Constructor de la Exception.
     * @param s Missatge d'error.
     */
    public EmptyTextFieldException(String s) {
        super(s);
    }
}

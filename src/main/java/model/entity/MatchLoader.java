package model.entity;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Classe usada per a enviar la llista de usuaris amb els que un ha fet match
 */
public class MatchLoader implements Serializable {
    private LinkedList<String> matchedUsernames;

    /**
     * Constructor per defecte.
     */
    public MatchLoader(){
        matchedUsernames = new LinkedList<>();
    }

    /**
     * Getter de l'atribut matchedUsernames.
     *
     * @return Llista de noms d'usuaris amb els quals s'ha fet match.
     */
    public LinkedList<String> getMatchedUsernames() {
        return matchedUsernames;
    }

    /**
     * Metode que afegeix un usuari mes al MatchLoader.
     *
     * @param username usuari nou amb el que s'ha fet match.
     */
    public void addMatch(String username){
        matchedUsernames.add(username);
    }
}
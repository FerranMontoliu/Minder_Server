package model;

import org.apache.commons.io.FileUtils;
import org.apache.commons.validator.routines.EmailValidator;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

public class User {
    private String username;
    private int age;
    private boolean premium;
    private String mail;
    private String password;
    private String passwordConfirmation;

    private Image photo;
    private String description;
    private boolean likesJava;
    private boolean likesC;
    private String favSong;
    private ArrayList<String> hobbies;

    private ArrayList<User> viewed;
    private ArrayList<User> accepted;
    private ArrayList<User> match;
    private ArrayList<User> acceptedMe;

    /**
     * Constructor que es crida quan es registra l'usuari.
     *
     */
    public User(String username, int age, boolean premium, String mail, String password, String passwordConfirmation) {
        this.username = username;
        this.age = age;
        this.premium = premium;
        this.mail = mail;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
    }

    /**
     * Funció que s'encarrega de comprovar si totes les dades introduïdes són correctes.
     *
     * @return Retorna true si és major totes les dades són correctes, false sinó.
     */
    public boolean dataIsCorrect() {
        return !usernameEmpty() && isAdult() && mailCorrectFormat() && passwordIsCorrect();
    }

    /**
     * Funció que s'encarrega de comprovar si l'usuari és major d'edat o no.
     *
     * @return Retorna true si és major d'edat, false sinó.
     */
    public boolean isAdult() {
        return age > 17;
    }

    /**
     * Funció que s'encarrega de comprovar si el format del mail és correcte o no.
     *
     * @return Retorna true si el format és correcte, false sinó.
     */
    public boolean mailCorrectFormat() {
        EmailValidator v = EmailValidator.getInstance();
        return v.isValid(mail);
    }

    /**
     * Funció que s'encarrega de comprovar si el camp del nom d'usuari està buit.
     *
     * @return Retorna true si està buit, false sinó.
     */
    public boolean usernameEmpty() {
        return username.isEmpty();
    }

    /**
     * Funció que s'encarrega de comprovar si la password concorda amb el camp de confirmació de password.
     *
     * @return Retorna true si concorda i no està buit, false sinó.
     */
    public boolean passwordConfirm() {
        //Si el camp de password no està buit, comprovar si coincideix:
        if(!password.isEmpty()) {
            return password.equals(passwordConfirmation);
        }
        //Si el camp de password està buit, retornar false:
        return false;
    }

    /**
     * Funció que s'encarrega de comprovar si el format de la password és correcte o no
     *
     * @return Retorna true si el format és correcte, false sinó.
     */
    public boolean passwordCorrectFormat() {
        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasLowercase = !password.equals(password.toUpperCase());
        boolean hasNumber  = password.matches(".*\\d.*");
        boolean isLongEnough = password.length() > 7;

        return hasUppercase && hasLowercase && hasNumber && isLongEnough;
    }

    /**
     * Funció que s'encarrega de comprovar si la password és correcta o no.
     *
     * @return Retorna true si és correcta, false sinó.
     */
    public boolean passwordIsCorrect() {
        return passwordConfirm() && passwordCorrectFormat();
    }

    /**
     * Getter del username de l'usuari.
     *
     * @return Retorna un String que conté el nom de l'usuari.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter de l'edat de l'usuari.
     *
     * @return Retorna un int que conté l'edat de l'usuari.
     */
    public int getAge() {
        return age;
    }

    /**
     * Getter del tipus de compte de l'usuari.
     *
     * @return Retorna true si l'usuari és Premium, false si l'usuari és Normal.
     */
    public boolean isPremium() {
        return premium;
    }

    /**
     * Getter del mail de l'usuari.
     *
     * @return Retorna un String que conté el mail de l'usuari.
     */
    public String getMail() {
        return mail;
    }

    /**
     * Getter de la password de l'usuari.
     *
     * @return Retorna un String que conté la password de l'usuari.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Getter de la foto de perfil de l'usuari.
     *
     * @return Retorna una Image que és la foto de perfil de l'usuari.
     */
    public Image getPhoto() {
        return photo;
    }

    /**
     * Getter de la descripció de l'usuari.
     *
     * @return Retorna un String que conté la descripció del perfil de l'usuari.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter de si a l'usuari li agrada Java.
     *
     * @return Retorna true si a l'usuari li agrada Java, false sinó.
     */
    public boolean isLikesJava() {
        return likesJava;
    }

    /**
     * Getter de si a l'usuari li agrada C.
     *
     * @return Retorna true si a l'usuari li agrada C, false sinó.
     */
    public boolean isLikesC() {
        return likesC;
    }

    /**
     * Getter de la cançó preferida de l'usuari.
     *
     * @return Retorna un String que conté la cançó preferida de l'usuari.
     */
    public String getFavSong() {
        return favSong;
    }

    /**
     * Getter de la llista de hobbies de l'usuari.
     *
     * @return Retorna una llista de Strings que conté els hobbies de l'usuari.
     */
    public ArrayList<String> getHobbies() {
        return hobbies;
    }

    /**
     * Getter de la llista de perfils que ha vist l'usuari.
     *
     * @return Retorna una llista de Users que conté els perfils que ja ha vist l'usuari.
     */
    public ArrayList<User> getViewed() {
        return viewed;
    }

    /**
     * Getter de la llista de perfils que ha acceptat l'usuari.
     *
     * @return Retorna una llista de Users que conté els perfils que ja ha acceptat l'usuari.
     */
    public ArrayList<User> getAccepted() {
        return accepted;
    }

    /**
     * Getter de la llista de perfils amb els quals ha fet match l'usuari.
     *
     * @return Retorna una llista de Users que conté els perfils amb els quals ha fet match l'usuari.
     */
    public ArrayList<User> getMatch() {
        return match;
    }

    public String imageToBase64() {
        String s = null;
        try {
            s =  Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File("data/image.jpg")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    public void base64ToImage(String encodedString) {
        try {
            FileUtils.writeByteArrayToFile(new File("data/imageConverted.jpg"), Base64.getDecoder().decode(encodedString));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

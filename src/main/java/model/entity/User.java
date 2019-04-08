package model.entity;

import model.UserManager;
import org.apache.commons.io.FileUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;

public class User implements Serializable {

    private boolean completed;

    private String username;
    private String age;
    private boolean premium;
    private String mail;
    private String password;
    private byte[] salt;

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


    public User(boolean completed, String username, String age, boolean premium, String mail, String password, byte[] salt, Image photo, String description, boolean likesJava, boolean likesC, String favSong, ArrayList<String> hobbies, ArrayList<User> viewed, ArrayList<User> accepted, ArrayList<User> match, ArrayList<User> acceptedMe) {
        this.completed = completed;
        this.username = username;
        this.age = age;
        this.premium = premium;
        this.mail = mail;
        this.password = password;
        this.salt = salt;
        this.photo = photo;
        this.description = description;
        this.likesJava = likesJava;
        this.likesC = likesC;
        this.favSong = favSong;
        this.hobbies = hobbies;
        this.viewed = viewed;
        this.accepted = accepted;
        this.match = match;
        this.acceptedMe = acceptedMe;
    }

    /**
     * Constructor que es crida quan es registra l'usuari.
     *
     **/
    public User(String username, String age, boolean premium, String mail, String password, byte[] salt) {
        this.username = username;
        this.age = age;
        this.premium = premium;
        this.mail = mail;
        this.password = password;
        this.salt = salt;
        this.completed = false;
    }

    /**
     * Constructor que es crida quan l'usuari fa login amb el username.
     *
     **/
    public User(String identificator, String password) {
        this.password = password;
        if(UserManager.mailInSignIn(identificator)){
            this.mail = identificator;
        }else{
            this.username = identificator;
        }
    }

    /**
     * Getter del username de l'usuari.
     *
     * @return Retorna un String que conté el nom de l'usuari.
     */
    public String getUsername() {
        return username;
    }

    public int getAge() {
        return Integer.parseInt(age);
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
    public boolean getLikesJava() {
        return likesJava;
    }

    /**
     * Getter de si a l'usuari li agrada C.
     *
     * @return Retorna true si a l'usuari li agrada C, false sinó.
     */
    public boolean getLikesC() {
        return likesC;
    }

    public byte[] getSalt() {
        return salt;
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

    public ArrayList<User> getAcceptedMe() {
        return acceptedMe;
    }

    /**
     * Funció encarregada de transformar una imatge a text.
     *
     * @return Retorna el text en Base64 que representa la imatge.
     */
    public String imageToBase64() {
        String s = null;
        try {
            s =  Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File("data/image.jpg")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * Mètode encarregat de reconstruir una imatge a partir d'un String.
     *
     * @param encodedString String que conté la imatge codificada en Base64.
     */
    public void base64ToImage(String encodedString) {
        try {
            FileUtils.writeByteArrayToFile(new File("data/imageConverted.jpg"), Base64.getDecoder().decode(encodedString));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
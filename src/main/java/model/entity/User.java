package model.entity;

import model.UserManager;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;

public class User implements Serializable {

    private boolean completed;

    private String username;
    private int age;
    private boolean premium;
    private String mail;
    private String password;
    private int minAge;
    private int maxAge;

    private String photo;
    private String description;
    private boolean likesJava;
    private boolean likesC;
    private String favSong;
    private String[] hobbies;

    private ArrayList<User> viewed;
    private ArrayList<User> accepted;
    private ArrayList<User> match;
    private ArrayList<User> acceptedMe;


    /**
     *Constructor que ompla TOTS els camps de l'usuari.
     *
     */
    public User(boolean completed, String username, int age, boolean premium, String mail, String password, int minAge, int maxAge, String photo, String description, boolean likesJava, boolean likesC, String favSong, String[] hobbies, ArrayList<User> viewed, ArrayList<User> accepted, ArrayList<User> match, ArrayList<User> acceptedMe) {
        this.completed = completed;
        this.username = username;
        this.age = age;
        this.premium = premium;
        this.mail = mail;
        this.password = password;
        this.minAge = minAge;
        this.maxAge = maxAge;
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
    public User(String username, int age, boolean premium, String mail, String password, int minAge, int maxAge) {
        this.username = username;
        this.age = age;
        this.premium = premium;
        this.mail = mail;
        this.password = password;
        this.completed = false;
        this.minAge = minAge;
        this.maxAge = maxAge;
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

    /**
     * Getter de l'edat de l'usuari.
     *
     * @return Retorna un enter que conté l'edat de l'usuari.
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
     * Getter de l'edat minima que ha seleccionat l'usuari per al filtre d'edat
     *
     * @return Retorna un String que conté l'edat minima de l'usuari
     */
    public int getMinAge() {
        return minAge;
    }

    /**
     * Setter de l'edat minima que ha seleccionat l'usuari per al filtre d'edat
     *
     */
    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    /**
     * Getter de l'edat maxima que ha seleccionat l'usuari per al filtre d'edat
     *
     * @return Retorna un String que conté l'edat maxima de l'usuari
     */
    public int getMaxAge() {
        return maxAge;
    }

    /**
     * Setter de l'edat maxima que ha seleccionat l'usuari per al filtre d'edat
     *
     */
    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    /**
     * Getter de la foto de perfil de l'usuari.
     *
     * @return Retorna una Image que és la foto de perfil de l'usuari.
     */
    public String getPhoto() {
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

    /**
     * Getter de la canso preferida de l'usuari.
     *
     * @return Retorna un String que conte la canso preferida de l'usuari.
     */
    public String getFavSong() {
        return favSong;
    }

    /**
     * Getter de la llista de hobbies de l'usuari.
     *
     * @return Retorna una String que conte els hobbies de l'usuari.
     */
    public String getHobbies() {
        StringBuilder convertedHobbies = new StringBuilder();
        for(String h: hobbies){
            convertedHobbies.append(h).append(",");
        }
        String hobbiesString = convertedHobbies.toString().replace(convertedHobbies.toString().substring(convertedHobbies.toString().length()-1), "");
        return hobbiesString;
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

    /**
     * Getter de la llista de perfils que han acceptat a l'usuari.
     *
     * @return Retorna una llista de Users que conté els perfils que ja han acceptat a l'usuari.
     */
    public ArrayList<User> getAcceptedMe() {
        return acceptedMe;
    }

    /**
     * Funcio encarregada de transformar una imatge a text.
     * @param fullPath imatge a transformar a base64
     */
    public void imageToBase64(String fullPath) {
        try {
            photo =  Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(fullPath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metode encarregat de reconstruir una imatge a partir d'un String.
     *
     * @param username String que definira el nom de la imatge a descarregar.
     */
    public void base64ToImage(String username) {
        try {
            try{
                String base64Image = photo.split(",")[1];
                FileUtils.writeByteArrayToFile(new File("MinderDownloads/" + username + ".jpg"), Base64.getDecoder().decode(base64Image));
            }catch(Exception e1){
                FileUtils.writeByteArrayToFile(new File("MinderDownloads/" + username + ".jpg"), Base64.getDecoder().decode(photo));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Funcio encarregada de comprovar si un usuari està completat o no.
     *
     * @return Retorna true si està completat, no altrament.
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Setter de l'atribut de completed.
     *
     * @param completed nou valor que contindrà el paràmetre completed.
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * Metode que actualitza els camps del User despres d'editar el perfil
     * @param description nova description
     * @param java nou valor del boolean java
     * @param c nou valor del boolean c
     * @param song nova canso preferida
     * @param hobbies nous hobbies
     */
    public void saveEdition(String description, boolean java, boolean c, String song, String hobbies) {
        this.description = description;
        this.likesJava = java;
        this.likesC = c;
        this.favSong = song;
        convertToStringArray(hobbies);
    }

    /**
     * Metode que converteix un String (amb comes) i el divideix per aquestes en un Array d'Strings
     * @param hobbies
     */
    private void convertToStringArray(String hobbies) {
        this.hobbies = hobbies.split(",");
        for (int i = 0; i < this.hobbies.length; i++) {
            // Fetch the item, trim it and put it back in
            this.hobbies[i] = this.hobbies[i].trim();
        }
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * Metode que actualitza les dades de l'usuari segons els camps seleccionats a la opcio Etit account preferences del
     * client
     * @param hashedPassword
     * @param isPremium
     * @param minAge
     * @param maxAge
     * @param noFilter
     */
    public void savePreferencesUpdate(String hashedPassword, boolean isPremium, int minAge, int maxAge, boolean noFilter) {
        this.password = hashedPassword;
        this.premium = isPremium;
        this.minAge = minAge;
        this.maxAge = noFilter?  0: maxAge;

    }

    /**
     * Setter de l'array d'strings de hobbies
     * @param hobbies
     */
    public void setHobbies(String[] hobbies) {
        this.hobbies = hobbies;
    }

    /**
     * Setter de la canco preferida de l'usuari
     * @param song
     */
    public void setFavSong(String song) {
        this.favSong = song;
    }
}
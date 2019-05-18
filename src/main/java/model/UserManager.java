package model;

import org.apache.commons.validator.routines.EmailValidator;

import javax.swing.*;

public class UserManager {
    /**
     * Funcio que s'encarrega de comprovar si l'usuari és major d'edat o no.
     *
     * @param age Edat de l'usuari.
     *
     * @throws InvalidFormatException Es tira si el format de les dades no es correcte.
     */
    public static void isAdult(String age) throws InvalidFormatException {
        try {
            int a = getAge(age);
            if(a < 18) {
                throw new InvalidFormatException("In order to register you have to be at least 18");
            }
        } catch(NumberFormatException e1) {
            throw new InvalidFormatException("Age field must be a number");
        }
    }

    /**
     * Metode que s'encarrega de comprovar si el format del mail es correcte o no.
     *
     * @param mail Mail del qual es vol comprovar el format.
     *
     * @throws InvalidFormatException Es tira si el format de les dades no es correcte.
     */
    public static void mailCorrectFormat(String mail) throws InvalidFormatException {
        EmailValidator v = EmailValidator.getInstance();
        if(!v.isValid(mail)){
            throw new InvalidFormatException("Email field is not correct");
        }
    }

    /**
     * Funcio encarregada de comprovar si el format del mail es correcte en el sign in.
     *
     * @param usernameField String que conte el mail.
     *
     * @return true si es correcte, false altrament.
     */
    public static boolean mailInSignIn(String usernameField){
        try {
            mailCorrectFormat(usernameField);
            return true;
        } catch (InvalidFormatException e) {
            return false;
        }
    }

    /**
     * Metode que s'encarrega de comprovar si la password concorda amb el camp de confirmacio de password.
     *
     * @param password Password que s'ha introduit.
     * @param passwordConfirmation Password confirmation que s'ha introduit.
     *
     * @throws InvalidFormatException Es tira si el format de les dades no es correcte.
     */
    public static void passwordConfirm(String password, String passwordConfirmation) throws InvalidFormatException {
        if(!password.equals(passwordConfirmation)){
            throw new InvalidFormatException("Password field and password do not match");
        }
    }

    /**
     * Funcio que s'encarrega de comprovar si el format de la password es correcte o no.
     *
     * @param password Password de la qual es vol comprovar el format.
     *
     * @throws InvalidFormatException Es tira si el format de les dades no es correcte.
     */
    public static void passwordCorrectFormat(String password) throws InvalidFormatException{
        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasLowercase = !password.equals(password.toUpperCase());
        boolean hasNumber  = password.matches(".*\\d.*");
        boolean isLongEnough = password.length() > 7;

        if(!(hasUppercase && hasLowercase && hasNumber && isLongEnough)){
            throw new InvalidFormatException("Invalid password: It must be 8 characters long, contain an uppercase letter, a lowercase letter and a number");
        }
    }

    /**
     * Funcio que s'encarrega de comprovar si la password es correcta o no quan un es registra.
     *
     * @param password Password que ha introduit l'usuari.
     * @param passwordConfirmation Confirmacio de password que ha introduit l'usuari.
     *
     * @throws InvalidFormatException Es tira si el format de les dades no es correcte.
     */
    public static void signUpPasswordIsCorrect(String password,String passwordConfirmation) throws InvalidFormatException {
        passwordConfirm(password, passwordConfirmation);
        passwordCorrectFormat(password);
    }

    /**
     * Getter de l'edat de l'usuari.
     *
     * @param age Edat de l'usuari en format string.
     *
     * @return Retorna un int que conte l'edat de l'usuari.
     */
    public static int getAge(String age) throws NumberFormatException{
        return Integer.parseInt(age);
    }

    /**
     * Mira si un camp esta buit o no.
     *
     * @param fieldContent Contingut del camp que s'esta comprovant.
     * @param fieldName Nom del camp que s'esta comprovant.
     *
     * @throws EmptyTextFieldException Es tira si el camp esta buit.
     */
    public static void isEmpty(String fieldContent, String fieldName) throws EmptyTextFieldException {
        if(fieldContent.isEmpty()){
            throw new EmptyTextFieldException("The field " + fieldName + " cannot be empty!");
        }
    }

    /**
     * Mira si s'ha seleccionat alguna dada en tots els camps de l'edit panel.
     *
     * @param img Foto del perfil de l'usuari.
     * @param description Descripcio del perfil de l'usuari.
     * @param java Indica si a l'usuari li agrada Java o no.
     * @param c Indica si a l'usuari li agrada C o no.
     *
     * @throws InvalidFormatException Es tira si el format de les dades no es correcte.
     */
    public static void checkEditProfileNewData(ImageIcon img, String description, boolean java, boolean c) throws InvalidFormatException {
        if(img == null) {
            throw new InvalidFormatException("An image must be selected!");
        }
        if(description.isEmpty()) {
            throw new InvalidFormatException("Description field must be added!");
        }
        if(!java &&!c) {
            throw new InvalidFormatException("A favourite programming language must be added!");
        }
    }
}


package model;

import org.apache.commons.validator.routines.EmailValidator;

import javax.swing.*;

public class UserManager {
    /**
     * Funció que s'encarrega de comprovar si l'usuari és major d'edat o no.
     *
     * @return Retorna true si es major d'edat, false sino.
     */
    public static void isAdult(String age) throws InvalidFormatException {
        try{
            int a = getAge(age);
            if(a < 18){
                throw new InvalidFormatException("In order to register you have to be at least 18");
            }
        }catch (NumberFormatException e1){
            throw new InvalidFormatException("Age field must be a number");
        }

    }

    /**
     * Funcio que s'encarrega de comprovar si el format del mail es correcte o no.
     *
     * @return Retorna true si el format es correcte, false sino.
     */
    public static void mailCorrectFormat(String mail) throws InvalidFormatException {
        EmailValidator v = EmailValidator.getInstance();
        if(!v.isValid(mail)){
            throw new InvalidFormatException("Email field is not correct");
        }
    }

    public static boolean mailInSignIn(String usernameField){
        try{
            mailCorrectFormat(usernameField);
            return true;
        } catch (InvalidFormatException e) {
            return false;
        }
    }

    /**
     * Funcio que s'encarrega de comprovar si la password concorda amb el camp de confirmacio de password.
     *
     * @return Retorna true si concorda, false sino.
     */
    public static void passwordConfirm(String password, String passwordConfirmation) throws InvalidFormatException {
        if(!password.equals(passwordConfirmation)){
            throw new InvalidFormatException("Password field and password do not match");
        }
    }

    /**
     * Funció que s'encarrega de comprovar si el format de la password és correcte o no
     *
     * @return Retorna true si el format és correcte, false sinó.
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
     * Funció que s'encarrega de comprovar si la password es correcta o no quan un es registra.
     * @return Retorna true si es correcta, false sino.
     */
    public static void signUpPasswordIsCorrect(String password,String passwordConfirmation) throws InvalidFormatException {
        passwordConfirm(password, passwordConfirmation);
        passwordCorrectFormat(password);
    }

    /**
     * Getter de l'edat de l'usuari.
     * @return Retorna un int que conté l'edat de l'usuari.
     */
    public static int getAge(String age) throws NumberFormatException{
        int a = Integer.parseInt(age);
        return a;
    }

    public static void isEmpty(String fieldContent, String fieldName) throws EmptyTextFieldException {
        if(fieldContent.isEmpty()){
            throw new EmptyTextFieldException("The field "+fieldName+" cannot be empty!");
        }
    }

    public static void checkEditProfileNewData(ImageIcon img, String description, boolean java, boolean c) throws InvalidFormatException {
        if(img == null){
            throw new InvalidFormatException("An image must be selected!");
        }
        if(description.isEmpty()){
            throw new InvalidFormatException("Description field must be added!");
        }
        if((java == false)&&(c == false)){
            throw new InvalidFormatException("A favourite programming language must be added!");
        }
    }
}


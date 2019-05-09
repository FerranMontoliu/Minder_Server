package model;

import org.apache.commons.validator.routines.EmailValidator;

/**
 * Classe que comprova els camps en el sign-up server per tal de comprovar si són correctes
 */
public class UserChecker {

    /**
     * Metode que comprova si MIN_AGE < MAX_AGE
     * @param min
     * @param max
     * @return
     */
    public boolean checkAgeFilters(int min, int max) {
        if(min < max) {
            return true;
        }
        return false;
    }
    /**
     * Metode que comprova si el mail compleix el format
     *
     * @return Retorna true si el format es correcte, false sino.
     */
    public boolean mailCorrectFormat(String mail){
        boolean ok = true;
        EmailValidator v = EmailValidator.getInstance();
        if(!v.isValid(mail)){
            ok = false;
        }
        return ok;
    }
    /**
     * Metode que comprova si la password concorda amb el camp de confirmacio de password.
     *
     * @return Retorna true si concorda, false sino.
     */
    public boolean passwordConfirm(String password, String passwordConfirmation) {
        if(!password.equals(passwordConfirmation)){
            return false;
        }
        return true;
    }

    /**
     * Funció que s'encarrega de comprovar si el format de la password és correcte o no
     *
     * @return Retorna true si el format és correcte, false sinó.
     */
    public boolean passwordCorrectFormat(String password) {
        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasLowercase = !password.equals(password.toUpperCase());
        boolean hasNumber  = password.matches(".*\\d.*");
        boolean isLongEnough = password.length() > 7;

        if(!(hasUppercase && hasLowercase && hasNumber && isLongEnough)){
            return false;
        }
        return true;
    }
}

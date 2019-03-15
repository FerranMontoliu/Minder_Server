import model.Json;
import model.ServerConfig;
import model.User;

public class Main {

    /**
     * Mètode principal del programa
     *
     * @param args Paràmetres d'entrada del programa.
     */
    public static void main(String[] args) {
        ServerConfig sc = Json.parseJson();
        User u = new User("name", 18, true, "test@example.com", "Password1", "Password1");
        String s = u.imageToBase64();
        u.base64ToImage(s);
    }
}


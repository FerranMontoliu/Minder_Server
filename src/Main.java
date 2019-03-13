import model.Json;
import model.ServerConfig;

public class Main {

    /**
     * Mètode principal del programa
     *
     * @param args Paràmetres d'entrada del programa.
     */
    public static void main(String[] args) {
        ServerConfig sc = Json.parseJson();
    }
}

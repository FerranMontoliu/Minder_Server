import model.Json;
import model.ServerConfig;

public class Main {
    public static void main(String[] args) {
        ServerConfig sc = Json.parseJson();
    }
}

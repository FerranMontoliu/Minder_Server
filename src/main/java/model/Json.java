package model;

import com.google.gson.Gson;
import network.ServerConfig;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Json {
    /**
     * Funcio encarregada de parsejar el Json per a extreure'n la informacio.
     *
     * @return Retorna un ServerConfig que conte tota la informacio del Json.
     */
    public static ServerConfig parseJson() {
        ServerConfig c = null;
        try {
            Gson gson = new Gson();
            FileReader nomFitxer = new FileReader("data/config.json");
            c = gson.fromJson(new BufferedReader(nomFitxer), ServerConfig.class);
        } catch (FileNotFoundException error) {
            System.out.println("Error! Fitxer de configuració no trobat!");
        }
        return c;
    }
}

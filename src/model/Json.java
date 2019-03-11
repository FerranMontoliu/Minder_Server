package model;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Json {
    /**
     * Funció encarregada de parsejar el Json per a extreure'n la informació
     *
     * @return Retorna un array d'activitats que conté tota la informació del Json
     */
    public static ServerConfig parseJson() {
        ServerConfig c = null;
        try {
            Gson gson = new Gson();
            FileReader nomFitxer = new FileReader("data/config.json");
            c = gson.fromJson(new BufferedReader(nomFitxer), ServerConfig.class);
        } catch (FileNotFoundException error) {
            System.out.println("Error! Fitxer no trobat!");
        }
        return c;
    }
}

package network;

import controller.Controller;
import model.Json;
import model.entity.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

public class Server extends Thread {

    private boolean isOn;
    private ServerSocket sSocket;

    private Controller controlador;

    private LinkedList<DedicatedServer> dServers;

    private ArrayList<User> model;

    /**
     * Constructor del servidor.
     *
     * @param model Llista de productes amb tota la informació d'aquests.
     * @param controlador Controlador del servidor.
     */
    public Server(ArrayList<User> model, Controller controlador) {
        try {
            this.isOn = false;
            this.model = model;

            ServerConfig sc = Json.parseJson();
            this.sSocket = new ServerSocket(sc.getListenerPort());
            this.dServers = new LinkedList<>();

            this.controlador = controlador;
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mètode encarregat d'iniciar el servidor.
     *
     */
    public void startServer() {
        isOn = true;
        this.start();
    }

    /**
     * Mètode que s'executa un cop iniciat el servidor. Es dedica a escoltar peticions i, cada cop que un client es vol connectar, obra un nou thread de DedicatedServer.
     */
    public void run()  {
        while (isOn) {
            try {
                Socket sClient = sSocket.accept();

                DedicatedServer pwClient = new DedicatedServer(sClient, dServers, this, controlador);
                dServers.add(pwClient);

                pwClient.startDedicatedServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (DedicatedServer dServer : dServers) {
            dServer.stopDedicatedServer();
        }
    }
}



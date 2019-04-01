package network;

import model.User;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

public class DedicatedServer extends Thread {

    private boolean isOn;
    private Socket sClient;
    private DataInputStream dataInput;
    private ObjectOutputStream objectOut;
    private LinkedList<DedicatedServer> clients;
    private ArrayList<User> model;
    private Server server;

    /**
     * Constructor de la classe.
     *
     * @param model Llista de productes amb tota la informació sobre aquests.
     * @param sClient Socket que connecta amb el client.
     * @param clients Llista de connexions (tantes connexions com clients connectants simultàneament).
     * @param server Servidor que controla els threads.
     */
    public DedicatedServer(ArrayList<User> model, Socket sClient, LinkedList<DedicatedServer> clients, Server server) {
        this.isOn = false;
        this.model = model;
        this.sClient = sClient;
        this.clients = clients;
        this.server = server;
    }

    /**
     * Mètode encarregat de crear un nou thread del servidor dedicat.
     *
     */
    public void startDedicatedServer() {
        isOn = true;
        this.start();
    }

    /**
     * Mètode encarregat d'aturar un thread d'un servidor dedicat.
     */
    public void stopDedicatedServer() {
        this.isOn = false;
        this.interrupt();
    }

    /**
     * Mètode que s'executa quan es crea un nou servidor dedicat.
     *
     */
    public void run() {
        try {
            dataInput = new DataInputStream(sClient.getInputStream());
            objectOut = new ObjectOutputStream(sClient.getOutputStream());

            objectOut.writeObject(new UserState(model));
            while(isOn) {
                //Rebre dades i tractar-les.

                updateAllClients();
            }
        } catch (IOException e1) {
            stopDedicatedServer();
            clients.remove(this);
        }
    }

    /**
     * Getter del canal de sortida.
     *
     * @return Canal de sortida.
     */
    private ObjectOutputStream getOutChannel() {
        return objectOut;
    }

    /**
     * Mètode encarregat d'enviar les noves dades actualitzades a tots els clients que hi ha connectats al servidor.
     */
    private void updateAllClients() {
        ObjectOutputStream outStream;
        for(DedicatedServer dServer : clients) {
            outStream = dServer.getOutChannel();
            try {
                outStream.reset();
                outStream.writeObject(new UserState(model));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


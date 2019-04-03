package network;

import model.User;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

public class DedicatedServer extends Thread {

    private static final char LOGIN_USER = 'a';
    private static final char REGISTER_USER = 'b';
    private static final char EDIT_PROFILE = 'c';
    private static final char USER_MATCHED = 'd';
    private static final char USER_UNMATCHED = 'e';
    private static final char LOAD_CHAT = 'f';
    private static final char SEND_MESSAGE = 'g';

    private boolean isOn;
    private Socket sClient;
    private DataInputStream dataInput;
    private DataOutputStream dataOutput;
    private ObjectInputStream objectIn;
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
            dataOutput = new DataOutputStream(sClient.getOutputStream());
            objectIn = new ObjectInputStream(sClient.getInputStream());
            objectOut = new ObjectOutputStream(sClient.getOutputStream());

            objectOut.writeObject(new UserState(model));
            while(isOn) {

                //Llegir char que indica quin missatge rebrà el servidor:
                char func = dataInput.readChar();
                switch(func){
                    case LOGIN_USER:
                        //TODO: llegir user.
                        //TODO: comprovar a la base de dades si l'usuari existeix. En cas afirmatiu retornar true, altrament retornar false.
                        boolean userExistsL = false;
                        dataOutput.writeBoolean(userExistsL);
                        break;

                    case REGISTER_USER:
                        //TODO: llegir user.
                        //TODO: comprovar a la base de dades si l'usuari existeix. En cas afirmatiu retornar true, altrament retornar false.
                        boolean userExistsR = false;
                        dataOutput.writeBoolean(userExistsR);
                        break;

                    case EDIT_PROFILE:
                        //TODO: llegir user.
                        //TODO: escriure els nous paràmetres a la base de dades.
                        break;

                    case USER_MATCHED:
                        //TODO: llegir 2 usuaris que han fet match.
                        //TODO: connectar amb la base de dades i ficar cada usuari a la llista de match de l'altre.
                        break;

                    case USER_UNMATCHED:
                        //TODO: llegir 2 usuaris que han fet match.
                        //TODO: connectar amb la base de dades i treure cada usuari de la llista de match de l'altre.
                        break;

                    case LOAD_CHAT:
                        //TODO: llegir 2 usuaris que conformen el xat.
                        //TODO: connectar amb la base de dades i agafar el seu xat.
                        break;

                    case SEND_MESSAGE:
                        //TODO: llegir String.
                        //TODO: connectar amb la base de dades i afegir el String a la llista de missatges que conforma el xat.
                        break;
                }
                //updateAllClients();
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


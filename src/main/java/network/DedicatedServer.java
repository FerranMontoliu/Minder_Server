package network;

import model.database.dao.UserDAO;
import model.entity.User;

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
    private Server server;

    /**
     * Constructor de la classe.
     *
     * @param sClient Socket que connecta amb el client.
     * @param clients Llista de connexions (tantes connexions com clients connectants simultàneament).
     * @param server Servidor que controla els threads.
     */
    public DedicatedServer(Socket sClient, LinkedList<DedicatedServer> clients, Server server) {
        this.isOn = false;
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
     * Mètode que s'executa quan es crea un nou servidor dedicat i que gestiona totes les peticions que passen per aquest.
     *
     */
    public void run() {
        try {
            dataInput = new DataInputStream(sClient.getInputStream());
            dataOutput = new DataOutputStream(sClient.getOutputStream());
            objectIn = new ObjectInputStream(sClient.getInputStream());
            objectOut = new ObjectOutputStream(sClient.getOutputStream());

            while(isOn) {
                //Llegir char que indica quin missatge rebrà el servidor:
                char func = dataInput.readChar();
                switch(func){
                    case LOGIN_USER:
                        boolean userExistsL = true;
                        try {
                            User u1 = (User) objectIn.readObject();
                            UserDAO userDAO = new UserDAO();
                            userExistsL = userDAO.existsUser(u1);
                            User dbUser = new User("Polete", "polete");
                            //TODO: set salt
                            dataOutput.writeBoolean(userExistsL);
                            if(userExistsL) {
                                objectOut.writeObject(dbUser);
                                User us = (User) objectIn.readObject();
                                //TODO: comprovo que la password hashejada coincideixi
                                boolean ok = true;
                                dataOutput.writeBoolean(ok);
                                if(ok) {
                                    User user = new User("polete", "polete");//TODO: agafa user base de dades complet
                                    objectOut.writeObject(user);
                                }
                            }
                        } catch (ClassNotFoundException e1) {
                            e1.printStackTrace();
                        }
                        break;

                    case REGISTER_USER:
                        boolean userExistsR = false;
                        try {
                            User u2 = (User) objectIn.readObject();
                            //TODO: comprovar a la base de dades si l'usuari existeix. En cas afirmatiu retornar true, altrament retornar false.
                            //userExistsR = check();
                        } catch (ClassNotFoundException e2) {
                            e2.printStackTrace();
                        }
                        dataOutput.writeBoolean(userExistsR);
                        break;

                    case EDIT_PROFILE:
                        try {
                            User u3 = (User) objectIn.readObject();
                            //TODO: escriure els nous paràmetres a la base de dades.
                        } catch (ClassNotFoundException e3) {
                            e3.printStackTrace();
                        }
                        break;

                    case USER_MATCHED:
                        try {
                            User u4 = (User) objectIn.readObject();
                            User u5 = (User) objectIn.readObject();
                            //TODO: connectar amb la base de dades i ficar cada usuari a la llista de match de l'altre.
                        } catch (ClassNotFoundException e4) {
                            e4.printStackTrace();
                        }
                        break;

                    case USER_UNMATCHED:
                        try {
                            User u6 = (User) objectIn.readObject();
                            User u7 = (User) objectIn.readObject();
                            //TODO: connectar amb la base de dades i treure cada usuari de la llista de match de l'altre.
                        } catch (ClassNotFoundException e5) {
                            e5.printStackTrace();
                        }
                        break;

                    case LOAD_CHAT:
                        try {
                            User u8 = (User) objectIn.readObject();
                            User u9 = (User) objectIn.readObject();
                            //TODO: connectar amb la base de dades i agafar el seu xat.
                        } catch (ClassNotFoundException e6) {
                            e6.printStackTrace();
                        }
                        break;

                    case SEND_MESSAGE:
                        String message = dataInput.readUTF();
                        //TODO: connectar amb la base de dades i afegir el String a la llista de missatges que conforma el xat.
                        break;
                    default:
                        System.out.println("WTF està passant aquí?!");
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
                outStream.writeObject(new User("",""));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


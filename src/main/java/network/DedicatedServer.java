package network;

import model.database.dao.ChatDAO;
import model.database.dao.MatchDAO;
import model.database.dao.UserDAO;
import model.entity.Chat;
import model.entity.MatchLoader;
import model.entity.Message;
import model.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    private static final char USER_MATCH_LIST = 'h';

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
                UserDAO userDAO = new UserDAO();
                MatchDAO matchDAO = new MatchDAO();
                ChatDAO chatDAO = new ChatDAO();
                switch(func){
                    case LOGIN_USER:
                        boolean userExistsL;
                        try {
                            User loginUser = (User) objectIn.readObject();
                            userExistsL = userDAO.existsUser(loginUser);
                            dataOutput.writeBoolean(userExistsL);
                            if(userExistsL) {
                                User realUser = userDAO.getUser(loginUser);
                                boolean sameUser = realUser.getPassword().equals(loginUser.getPassword());
                                dataOutput.writeBoolean(sameUser);
                                if(sameUser) {
                                    objectOut.writeObject(realUser);
                                }
                            }
                        } catch (ClassNotFoundException e1) {
                            e1.printStackTrace();
                        }
                        break;

                    case REGISTER_USER:
                        boolean userExistsR;
                        try {
                            User registeringUser = (User) objectIn.readObject();
                            userExistsR = userDAO.existsUser(registeringUser);
                            dataOutput.writeBoolean(userExistsR);
                            if(!userExistsR){
                                userDAO.addUser(registeringUser);
                            }
                        } catch (ClassNotFoundException e2) {
                            e2.printStackTrace();
                        }
                        break;

                    case EDIT_PROFILE:
                        try {
                            User u3 = (User) objectIn.readObject();
                            userDAO.updateInfoUser(u3);
                        } catch (ClassNotFoundException e3) {
                            e3.printStackTrace();
                        }
                        break;

                    case USER_MATCHED:
                        try {
                            User u4 = (User) objectIn.readObject();
                            User u5 = (User) objectIn.readObject();
                            matchDAO.addMatch(u4.getUsername(), u5.getUsername());
                        } catch (ClassNotFoundException e4) {
                            e4.printStackTrace();
                        }
                        break;

                    case USER_UNMATCHED:
                        try {
                            User u6 = (User) objectIn.readObject();
                            User u7 = (User) objectIn.readObject();
                            matchDAO.deleteMatch(u6.getUsername(), u7.getUsername());
                        } catch (ClassNotFoundException e5) {
                            e5.printStackTrace();
                        }
                        break;

                    case LOAD_CHAT:
                        try {
                            String sender = dataInput.readUTF();
                            String receiver = dataInput.readUTF();
                            Chat storedChat = chatDAO.loadChat(sender, receiver);
                            objectOut.writeObject(storedChat);
                        } catch (IOException e6) {
                            e6.printStackTrace();
                        }
                        break;

                    case SEND_MESSAGE:
                        Message m = null;
                        try {
                            m = (Message) objectIn.readObject();
                        } catch (ClassNotFoundException e7) {
                            e7.printStackTrace();
                        }
                        chatDAO.sendMessage(m);
                        break;

                    case USER_MATCH_LIST:
                        try {
                            String username = dataInput.readUTF();
                            MatchLoader matchLoader = matchDAO.getUserMatches(username);
                            objectOut.writeObject(matchLoader);
                        }catch(Exception e1){
                            e1.printStackTrace();
                        }
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


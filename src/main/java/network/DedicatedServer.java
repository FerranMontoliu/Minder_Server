package network;

import controller.Controller;
import model.database.dao.*;
import model.entity.Chat;
import model.entity.MatchLoader;
import model.entity.Message;
import model.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.*;
import java.net.Socket;
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
    private static final char CONNECT_LIKE = 'i';
    private static final char CONNECT_DISLIKE = 'j';
    private static final char CONNECT_USER = 'k';
    private static final char EDIT_PREFERENCES = 'l';
    private static final char USER_INFO = 'm';

    private boolean isOn;
    private Socket sClient;
    private DataInputStream dataInput;
    private DataOutputStream dataOutput;
    private ObjectInputStream objectIn;
    private ObjectOutputStream objectOut;
    private LinkedList<DedicatedServer> clients;
    private String clientUser;
    private Server server;

    private Controller controlador;

    /**
     * Constructor de la classe.
     *
     * @param sClient Socket que connecta amb el client.
     * @param clients Llista de connexions (tantes connexions com clients connectants simultàneament).
     * @param server Servidor que controla els threads.
     */
    public DedicatedServer(Socket sClient, LinkedList<DedicatedServer> clients, Server server, Controller controlador) {
        this.isOn = false;
        this.sClient = sClient;
        this.clients = clients;
        this.server = server;
        this.clientUser = "";
        this.controlador = controlador;
    }

    /**
     * Metode encarregat de crear un nou thread del servidor dedicat.
     *
     */
    public void startDedicatedServer() {
        isOn = true;
        this.start();
    }

    /**
     * Metode encarregat d'aturar un thread d'un servidor dedicat.
     */
    public void stopDedicatedServer() {
        this.isOn = false;
        this.interrupt();
    }

    /**
     * Metode que s'executa quan es crea un nou servidor dedicat i que gestiona totes les peticions que passen per aquest.
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
                LikeDAO likeDAO = new LikeDAO();

                switch(func){
                    case LOGIN_USER:
                        boolean userExistsL;
                        try {
                            User loginUser = (User) objectIn.readObject();
                            userExistsL = userDAO.existsUser(loginUser);
                            dataOutput.writeBoolean(userExistsL);
                            if(userExistsL) {
                                User realUser = userDAO.getUser(loginUser);
                                PasswordEncoder encoder = new BCryptPasswordEncoder();
                                System.out.println(realUser.getPassword());
                                System.out.println(loginUser.getPassword());
                                boolean sameUser = encoder.matches(loginUser.getPassword(), realUser.getPassword());
                                dataOutput.writeBoolean(sameUser);
                                if(sameUser) {
                                    System.out.println("Són iguals");//TODO: borrar en acabar de debugar.
                                    objectOut.writeObject(realUser);
                                }
                            }
                        } catch (ClassNotFoundException e1) {
                            e1.printStackTrace();
                        }
                        break;

                    case REGISTER_USER:
                        try {
                            User registeringUser = (User) objectIn.readObject();
                            boolean usernameExistsR = userDAO.existsUser(registeringUser);
                            boolean mailExistsR = userDAO.existsUser(new User(registeringUser.getMail(), registeringUser.getPassword()));
                            if((!usernameExistsR) && (!mailExistsR)){
                                dataOutput.writeBoolean(true);
                                userDAO.addUser(registeringUser);
                            }else{
                                dataOutput.writeBoolean(false);
                            }
                        } catch (ClassNotFoundException e2) {
                            e2.printStackTrace();
                        }
                        break;

                    case EDIT_PROFILE:
                        try {
                            User u3 = (User) objectIn.readObject();
                            userDAO.updateInfoUser(u3);
                            dataOutput.writeBoolean(true);
                        } catch (ClassNotFoundException e3) {
                            e3.printStackTrace();
                        }
                        break;

                    case USER_MATCHED:
                        try {
                            User u4 = (User) objectIn.readObject();
                            User u5 = (User) objectIn.readObject();
                            matchDAO.addMatch(u4.getUsername(), u5.getUsername());
                            controlador.updateWindow();
                        } catch (ClassNotFoundException e4) {
                            e4.printStackTrace();
                        }
                        break;

                    case USER_UNMATCHED:
                        try {
                            String sender = dataInput.readUTF();
                            String deleted = dataInput.readUTF();
                            matchDAO.deleteMatch(sender, deleted);
                            chatDAO.deleteMessages(sender, deleted);
                            controlador.updateWindow();

                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                        break;

                    case LOAD_CHAT:
                        try {
                            String sender = dataInput.readUTF();
                            String receiver = dataInput.readUTF();
                            Chat storedChat = chatDAO.loadChat(sender, receiver);
                            boolean existsChat = storedChat.getMessages().size() > 0;
                            dataOutput.writeBoolean(existsChat);
                            if(existsChat){
                                objectOut.writeObject(storedChat);
                            }
                        } catch (IOException e6) {
                            e6.printStackTrace();
                        }
                        break;

                    case SEND_MESSAGE:
                        clientUser = dataInput.readUTF();
                        while (isOn) {
                            Message message = (Message) objectIn.readObject();
                            String receiver = message.getDestination();
                            System.out.println(message.toString());
                            chatDAO.sendMessage(message);
                            Chat storedChat = chatDAO.loadChat(clientUser, receiver);
                            sendToDestination(receiver, storedChat);
                        }
                        break;

                    case USER_MATCH_LIST:
                        try {
                            String username = dataInput.readUTF();
                            MatchLoader matchLoader = matchDAO.getUserMatches(username);
                            objectOut.writeObject(matchLoader);
                        }catch(IOException e1){
                            e1.printStackTrace();
                        }
                        break;

                    case CONNECT_USER: //TODO: Solicita un USER a visualitzar pel connect panel. Per acabar.
                        try {
                            User associatedUser = (User) objectIn.readObject();
                            String nextUsername = userDAO.getNextUser(associatedUser.getUsername(), associatedUser.getMinAge(), associatedUser.getMaxAge(), associatedUser.isPremium(), associatedUser.getLikesC(), associatedUser.getLikesJava());
                            User nextUser = userDAO.getConnectUser(nextUsername);
                            objectOut.writeObject(nextUser);
                        } catch (ClassNotFoundException e8) {
                            e8.printStackTrace();
                        }
                        //TODO: demanar al userDAO la llista d'usuaris i llavors controlar quin es el següent usuari que li toca visualitzar
                        break;

                    case CONNECT_LIKE: //TODO: Fas LIKE en el connect panel.
                        String sender = dataInput.readUTF();
                        String liked = dataInput.readUTF();
                        likeDAO.addLike(sender, liked);

                        boolean isMatch = matchDAO.isMatch(liked, sender);
                        dataOutput.writeBoolean(isMatch);
                        break;

                    case CONNECT_DISLIKE: //TODO: Fas DISLIKE en el connect panel.
                        String source = dataInput.readUTF();
                        String disliked = dataInput.readUTF();
                        likeDAO.addDislike(source, disliked);
                        //Nose si això s'ha de guardar, però si s'ha de fer només, cal escriure la comanda del DAO.
                        //Actualitzar la vista
                        break;

                    case EDIT_PREFERENCES:
                        try {
                            User updatedUser = (User) objectIn.readObject();
                            System.out.println("update pref: "+updatedUser.getUsername() + updatedUser.getMaxAge());
                            userDAO.updatePreferences(updatedUser);
                            boolean editDone = true; //hi pot haver algun cas en que no es pugui fer el canvi?
                            dataOutput.writeBoolean(editDone);
                        } catch (ClassNotFoundException e9) {
                            e9.printStackTrace();
                        }
                        break;
                    case USER_INFO:
                        String username = dataInput.readUTF();
                        User infoUser = userDAO.getConnectUser(username);
                        objectOut.writeObject(infoUser);
                        break;

                    default:
                        System.out.println("WTF està passant aquí?!");
                        break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            clients.remove(this);
        } finally {
            try {
                dataOutput.close();
            } catch (IOException e) {}
            try {
                dataInput.close();
            } catch (IOException e) {}
            try {
                objectOut.close();
            } catch (IOException e) {}
            try {
                objectIn.close();
            } catch (IOException e) {}
            try {
                sClient.close();
            } catch (IOException e) {}
            clients.remove(this);
        }
    }

    public String getClientUser() {
        return clientUser;
    }

    private void sendToDestination(String receiver, Chat dbChat) {
        System.out.println("Size:"+clients.size());
        for(DedicatedServer ds: clients){
            System.out.println("Receiver: "+receiver+" Sender: "+clientUser);
            if((ds.getClientUser().equals(receiver)) || (ds.getClientUser().equals(clientUser))){
                System.out.println("En algun entro?");
                ds.updateMessagesToClient(dbChat, receiver, clientUser);
            }

        }
    }

    private void updateMessagesToClient(Chat dbChat, String receiver, String sender) {
        try {
            MatchDAO matchDAO = new MatchDAO();
            boolean stillMatch = matchDAO.existsMatch(receiver, sender);
            dataOutput.writeBoolean(stillMatch);
            if(stillMatch){
                objectOut.writeObject(dbChat);
            }else{
                ChatDAO chatDAO = new ChatDAO();
                chatDAO.deleteMessages(receiver, sender);
            }
        } catch (IOException e) {
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
     * Metode encarregat d'enviar les noves dades actualitzades a tots els clients que hi ha connectats al servidor.
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


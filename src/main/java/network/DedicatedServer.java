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
    private static final char USER_DISCONNECTS = 'n';

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
     * @param controlador Controlador de la vista.
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
            //Obrim els streams d'entrada i sortida:
            dataInput = new DataInputStream(sClient.getInputStream());
            dataOutput = new DataOutputStream(sClient.getOutputStream());
            objectIn = new ObjectInputStream(sClient.getInputStream());
            objectOut = new ObjectOutputStream(sClient.getOutputStream());

            while(isOn) {
                //Llegir char que indica quin missatge rebrà el servidor:
                char func = dataInput.readChar();

                switch(func){
                    case LOGIN_USER:
                        loginUser();
                        break;

                    case REGISTER_USER:
                        registerUser();
                        break;

                    case EDIT_PROFILE:
                        editProfile();
                        break;

                    case USER_MATCHED:
                        userMatched();
                        break;

                    case USER_UNMATCHED:
                        userUnmatched();
                        break;

                    case LOAD_CHAT:
                        loadChat();
                        break;

                    case SEND_MESSAGE:
                        sendMessage();
                        break;

                    case USER_MATCH_LIST:
                        userMatchList();
                        break;

                    case CONNECT_USER:
                        connectUser();
                        break;

                    case CONNECT_LIKE:
                        connectLike();
                        break;

                    case CONNECT_DISLIKE:
                        connectDislike();
                        break;

                    case EDIT_PREFERENCES:
                        editPreferences();
                        break;

                    case USER_INFO:
                        userInfo();
                        break;

                    case USER_DISCONNECTS:
                        userDisconnects();
                        break;
                }
            }
        } catch(IOException | ClassNotFoundException e) {
            clients.remove(this);
        } finally {
            //Tanquem streams:
            try {
                dataOutput.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
            try {
                dataInput.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
            try {
                objectIn.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
            try {
                objectOut.close();
            } catch(IOException e) {
                e.printStackTrace();
            }

            //Tanquem socket:
            try {
                sClient.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
            clients.remove(this);
        }
    }

    /**
     * Metode que cridem quan l'usuari es vol desconnectar de la bbdd.
     *
     * @throws IOException Es tira quan hi ha hagut algun error enviant o rebent dades pels streams.
     * @throws ClassNotFoundException Es tira quan s'intenta llegir un objecte d'una classe pero no es troba aquesta classe.
     */
    private void userDisconnects() throws IOException, ClassNotFoundException {
        UserDAO userDAO = new UserDAO();
        String userDisc = (String) objectIn.readObject();
        userDAO.userDisconnects(userDisc);
    }

    /**
     * Metode que cridem quan volem enviar la info d'un usuari.
     *
     * @throws IOException Es tira quan hi ha hagut algun error enviant o rebent dades pels streams.
     */
    private void userInfo() throws IOException {
        UserDAO userDAO = new UserDAO();
        String username = dataInput.readUTF();
        User infoUser = userDAO.getConnectUser(username);
        objectOut.writeObject(infoUser);
    }

    /**
     * Metode que cridem quan volem editar les preferencies d'un usuari.
     *
     * @throws IOException Es tira quan hi ha hagut algun error enviant o rebent dades pels streams.
     */
    private void editPreferences() throws IOException {
        UserDAO userDAO = new UserDAO();
        try {
            User updatedUser = (User) objectIn.readObject();
            userDAO.updatePreferences(updatedUser);
            boolean editDone = true;
            dataOutput.writeBoolean(editDone);
        } catch(ClassNotFoundException e9) {
            e9.printStackTrace();
        }
    }

    /**
     * Metode que cridem quan volem obtenir el seguent usuari a mostrar en el panell de fer matchs del client.
     *
     * @throws IOException Es tira quan hi ha hagut algun error enviant o rebent dades pels streams.
     */
    private void connectUser() throws IOException {
        UserDAO userDAO = new UserDAO();
        try {
            User associatedUser = (User) objectIn.readObject();
            String nextUsername = userDAO.getNextUser(associatedUser.getUsername(), associatedUser.getMinAge(), associatedUser.getMaxAge(), associatedUser.isPremium(), associatedUser.getLikesC(), associatedUser.getLikesJava());
            User nextUser = userDAO.getConnectUser(nextUsername);
            objectOut.writeObject(nextUser);
        } catch(ClassNotFoundException e8) {
            e8.printStackTrace();
        }
    }

    /**
     * Metode que es crida quan es fica dislike a un usuari en el panell de match.
     *
     * @throws IOException Es tira quan hi ha hagut algun error enviant o rebent dades pels streams.
     */
    private void connectDislike() throws IOException {
        LikeDAO likeDAO = new LikeDAO();

        String source = dataInput.readUTF();
        String disliked = dataInput.readUTF();
        likeDAO.addDislike(source, disliked);
        controlador.updateBars();

    }

    /**
     * Metode que es crida quan es fica like a un usuari en el panell de match.
     *
     * @throws IOException Es tira quan hi ha hagut algun error enviant o rebent dades pels streams.
     */
    private void connectLike() throws IOException {
        LikeDAO likeDAO = new LikeDAO();
        MatchDAO matchDAO = new MatchDAO();

        String sender = dataInput.readUTF();
        String liked = dataInput.readUTF();
        likeDAO.addLike(sender, liked);

        boolean isMatch = matchDAO.isMatch(liked, sender);
        dataOutput.writeBoolean(isMatch);
        controlador.updateBars();
    }

    /**
     * Metode que es crida per obtenir la llista de matchs d'un usuari de la bbdd.
     */
    private void userMatchList() {
        MatchDAO matchDAO = new MatchDAO();
        try {
            String username = dataInput.readUTF();
            MatchLoader matchLoader = matchDAO.getUserMatches(username);
            objectOut.writeObject(matchLoader);
        } catch(IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Metode que es crida per enviar un missatge.
     *
     * @throws IOException Es tira quan hi ha hagut algun error enviant o rebent dades pels streams.
     * @throws ClassNotFoundException Es tira quan s'intenta llegir un objecte d'una classe pero no es troba aquesta classe.
     */
    private void sendMessage() throws IOException, ClassNotFoundException {
        ChatDAO chatDAO = new ChatDAO();
        clientUser = dataInput.readUTF();
        while(isOn) {
            Message message = (Message) objectIn.readObject();
            String receiver = message.getDestination();
            chatDAO.sendMessage(message);
            Chat storedChat = chatDAO.loadChat(clientUser, receiver);
            sendToDestination(receiver, storedChat);
        }
    }

    /**
     * Metode que es crida quan es vol carregar un xat nou sencer.
     */
    private void loadChat() {
        ChatDAO chatDAO = new ChatDAO();
        try {
            String sender = dataInput.readUTF();
            String receiver = dataInput.readUTF();
            Chat storedChat = chatDAO.loadChat(sender, receiver);
            boolean existsChat = storedChat.getMessages().size() > 0;
            dataOutput.writeBoolean(existsChat);
            if(existsChat) {
                objectOut.writeObject(storedChat);
            }
        } catch(IOException e6) {
            e6.printStackTrace();
        }
    }

    /**
     * Metode que es crida quan un usuari fa unmatch amb un altre.
     */
    private void userUnmatched() {
        MatchDAO matchDAO = new MatchDAO();
        ChatDAO chatDAO = new ChatDAO();
        try {
            String sender = dataInput.readUTF();
            String deleted = dataInput.readUTF();
            matchDAO.deleteMatch(sender, deleted);
            chatDAO.deleteMessages(sender, deleted);
            controlador.updateWindow();
        } catch(IOException e5) {
            e5.printStackTrace();
        }
    }

    /**
     * Metode que es crida quan un usuari fa match amb un altre.
     *
     * @throws IOException Es tira quan hi ha hagut algun error enviant o rebent dades pels streams.
     */
    private void userMatched() throws IOException {
        MatchDAO matchDAO = new MatchDAO();
        try {
            User u4 = (User) objectIn.readObject();
            User u5 = (User) objectIn.readObject();
            matchDAO.addMatch(u4.getUsername(), u5.getUsername());
            controlador.updateWindow();
        } catch(ClassNotFoundException e4) {
            e4.printStackTrace();
        }
    }

    /**
     * Metode que es crida quan un usuari vol editar les dades del seu perfil.
     *
     * @throws IOException Es tira quan hi ha hagut algun error enviant o rebent dades pels streams.
     */
    private void editProfile() throws IOException {
        UserDAO userDAO = new UserDAO();
        try {
            User u3 = (User) objectIn.readObject();
            userDAO.updateInfoUser(u3);
            dataOutput.writeBoolean(true);
        } catch(ClassNotFoundException e3) {
            e3.printStackTrace();
        }
    }

    /**
     * Metode que es crida quan es vol registrar un usuari en la bbdd.
     *
     * @throws IOException Es tira quan hi ha hagut algun error enviant o rebent dades pels streams.
     */
    private void registerUser() throws  IOException {
        UserDAO userDAO = new UserDAO();
        try {
            User registeringUser = (User) objectIn.readObject();
            boolean usernameExistsR = userDAO.existsUser(registeringUser);
            boolean mailExistsR = userDAO.existsUser(new User(registeringUser.getMail(), registeringUser.getPassword()));
            if((!usernameExistsR) && (!mailExistsR)) {
                dataOutput.writeBoolean(true);
                userDAO.addUser(registeringUser);
            } else {
                dataOutput.writeBoolean(false);
            }
        } catch(ClassNotFoundException e2) {
            e2.printStackTrace();
        }
    }

    /**
     * Metode que es crida quan un usuari intenta fer login des del client.
     *
     * @throws IOException Es tira quan hi ha hagut algun error enviant o rebent dades pels streams.
     */
    private void loginUser() throws IOException {
        UserDAO userDAO = new UserDAO();
        boolean userExistsL;
        try {
            User loginUser = (User) objectIn.readObject();
            userExistsL = userDAO.existsUser(loginUser);
            dataOutput.writeBoolean(userExistsL);
            if(userExistsL) {
                User realUser = userDAO.getUser(loginUser);
                PasswordEncoder encoder = new BCryptPasswordEncoder();
                boolean sameUser = encoder.matches(loginUser.getPassword(), realUser.getPassword());
                boolean isConnected = userDAO.isConnected(realUser.getUsername());
                dataOutput.writeBoolean(sameUser && !isConnected);
                if(sameUser && !isConnected) {
                    userDAO.userConnects(realUser.getUsername());
                    objectOut.writeObject(realUser);
                }
            }
        } catch(ClassNotFoundException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Getter de l'usuari que esta connectat.
     *
     * @return Nom de l'usuari que esta connectat.
     */
    private String getClientUser() {
        return clientUser;
    }

    /**
     * Metode encarregat d'enviar un xat al receptor.
     *
     * @param receiver Receptor del xat.
     * @param dbChat Xat entre dos usuaris (un dels quals és el "receiver").
     */
    private void sendToDestination(String receiver, Chat dbChat) {
        for(DedicatedServer ds: clients){
            if((ds.getClientUser().equals(receiver)) || (ds.getClientUser().equals(clientUser))){
                ds.updateMessagesToClient(dbChat, receiver, clientUser);
            }
        }
    }

    /**
     * Metode encarregat d'actualitzar un xat al client.
     *
     * @param dbChat Xat entre dos usuaris (el "sender" i el "receiver").
     * @param receiver Receptor del xat.
     * @param sender Emissor del xat.
     */
    private void updateMessagesToClient(Chat dbChat, String receiver, String sender) {
        try {
            MatchDAO matchDAO = new MatchDAO();
            boolean stillMatch = matchDAO.existsMatch(receiver, sender);
            dataOutput.writeBoolean(stillMatch);
            if(stillMatch) {
                objectOut.writeObject(dbChat);
            } else {
                ChatDAO chatDAO = new ChatDAO();
                chatDAO.deleteMessages(receiver, sender);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}


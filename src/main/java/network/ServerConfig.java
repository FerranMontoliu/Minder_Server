package network;

public class ServerConfig {

    private int port;
    private String serverIP;
    private String name;
    private String user;
    private String password;
    private int listenerPort;

    /**
     * Constructor del ServerConfig.
     *
     * @param port Port de connexió amb la base de dades.
     * @param serverIP IP de la base de dades.
     * @param name Nom de la base de dades.
     * @param user Username de l'admin de la base de dades.
     * @param password Password de l'admin de la base de dades.
     * @param listenerPort Port per on el servidor escolta noves peticions de clients.
     */
    public ServerConfig(int port, String serverIP, String name, String user, String password, int listenerPort) {
        this.port = port;
        this.serverIP = serverIP;
        this.name = name;
        this.user = user;
        this.password = password;
        this.listenerPort = listenerPort;
    }

    /**
     * Getter del port de connexió amb la base de dades.
     *
     * @return Retorna un int que conté el port de connexió amb la base de dades.
     */
    public int getPort() {
        return port;
    }

    /**
     * Getter de la IP del servidor.
     *
     * @return Retorna un String que conté la IP del servidor.
     */
    public String getServerIP() {
        return serverIP;
    }

    /**
     * Getter del nom de la base de dades.
     *
     * @return Retorna un String que conté el nom de la base de dades.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter del nom de l'usuari administrador de la base de dades.
     *
     * @return Retorna un String que conté el nom de l'admin de la base de dades.
     */
    public String getUser() {
        return user;
    }

    /**
     * Getter de la password de l'usuari administrador de la base de dades.
     *
     * @return Retorna un String que conté la password de l'admin de la base de dades.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Getter del port per on el servidor escolta peticions dels clients.
     *
     * @return Retorna un int que conté el port per on el servidor escolta peticions de clients.
     */
    public int getListenerPort() {
        return listenerPort;
    }
}
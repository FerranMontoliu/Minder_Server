package model;

public class ServerConfig {
    private int port;
    private String serverIP;
    private String name;
    private String user;
    private String password;
    private int listernerPort;

    public ServerConfig(int port, String serverIP, String name, String user, String password, int listernerPort) {
        this.port = port;
        this.serverIP = serverIP;
        this.name = name;
        this.user = user;
        this.password = password;
        this.listernerPort = listernerPort;
    }

    public int getPort() {
        return port;
    }

    public String getServerIP() {
        return serverIP;
    }

    public String getName() {
        return name;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public int getListernerPort() {
        return listernerPort;
    }
}
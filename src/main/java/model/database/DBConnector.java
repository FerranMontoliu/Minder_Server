package model.database;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnector {
    private final static String OS = System.getProperty("os.name").toLowerCase();
    private static String username;
    private static String password;
    private static String db;
    private static int port;
    private static String url = "jdbc:mysql://localhost";
    private static Connection conn;
    private static Statement s;
    private static DBConnector instance;

    /**
     * Constructor de la classe encarregada de gestionar la connexio amb la base de dades.
     */
    private DBConnector(String user, String pass, String db, int port) {
        this.username = user;
        this.password = pass;
        this.db = db;
        this.port = port;
        this.url += ":"+port+"/";
        this.url += db;
        this.instance = null;
    }

    /**
     * Metode encarregat de crear la instancia de la connexio amb la nostra base de dades utilitzant el nom de l'admin, la seva password, el nom de la db i el seu port.
     *
     * @return Retorna la instància de la connexió.
     */
    public static DBConnector getInstance(){
        if(instance == null) {
            if(OS.contains("win")) {
                //Windows users:
                instance = new DBConnector("root", "", "minderdb", 3306);
            } else if (OS.contains("mac")){
                //Mac users:
                instance = new DBConnector("root", "root", "minderdb", 3306);
            } else {
                //Other OS users:
                instance = new DBConnector("root", "", "minderdb", 3306);
            }
            instance.connect();
        }
        return instance;
    }

    /**
     * Metode encarregat d'establir la connexio amb la base de dades.
     */
    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Connection");
            conn = (Connection) DriverManager.getConnection(url, username, password);
            if(conn != null) {
                System.out.println("Connexió a base de dades " + url + " ... Ok");
            }
        } catch(SQLException ex) {
            System.out.println("Problema al connectar-nos a la BBDD --> " + url);
        } catch(ClassNotFoundException ex) {
            System.out.println(ex);
        }

    }

    /**
     * Metode encarregat d'executar una query d'nsercio, eliminacio o actualitzacio d'informacio.
     *
     * @param query Query que es vol executar.
     */
    public void executeQuery(String query) {
        try {
            s =(Statement) conn.createStatement();
            s.executeUpdate(query);
        } catch(SQLException ex) {
            System.out.println("Problema a l'executar la Query --> " + ex.getSQLState());
        }
    }

    /**
     * Metode encarregat d'executar una query de seleccio.
     *
     * @param query Query que es vol executar.
     * @return
     */
    public ResultSet selectQuery(String query) {
        ResultSet rs = null;
        try {
            s =(Statement) conn.createStatement();
            rs = s.executeQuery (query);
        } catch(SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return rs;
    }

    /**
     * Metode que es crida quan es vol tancar la connexio amb la base de dades.
     */
    public void disconnect() {
        //TODO: fer un windowlistener i cridat-la des del controller de la vista. Tancar també els threads amb els clients.
        try {
            conn.close();
        } catch(SQLException e) {
            System.out.println("Problema al tancar la connexió --> " + e.getSQLState());
        }
    }
}


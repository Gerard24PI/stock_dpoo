package model.db;


import model.ServerConfiguration;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnector {
    private static String userName;
    private static String password;
    private static String db;
    private static int port;
    private static String url;
    private static Connection conn;
    private static Statement s;
    private static DBConnector instance;

    private DBConnector() {
        userName = ServerConfiguration.getDbUser();
        password = ServerConfiguration.getDbPassword();
        db = ServerConfiguration.getDbName();
        port = ServerConfiguration.getDbPORT();
        url = ServerConfiguration.getDbIP();
        url += ":"+port+"/";
        url += db;
        instance = null;
    }

    public static DBConnector getInstance(){
        if(instance == null){
            instance = new DBConnector();
            instance.connect();
        }
        return  instance;
    }

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Connection");
            conn = (Connection) DriverManager.getConnection(url, userName, password);
            if (conn != null) {
                System.out.println("Database Connection "+url+" ... Ok");
            }
        }
        catch(SQLException ex) {
            System.out.println("Problem connecting to DB --> "+url);
        }
        catch(ClassNotFoundException ex) {
            System.out.println(ex);
        }

    }

    public void insertQuery(String query){
        try {
            s =(Statement) conn.createStatement();
            s.executeUpdate(query);

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Insert Problem --> " + ex.getSQLState());
        }
    }

    public void updateQuery(String query){
        try {
            s =(Statement) conn.createStatement();
            s.executeUpdate(query);

        } catch (SQLException ex) {
            System.out.println("Update Problem --> " + ex.getSQLState());
        }
    }

    public void deleteQuery(String query){
        try {
            s =(Statement) conn.createStatement();
            s.executeUpdate(query);

        } catch (SQLException ex) {
            System.out.println("Delete Problem --> " + ex.getSQLState());
        }

    }

    public ResultSet selectQuery(String query){
        ResultSet rs = null;
        try {
            s =(Statement) conn.createStatement();
            rs = s.executeQuery (query);

        } catch (SQLException ex) {
            System.out.println("Select Problem --> " + ex.getSQLState());
        }
        return rs;
    }

    public void disconnect(){
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("Closing Connection Problem --> " + e.getSQLState());
        }
    }
}

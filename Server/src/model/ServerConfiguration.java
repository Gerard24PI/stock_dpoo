package model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;

public class ServerConfiguration {
    private static int dbPORT, serverPORT;
    private static String dbIP, dbName, dbUser, dbPassword;
    private static final String pathConfig = "Server\\config.json";

    /**
     * It reads config.json file of Server and gets ip and port of Server and ip, port, name,
     * user and password of DB
     */
    public ServerConfiguration () {
        try {
            //Opens config.json and gets all data
            FileReader reader = new FileReader(pathConfig);
            JsonParser parser = new JsonParser();
            JsonObject datum = parser.parse(reader).getAsJsonObject();
            reader.close();

            //Collects information that needs
            dbPORT = datum.get("DB_PORT").getAsInt();
            dbIP = datum.get("DB_IP").getAsString();
            dbName = datum.get("DB_NAME").getAsString();
            dbUser = datum.get("DB_USER").getAsString();
            dbPassword = datum.get("DB_PASSWORD").getAsString();
            serverPORT = datum.get("SERVER_PORT").getAsInt();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "ServerConfiguration{" +
                "dbPORT=" + dbPORT +
                ", serverPORT=" + serverPORT +
                ", dbIP='" + dbIP + '\'' +
                ", dbName='" + dbName + '\'' +
                ", dbUser='" + dbUser + '\'' +
                ", dbPassword='" + dbPassword + '\'' +
                '}';
    }

    public static int getDbPORT()
    {
        return dbPORT;
    }

    public static String getDbIP() {
        return dbIP;
    }

    public static String getDbName() {
        return dbName;
    }

    public static String getDbUser() {
        return dbUser;
    }

    public static String getDbPassword() {
        return dbPassword;
    }

    public static int getServerPORT() {
        return serverPORT;
    }
}

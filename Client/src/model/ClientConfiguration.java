package model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;

public class ClientConfiguration {
    private static final String pathConfig = "Client//config.json";
    private static int serverPORT;
    private static String serverIP;

    /**
     * It reads config.json file of Client and gets ip and port of Client
     */
    public ClientConfiguration() {
        try {
            FileReader reader = new FileReader(pathConfig);
            JsonParser parser = new JsonParser();
            JsonObject datum = parser.parse(reader).getAsJsonObject();
            reader.close();

            serverIP = datum.get("SERVER_IP").getAsString();
            serverPORT = datum.get("SERVER_PORT").getAsInt();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getServerPORT() {
        return serverPORT;
    }

    public static String getServerIP() {
        return serverIP;
    }
}

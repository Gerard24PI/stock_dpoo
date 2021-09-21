package model.network;

import controller.ServerController;
import model.ServerConfiguration;
import network.DataTransferObject;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server extends Thread {
    private boolean isOn;
    private ServerSocket sSocket;
    private ServerController controller;
    private LinkedList<DedicatedServer> dServers;

    public Server() {
        try {
            this.isOn = false;
            this.sSocket = new ServerSocket(ServerConfiguration.getServerPORT());
            this.dServers = new LinkedList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setController(ServerController controller) {
        this.controller = controller;
    }

    /**
     * It starts thread and server starts to work
     */
    public void startServer() {
        isOn = true;
        this.start();
    }

    /**
     * It removes specific dedicated server from Server's list
     * @param dServer to remove
     */
    public void removeDedicatedServer(DedicatedServer dServer) {
        dServers.remove(dServer);
    }

    public int getNumberDedicatedServers() {
        return dServers.size();
    }

    /**
     * Sends to all verified clients a message
     * @param message to send
     */
    public synchronized void sendBroadcast(DataTransferObject message) {
        for (DedicatedServer dServer : dServers) {
            if (dServer.isVerified()) {
                dServer.updateClient(message);
            }
        }
    }

    /**
     * Check if a determinated user is already logged
     * @param username username of determinated user
     * @return true if it is already logged, false if it doesn't
     */
    public boolean isLogged(String username) {
        for (DedicatedServer dServer : dServers) {
            if (dServer.isVerified() && dServer.getUsername().equals(username))
                return true;
        }
        return false;
    }

    @Override
    public void run() {
        while(isOn) {
            try {
                //Developes connection with Client that requires it
                Socket sClient = sSocket.accept();
                //Creates a new dedicated server, adds it in list and starts to work
                DedicatedServer dServer4Client = new DedicatedServer(controller, sClient, this);
                dServers.add(dServer4Client);
                dServer4Client.startDedicatedServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

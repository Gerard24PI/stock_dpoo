import controller.ClientController;
import controller.InitController;
import model.ClientConfiguration;
import network.ServerComunication;
import view.initial.InitView;

import java.io.IOException;

public class ClientMain {

    public static void main(String[] args) {
        System.out.println("This is Client program");
        //Reads config.json for connections with Server
        new ClientConfiguration();
        try {
            //Starts connection with Server
            ServerComunication com = new ServerComunication();
            com.startServerComunication();
            //Inits the Log in and Sign up View and controller
            InitView initView = new InitView();
            InitController initController = new InitController(com, initView);
            initView.registerController(initController);
            //Inits the general controller that contains both view controllers
            ClientController controller = new ClientController(initController);
            initController.setController(controller);
            //Network needs the general controller and it is set
            com.setController(controller);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Server not connected");
        }

    }
}

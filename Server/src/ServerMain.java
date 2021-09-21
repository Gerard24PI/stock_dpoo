import controller.ServerController;
import model.Bot;
import model.Company;
import model.db.dao.CompanyDAO;
import model.network.Server;
import model.ServerConfiguration;
import service.CompanyService;
import view.ServerView;

import java.util.LinkedList;

public class ServerMain {

    public static void main(String[] args) {

        System.out.println("This is Server program");
        //Reads config.json for connections with Client and DB
        new ServerConfiguration();
        Server server = new Server();
        //Inits the controller and view of Server
        ServerController controller = new ServerController(server);
        ServerView serverView = new ServerView(controller);
        serverView.registerController(controller);
        serverView.setVisible(true);
        controller.setView(serverView);
        controller.initBots();
        //After creating view and controller, server starts
        server.setController(controller);
        server.startServer();
        //Service starts to collect information for every company
        CompanyService companyService = new CompanyService(controller, server);
        controller.setService(companyService);
    }
}
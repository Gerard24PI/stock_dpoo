package controller;

import model.ClientModel;
import model.Error;
import network.ServerComunication;
import view.ErrorView;
import view.main.MainView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientController implements ActionListener {
    private InitController initController;
    private MainController mainController;
    private ErrorView errorView;

    public static final String OK_COMMAND = "OK";

    public ClientController(InitController initController) {
        this.initController = initController;
        this.mainController = null;
        this.errorView = null;
    }

    public void actionPerformed(ActionEvent event) {
        switch (event.getActionCommand()) {
            //Ok button from ErrorView
            case OK_COMMAND:
                errorView.dispose();
                break;
        }
    }

    /**
     * Starts client session (main program view)
     * @param model information required to start session
     * @param com connection with server manager
     */
    public void startClientSession(ClientModel model, ServerComunication com) {
        initController.initViewVisible(false);
        //Inits the Main program View and controller
        mainController = new MainController(model, com,this);
        MainView view = new MainView(mainController);
        mainController.setView(view);
        view.registerController(mainController);
    }

    /**
     * Destroys MainView and shows InitView
     */
    public void stopClientSession() {
        mainController.getView().dispose();
        mainController = null;
        initController.restartInitView();
    }

    /**
     * Destroys MainView and InitView
     */
    public void disconnect() {
        mainController.getView().dispose();
        initController.getView().dispose();
    }

    public MainController getMainController() {
        return mainController;
    }

    /**
     * Shows an error with a new JFrame
     * @param error error to show
     */
    public void setError (Error error) {
        errorView = new ErrorView(error);
        errorView.registerController(this);
    }
}

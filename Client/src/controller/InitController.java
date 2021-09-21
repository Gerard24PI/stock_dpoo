package controller;

import model.Error;
import network.LogInRequestDTO;
import network.ServerComunication;
import network.SignUpRequestDTO;
import view.initial.InitView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InitController implements ActionListener {
    private ClientController controller;
    private ServerComunication com;
    private InitView view;
    //private ErrorView error;

    public static final String LOGIN_COMMAND = "LOGIN";
    public static final String SIGNUP_COMMAND = "SIGNUP";

    public InitController(ServerComunication serverComunication, InitView view) {
        this.com = serverComunication;
        this.view = view;
    }

    /**
     * ErrorView is set visible or invisible
     * @param status status of ErrorView visibility
     */
    public void initViewVisible(boolean status) {
        view.initViewVisible(status);
    }

    /**
     * Clears init view fields and shows it
     */
    public void restartInitView() {
        view.clearAllFields();
        view.initViewVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {
            //Log in routine
            case LOGIN_COMMAND:
                //Gets information from fields filled
                String login, pass;
                login = view.getLogInPanel().getUserEmail().getText();
                pass = new String(view.getLogInPanel().getPass().getPassword());
                //Fields are empty
                if (checkLogInEmpty(login, pass)) {
                    controller.setError(Error.EMPTY_FIELDS);
                }
                //Fields are not empty
                else {
                    LogInRequestDTO logInRequest = new LogInRequestDTO(login, pass);
                    com.sendRequest(logInRequest);
                }
                break;
            case SIGNUP_COMMAND:
                //Gets information from fields filled
                String username, email, password, confirm;
                username = view.getSignUpPanel().getJtfUserName().getText();
                email = view.getSignUpPanel().getJtfEmail().getText();
                password = new String(view.getSignUpPanel().getJpfPass().getPassword());
                confirm = new String(view.getSignUpPanel().getJpfPassVerify().getPassword());
                //Checks errors in the sign up information filled (No DB)
                Error checkError = Commons.checkSignUp(username, email, password, confirm);
                if (checkError != Error.SIGNUP_OK) {
                    controller.setError(checkError);
                }
                else {
                    SignUpRequestDTO signUpRequest = new SignUpRequestDTO(username, email, password, confirm);
                    com.sendRequest(signUpRequest);
                }
                break;

        }
    }

    /**
     * Checks login or password are not empty
     * @param login login input
     * @param password password input
     * @return true if some empty
     */
    private boolean checkLogInEmpty(String login, String password){
        return login.equals("") || password.equals("");
    }

    public InitView getView() {
        return view;
    }

    public void setController(ClientController controller) {
        this.controller = controller;
    }
}

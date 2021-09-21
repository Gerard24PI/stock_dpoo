package model.network;

import controller.Commons;
import controller.ServerController;
import model.Company;
import model.db.dao.CompanyDAO;
import model.db.dao.ShareDAO;
import model.db.dao.UserDAO;
import model.Error;
import model.Share;
import model.User;
import network.*;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

public class DedicatedServer extends Thread {
    private boolean isOn;
    private ServerController controller;
    private Socket sClient;
    private ObjectOutputStream outStream;
    private ObjectInputStream inStream;
    private Server server;
    private String username;
    private boolean verified;


    public DedicatedServer(ServerController controller, Socket sClient, Server server) {
        this.isOn = false;
        this.controller = controller;
        this.sClient = sClient;
        this.server = server;
        this.verified = false;
        this.username = null;
    }

    public boolean isVerified() {
        return verified;
    }

    public String getUsername() {
        return username;
    }

    /**
     * It starts thread and dedicated server starts to work
     */
    public void startDedicatedServer() {
        this.isOn = true;
        this.start();
        System.out.println("Dedicated servers connected: " + server.getNumberDedicatedServers());
    }

    /**
     * It stops thread and removes dedicated server from Server's list
     */
    public void stopDedicatedServer() {
        this.isOn = false;
        this.interrupt();
        server.removeDedicatedServer(this);
        System.out.println("Dedicated Server disconnected (" + server.getNumberDedicatedServers() + " alive)");
    }

    /**
     * Sends to Client updated data
     * @param message updated data
     */
    protected synchronized void updateClient(DataTransferObject message) {
        try {
            outStream.writeObject(message);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error en broadcast Share Value");
        }
    }

    /**
     * Starts dedicated communication with Client and manages all its requests
     */
    @Override
    public void run() {
        try {
            //It gets the input and output channels through which it will communicate with Client
            outStream = new ObjectOutputStream(sClient.getOutputStream());
            inStream = new ObjectInputStream(sClient.getInputStream());
            //Loop where it receives and manages all requests from Client
            while (isOn) {
                Object o = inStream.readObject();
                manageDataReceived(o);
            }
        } catch (ClassNotFoundException | IOException e) {
            //Connection with Client failed
            stopDedicatedServer();
        }
    }

    /**
     * It detects the type of request from Client and it runs the appropiate routine
     * @param o: object received from Client
     */
    private void manageDataReceived(Object o) {
        //Log in request
        if (o instanceof LogInRequestDTO) {
            logInUserManagement((LogInRequestDTO) o);
        }
        //Sign up request
        else if (o instanceof SignUpRequestDTO) {
            registerUserManagement((SignUpRequestDTO) o);
        }
        //Log out request
        else if (o instanceof LogOutRequestDTO) {
            verified = false;
            username = null;
        }
        //Client buys or sells shares
        else if (o instanceof TransactionDTO) {
            transactionManagement((TransactionDTO) o);
        }
        //Client adds money to account
        else if (o instanceof  UpdateCashDTO) {
            updateCash((UpdateCashDTO) o);
        }
        //Client updates profile
        else if (o instanceof ProfileDTO){
            ProfileDTO p = (ProfileDTO) o;
            UserDAO.updateProfile(p.getUsername(), p.getImage(), p.getDescription());
        }
    }

    /**
     * It checks log in request and sends back the complete client information if it is successful or
     * general error if it isn't
     * @param logInRequestDTO: Log in request from Client
     */
    private void logInUserManagement(LogInRequestDTO logInRequestDTO) {
        try {
            //Checking credentials in DB
            User user = UserDAO.userLogin(logInRequestDTO.getLogin(), logInRequestDTO.getPassword());
            //Credentials not correct
            if (user == null || server.isLogged(user.getUsername())) {
                //Sends error to Client
                ErrorDTO message = new ErrorDTO(Error.LOGIN_FAILED);
                outStream.writeObject(message);
            }
            else {
                //Sends all information that Client needs to start session
                LinkedList<Company> companies = controller.getCompanies();
                ClientDTO message = new ClientDTO(user, companies);
                outStream.writeObject(message);
                verified = true;
                username = user.getUsername();
                System.out.println(username + " has logged");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * It checks sign up request and sends back the complete client information if it is successful or
     * an explicative error if it isn't
     * @param regInfo: Sign up request from Client
     */
    private void registerUserManagement (SignUpRequestDTO regInfo) {
        //Checks errors in the sign up information received (No DB)
        Error error = Commons.checkSignUp(
                regInfo.getUsername(), regInfo.getEmail(), regInfo.getPassword(), regInfo.getConfirmPassword());
        if(error == Error.SIGNUP_OK){
            //Checks errors in the sign up information received (DB)
            error = controller.checkSignUpDB(regInfo.getUsername(), regInfo.getEmail());
        }
        try {
            //Client can sign up
            if (error == Error.SIGNUP_OK) {
                //Adds a new user in DB
                User user = new User(regInfo.getUsername(),regInfo.getEmail(), regInfo.getPassword(),0);
                UserDAO.addUser(user);
                //Sends all information that Client needs to start session
                LinkedList<Company> companies = controller.getCompanies();
                ClientDTO message = new ClientDTO(user, companies);
                outStream.writeObject(message);
                verified = true;
                username = user.getUsername();
                System.out.println(username + " has logged");
            }
            else {
                //Sends specific error to Client
                ErrorDTO errorMessage = new ErrorDTO(error);
                outStream.writeObject(errorMessage);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void transactionManagement(TransactionDTO t) {
        float companyShareValue = CompanyDAO.getCompany(CompanyDAO.getCompanyId(t.getCompanyName())).getShareValue();
        float money = companyShareValue * t.getNumShares();
        try {
            //If it is buying, do user have enough money?
            if (t.isBuy() && money > UserDAO.getCash(t.getUsername())) {
                ErrorDTO error = new ErrorDTO(Error.NO_MONEY);
                outStream.writeObject(error);
            //If it is selling, do user have the amount of shares that wants to sell?
            } else if (!t.isBuy() && t.getNumShares() > ShareDAO.getOwnedShares(t.getUsername(), t.getCompanyName())) {
                ErrorDTO error = new ErrorDTO(Error.NO_SHARES);
                outStream.writeObject(error);
            //If user can make the transaction
            } else {
                float addCash = money;
                int addShares = t.getNumShares();
                //Buying shares / Wasting money
                if (t.isBuy())  addCash *= -1;
                //Selling shares / Gaining money
                else            addShares *= -1;
                //If it is buying, adds shares; if it is selling, substracts shares
                ShareDAO.addShare(t.getUsername(), t.getCompanyName(), addShares, companyShareValue);
                //Updates list buyed actions in BuyAction
                if (controller.getComboBoxItemUser().equals(t.getUsername())){
                    float shareValue = CompanyDAO.getShareValue(t.getCompanyName());
                    controller.updateCompanyNumberShares(t.getCompanyName(), ShareDAO.getOwnedShares(t.getUsername(),t.getCompanyName()), shareValue);
                }
                //If it is buying, substracts cash; if it is selling, adds cash
                controller.modifyCash(t.getUsername(), addCash);
                //Sends to Client the new money and new share number
                LinkedList<Share> sharesOf = ShareDAO.getSharesOf(t.getUsername(), t.getCompanyName());
                UserShareUpdateDTO userShareUpdateDTO =
                        new UserShareUpdateDTO(t.isBuy(), UserDAO.getCash(t.getUsername()), sharesOf, t.getCompanyName());
                outStream.writeObject(userShareUpdateDTO);
                //Sends to all verified users the updated share value
                controller.transaction(t.getCompanyName(), t.isBuy());

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds money in user's account and send confirmation to client
     * @param t DTO object
     */
    private void updateCash(UpdateCashDTO t){
        try {
            UserDAO.addCash(t.getUsername(),t.getNewCash());
            float cash = UserDAO.getCash(t.getUsername());
            UpdateCashDTO newcash = new UpdateCashDTO(t.getUsername(), cash);
            outStream.writeObject(newcash);
        }catch (IOException e){
            e.printStackTrace();
        }
        
    }

}

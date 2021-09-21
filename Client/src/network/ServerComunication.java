package network;

import controller.ClientController;
import controller.Commons;
import model.*;
import model.Error;

import java.io.*;
import java.net.Socket;

public class ServerComunication extends Thread {
    private boolean isOn;
    private ClientController controller;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket serverSocket;

    public ServerComunication() throws IOException {
        try {
            isOn = false;
            serverSocket = new Socket(ClientConfiguration.getServerIP(), ClientConfiguration.getServerPORT());
            out = new ObjectOutputStream(serverSocket.getOutputStream());
            in = new ObjectInputStream(serverSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            stopServerComunication();
            throw new IOException();
        }
    }

    /**
     * Client starts the comunication with Server
     */
    public void startServerComunication() {
        isOn = true;
        this.start();
        System.out.println("Connected to Server");
    }

    public void stopServerComunication() {
        isOn = false;
        this.interrupt();
        System.out.println("Disconnected from Server");
    }

    public void run() {
        while(isOn) {
            try {
                Object o = in.readObject();
                manageDataReceived(o);
            }
            catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
                System.out.println("Server disconnected");
                controller.disconnect();
                stopServerComunication();
            }
        }
    }

    /**
     * It detects the type of response from Server and it runs the appropiate routine
     * @param o: object received from Client
     */
    private void manageDataReceived (Object o) {
        //Client receives from server the request of updating the share value of a company
        if (o instanceof ShareValueUpdateDTO) {
            ShareValueUpdateDTO shareValueUpdateDTO = (ShareValueUpdateDTO) o;
            String name = shareValueUpdateDTO.getCompanyName();
            //Updating model
            controller.getMainController().getClient().updateShareValue(name, shareValueUpdateDTO.getShareValue());
            ClientModel client = controller.getMainController().getClient();
            Company company = client.getCompanyByName(name);
            int index = client.getIndexCompanyByName(name);
            //Refreshing view
            controller.getMainController().getView().getViewCompanies().updateChangePriceValue(company, index);
            controller.getMainController().getView().getViewCompanies().updateProfitLossValues
                    (company, index, client.getUser().getShares());
            if (controller.getMainController().getView().getCompanyDetailPanel().isVisible())
                if (name.equals(controller.getMainController().getView().getCompanyDetailPanel().getCompanyName()))
                    controller.getMainController().getView().getCompanyDetailPanel().getCompanyInfoPanel().updateShareValues(shareValueUpdateDTO.getShareValue());
        }
        //Client receives from server the share value of 5 minutes ago
        else if (o instanceof ShareValueSampleDTO) {
            ShareValueSampleDTO shareValueSampleDTO = (ShareValueSampleDTO) o;
            //Collecting information
            ClientModel client = controller.getMainController().getClient();
            String name = shareValueSampleDTO.getCompanyName();
            Company c = client.getCompanyByName(name);
            //Updating model
            c.setSample(shareValueSampleDTO.getShareValue());
            //Refreshing View
            controller.getMainController().getView().getViewCompanies().updateChangePriceValue
                    (client.getCompanyByName(name), client.getIndexCompanyByName(name));
        }
        //Client receives from server a new candle information
        else if (o instanceof NewCandleDTO) {
            NewCandleDTO newCandleDTO = (NewCandleDTO) o;
            Company c = controller.getMainController().getClient().getCompanyByName(newCandleDTO.getCompanyName());
            //Updating model
            Commons.addCandle(newCandleDTO.getCandle(), c.getCandles());
            //Refreshing view if needed
            if (controller.getMainController().getView().getCompanyDetailPanel().isVisible())
                if (c.getName().equals(controller.getMainController().getView().getCompanyDetailPanel().getCompanyName()))
                    controller.getMainController().getView().getCompanyDetailPanel().updateChart(c.getCandles());
        }
        //Client receives from server updated shares of company owned by user
        else if (o instanceof UserShareUpdateDTO) {
            UserShareUpdateDTO userShareUpdateDTO = (UserShareUpdateDTO) o;
            //Recollecting information
            Company c = controller.getMainController().getClient().getCompanyByName(userShareUpdateDTO.getCompanyName());
            int index = controller.getMainController().getClient().getIndexCompanyByName(c.getName());
            for (Share s : userShareUpdateDTO.getSharesOf())
                s.setCompany(c);
            //Updating model
            controller.getMainController().getClient().getUser().setCash(userShareUpdateDTO.getMoney());
            controller.getMainController().getClient().getUser().updateUserShares(c.getName(), userShareUpdateDTO.getSharesOf());
            //Refreshing view
            controller.getMainController().getView().getViewCompanies().updateProfitLossValues(c, index, userShareUpdateDTO.getSharesOf());
            controller.getMainController().getView().getMoneyAvailablePanel().refreshMoney(userShareUpdateDTO.getMoney());
            if (controller.getMainController().getView().getCompanyDetailPanel().isVisible()) {
                if (userShareUpdateDTO.getCompanyName().equals(controller.getMainController().getView().getCompanyDetailPanel().getCompanyName())) {
                    //Get total number of shares
                    int numShares = 0;
                    for (Share s : userShareUpdateDTO.getSharesOf())
                        numShares += s.getNumber();
                    controller.getMainController().getView().getCompanyDetailPanel().getCompanyInfoPanel().updateUserSharesNumber(numShares);
                }
            }
        }
        //Client receives from server his updated cash
        else if (o instanceof UpdateCashDTO){
            UpdateCashDTO updateCashDTO = (UpdateCashDTO) o;
            //Updating model
            controller.getMainController().getClient().getUser().setCash(updateCashDTO.getNewCash());
            //Refreshing view
            controller.getMainController().getView().getMoneyAvailablePanel().refreshMoney(updateCashDTO.getNewCash());
        }
        //Client receives from server an error
        else if (o instanceof ErrorDTO) {
            Error error = ((ErrorDTO) o).getError();
            //Shows error
            controller.setError(error);
        }
        //Client receives from server all information needed for start session and log in
        else if (o instanceof ClientDTO) {
            ClientDTO clientDTO = (ClientDTO) o;
            ClientModel client = new ClientModel(clientDTO.getCompanies(), clientDTO.getUser());
            //Log in
            controller.startClientSession(client, this);
        }
    }

    /**
     * Sends message to Server
     * @param message to send
     */
    public void sendRequest(DataTransferObject message) {
        try {
            out.writeObject(message);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setController(ClientController controller) {
        this.controller = controller;
    }

}

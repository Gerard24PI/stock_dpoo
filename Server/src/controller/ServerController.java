package controller;

import model.*;
import model.Error;
import model.db.dao.BotDAO;
import model.db.dao.CompanyDAO;
import model.db.dao.ShareDAO;
import model.db.dao.UserDAO;
import model.network.Server;
import network.ShareValueUpdateDTO;
import service.CompanyInfoCollector;
import view.*;
import service.CompanyService;
import view.bot.BotRowChangeStatus;
import view.bot.BotRowEliminateButton;
import view.bot.BotCreation;
import view.bot.BotTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

public class ServerController implements ActionListener, MouseListener {
    private Server server;
    private ServerView view;
    private CompanyService service;
    private LinkedList<Bot> botsList;

    //Commands
    public static final String CREATE_BOT = "CREATE";
    public static final String ELIMINATE_BOT = "ELIMINATE";
    public static final String STATUS_ON = "STATUS_ON";
    public static final String STATUS_OFF = "STATUS_OFF";
    public static final String SEARCH_COMPANY = "SEARCH_COMPANY";
    public static final String USERSTABLE_COMMAND = "USERS TABLE";
    public static final String TOPCHART_COMMAND = "TOP CHART";
    public static final String BOTENGINE_COMMAND = "BOT ENGINE";


    public ServerController(Server server) {
        this.server = server;
        this.view = null;
    }

    /**
     * initialize the bots list getting the info from the BotDao
     */
    public void initBots() {
        botsList = BotDAO.getBots();
        for (Bot bot : botsList) {
            bot.setSc(this);
            bot.start();
        }
    }

    /**
     * updates the cash of the UserDao
     * @param username name of the user
     * @param transactionValue gets true or false depending on if it's sells or buys
     */
    public synchronized void modifyCash(String username, float transactionValue) {
        UserDAO.addCash(username, transactionValue);
    }

    /**
     * Adds or substracts 1% of share value of a company regarding transaction type (buy or sell).
     * Updates share value in DB. Sends to all clients the updated share value.
     * @param companyName company of share value
     * @param transactionType trasaction type (buy or sell)
     */
    public synchronized void transaction(String companyName, boolean transactionType) {
        //Updating DB
        float shareValue = updateShareValueDB(companyName, transactionType);
        //Refreshing view
        view.getJpBuyAction().updateCompanySharePrice(companyName, shareValue);
        view.getJpTop().setTopInfo();
        //Sending updated information to all clients
        ShareValueUpdateDTO shareValueUpdate = new ShareValueUpdateDTO(companyName, shareValue);
        server.sendBroadcast(shareValueUpdate);
    }

    /**
     * Adds or substracts 1% of share value of a company regarding transaction type (buy or sell)
     * @param companyName company of transaction
     * @param transactionType transaction type (buy or sell)
     * @return updated share value
     */
    public synchronized float updateShareValueDB(String companyName, boolean transactionType) {
        float shareValue = CompanyDAO.getShareValue(companyName);
        float onePercent = (float) (shareValue * 0.01);
        if (transactionType == Commons.BUY) shareValue += onePercent;
        else shareValue -= onePercent;
        CompanyDAO.setShareValue(companyName, shareValue);
        return shareValue;
    }

    /**
     * checks if both of the strings are on the DDBB
     * @param username name user
     * @param email email user
     * @return error ocurred
     */
    public Error checkSignUpDB(String username, String email) {
        if (!UserDAO.findUserByName(username)) {
            if (!UserDAO.findUserByEmail(email)) {
                return Error.SIGNUP_OK;
            } else {
                return Error.EMAIL_EXISTS;
            }
        } else {
            return Error.USERNAME_EXISTS;
        }
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {
            //Server creates new bot
            case CREATE_BOT:
                BotCreation viewBotFrame = view.getBotCreation();
                BotTable viewBotList = view.getBotTable();
                //Checks errors in entered data
                if (checkBotsParameters(viewBotFrame.getPercentatge(), viewBotFrame.getActivationTime())) {
                    String item = viewBotFrame.getComboBoxItem().toString();
                    int i = CompanyDAO.getCompanyId(item);
                    Bot b = new Bot(Float.parseFloat(viewBotFrame.getPercentatge()), Float.parseFloat(viewBotFrame.getActivationTime()), true, CompanyDAO.getCompany(i));
                    BotDAO.addBot(b);
                    b.setSc(this);
                    b.start();

                    botsList.add(b);
                    viewBotList.refreshBotsList(botsList);
                    viewBotList.registerControllers(this);
                    viewBotFrame.clearTextFields();
                    break;
                }
                JOptionPane.showMessageDialog(null, "You have entered the data incorrectly");
                viewBotFrame.clearTextFields();
                break;
            //Server deletes a bot
            case ELIMINATE_BOT:
                BotRowEliminateButton o=(BotRowEliminateButton) actionEvent.getSource();
                int posicioBot = o.getPosicio();
                BotDAO.removeBot(botsList.get(posicioBot));
                botsList.get(posicioBot).killThread();
                botsList.remove(posicioBot);
                view.getBotTable().refreshBotsList(botsList);
                view.getBotTable().registerControllers(this);
                break;
            //Server activates bot
            case STATUS_ON:
                BotRowChangeStatus s=(BotRowChangeStatus) actionEvent.getSource();
                int posicioBot2 = s.getPosicio();
                BotDAO.updateStatusBot(botsList.get(posicioBot2), true);
                botsList.get(posicioBot2).resumeThread();
                break;
            //Server desactivate bot
            case STATUS_OFF:
                BotRowChangeStatus s2=(BotRowChangeStatus) actionEvent.getSource();
                int posicioBot3 = s2.getPosicio();
                BotDAO.updateStatusBot(botsList.get(posicioBot3), false);
                Bot bot = botsList.get(posicioBot3);
                bot.pauseThread();
                break;
            //Server selects one user to display his information
            case SEARCH_COMPANY :
                //Get all the shares related to the user selected on the ComboBox
                LinkedList<Share> shares = ShareDAO.getUserShares(view.getJpBuyAction().getComboBoxItem().toString());
                LinkedList<String> companyTable= new LinkedList<>();
                int cont =0;
                //Search all the companies that has the same name to add them if they are found
                for (int i = 0; i < shares.size(); i++) {
                    for (int j = 0; j < shares.size(); j++) {
                        if (shares.get(i).getCompany().getName().equals(shares.get(j).getCompany().getName())){
                            if (j>i){
                                cont=0;
                                break;
                            }
                            cont=cont+shares.get(j).getNumber();
                        }
                    }
                    //Add the name, shares and value shares from each company (each just once) and we send it to the view
                    if (cont!=0){
                        companyTable.add(shares.get(i).getCompany().getName());
                        companyTable.add(String.valueOf(cont));
                        float shareValue = CompanyDAO.getShareValue(shares.get(i).getCompany().getName());
                        companyTable.add(String.valueOf((shareValue)));
                    }
                    cont=0;
                }
                view.getJpBuyAction().createTable(companyTable);
        }
    }

    /**
     * Updates the view of the Companies when some transaction has been made
     * @param company company name
     * @param numshare   shares quantity
     * @param shareValue  value of the share
     */
    public void updateCompanyNumberShares(String company, int numshare, float shareValue){
        view.getJpBuyAction().updateCompanyNumberShare(company, numshare, shareValue);
    }

    /**
     * gets the combobox item selected
     * @return Selected item in combobox
     */
    public String getComboBoxItemUser(){
        return view.getJpBuyAction().getComboBoxItem().toString();
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Object source = mouseEvent.getSource();
        if (source instanceof JPanel) {
            JPanel jpAux = (JPanel) source;
            //Changing card to show of ServerMain
            switch(jpAux.getName()) {
                case USERSTABLE_COMMAND:
                case BOTENGINE_COMMAND:
                case TOPCHART_COMMAND:
                    view.changeCard(jpAux.getName());
                    view.getHeaderPanel().select(jpAux.getName());
                    break;
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        Object source = mouseEvent.getSource();
        if (source instanceof JPanel) {
            JPanel jpAux = (JPanel) source;
            if (jpAux.getBackground().equals(Color.BLACK)) {
                jpAux.setBackground(Color.DARK_GRAY);
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        Object source = mouseEvent.getSource();
        if (source instanceof JPanel) {
            JPanel jpAux = (JPanel) source;
            if (jpAux.getBackground().equals(Color.DARK_GRAY)) {
                jpAux.setBackground(Color.BLACK);
            }
        }
    }

    /**
     * Use DAO for extract all bots in DB
     * @return list of companies
     */
    public LinkedList<Bot> getBotsDB(){
        return BotDAO.getBots();
    }

    /**
     * Use DAO for extract all usernames in DB
     * @return list of companies
     */
    public LinkedList<String> getUsersNameDB(){
        return UserDAO.getUsersName();
    }

    /**
     * Use DAO for extract all companies in DB
     * @return list of companies
     */
    public LinkedList<Company> getCompaniesDB() {
        return CompanyDAO.getCompanies();
    }

    /**
     * Get all companies (completed with candles and sample)
     * @return list of complete companies
     */
    public LinkedList<Company> getCompanies() {
        LinkedList<Company> companies = getCompaniesDB();
        //Extracts info from company info collector service and set it to complete company
        for (int i = 0; i < companies.size(); i++) {
            CompanyInfoCollector info = service.getCompanyInfoCollectors().get(i);
            companies.get(i).setCandles(info.getCandles());
            companies.get(i).setSample(info.getShareValueSamples().get(0));
        }
        return companies;
    }


    /**
     * check if the parameters introduce are correct (numbers between 0 and 100)
     * @param a 1st number
     * @param b 2nd number
     * @return True if percentage is adequate
     */
    public static boolean checkBotsParameters(String a, String b){
        try {
            Float.parseFloat(a);
            Float.parseFloat(b);
            if (Float.parseFloat(a)>=0 && Float.parseFloat(a)<=100) {
                return true;
            }
        } catch (NumberFormatException nfe){
            return false;
        }
        return false;
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
    }
    @Override
    public void mouseClicked(MouseEvent event) {
    }

    public void setView(ServerView view){
        this.view=view;
    }

    public void setService(CompanyService service) {
        this.service = service;
    }

}

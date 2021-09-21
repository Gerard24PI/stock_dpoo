package controller;

import model.ClientModel;
import model.Company;
import model.Error;
import model.Share;
import network.*;
import view.main.companydetails.CompanyInfoPanel;
import view.main.companydetails.CompanyDetailPanel;
import view.main.companydetails.TransactionPanel;
import view.main.companytable.CompanyRow;
import view.main.MainView;

import javax.swing.*;
import java.awt.event.*;

public class MainController implements ActionListener, KeyListener, MouseListener {
    private ClientModel client;
    private ServerComunication com;
    private MainView view;
    private ClientController controller;

    //Commands
    public static final String LOGOUT_COMMAND = "LOGOUT";
    public static final String BUY_COMMAND = "BUY";
    public static final String SELL_COMMAND = "SELL";
    public static final String SELLALL_COMMAND = "SELLALL";
    public static final String ADD = "ADD";
    public static final String RETURN_COMPANY_COMMAND = "RETURN_COMPANY";
    public static final String RETURN_PROFILE_COMMAND = "RETURN_PROFILE";
    public static final String COMPANYSHARE_TABLE = "TABLE";
    public static final String COMPANY_DETAILS = "DETAILS";
    public static final String SETIMAGE_COMMAND = "SET_IMAGE";
    public static final String PROFILE_COMMAND = "PROFILE";

    public MainController(ClientModel client, ServerComunication com, ClientController controller){
        this.client = client;
        this.com = com;
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String action = actionEvent.getActionCommand();
        switch (action) {
            //User wants lo log out
            case LOGOUT_COMMAND:
                //Remove main view and starts init view
                controller.stopClientSession();
                client = null;
                //Send request of log out to Server
                LogOutRequestDTO logOutRequest = new LogOutRequestDTO();
                com.sendRequest(logOutRequest);
                break;
            //User wants to buy/sell (transaction)
            case SELLALL_COMMAND:
            case BUY_COMMAND:
            case SELL_COMMAND:
                int numShares;
                CompanyInfoPanel companyInfoPanel = view.getCompanyDetailPanel().getCompanyInfoPanel();
                TransactionPanel transactionPanel = view.getCompanyDetailPanel().getTransactionPanel();
                //Get the number of all shares (of selected company)
                if (action.equals(SELLALL_COMMAND)) {
                    numShares = companyInfoPanel.getUserShares();
                }
                //Get the number of shares that user wants to buy/sell (of selected company)
                else {
                    //Check empty field of number of shares to buy/sell
                    String text = transactionPanel.getNumShares();
                    if (text.equals("")) {
                        controller.setError(Error.BLANK_FIELD);
                        break;
                    }
                    numShares = Integer.parseInt(text);
                    transactionPanel.setJtfShares("");
                }
                //Send request of transaction to Server
                boolean transactionType = action.equals(BUY_COMMAND) ? Commons.BUY : Commons.SELL;
                String companyName = companyInfoPanel.getCompanyName();
                TransactionDTO transactionRequest = new TransactionDTO(transactionType, numShares, companyName, client.getUser().getUsername());
                com.sendRequest(transactionRequest);
                break;
            //User wants to add money to their account
            case ADD:
                String cashIntroduced = view.getMoneyAvailablePanel().getUserCash();
                //Check empty field of amount of money
                if(cashIntroduced.equals("")){
                    controller.setError(Error.BLANK_FIELD);
                    break;
                }
                //Send request of addition of money to Server
                float cash = Float.parseFloat(view.getMoneyAvailablePanel().getUserCash());
                view.getMoneyAvailablePanel().clearText();
                UpdateCashDTO updateCashDTO = new UpdateCashDTO(client.getUser().getUsername(),cash);
                com.sendRequest(updateCashDTO);
                break;
            //User wants to return to MainView from ProfileUser panel
            case RETURN_PROFILE_COMMAND:
                //Saves profile image and send it to Server
                ProfileDTO profileDTO = new ProfileDTO(view.getProfileUser().getJlUserName(), view.getProfileUser().getJlImgUser(), view.getProfileUser().getJtDescription());
                com.sendRequest(profileDTO);
                //Shows MainView and updates new profile image in MainView
                view.getGlassPane().setVisible(false);
                view.setVisible(true);
                view.getHeaderPanel().setImage(Commons.getJlImgUser(view.getProfileUser().getJlImgUser()));
                //Updating model
                client.getUser().setImage(view.getProfileUser().getJlImgUser());
                client.getUser().setDescription(view.getProfileUser().getJtDescription());
                break;
            //Change produced on user's image selection
            case SETIMAGE_COMMAND:
                JComboBox jcSetImage = (JComboBox) actionEvent.getSource();
                String selectedItem = jcSetImage.getSelectedItem().toString();
                //Updates profile image to selected one on view and model
                view.getProfileUser().setImage(selectedItem);
                client.getUser().setImage(view.getProfileUser().getJlImgUser());

        }
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        Object o = keyEvent.getSource();
        if (o instanceof JTextField) {
            //JTextField filters (only accept numbers)
            JTextField jtfShares = (JTextField) o;
            char key = keyEvent.getKeyChar();
            if (jtfShares.getText().equals("") && key == '0') {
                jtfShares.setEditable(false);
            }
            else jtfShares.setEditable(key >= '0' && key <= '9' || key == KeyEvent.VK_BACK_SPACE);
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Object source = mouseEvent.getSource();
        if (source instanceof JPanel) {
            //Company selected in ViewCompanies panel
            if (source instanceof CompanyRow) {
                //Recolecting information required to display company details panel
                CompanyRow row = (CompanyRow) mouseEvent.getSource();
                Company c = getClient().getCompanyByName(row.getCompanyName());
                int userShares = 0;
                for (Share s : getClient().getUser().getShares())
                    if (s.getCompany().getName().equals(c.getName()))
                        userShares += s.getNumber();
                //Show CompanyDetailPanel
                CompanyDetailPanel companyDetailPanel = new CompanyDetailPanel(c, userShares);
                companyDetailPanel.registerController(this);
                view.setCompanyDetailPanel(companyDetailPanel);
                view.getCards().add(companyDetailPanel, COMPANY_DETAILS);
                view.changeCard(MainController.COMPANY_DETAILS);
                view.getHeaderPanel().updateHeaderLabels(RETURN_COMPANY_COMMAND, c.getName());
                view.setVisible(false);
                view.setVisible(true);
                view.repaint();
            }
        } else if (source instanceof JLabel){
            JLabel jl_aux = (JLabel) source;
            //Shows ProfileUser if Profile image pressed
            if(jl_aux.getName().equals(PROFILE_COMMAND)){
                view.getProfileUser().updatePanel(client.getUser());
                view.getGlassPane().setVisible(true);
            }
            //Returns to MainView (ViewCompanies) if Stock logo is pressed
            else if (jl_aux.getName().equals(RETURN_COMPANY_COMMAND)) {
                view.setCompanyDetailPanel(new CompanyDetailPanel());
                view.changeCard(MainController.COMPANYSHARE_TABLE);
                view.getHeaderPanel().updateHeaderLabels("", "");
                view.setVisible(false);
                view.setVisible(true);
            }
        //Erases JTextArea (user's description) if it is pressed
        } else if (source instanceof JTextArea){
            JTextArea jt_aux = (JTextArea) source;
            if(jt_aux.getName().equals("jtdescription") && jt_aux.getText().equals("Add a description...")){
                jt_aux.setText("");
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }
    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
    @Override
    public void mouseClicked(MouseEvent event) {

    }
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }
    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }
    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }


    public ClientModel getClient () {
        return client;
    }

    public void setView (MainView mainView){
        this.view = mainView;
    }

    public MainView getView() {
        return view;
    }

}

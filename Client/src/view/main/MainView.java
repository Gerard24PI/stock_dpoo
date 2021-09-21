package view.main;

import controller.Commons;
import controller.MainController;
import model.User;
import view.main.companydetails.CompanyDetailPanel;
import view.main.companytable.ViewCompanies;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static controller.MainController.COMPANYSHARE_TABLE;

public class MainView extends JFrame {
    private HeaderPanel headerPanel;
    private MoneyAvailablePanel moneyAvailablePanel;

    private CompanyDetailPanel companyDetailPanel;
    private ViewCompanies viewCompanies;
    private ProfileUser profileUser;
    private JPanel cards;
    private CardLayout cardLayout;

    private MainController controller;

    public MainView(MainController mainController) {
        this.controller = mainController;

        this.getContentPane().setLayout (new BorderLayout());
        this.getContentPane().setBackground(Color.white);

        Image icon = Toolkit.getDefaultToolkit().getImage("Client/Images/s_logo.png");
        setIconImage(icon);

        User user = controller.getClient().getUser();

        //Header
        headerPanel = new HeaderPanel(user);
        headerPanel.setImage(Commons.getJlImgUser(user.getImage()));
        headerPanel.setBorder(new EmptyBorder(0,0,15,0));
        this.getContentPane().add(headerPanel,BorderLayout.NORTH);

        //List of companies and shares
        viewCompanies = new ViewCompanies(mainController.getClient().getCompanies(), mainController.getClient().getUser().getShares());
        viewCompanies.setBackground(Color.white);
        viewCompanies.setBorder(new EmptyBorder(0,20,0,20));
        viewCompanies.setVisible(true);

        //Empty company detail
        companyDetailPanel = new CompanyDetailPanel();

        //Construction of cards and layout
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        cards.add(viewCompanies, COMPANYSHARE_TABLE);
        cards.add(companyDetailPanel, MainController.COMPANY_DETAILS);
        this.getContentPane().add(cards, BorderLayout.CENTER);
        cardLayout.show(cards, COMPANYSHARE_TABLE);

        //User profile
        profileUser = new ProfileUser();
        setGlassPane(profileUser);
        getGlassPane().setVisible(false);

        //Money Available
        moneyAvailablePanel = new MoneyAvailablePanel(user.getCash());
        moneyAvailablePanel.setBorder(new EmptyBorder(15,0,15,0));
        this.getContentPane().add(moneyAvailablePanel,BorderLayout.SOUTH);

        setSize(1280,720);
        setTitle("Stock");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public void registerController(MainController controller) {
        headerPanel.registerController(controller);
        moneyAvailablePanel.registerController(controller);
        viewCompanies.registerController(controller);
        profileUser.registerController(controller);
    }

    /**
     * Change view regarding the name of panel
     * @param name name of panel
     */
    public void changeCard(String name) {
        cardLayout.show(cards, name);
        cards.repaint();
        cards.setVisible(false);
        cards.setVisible(true);
    }

    public MoneyAvailablePanel getMoneyAvailablePanel(){
        return moneyAvailablePanel;
    }

    public ViewCompanies getViewCompanies() {
        return viewCompanies;
    }

    public CompanyDetailPanel getCompanyDetailPanel() {
        return companyDetailPanel;
    }

    public void setCompanyDetailPanel(CompanyDetailPanel companyDetailPanel) {
        this.companyDetailPanel = companyDetailPanel;
    }

    public JPanel getCards() {
        return cards;
    }

    public HeaderPanel getHeaderPanel() {
        return headerPanel;
    }

    public ProfileUser getProfileUser() {
        return profileUser;
    }
}

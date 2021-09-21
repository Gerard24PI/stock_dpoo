package view;

import controller.ServerController;
import view.bot.BotCreation;
import view.bot.BotTable;
import view.topten.TopChart;
import view.usershares.BuyAction;

import javax.swing.*;
import java.awt.*;

import static controller.ServerController.*;

public class ServerView extends JFrame{
    private ServerController controller;
    private CardLayout cardLayout;
    private JPanel cards;
    private HeaderPanel headerPanel;
    private JPanel jpBots;
    private TopChart jpTop;

    //Aixo estara a jpBots
    private BotCreation botCreation;
    private BotTable botTable;

    private BuyAction jpBuyAction;

    public ServerView(ServerController controller) {
        this.controller = controller;

        setLayout(new BorderLayout());

        Image icon = Toolkit.getDefaultToolkit().getImage("Client/Images/s_logo.png");
        setIconImage(icon);

        headerPanel = new HeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        //Bot Table
        jpBots = new JPanel(new BorderLayout());
        botCreation = new BotCreation(controller.getCompaniesDB());
        botTable = new BotTable(controller.getBotsDB());
        jpBots.add(botCreation, BorderLayout.NORTH);
        jpBots.add(botTable, BorderLayout.CENTER);

        //Bar Chart
        jpTop = new TopChart();
        jpTop.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));

        //UsersTable
        jpBuyAction = new BuyAction(controller.getUsersNameDB());

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        cards.add(jpTop, TOPCHART_COMMAND);
        cards.add(jpBots, BOTENGINE_COMMAND);
        cards.add(jpBuyAction, USERSTABLE_COMMAND);
        add(cards, BorderLayout.CENTER);
        ((CardLayout)cards.getLayout()).show(cards, USERSTABLE_COMMAND);
        headerPanel.select(USERSTABLE_COMMAND);

        setTitle("Stock Server");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(1280,720);
        setVisible(true);
    }

    public void registerController(ServerController s) {
        botCreation.registerController(s);
        botTable.registerControllers(s);
        headerPanel.registerController(s);
        jpBuyAction.registerController(s);
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

    public HeaderPanel getHeaderPanel() {
        return headerPanel;
    }

    public BotCreation getBotCreation() {
        return botCreation;
    }

    public BotTable getBotTable() {
        return botTable;
    }

    public BuyAction getJpBuyAction() {
        return jpBuyAction;
    }

    public TopChart getJpTop() {
        return jpTop;
    }
}

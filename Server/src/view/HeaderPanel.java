package view;

import controller.Commons;
import controller.ServerController;
import model.User;

import javax.swing.*;
import java.awt.*;

import static controller.ServerController.*;
import static view.Typo.fontBoldLarge;
import static view.Typo.fontBoldMedium;

public class HeaderPanel extends JPanel {
    private JPanel jpUsersTable, jpTopChart, jpBotEngine;
    private JLabel jlImgLogo;

    public HeaderPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.white);

        //Stock logo image
        JPanel jpNorth = new JPanel();
        jpNorth.setBackground(Color.WHITE);
        jpNorth.setBorder(BorderFactory.createEmptyBorder(10,50,20,0));
        jlImgLogo = new JLabel();
        jlImgLogo.setIcon(new ImageIcon("Client/Images/stock_client.png"));
        jlImgLogo.setHorizontalAlignment(SwingConstants.CENTER);
        jlImgLogo.setVerticalAlignment(SwingConstants.CENTER);
        jpNorth.add(jlImgLogo);
        add(jpNorth, BorderLayout.CENTER);

        //Menu
        JPanel jpSouth = new JPanel(new GridLayout(1, 3));
        jpSouth.setBackground(Color.WHITE);

        jpUsersTable = new JPanel();
        JLabel jlUsersTable = new JLabel("USER SHARES");
        jpUsersTable.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jlUsersTable.setFont(fontBoldLarge);
        jlUsersTable.setHorizontalAlignment(SwingConstants.CENTER);
        jlUsersTable.setVerticalAlignment(SwingConstants.CENTER);
        jpUsersTable.add(jlUsersTable);
        jpSouth.add(jpUsersTable);

        //Top 10 chart
        jpTopChart = new JPanel();
        JLabel jlTopChart = new JLabel("TOP 10 COMPANIES");
        jpTopChart.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jlTopChart.setFont(fontBoldLarge);
        jlTopChart.setHorizontalAlignment(SwingConstants.CENTER);
        jlTopChart.setVerticalAlignment(SwingConstants.CENTER);
        jpTopChart.add(jlTopChart);
        jpSouth.add(jpTopChart);

        //Bot engine
        jpBotEngine = new JPanel();
        JLabel jlBotEngine = new JLabel("BOTS ENGINE");
        jpBotEngine.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jlBotEngine.setFont(fontBoldLarge);
        jlBotEngine.setHorizontalAlignment(SwingConstants.CENTER);
        jlBotEngine.setVerticalAlignment(SwingConstants.CENTER);
        jpBotEngine.add(jlBotEngine);
        jpSouth.add(jpBotEngine);

        add(jpSouth, BorderLayout.SOUTH);
    }

    public void registerController(ServerController controller) {
        jpUsersTable.setName(USERSTABLE_COMMAND);
        jpUsersTable.addMouseListener(controller);
        jpBotEngine.setName(BOTENGINE_COMMAND);
        jpBotEngine.addMouseListener(controller);
        jpTopChart.setName(TOPCHART_COMMAND);
        jpTopChart.addMouseListener(controller);
    }

    /**
     * Change panel colors for selection effect
     * @param name name of panel
     */
    public void select(String name) {
        switch(name) {
            case USERSTABLE_COMMAND:
                selectPanel(jpUsersTable, jpBotEngine, jpTopChart);
                break;
            case BOTENGINE_COMMAND:
                selectPanel(jpBotEngine, jpTopChart, jpUsersTable);
                break;
            case TOPCHART_COMMAND:
                selectPanel(jpTopChart, jpBotEngine, jpUsersTable);
                break;
        }
    }

    /**
     * Change panel colors for selection effect
     * @param selected panel selected
     * @param unselected0 panel unselected
     * @param unselected1 panel unselected
     */
    public void selectPanel(JPanel selected, JPanel unselected0, JPanel unselected1) {
        selected.setBackground(Color.WHITE);
        (selected.getComponent(0)).setForeground(Color.BLACK);
        unselected0.setBackground(Color.BLACK);
        (unselected0.getComponent(0)).setForeground(Color.WHITE);
        unselected1.setBackground(Color.BLACK);
        (unselected1.getComponent(0)).setForeground(Color.WHITE);
    }

}

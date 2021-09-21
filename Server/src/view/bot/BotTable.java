package view.bot;

import controller.ServerController;
import model.Bot;
import model.Company;

import javax.sound.sampled.Line;
import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

import static view.Typo.fontBoldMedium;

public class BotTable extends JPanel {
    private LinkedList<BotRow> botsRows;
    private JPanel jpTable2;
    private ServerController sc;

    public BotTable(LinkedList<Bot> botsList) {
        botsRows = new LinkedList<>();
        JPanel jpTable = new JPanel(new GridLayout(1,6));
        jpTable.setPreferredSize(new Dimension(720,30));
        jpTable.setBackground(Color.BLACK);

        JLabel jlId = new JLabel("Id");
        jlId.setFont(fontBoldMedium);
        jlId.setForeground(Color.WHITE);
        jlId.setHorizontalAlignment(SwingConstants.CENTER);
        jpTable.add(jlId);
        JLabel jlCompany = new JLabel("Company");
        jlCompany.setFont(fontBoldMedium);
        jlCompany.setForeground(Color.WHITE);
        jlCompany.setHorizontalAlignment(SwingConstants.CENTER);
        jpTable.add(jlCompany);
        JLabel jlPercentatge = new JLabel("Buy Probability");
        jlPercentatge.setFont(fontBoldMedium);
        jlPercentatge.setForeground(Color.WHITE);
        jlPercentatge.setHorizontalAlignment(SwingConstants.CENTER);
        jpTable.add(jlPercentatge);
        JLabel jlActivation = new JLabel("Activation Time");
        jlActivation.setFont(fontBoldMedium);
        jlActivation.setForeground(Color.WHITE);
        jlActivation.setHorizontalAlignment(SwingConstants.CENTER);
        jpTable.add(jlActivation);
        JLabel jlStatus = new JLabel("Status");
        jlStatus.setFont(fontBoldMedium);
        jlStatus.setForeground(Color.WHITE);
        jlStatus.setHorizontalAlignment(SwingConstants.CENTER);
        jpTable.add(jlStatus);
        JLabel jlDelete = new JLabel("Delete Bot");
        jlDelete.setFont(fontBoldMedium);
        jlDelete.setForeground(Color.WHITE);
        jlDelete.setHorizontalAlignment(SwingConstants.CENTER);
        jpTable.add(jlDelete);

        jpTable2 = new JPanel();
        jpTable2.setLayout(new BoxLayout(jpTable2, BoxLayout.Y_AXIS));

        JScrollPane jspList = new JScrollPane(jpTable2, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        generateBotsList(botsList);

        setLayout(new BorderLayout());
        add(jspList, BorderLayout.CENTER);
        add(jpTable, BorderLayout.NORTH);
        this.repaint();
    }

    private void generateBotsList(LinkedList<Bot> botsList) {
        botsRows = new LinkedList<>();
        for (int i = 0; i < botsList.size(); i++) {
            Bot bot = botsList.get(i);
            Company c = bot.getCompany();
            BotRow botRow = new BotRow("Bot "+i, c.getName(), String.valueOf(bot.getProbability()), String.valueOf(bot.getTimeSleep()), bot.isStatus(), i);
            botRow.registerController(sc);
            jpTable2.add(botRow);
            botsRows.add(botRow);
        }
    }

    public void refreshBotsList(LinkedList<Bot> bots){
        botsRows.clear();
        jpTable2.removeAll();
        generateBotsList(bots);
        revalidate();
        jpTable2.repaint();
        setVisible(true);
    }

    public void registerControllers(ServerController sc) {
        for (BotRow br : botsRows) {
            br.registerController(sc);
        }
    }

}
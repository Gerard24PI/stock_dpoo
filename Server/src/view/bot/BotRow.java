package view.bot;

import controller.ServerController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static view.Typo.fontBoldMedium;

public class BotRow extends JPanel {
    private BotRowEliminateButton bEliminate;
    private JRadioButton jrb, jrb2;

    public BotRow(String botName, String companyName, String probability, String time, boolean status, int posicio) {
        setLayout(new GridLayout(1,6));
        setBackground(Color.white);
        setPreferredSize(new Dimension(1280, 30));

        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK)));
        JLabel jlBotName = new JLabel(botName);
        jlBotName.setFont(fontBoldMedium);
        jlBotName.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(jlBotName);
        JLabel jlCompany = new JLabel(companyName);
        jlCompany.setFont(fontBoldMedium);
        jlCompany.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(jlCompany);
        JLabel jlProbability = new JLabel(String.valueOf(probability));
        jlProbability.setFont(fontBoldMedium);
        jlProbability.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(jlProbability);
        JLabel jlActiveTime = new JLabel(String.valueOf(time));
        jlActiveTime.setFont(fontBoldMedium);
        jlActiveTime.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(jlActiveTime);
        ButtonGroup group = new ButtonGroup();
        jrb = new BotRowChangeStatus(posicio,"ON");
        jrb.setBackground(Color.white);
        jrb.setFont(fontBoldMedium);
        jrb2 = new BotRowChangeStatus(posicio,"OFF");
        jrb2.setBackground(Color.white);
        jrb2.setFont(fontBoldMedium);
        if (status){
            jrb.setSelected(true);
        }else{
            jrb2.setSelected(true);
        }
        group.add(jrb);
        group.add(jrb2);
        JPanel jpRadio = new JPanel(new GridLayout(1,2));
        jpRadio.setBackground(Color.WHITE);
        jpRadio.setBorder(new EmptyBorder(0,35,0,0));
        jpRadio.add(jrb);
        jpRadio.add(jrb2);
        this.add(jpRadio);

        bEliminate = new BotRowEliminateButton(posicio,"Delete");
        bEliminate.setBackground(Color.red);
        bEliminate.setForeground(Color.white);
        this.add(bEliminate);

    }

    public void registerController(ServerController sc) {
        bEliminate.setActionCommand(ServerController.ELIMINATE_BOT);
        bEliminate.addActionListener(sc);
        jrb.setActionCommand(ServerController.STATUS_ON);
        jrb2.setActionCommand(ServerController.STATUS_OFF);
        jrb.addActionListener(sc);
        jrb2.addActionListener(sc);

    }

    public BotRowEliminateButton getbEliminate() {
        return bEliminate;
    }
}

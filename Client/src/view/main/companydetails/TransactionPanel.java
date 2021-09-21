package view.main.companydetails;

import controller.MainController;

import javax.swing.*;
import java.awt.*;

import static controller.MainController.*;

public class TransactionPanel extends JPanel {
    private JTextField jtfShares;
    private JButton jbBuy, jbSell, jbSellAll;

    public TransactionPanel() {
        setLayout(new GridLayout(3, 1));
        setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(200,130));

        //Number of shares text field
        jtfShares = new JTextField();
        add(jtfShares);

        //Buy and sell buttons
        JPanel jpButtons = new JPanel();
        jpButtons.setBackground(Color.white);
        jpButtons.setLayout(new GridLayout(1,2));
        jbBuy = new JButton("BUY");
        jbBuy.setBackground(Color.black);
        jbBuy.setForeground(Color.white);
        jpButtons.add(jbBuy);
        jbSell = new JButton("SELL");
        jbSell.setBackground(Color.black);
        jbSell.setForeground(Color.white);
        jpButtons.add(jbSell);
        add(jpButtons);

        //Sell all button
        jbSellAll = new JButton("SELL ALL");
        jbSellAll.setBorder(BorderFactory.createEmptyBorder(5,0,0,0));
        jbSellAll.setBackground(Color.red);
        jbSellAll.setForeground(Color.black);
        add(jbSellAll);
    }

    public String getNumShares() {
        return jtfShares.getText();
    }

    public void setJtfShares(String shares) {
        this.jtfShares.setText(shares);
    }

    public void registerController(MainController controller) {
        jbBuy.setActionCommand(BUY_COMMAND);
        jbBuy.addActionListener(controller);
        jbSell.setActionCommand(SELL_COMMAND);
        jbSell.addActionListener(controller);
        jbSellAll.setActionCommand(SELLALL_COMMAND);
        jbSellAll.addActionListener(controller);
        jtfShares.addKeyListener(controller);
    }
}

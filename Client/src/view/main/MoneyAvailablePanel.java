package view.main;

import controller.MainController;
import model.ClientModel;
import view.SButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static view.Typo.df_cashEuro;

public class MoneyAvailablePanel extends JPanel {

    private JTextField jtAddMoney;
    private JLabel jlAvailableMoney,jlRealAvailableMoney;
    private SButton jbSend;

    public MoneyAvailablePanel (float cash){

        setLayout (new GridLayout(1,3));
        setBackground(Color.white);

        //Label
        jlAvailableMoney = new JLabel();
        jlAvailableMoney.setText("Available money: ");
        jlAvailableMoney.setFont(new Font (Font.SANS_SERIF,Font.BOLD,20));
        jlAvailableMoney.setHorizontalAlignment(JLabel.CENTER);
        jlAvailableMoney.setVerticalAlignment(JLabel.CENTER);
        add(jlAvailableMoney);

        //Available Money
        jlRealAvailableMoney = new JLabel();
        jlRealAvailableMoney.setText(df_cashEuro.format(cash));
        jlRealAvailableMoney.setFont(new Font (Font.SANS_SERIF,Font.BOLD,40));
        jlRealAvailableMoney.setHorizontalAlignment(JLabel.CENTER);
        jlRealAvailableMoney.setVerticalAlignment(JLabel.CENTER);
        add(jlRealAvailableMoney);

        //Cash addition section
        JPanel jpCenter = new JPanel();
        jpCenter.setBackground(Color.white);
        jpCenter.setLayout(new GridLayout(2,1));
        jtAddMoney = new JTextField();
        jtAddMoney.setHorizontalAlignment(JTextField.CENTER);
        jpCenter.add(jtAddMoney);
        jpCenter.setBorder(new EmptyBorder(0, 80, 0, 20));
        add(jpCenter);
        //Send request button
        jbSend = new SButton("Add money", "white", "black");
        jbSend.setActionCommand("ADD");
        jpCenter.add(jbSend);
    }

    public void registerController (MainController mainController){
        jbSend.addActionListener(mainController);
        jtAddMoney.addKeyListener(mainController);
    }

    /**
     * Clears text of JTextField of adding cash section
     */
    public void clearText() {
        jtAddMoney.setText("");
    }

    /**
     * Refresh available money number regarding updated cash
     * @param cash updated cash
     */
    public void refreshMoney (float cash){
        jlRealAvailableMoney.setText(df_cashEuro.format(cash));
        repaint();
        setVisible(true);
    }

    public String getUserCash() {
        return jtAddMoney.getText();
    }

}

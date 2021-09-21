package view.bot;

import controller.ServerController;
import model.Company;
import view.SButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.LinkedList;

public class BotCreation extends JPanel{

    private JComboBox<String> jcCompanies;
    private JTextField percentatge;
    private JTextField activationTime;
    private SButton jbbot;

    public BotCreation(LinkedList<Company> companies) {
        setBackground(Color.WHITE);
        setLayout(new GridLayout(1, 4));
        setBorder(new EmptyBorder(10,20,10,20));

        JPanel crearBots = new JPanel(new GridLayout(2,1));
        crearBots.setBackground(Color.WHITE);
        crearBots.setBorder(new EmptyBorder(0,5,0,5));
        crearBots.add(new JLabel("   Company"));
        jcCompanies = new JComboBox<>();
        jcCompanies.setBackground(Color.BLACK);
        jcCompanies.setForeground(Color.WHITE);
        for (Company company : companies) {
            jcCompanies.addItem(company.getName());
        }

        percentatge = new JTextField();
        activationTime = new JTextField();
        crearBots.add(jcCompanies);
        add(crearBots);
        JPanel crearBots2 = new JPanel(new GridLayout(2,1));
        crearBots2.setBorder(new EmptyBorder(0,5,0,5));
        crearBots2.setBackground(Color.WHITE);
        crearBots2.add(new JLabel("   Buy Probability"));
        crearBots2.add(percentatge);
        add(crearBots2);

        JPanel crearBots3 = new JPanel(new GridLayout(2,1));
        crearBots3.setBorder(new EmptyBorder(0,5,0,5));
        crearBots3.setBackground(Color.WHITE);
        crearBots3.add(new JLabel("   Activation Time"));
        crearBots3.add(activationTime);
        add(crearBots3);

        JPanel jpCreate = new JPanel(new GridLayout(2,1));
        JPanel jpAux = new JPanel();
        jpAux.setBackground(Color.WHITE);
        jpAux.add(new JLabel(""));
        jpCreate.add(jpAux);
        jpCreate.setBorder(new EmptyBorder(0,30,0,30));
        jpCreate.setBackground(Color.WHITE);
        jbbot = new SButton("Create", "white", "black");
        jpCreate.add(jbbot);
        add(jpCreate);

    }

    public void registerController(ServerController serverController){
        jbbot.setActionCommand(ServerController.CREATE_BOT);
        jbbot.addActionListener(serverController);
    }

    public void clearTextFields(){
        percentatge.setText("");
        activationTime.setText("");
    }

    public String getActivationTime(){ return activationTime.getText(); }

    public String getPercentatge(){ return percentatge.getText(); }

    public Object getComboBoxItem(){return jcCompanies.getSelectedItem();}

}

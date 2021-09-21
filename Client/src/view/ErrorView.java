package view;

import controller.ClientController;
import model.Error;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ErrorView extends JFrame {
    private SButton jbOK;

    public ErrorView(Error error){

        setLayout(new BorderLayout());
        setBackground(Color.white);
        JLabel jlNorth = new JLabel();


        switch(error) {
            //Server response
            case USERNAME_EXISTS:
                jlNorth.setText("User already exists.");
                break;
            case EMAIL_EXISTS:
                jlNorth.setText("Email already exists.");
                break;
            //Client check
            case EMAIL_FORMAT:
                jlNorth.setText("Invalid Email format.");
                break;
            case PASSWORD_FORMAT:
                jlNorth.setText("Password not strong enough.");
                break;
            case PASSWORD_CONFIRMATION:
                jlNorth.setText("Confirm password does not match.");
                break;
            case EMPTY_FIELDS:
                jlNorth.setText("There is at least one blank field.");
                break;
            case LOGIN_FAILED:
                jlNorth.setText("Credentials incorrect.");
                break;
            case NO_MONEY:
                jlNorth.setText("¿De qué vas? No tienes ese dinero.");
                break;
            case NO_SHARES:
                jlNorth.setText("No me estafes, no tienes esas shares.");
                break;
            case BLANK_FIELD:
                jlNorth.setText("The field is empty. Please enter a number.");
                break;
            default:
                jlNorth.setText("Error.");
                break;
        }

        jlNorth.setFont(new Font (Font.SANS_SERIF,Font.BOLD,15));
        jlNorth.setHorizontalAlignment(JLabel.CENTER);
        jlNorth.setVerticalAlignment(JLabel.CENTER);
        jlNorth.setBorder(new EmptyBorder(20, 20, 20, 20));
        this.add(jlNorth,BorderLayout.NORTH);

        jbOK = new SButton("OK", "white", "black");
        this.add(jbOK,BorderLayout.SOUTH);

        setTitle("Error");
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        pack();
    }

    public void registerController(ClientController controller) {
        jbOK.addActionListener(controller);
        jbOK.setActionCommand(ClientController.OK_COMMAND);
    }

}

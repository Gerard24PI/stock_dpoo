package view.initial;

import controller.InitController;
import view.SButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.Font;

public class SignUpPanel extends JPanel {

    private JTextField jtfUserName,jtfEmail;
    private JPasswordField jpfPass,jpfPassVerify;
    private SButton jbSignUp;
    /**
     * Default constructor that initializes the Sign Up panel.
     */
    public SignUpPanel(){
        setLayout(new GridBagLayout());
        setBackground(Color.white);

        JLabel jlNorth = new JLabel();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        jlNorth.setText("Sign Up");
        jlNorth.setFont(new Font (Font.SANS_SERIF,Font.BOLD,20));
        jlNorth.setHorizontalAlignment(JLabel.CENTER);
        jlNorth.setVerticalAlignment(JLabel.CENTER);
        jlNorth.setBorder(new EmptyBorder(0, 0, 30, 0));
        add(jlNorth,constraints);

        JPanel jpCenter = new JPanel();
        jpCenter.setBackground(Color.white);
        jpCenter.setLayout(new GridLayout(4,2));
        JLabel jlUserName = new JLabel("User Name  ",SwingConstants.RIGHT);
        jtfUserName = new JTextField();
        jtfUserName.setHorizontalAlignment(JTextField.CENTER);
        jpCenter.add(jlUserName);
        jpCenter.add(jtfUserName);
        JLabel jlEmail = new JLabel("Email  ",SwingConstants.RIGHT);
        jtfEmail = new JTextField();
        jtfEmail.setHorizontalAlignment(JTextField.CENTER);
        jpCenter.add(jlEmail);
        jpCenter.add(jtfEmail);
        JLabel jlPass = new JLabel("Password  ",SwingConstants.RIGHT);
        jpfPass = new JPasswordField();
        jpfPass.setHorizontalAlignment(JPasswordField.CENTER);
        jpCenter.add(jlPass);
        jpCenter.add(jpfPass);
        JLabel jlPassVerify = new JLabel("Confirm password  ",SwingConstants.RIGHT);
        jpfPassVerify = new JPasswordField();
        jpfPassVerify.setHorizontalAlignment(JPasswordField.CENTER);
        jpCenter.add(jlPassVerify);
        jpCenter.add(jpfPassVerify);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 4;
        add(jpCenter, constraints);
        jpCenter.setBorder(new EmptyBorder(0, 10, 20, 10));

        jbSignUp = new SButton("Sign Up", "white", "black");

        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;

        add(jbSignUp,constraints);

    }

    public void registerController(InitController controller) {
        jbSignUp.addActionListener(controller);
        jbSignUp.setActionCommand(InitController.SIGNUP_COMMAND);
    }

    /**
     * Clears the fields userName, Email, Password and Password Verify.
     */
    public void clearFields() {
        jtfUserName.setText("");
        jtfEmail.setText("");
        jpfPass.setText("");
        jpfPassVerify.setText("");
    }

    public JTextField getJtfUserName() {
        return jtfUserName;
    }

    public JTextField getJtfEmail() {
        return jtfEmail;
    }

    public JPasswordField getJpfPass() {
        return jpfPass;
    }

    public JPasswordField getJpfPassVerify() {
        return jpfPassVerify;
    }



}

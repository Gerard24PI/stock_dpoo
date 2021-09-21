package view.initial;

import controller.InitController;
import view.SButton;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LogInPanel extends JPanel{

    private JTextField jtfUserEmail;
    private JPasswordField jpfPass;
    private SButton jbLogIn;

    /**
     * Default constructor that initializes the Log In panel.
     */
    public LogInPanel (){

        setLayout (new GridBagLayout());
        setBackground(Color.white);

        JLabel jlNorth = new JLabel();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        jlNorth.setText("Log In");
        jlNorth.setFont(new Font (Font.SANS_SERIF,Font.BOLD,20));
        jlNorth.setHorizontalAlignment(JLabel.CENTER);
        jlNorth.setVerticalAlignment(JLabel.CENTER);
        jlNorth.setBorder(new EmptyBorder(0, 0, 50, 0));
        add(jlNorth,constraints);

        JPanel jpCenter = new JPanel();
        jpCenter.setBackground(Color.white);
        jpCenter.setLayout(new GridLayout(2,2));
        JLabel jlUserEmail = new JLabel("User name / Email  ",SwingConstants.RIGHT);
        jtfUserEmail = new JTextField();
        jtfUserEmail.setHorizontalAlignment(JTextField.CENTER);
        jpCenter.add(jlUserEmail);
        jpCenter.add(jtfUserEmail);
        JLabel jlPass = new JLabel("Password  ",SwingConstants.RIGHT);
        jpfPass = new JPasswordField();
        jpfPass.setHorizontalAlignment(JPasswordField.CENTER);
        jpCenter.add(jlPass);
        jpCenter.add(jpfPass);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 4;
        add(jpCenter, constraints);
        jpCenter.setBorder(new EmptyBorder(0, 10, 40, 10));

        jbLogIn = new SButton("Log In", "white", "black");
        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;

        add(jbLogIn,constraints);
    }

    public void registerController(InitController controller) {
        jbLogIn.addActionListener(controller);
        jbLogIn.setActionCommand(InitController.LOGIN_COMMAND);
    }

    /**
     * Clears the fields userEmail and Password.
     */
    public void clearFields() {
        jtfUserEmail.setText("");
        jpfPass.setText("");
    }

    public JTextField getUserEmail() {
        return jtfUserEmail;
    }

    public JPasswordField getPass() {
        return jpfPass;
    }
}

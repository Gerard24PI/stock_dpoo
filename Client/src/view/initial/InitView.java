package view.initial;

import controller.InitController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class InitView extends JFrame {

    private SignUpPanel signUpPanel;
    private LogInPanel logInPanel;

    /**
     * Default constructor of the initialView, where the user can Log In and Sign Up.
     */
    public InitView() {

        this.getContentPane().setLayout(new GridBagLayout());
        this.getContentPane().setBackground(Color.white);

        Image icon = Toolkit.getDefaultToolkit().getImage("Client/Images/s_logo.png");
        setIconImage(icon);

        JLabel img = new JLabel(" ");
        ImageIcon image = new ImageIcon("Client/Images/stock_client.png");
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 1; // El área de texto empieza en la columna cero.
        constraints.gridy = 0; // El área de texto empieza en la fila cero
        constraints.gridwidth = 2; // El área de texto ocupa dos columnas.
        constraints.gridheight = 1; // El área de texto ocupa 2 filas.
        img.setBorder(new EmptyBorder(30,80,30,0));
        this.getContentPane().add(img, constraints);
        img.setIcon(image);
        img.setSize(200,200);
        img.setVisible(true);

        signUpPanel = new SignUpPanel();
        constraints.gridx = 0; // El área de texto empieza en la columna cero.
        constraints.gridy = 1; // El área de texto empieza en la fila cero
        constraints.gridwidth = 2; // El área de texto ocupa dos columnas.
        constraints.gridheight = 2; // El área de texto ocupa 2 filas.
        signUpPanel.setBorder(new EmptyBorder(0,0,20,0));
        this.getContentPane().add (signUpPanel, constraints);

        logInPanel = new LogInPanel();
        constraints.gridx = 2; // El área de texto empieza en la columna cero.
        constraints.gridy = 1; // El área de texto empieza en la fila cero
        constraints.gridwidth = 2; // El área de texto ocupa dos columnas.
        constraints.gridheight = 2; // El área de texto ocupa 2 filas.
        logInPanel.setBorder(new EmptyBorder(0,0,20,0));
        this.getContentPane().add (logInPanel, constraints);

        setTitle("Stock");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        pack();
    }

    public void registerController(InitController controller) {
        signUpPanel.registerController(controller);
        logInPanel.registerController(controller);
    }
    /**
     * Calls the cleaning functions of both panels.
     */
    public void clearAllFields() {
        signUpPanel.clearFields();
        logInPanel.clearFields();
    }

    /**
     * Shows/Hide the initial view
     * @param status true to make visible
     */
    public void initViewVisible(boolean status) {
        setVisible(status);
    }

    public SignUpPanel getSignUpPanel() {
        return signUpPanel;
    }

    public LogInPanel getLogInPanel() {
        return logInPanel;
    }

}
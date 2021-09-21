package view.main;

import controller.Commons;
import controller.MainController;
import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static controller.MainController.PROFILE_COMMAND;
import static view.Typo.fontBoldLarge;
import static view.Typo.fontBoldMedium;

public class HeaderPanel extends JPanel {
    private JLabel jlImgLogo;
    private JLabel jlImgUser;
    private JLabel jlCompanyName;

    public HeaderPanel(User user) {
        //setLayout (new GridBagLayout());
        setLayout(new BorderLayout());
        setBackground(Color.white);

        //Stock logo image
        JPanel jpWest = new JPanel();
        jpWest.setBackground(Color.WHITE);
        jpWest.setBorder(BorderFactory.createEmptyBorder(30,50,10,0));
        jlImgLogo = new JLabel();
        jlImgLogo.setIcon(new ImageIcon("Client/Images/stock_client.png"));
        jlImgLogo.setHorizontalAlignment(SwingConstants.CENTER);
        jlImgLogo.setVerticalAlignment(SwingConstants.CENTER);
        jpWest.add(jlImgLogo);
        add(jpWest, BorderLayout.WEST);

        //Company name (visible when company details required)
        JPanel jpName = new JPanel();
        jlCompanyName = new JLabel("");
        jlCompanyName.setBorder(BorderFactory.createEmptyBorder(35,0,0,0));
        jlCompanyName.setFont(fontBoldLarge);
        jlCompanyName.setHorizontalAlignment(SwingConstants.CENTER);
        jpName.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        jpName.setAlignmentY(JPanel.BOTTOM_ALIGNMENT);
        jlCompanyName.setVerticalAlignment(SwingConstants.BOTTOM);
        jpName.add(jlCompanyName);
        jpName.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
        jpName.setBackground(Color.WHITE);
        add(jpName, BorderLayout.CENTER);

        //User image and username Panel
        //Image
        JPanel jpEast = new JPanel(new BorderLayout());
        jpEast.setBackground(Color.WHITE);
        jpEast.setBorder(BorderFactory.createEmptyBorder(30,0,10,50));
        jlImgUser = new JLabel(new ImageIcon("Client/Images/"+ Commons.getJlImgUser(user.getImage()) + ".png"));
        jlImgUser.setHorizontalAlignment(SwingConstants.CENTER);
        jlImgUser.setVerticalAlignment(SwingConstants.CENTER);
        jpEast.add(jlImgUser, BorderLayout.CENTER);
        //Username
        JLabel jlUsername = new JLabel(user.getUsername());
        jlUsername.setFont(fontBoldMedium);
        jlUsername.setHorizontalAlignment(JLabel.CENTER);
        jlUsername.setVerticalAlignment(JLabel.CENTER);
        jlUsername.setBorder(BorderFactory.createEmptyBorder(0,0,0,20));
        jpEast.add(jlUsername, BorderLayout.WEST);
        add(jpEast, BorderLayout.EAST);
    }

    public void registerController(MainController controller) {
        jlImgUser.addMouseListener(controller);
        jlImgUser.setName(PROFILE_COMMAND);
        jlImgLogo.addMouseListener(controller);
        jlImgLogo.setName(MainController.RETURN_COMPANY_COMMAND);
    }

    /**
     * Updates profile image
     * @param image updated image
     */
    public void setImage (String image){
        ImageIcon imageUser = new ImageIcon("Client/Images/"+image+".png");
        jlImgUser.setIcon(imageUser);
    }

    /**
     * Refresh command of logo button function and header's title
     * @param command command of logo button
     * @param name header's title
     */
    public void updateHeaderLabels(String command, String name) {
        jlImgLogo.setName(command);
        jlCompanyName.setText(name);
        setVisible(true);
    }

}

package view.main;

import controller.Commons;
import controller.MainController;
import model.User;
import view.SButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

import static view.Typo.fontBoldMedium;

public class ProfileUser extends JPanel {

    private final JTextArea jtDescription;
    private final JLabel jlImgUser;
    private final JLabel jlUserName;
    private final JComboBox<String> jcImgs;
    private final SButton jbReturn;
    private final SButton jbLogOut;

    public ProfileUser () {
        setLayout(new GridLayout(1,3));
        setBackground(Color.white);
        setBorder(new EmptyBorder(30,0,30,0));

        //Return button
        JPanel jpReturn = new JPanel(new BorderLayout());
        jpReturn.setBorder(new EmptyBorder(5,75,5,75));
        jpReturn.setBackground(Color.WHITE);
        jbReturn = new SButton("Return","white","black");
        jpReturn.add(jbReturn, BorderLayout.NORTH);
        add(jpReturn);

        JPanel jpProfileInfo = new JPanel(new GridLayout(2,1));
        jpProfileInfo.setBorder(BorderFactory.createEmptyBorder(0,20,0,20));
        jpProfileInfo.setBackground(Color.WHITE);

        JPanel jpUserImage = new JPanel(new BorderLayout());
        jpUserImage.setBorder(BorderFactory.createEmptyBorder(85,50,20,50));
        jpUserImage.setBackground(Color.WHITE);
        //Image selection
        JPanel jpComboBox = new JPanel();
        jpComboBox.setBackground(Color.WHITE);
        jcImgs = new JComboBox<>();
        jcImgs.addItem("Default");
        jcImgs.addItem("Doggo");
        jcImgs.addItem("Grumpy Cat");
        jcImgs.addItem("Boromir");
        jcImgs.addItem("Horse");
        jcImgs.addItem("Sculpture");
        jcImgs.addItem("Gentleman");
        jcImgs.addItem("Ceus");
        jcImgs.addItem("Super Taldo");
        jcImgs.addItem("Pedobear");
        jcImgs.setBackground(Color.BLACK);
        jcImgs.setForeground(Color.white);
        jpComboBox.add(jcImgs);
        jpUserImage.add(jpComboBox, BorderLayout.NORTH);
        //Image
        JPanel jpImage = new JPanel(new BorderLayout());
        jpImage.setBorder(new EmptyBorder(0,0, 40, 0));
        jpImage.setBackground(Color.WHITE);
        jlImgUser = new JLabel(" ");
        jlImgUser.setName("imgUser");
        ImageIcon imageUser = new ImageIcon("Client/Images/Default.png");
        jlImgUser.setBorder(new EmptyBorder(0, 100, 0, 100));
        jlImgUser.setIcon(imageUser);
        jlImgUser.setVisible(true);
        jpImage.add(jlImgUser, BorderLayout.CENTER);
        //Username
        jlUserName = new JLabel();
        jlUserName.setFont(new Font (Font.SANS_SERIF,Font.BOLD,15));
        jlUserName.setHorizontalAlignment(SwingConstants.CENTER);
        jlUserName.setVerticalAlignment(SwingConstants.CENTER);
        jlUserName.setBorder(new EmptyBorder(0, 0, 40, 0));
        jpImage.add(jlUserName, BorderLayout.SOUTH);

        jpUserImage.add(jpImage, BorderLayout.CENTER);
        jpProfileInfo.add(jpUserImage);

        //Description text area
        JPanel jpDescription = new JPanel(new BorderLayout());
        jpDescription.setBackground(Color.WHITE);
        JLabel jlDescription = new JLabel("Description:");
        jlDescription.setFont(fontBoldMedium);
        jpDescription.add(jlDescription, BorderLayout.NORTH);
        jtDescription = new JTextArea("Add a description...");
        jtDescription.setName("jtdescription");
        jtDescription.setLineWrap(true);
        jtDescription.setBackground(Color.BLACK);
        jtDescription.setForeground(Color.WHITE);
        jtDescription.setFont(fontBoldMedium);
        jtDescription.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK,1),BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        jpDescription.add(jtDescription,BorderLayout.CENTER);

        jpProfileInfo.add(jpDescription);
        add(jpProfileInfo);

        //Log out button
        JPanel jpLogout = new JPanel(new BorderLayout());
        jpLogout.setBorder(new EmptyBorder(5,75,5,75));
        jpLogout.setBackground(Color.WHITE);
        jbLogOut = new SButton("Log out","white","red");
        jpLogout.add(jbLogOut, BorderLayout.NORTH);
        add(jpLogout);

        setPreferredSize(new Dimension(500,400));
        setVisible(true);
    }

    public void registerController(MainController controller) {
        jlUserName.setText(controller.getClient().getUser().getUsername());
        jbReturn.setActionCommand(MainController.RETURN_PROFILE_COMMAND);
        jbReturn.addActionListener(controller);
        jcImgs.addActionListener(controller);
        jcImgs.setActionCommand(MainController.SETIMAGE_COMMAND);
        jtDescription.addMouseListener(controller);
        jbLogOut.setActionCommand(MainController.LOGOUT_COMMAND);
        jbLogOut.addActionListener(controller);
    }

    /**
     * Refresh image with updated image
     * @param image updated image
     */
    public void setImage (String image){
        ImageIcon imageUser = new ImageIcon("Client/Images/"+image+".png");
        jlImgUser.setIcon(imageUser);
        jlImgUser.setName(image);
    }

    /**
     * Refresh user's profile with updated user
     * @param user updated user
     */
    public void updatePanel(User user) {
        setImage(Commons.getJlImgUser(user.getImage()));
        jcImgs.setSelectedItem(Commons.getJlImgUser(user.getImage()));
        jtDescription.setText(user.getDescription());
        repaint();
        setVisible(true);
    }

    /**
     * Get id number regarding the user's image name
     * @return id number of image
     */
    public int getJlImgUser() {
        switch (jlImgUser.getName()){
            case "Doggo":
                return 1;
            case "Grumpy Cat":
                return 2;
            case "Boromir":
                return 3;
            case "Horse":
                return 4;
            case "Sculpture":
                return 5;
            case "Gentleman":
                return 6;
            case "Ceus":
                return 7;
            case "Super Taldo":
                return 8;
            case "Pedobear":
                return 9;
            default:
                return 0;
        }
    }

    public String getJtDescription() {
        return jtDescription.getText();
    }

    public String getJlUserName() {
        return jlUserName.getText();
    }

}

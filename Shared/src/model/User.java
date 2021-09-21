package model;

import java.io.Serializable;
import java.util.LinkedList;

public class User implements Serializable{
    private String username;
    private String email;
    private String password;
    //private String hashedPass;
    private float cash;
    private LinkedList<Share> shares;
    private int image;
    private String description;

    public User(String username, String email, String password, float cash) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.cash = cash;
        this.shares = new LinkedList<>();
        this.image = 0;
        this.description ="";
    }

    public User(String username, String email, String password, float cash, int img, String desc) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.cash = cash;
        this.shares = new LinkedList<>();
        this.image = img;
        this.description = desc;
    }

    public String getDescription() {return description; }

    public void setDescription(String description) {this.description = description; }

    public int getImage() { return image; }

    public void setImage(int image) { this.image = image; }

    public String getUsername() {
        return username;
    }

    public LinkedList<Share> getShares() {
        return shares;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    /*public String getHashedPass() {
        return hashedPass;
    }*/

    public float getCash() {
        return cash;
    }

    public void setShares(LinkedList<Share> shares) {
        this.shares = shares;
    }

    public void setCash (float cash) {this.cash = cash;}

    /**
     * Remove all shares of one company and updates with list received
     * @param companyName of updated shares
     * @param sharesOf list of updated shares in one company
     */
    public void updateUserShares(String companyName, LinkedList<Share> sharesOf) {
        //Removes all shares of a determinated company
        shares.removeIf(s -> s.getCompany().getName().equals(companyName));
        //Adds all updated share of that company
        shares.addAll(sharesOf);
        //Sorts the list according to company's name and purchase price
        System.out.println(shares);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", cash=" + cash +
                '}';
    }
}

package model.db.dao;

import model.db.DBConnector;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class UserDAO {
    /**
     * Adds a user into User DB Entity
     * @param u User object to add
     */
    public static void addUser(User u) {
        String query = "INSERT INTO User(username, email, password) VALUES ('"+u.getUsername()+"', '"+
                u.getEmail()+"', '"+u.getPassword()+"');";
        System.out.println(query);
        DBConnector.getInstance().insertQuery(query);
    }

    /**
     * Get cash of determinated user
     * @param username determinated user name
     * @return cash of user
     */
    public static float getCash(String username) {
        float cash = 0;
        String query = "SELECT cash FROM User WHERE username = '" + username +"';";
        ResultSet response = DBConnector.getInstance().selectQuery(query);
        try{
            while(response.next())
                cash = response.getFloat("cash");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cash;
    }

    /**
     * Modifies the cash in DB with an addition of a determinated user
     * @param username of user that his cash is modified
     * @param add addition operation made on user's cash
     */
    public static void addCash(String username, float add){
        float cash = getCash(username);
        cash += add;
        System.out.println("Resultat cash: " + cash);
        String query = "UPDATE User SET cash = "+ cash +" WHERE username = '"+ username +"';";
        System.out.println(query);
        DBConnector.getInstance().updateQuery(query);
    }

    /**
     * Retunrs user id from DB
     * @param username User to check
     * @return userid o -1 si no es troba
     */

    public static int getUserId(String username){
        int userId = -1;
        String query = "SELECT iduser FROM User WHERE username = '" + username+"';";
        ResultSet response = DBConnector.getInstance().selectQuery(query);
        try{
            while(response.next())
                userId = response.getInt("iduser");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;
    }

    /**
     * Get list of usernames of all users
     * @return list of usernames
     */
    public static LinkedList<String> getUsersName() {
        LinkedList<String> users = new LinkedList<>();

        String query = "SELECT username FROM user;";
        ResultSet response = DBConnector.getInstance().selectQuery(query);
        try {
            while (response.next()) {
                String user = response.getString("username");
                users.add(user);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Checks that login and password exists for a user and correspond
     * @param login login used by client (either username or email)
     * @param password  password used to logged in by client
     * @return User object corresponding with a correct login (with its shares)
     */
    public static User userLogin(String login, String password){
        String username = "", email = "", pass = "", desc = "";
        int img = 0;
        float cash = 0;
        String query = "SELECT * FROM User WHERE username = '" + login + "' OR email = '" + login +"';";
        ResultSet response = DBConnector.getInstance().selectQuery(query);
        try{
            while(response.next()) {
                username = response.getString("username");
                email = response.getString("email");
                pass = response.getString("password");
                cash = response.getFloat("cash");
                img = response.getInt("image");
                desc = response.getString("description");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(password.equals(pass)){
            User aux = new User(username, email, pass, cash, img, desc);
            aux.setShares(ShareDAO.getUserShares(username));
            return aux;
        }
        else return null;
    }

    /**
     * Updates user profile's image and description
     * @param username User to update
     * @param image New image
     * @param description New description
     */
    public static void updateProfile(String username, int image, String description){
        String query = "UPDATE User SET image = " + image + ", description = '" + description + "' WHERE username = '" +
                username + "';";
        DBConnector.getInstance().updateQuery(query);
    }

    /**
     * Checks if username exists in DB, for signup
     * @param username to be checked
     * @return  true if exists
     */
    public static boolean findUserByName(String username){
        String name = "";
        String query = "SELECT username FROM User WHERE username ='" +username +"';";
        ResultSet response = DBConnector.getInstance().selectQuery(query);
        try{
            while(response.next())
                name = response.getString("username");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return !name.equals("");
    }

    /**
     * Checks if email exists in DB, for signup
     * @param email to be checked
     * @return true if exists
     */
    public static boolean findUserByEmail(String email){
        String mail = "";
        String query = "SELECT email FROM User WHERE email ='" +email +"';";
        ResultSet response = DBConnector.getInstance().selectQuery(query);
        try{
            while(response.next())
                mail = response.getString("email");
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return !mail.equals("");
    }

}

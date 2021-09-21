package model.db.dao;

import model.Company;
import model.db.DBConnector;
import model.Share;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class ShareDAO {

    /**
     * Adds or substracts shares owned by determinated user
     * @param username that buy or sell shares
     * @param companyName linked with shares
     * @param numShares that user wants to add/substract (negative if he wants to sell)
     * @param companyShareValue value of a company share
     */
    public static void addShare(String username, String companyName, int numShares, float companyShareValue){
        int userID = UserDAO.getUserId(username);
        int companyID = CompanyDAO.getCompanyId(companyName);
        //Add shares
        if (numShares > 0) {
            int numSharesEqualPurchasePrice = sharesEqualPurchasePrice(userID, companyID, companyShareValue);
            //Sumar numShare en la fila (UPDATE)
            if (numSharesEqualPurchasePrice != -1) {
                numShares += numSharesEqualPurchasePrice;
                String query = "UPDATE Share SET numshares = "+ numShares +" WHERE iduser =" + userID + " AND idcompany =" + companyID +
                        " AND purchaseprice =" + companyShareValue + ";";
                DBConnector.getInstance().updateQuery(query);
            }
            //Afegir nova fila (INSERT)
            else {
                String query = "INSERT INTO Share (iduser, idcompany, numshares, purchaseprice) " +
                        "VALUES (" + userID + ", " + companyID + ", " + numShares + ", " + companyShareValue + ");";
                DBConnector.getInstance().insertQuery(query);
            }
        }
        //Substract shares
        else if (numShares < 0) {
            String query = "SELECT * from Share WHERE iduser =" + userID + " AND idcompany =" + companyID + ";";
            ResultSet response = DBConnector.getInstance().selectQuery(query);
            try {
                while(numShares != 0){
                    response.next();
                    numShares += response.getInt("numshares");
                    if (numShares <= 0) {
                        String delete = "DELETE FROM Share WHERE idusershare =" + response.getInt("idusershare") + ";";
                        DBConnector.getInstance().deleteQuery(delete);
                    }
                    else {
                        String update = "UPDATE Share SET numshares = "+ numShares +" WHERE idusershare =" + response.getInt("idusershare") + ";";
                        DBConnector.getInstance().updateQuery(update);
                        numShares = 0;
                    }
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get number of shares with same purchase price regarding a determinated user and company
     * @param userID determined user id
     * @param companyID determined company id
     * @param purchasePrice purchase price
     * @return  returns the number of shares
     */
    private static int sharesEqualPurchasePrice(int userID, int companyID, float purchasePrice) {
        String query = "SELECT * from Share WHERE iduser =" + userID + " AND idcompany =" + companyID + ";";
        ResultSet response = DBConnector.getInstance().selectQuery(query);
        try {
            while(response.next()){
                if (purchasePrice == response.getFloat("purchaseprice"))
                    return response.getInt("numshares");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }



    /**
     * Returns shares from Share table in DB related with User
     * @param username User whose shares must be found
     * @return Shares list of that user
     */
    public static LinkedList<Share> getUserShares(String username){
        LinkedList<Share> shares = new LinkedList<>();
        Company company;
        String query = "SELECT * from Share WHERE iduser =" + UserDAO.getUserId(username) + ";";
        ResultSet response = DBConnector.getInstance().selectQuery(query);
        try{
            while(response.next()){
                company = CompanyDAO.getCompany(response.getInt("idcompany"));
                int numshares = response.getInt("numshares");
                float purchasePrice = response.getFloat("purchaseprice");
                shares.add(new Share(company, numshares, purchasePrice));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shares;
    }

    /**
     * Returns list of shares of a determinate user and company
     * @param username owner of shares
     * @param companyName of the shares
     * @return list of shares of a determinate user and company
     */
    public static LinkedList<Share> getSharesOf(String username, String companyName) {
        int userID = UserDAO.getUserId(username);
        int companyID = CompanyDAO.getCompanyId(companyName);
        LinkedList<Share> shares = new LinkedList<>();
        String query = "SELECT * from Share WHERE iduser =" + userID + " AND idcompany =" + companyID + " ORDER BY purchaseprice ASC;";
        ResultSet response = DBConnector.getInstance().selectQuery(query);
        try{
            while(response.next()){
                Company c = CompanyDAO.getCompany(companyID);
                int numshares = response.getInt("numshares");
                float purchasePrice = response.getFloat("purchaseprice");
                shares.add(new Share(c, numshares, purchasePrice));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shares;
    }

    /**
     * Gives the number of shares of the company owned by a user
     * @param username of user that owns the shares
     * @param companyName of shares
     * @return number of shares that owns a user of respective company
     */
    public static int getOwnedShares(String username, String companyName){
        int numShares = 0;
        int userID = UserDAO.getUserId(username);
        int companyID = CompanyDAO.getCompanyId(companyName);
        String query = "SELECT numshares from Share WHERE iduser =" + userID + " AND idcompany =" + companyID + ";";
        ResultSet response = DBConnector.getInstance().selectQuery(query);
        try {
            while(response.next()){
                numShares += response.getInt("numshares");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return numShares;
    }
}

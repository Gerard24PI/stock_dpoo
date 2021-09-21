package model.db.dao;

import model.Company;
import model.db.DBConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

public class CompanyDAO {

    /**
     * Get DBid of company by name
     * @param name name of company
     * @return id in DB
     */
    public static int getCompanyId(String name){
        int companyId = 0;
        String query = "SELECT idcompany FROM Company WHERE name = '" + name +"';";
        ResultSet response = DBConnector.getInstance().selectQuery(query);
        try{
            response.next();
            companyId = response.getInt("idcompany");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(companyId == 0) return -1;
        return companyId;
    }

    /**
     * Get Company from DB regarding his company id
     * @param id company id in DB
     * @return company
     */
    public static Company getCompany(int id){
        String name = null;
        int shares = 0;
        float sharevalue = 0;
        String query = "SELECT * FROM Company WHERE idcompany = " + id + ";";
        ResultSet response = DBConnector.getInstance().selectQuery(query);
        try{
            while(response.next()) {
                name = response.getString("name");
                shares = response.getInt("shares");
                sharevalue = response.getFloat("sharevalue");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new Company(name, shares, sharevalue);
    }


    /**
     * Returns a list of all companies stored in DB
     * @return Companies list stored in DB
     */
    public static LinkedList<Company> getCompanies(){
        LinkedList<Company> companies = new LinkedList<>();
        String query = "SELECT * FROM Company ORDER BY name ASC;";
        ResultSet response = DBConnector.getInstance().selectQuery(query);
        try{
            while(response.next()){
                String name = response.getString("name");
                int shares = response.getInt("shares");
                float sharevalue = response.getFloat("sharevalue");
                Company c = new Company(name, shares, sharevalue);
                companies.add(c);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return companies;
    }

    /**
     * Updates share value of a company
     * @param companyName to be updated
     * @param shareValue of company to be updated
     */

    public static synchronized void setShareValue(String companyName, float shareValue){
        String query = "UPDATE Company SET sharevalue = " + shareValue + " WHERE name = '" + companyName + "';";
        DBConnector.getInstance().updateQuery(query);
    }

    public static synchronized float getShareValue(String companyName) {
        float shareValue = -1;
        String query = "SELECT sharevalue FROM Company WHERE name = '" + companyName+"';";
        ResultSet response = DBConnector.getInstance().selectQuery(query);
        try{
            while(response.next())
                shareValue = response.getFloat("sharevalue");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shareValue;
    }

    public static ArrayList<Company> getTopTen(){
        ArrayList<Company> companies = new ArrayList<>();
        String query = "SELECT * FROM Company ORDER BY sharevalue DESC LIMIT 10";
        ResultSet response = DBConnector.getInstance().selectQuery(query);
        try{
            while(response.next()){
                String name = response.getString("name");
                int nShares = response.getInt("shares");
                float sValue = response.getFloat("sharevalue");
                companies.add(new Company(name, nShares, sValue));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return companies;
    }

}

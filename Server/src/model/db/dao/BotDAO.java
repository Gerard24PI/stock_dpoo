package model.db.dao;

import controller.ServerController;
import model.Bot;
import model.db.DBConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class BotDAO {

    /**
     * Add bot to DB
     * @param b bot to be added in DB
     */
    public static void addBot(Bot b){
        String query = "INSERT INTO Bot(idcompany, buyprob, activetime, state) VALUES (" + CompanyDAO.getCompanyId(b.getCompany().getName())
                + ", " + b.getProbability() + ", " + b.getTimeSleep() + ", " + b.isStatus() + ");";
        DBConnector.getInstance().insertQuery(query);
    }

    /**
     * Get bots from DB
     * @return bots
     */
    public static LinkedList<Bot> getBots(){
        LinkedList<Bot> bots = new LinkedList<>();
        String query = "SELECT * FROM Bot;";
        ResultSet response = DBConnector.getInstance().selectQuery(query);
        try{
            while(response.next()){
                int id =  response.getInt("idcompany");
                float buyprob = response.getFloat("buyprob");
                float active = response.getFloat("activetime");
                boolean state = response.getBoolean("state");
                bots.add(new Bot(buyprob, active, state, CompanyDAO.getCompany(id)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return bots;
    }

    /**
     * Remove bot from DB
     * @param b bot to be deleted
     */
    public static void removeBot(Bot b){
        int idcompany = CompanyDAO.getCompanyId(b.getCompany().getName());
        int state = b.isStatus() ? 1 : 0;
        String query = "DELETE FROM Bot WHERE idcompany = " + idcompany + " AND buyprob = " + b.getProbability() + " AND activetime = " + b.getTimeSleep() + " AND state = " + state + ";";
        DBConnector.getInstance().deleteQuery(query);
    }

    /**
     * Update status of bot in DB
     * @param b bot to be updated
     * @param status status of bot
     */
    public static void updateStatusBot(Bot b, boolean status){
        int idcompany = CompanyDAO.getCompanyId(b.getCompany().getName());
        int botState = b.isStatus() ? 1 : 0;
        int state = status ? 1 : 0;

        String query = "UPDATE Bot SET state = " + state + " WHERE idcompany = " + idcompany + " AND buyprob = " + b.getProbability() + " AND activetime = " + b.getTimeSleep() + " AND state = " + botState + ";";
        DBConnector.getInstance().updateQuery(query);
    }
}

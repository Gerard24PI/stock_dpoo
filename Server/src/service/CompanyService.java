package service;

import controller.ServerController;
import model.Company;
import model.db.dao.CompanyDAO;
import model.network.Server;
import network.DataTransferObject;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class CompanyService {
    private LinkedList<CompanyInfoCollector> companyInfoCollectors;
    private ServerController controller;
    private Server server;

    public CompanyService(ServerController controller, Server server) {
        this.controller = controller;
        this.server = server;
        companyInfoCollectors = new LinkedList<>();

        //Creates one collecting information service per company
        LinkedList<Company> companies = this.controller.getCompaniesDB();
        for (Company c : companies) {
            CompanyInfoCollector infoCollector = new CompanyInfoCollector(c.getName(), c.getShareValue(), this);
            companyInfoCollectors.add(infoCollector);
            //It creates one service every 1ms
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Sends message to all clients
     * @param message message
     */
    public void sendBroadcast(DataTransferObject message) {
        server.sendBroadcast(message);
    }

    /**
     * Use DAO for extract share value of a determinated company from DB
     * @param companyName determinated company name
     * @return share value of company
     */
    public float getShareValue(String companyName) {
        return CompanyDAO.getShareValue(companyName);
    }

    public LinkedList<CompanyInfoCollector> getCompanyInfoCollectors() {
        return companyInfoCollectors;
    }

}

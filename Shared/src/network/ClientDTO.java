package network;

import model.Company;
import model.User;

import java.io.Serializable;
import java.util.LinkedList;

public class ClientDTO extends DataTransferObject implements Serializable {
    private User user;
    private LinkedList<Company> companies;

    public ClientDTO(User user, LinkedList<Company> companies) {
        this.user = user;
        this.companies = companies;
    }

    public User getUser() {
        return user;
    }

    public LinkedList<Company> getCompanies() {
        return companies;
    }
}

package model;

import java.io.Serializable;
import java.util.LinkedList;

public class ClientModel implements Serializable {
    private LinkedList<Company> companies;
    private User user;

    public ClientModel(User user, LinkedList<Company> companies) {
        this.user = user;
        this.companies = companies;
    }

}

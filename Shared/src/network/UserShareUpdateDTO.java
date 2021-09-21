package network;

import model.Share;

import java.io.Serializable;
import java.util.LinkedList;

public class UserShareUpdateDTO extends DataTransferObject implements Serializable {
    private boolean buy;
    private float money;
    private LinkedList<Share> sharesOf;
    private String companyName;

    public UserShareUpdateDTO(boolean buy, float money, LinkedList<Share> sharesOf, String companyName) {
        this.buy = buy;
        this.money = money;
        this.sharesOf = sharesOf;
        this.companyName = companyName;
    }

    public boolean isBuy() {
        return buy;
    }

    public float getMoney() {
        return money;
    }

    public LinkedList<Share> getSharesOf() {
        return sharesOf;
    }

    public String getCompanyName() {
        return companyName;
    }
}

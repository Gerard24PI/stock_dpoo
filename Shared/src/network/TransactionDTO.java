package network;

import java.io.Serializable;

public class TransactionDTO extends DataTransferObject implements Serializable {
    private boolean transactionType;
    private int numShares;
    private String companyName;
    private String username;

    public TransactionDTO(boolean transactionType, int numShares, String companyName, String username) {
        this.transactionType = transactionType;
        this.numShares = numShares;
        this.companyName = companyName;
        this.username = username;
    }

    public boolean isBuy() {
        return transactionType;
    }

    public int getNumShares() {
        return numShares;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getUsername() {
        return username;
    }
}

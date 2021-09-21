package model;

import java.io.Serializable;

public class Share implements Serializable {
    private Company company;
    private int number;
    private float purchasePrice;

    public Share(Company company, int number, float purchasePrice) {
        this.company = company;
        this.number = number;
        this.purchasePrice = purchasePrice;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public float getPurchasePrice() {
        return purchasePrice;
    }

    @Override
    public String toString() {
        return "Share{" +
                "company=" + company +
                ", number=" + number +
                ", purchasePrice=" + purchasePrice +
                "}\n";
    }
}

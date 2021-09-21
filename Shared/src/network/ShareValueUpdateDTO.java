package network;

import java.io.Serializable;

public class ShareValueUpdateDTO extends DataTransferObject implements Serializable {
    private String companyName;
    private float shareValue;

    public ShareValueUpdateDTO(String companyName, float shareValue) {
        this.companyName = companyName;
        this.shareValue = shareValue;
    }

    public String getCompanyName() {
        return companyName;
    }

    public float getShareValue() {
        return shareValue;
    }
}

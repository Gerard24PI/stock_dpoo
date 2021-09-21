package network;

import java.io.Serializable;

public class ShareValueSampleDTO extends DataTransferObject implements Serializable {
    private float shareValue;
    private String companyName;

    public ShareValueSampleDTO(float shareValue, String companyName) {
        this.shareValue = shareValue;
        this.companyName = companyName;
    }

    public float getShareValue() {
        return shareValue;
    }

    public String getCompanyName() {
        return companyName;
    }
}

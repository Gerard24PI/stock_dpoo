package network;

import model.Candle;

import java.io.Serializable;

public class NewCandleDTO extends DataTransferObject implements Serializable {
    private Candle candle;
    private String companyName;

    public NewCandleDTO(Candle candle, String companyName) {
        this.candle = candle;
        this.companyName = companyName;
    }

    public Candle getCandle() {
        return candle;
    }

    public String getCompanyName() {
        return companyName;
    }
}

package network;

import java.io.Serializable;

public class UpdateCashDTO extends DataTransferObject implements Serializable {

    private String username;
    private float newCash;

    public UpdateCashDTO(String username, float newCash){
        this.username = username;
        this.newCash = newCash;
    }

    public String getUsername() {
        return username;
    }

    public float getNewCash() {
        return newCash;
    }
}

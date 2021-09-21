package network;

import model.User;

import java.io.Serializable;

/**
 * When user updates profile, this is sent from client when clicks return to server and saved in db
 */

public class ProfileDTO extends DataTransferObject implements Serializable {
    private String username;
    private int image;
    private String description;

    public ProfileDTO(String usr, int img, String desc){
        username = usr;
        image = img;
        description = desc;
    }

    public String getUsername() {
        return username;
    }

    public int getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }
}

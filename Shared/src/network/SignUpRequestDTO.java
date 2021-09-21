package network;

import java.io.Serializable;

public class SignUpRequestDTO extends DataTransferObject implements Serializable {
    private String username, email, password, confirmPassword;

    public SignUpRequestDTO(String username, String email, String password, String confirmPassword) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }


}

package network;

import java.io.Serializable;

public class LogInRequestDTO extends DataTransferObject implements Serializable {
    private String login, password;

    public LogInRequestDTO(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}

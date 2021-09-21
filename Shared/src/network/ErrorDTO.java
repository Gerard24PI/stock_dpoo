package network;

import model.Error;

import java.io.Serializable;
public class ErrorDTO extends DataTransferObject implements Serializable {
    private Error error;

    public ErrorDTO(Error error) {
        this.error = error;
    }

    public Error getError() {
        return error;
    }
}

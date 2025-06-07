package model.obieErrCode;

public class InvalidStatus extends RuntimeException {
    public InvalidStatus(String message) {
        super(message);
    }
}

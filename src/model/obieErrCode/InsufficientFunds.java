package model.obieErrCode;

/**
 * UK.OBIE.Payment.InsufficientFunds
 */
public class InsufficientFunds extends Exception {
    public InsufficientFunds(String message) {
        super(message);
    }
}

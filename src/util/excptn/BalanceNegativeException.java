package util.excptn;

public class BalanceNegativeException extends RuntimeException {
    public BalanceNegativeException(String message) {
        super(message);
    }
}

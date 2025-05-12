package handler.wthdr;

public class AlreadyProcessedEx extends RuntimeException {
    public AlreadyProcessedEx(String s) {
        super(s);
    }
}

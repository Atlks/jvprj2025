package model.obieErrCode;

public class AmtCantLessThan0Excptn extends RuntimeException {
    public AmtCantLessThan0Excptn(String string) {
        super(string);
    }
}

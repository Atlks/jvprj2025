package handler.pay;

import util.excptn.ExceptionBase;

public class AreadyProcessedEx  extends Exception {
    public AreadyProcessedEx(String s) {
  super(s);
    }
}

package util.ex;

import util.excptn.ExceptionObj;

public class UserNotExistEx extends ExceptionObj {
    public UserNotExistEx(String msg) {
        super(msg);
    }

    public UserNotExistEx(String msg, String currentMethodName, Object funPrms) {
        super(msg);
        this.fun=currentMethodName;
        this.funPrm=funPrms;
    }
}

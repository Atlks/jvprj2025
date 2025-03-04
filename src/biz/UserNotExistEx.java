package biz;

import entityx.ExceptionBase;

public class UserNotExistEx extends ExceptionBase {
    public UserNotExistEx(String msg) {
        super(msg);
    }

    public UserNotExistEx(String msg, String currentMethodName, Object funPrms) {
        super(msg);
        this.fun=currentMethodName;
        this.funPrm=funPrms;
    }
}

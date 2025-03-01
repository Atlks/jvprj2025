package api.usr;

import biz.PwdErrEx;
import entityx.ExceptionBase;

public class LoginEx extends ExceptionBase {
    public LoginEx(String msg
    ) {
        super(msg);
    }
}

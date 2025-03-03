package api.usr;

import biz.PwdErrEx;

public class RuntimeExceptionPwdErrEx extends RuntimeException {
    public RuntimeExceptionPwdErrEx(String pwdErrEx, Exception eInfo) {
        super(pwdErrEx,eInfo);
    }
}

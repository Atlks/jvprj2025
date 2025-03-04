package biz;

public class PwdErrRuntimeExcept extends RuntimeException {
    public PwdErrRuntimeExcept(String pwdErrEx, Throwable eInfo) {
        super(pwdErrEx,eInfo);
    }
}

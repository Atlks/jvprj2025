package util.ex;

public class PwdErrRuntimeExcept extends RuntimeException {
    public PwdErrRuntimeExcept(String pwdErrEx, Throwable eInfo) {
        super(pwdErrEx,eInfo);
    }
}

package service.auth;

public class AuthenticationExceptionRuntimeException extends RuntimeException {
    public AuthenticationExceptionRuntimeException(Throwable e) {
   super(e);
    }
}

package util.ex;

public class UserNotExistRuntimeExcept extends RuntimeException {
    public UserNotExistRuntimeExcept(String userNotExistEx, Throwable eInfo) {
   super(userNotExistEx,eInfo);
    }
}

package api.usr;

public class RuntimeExceptionUserNotExistEx extends RuntimeException {
    public RuntimeExceptionUserNotExistEx(String userNotExistEx, Exception eInfo) {
   super(userNotExistEx,eInfo);
    }
}

package api.usr;

import entityx.ExceptionBase;

public class UserNotExistEx extends ExceptionBase {
    public UserNotExistEx(String msg) {
        super(msg);
    }
}

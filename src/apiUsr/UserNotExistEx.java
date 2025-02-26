package apiUsr;

import entityx.ExceptionBase;

public class UserNotExistEx extends ExceptionBase {
    public UserNotExistEx(String msg) {
        super(msg);
    }
}

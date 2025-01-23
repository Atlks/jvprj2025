package biz;

public class existUserEx extends Throwable {
    public existUserEx(String uname) {
        super("exituser="+uname);
    }
}

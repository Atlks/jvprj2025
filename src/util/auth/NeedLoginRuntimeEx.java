package util.auth;

public class NeedLoginRuntimeEx extends RuntimeException {
    public NeedLoginRuntimeEx(String s, Exception e) {
super(s,e);    }
}

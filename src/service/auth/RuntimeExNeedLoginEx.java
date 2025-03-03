package service.auth;

import biz.NeedLoginEx;

public class RuntimeExNeedLoginEx extends RuntimeException {
    public RuntimeExNeedLoginEx(String s, Exception e) {
super(s,e);    }
}

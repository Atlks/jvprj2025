package biz;

import entityx.ExceptionBase;

public class existUserEx extends ExceptionBase {
    public existUserEx(String msg) {
        super( msg);
    }
    public existUserEx(String msg,String curFun,Object curFunPrm) {
        super( msg);
        this.fun=curFun;
        this.funPrm=curFunPrm;
    }
}

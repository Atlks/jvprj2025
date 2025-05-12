package util.ex;

import util.excptn.ExceptionObj;

public class existUserEx extends Exception{
    public existUserEx(String msg) {
        super( msg);
    }
//    public existUserEx(String msg,String curFun,Object curFunPrm) {
//        super( msg);
//        this.fun=curFun;
//        this.funPrm=curFunPrm;
//    }
}

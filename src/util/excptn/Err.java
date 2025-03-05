package util.excptn;

public class Err {
    public String errmsg;
    public Object info;
   public  String fun;
    public  Object funPrm;
    public String stackTrace;

    public Err(String errmsg0,String fun0,Object funPrm0,Object infoobj) {

        errmsg=errmsg0;
        info=infoobj;
        fun=fun0;
        funPrm=funPrm0;

    }
}

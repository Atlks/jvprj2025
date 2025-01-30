package apiUsr;

public class ThrowableX extends Throwable {

    public String errmsg;
    public String errcode;
    public  String fun;
    public  Object funPrm;
    public Object info;
    public String stackTrace;
    public ThrowableX(String message) {
        super(message);
        errmsg=message;
    }
}

package util.excptn;

import java.util.HashMap;
import java.util.Map;

public class ThrowableX extends Throwable {

    public String  timestamp;
    public String  details;
    public String errmsg;
    public String errcode;
    public  String fun;
    public  Object funPrm;
    public Object info;
    public String stackTrace;
    public String   requestId;
    public  Throwable cause;
    public Map<String, Object> extraData=new HashMap<>();
    public ThrowableX(String message) {
        super(message);
        errmsg=message;

    }
}

package util;

import java.util.HashMap;
import java.util.Map;

public class ExceptionBase extends  Exception {

    public String url;
    public String urlprm;

    public ExceptionBase() {


    }

    public String  timestamp;
    public String  details;
    public String errmsg;
    public String errcode;
    public  String fun="";
    public  Object funPrm;
    public Object info;
    public String stackTrace;
    public String   requestId;
    public  Throwable cause;
    public Map<String, Object> extraData=new HashMap<>();
    public ExceptionBase(String message) {
        super(message);
        errmsg=message;

    }
}

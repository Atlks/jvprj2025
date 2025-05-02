package util.excptn;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
@Data
public class ExceptionObj extends  Exception {

    public String url;
    public String urlprm;
    public String stackTraceStr;



    public ExceptionObj() {


    }

    public String getUrl() {
        return url;
    }

    public String getUrlprm() {
        return urlprm;
    }

    public String getStackTraceStr() {
        return stackTraceStr;
    }

    public String getTimestamp() {
        return timestamp;
    }



    public Object getInfo() {
        return info;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

    public String  timestamp;
    public String  details;
    public String errmsg;
    public String errcode;
    public  String fun="";
    public  Object funPrm;
    public Object info;
    public  Object lastExsList;

    public String   requestId;
    public  Throwable cause;
    public Map<String, Object> extraData=new HashMap<>();
    public ExceptionObj(String message) {
        super(message);
        errmsg=message;

    }
}

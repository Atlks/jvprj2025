package util.oo;

import entityx.ApiResponse;
import com.sun.net.httpserver.HttpExchange;
import util.excptn.ExceptionBase;
import util.excptn.ExceptionBaseRtm;
import util.serverless.ApiGatewayResponse;


import static util.excptn.ExptUtil.addInfo2ex;
import static util.misc.Util2025.*;
import static util.misc.util2026.wrtRespErrNoex;
import static util.serverless.ApiGatewayResponse.createErrResponseWzErrcode;

public class WebsrvUtil {


    public static String processNmlExptn(HttpExchange exchange, Throwable e) {
        ExceptionBase ex;
//        System.out.println(
//                "⚠\uFE0F e="
//                        + e.getMessage() + "\nStackTrace="
//                        + getStackTraceAsString(e)
//                        + "\n end stacktrace......................"
//        );


        //my throw ex.incld funprm
        if (e instanceof ExceptionBase) {
            ex = (ExceptionBase) e;
            ex.errcode = e.getClass().getName();


        }else if( e instanceof ExceptionBaseRtm){
            ex = new ExceptionBase(e.getMessage());

            //cvt to cstm ex
            String message = e.getMessage();
            ex = new ExceptionBase(message);
            ex.cause = e;
            ex.errcode =((ExceptionBaseRtm) e).getType();
        }

        else {
            //nml err
            ex = new ExceptionBase(e.getMessage());

            //cvt to cstm ex
            String message = e.getMessage();
            ex = new ExceptionBase(message);
            ex.cause = e;
            ex.errcode = e.getClass().getName();

        }

        addInfo2ex(ex, e);


        ApiGatewayResponse errResponseWzErrcode = createErrResponseWzErrcode(ex);
        String responseTxt =  encodeJson4ex(errResponseWzErrcode);

        wrtRespErrNoex(exchange, responseTxt);
        return responseTxt;
    }
}

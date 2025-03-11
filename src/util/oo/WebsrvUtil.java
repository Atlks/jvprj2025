package util.oo;

import com.sun.net.httpserver.HttpExchange;
import util.excptn.ExceptionBase;

import static biz.Response.createErrResponseWzErrcode;
import static util.excptn.ExptUtil.addInfo2ex;
import static util.misc.Util2025.encodeJson;
import static util.misc.util2026.wrtRespErrNoex;

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


        } else {
            //nml err
            ex = new ExceptionBase(e.getMessage());

            //cvt to cstm ex
            String message = e.getMessage();
            ex = new ExceptionBase(message);
            ex.cause = e;
            ex.errcode = e.getClass().getName();

        }

        addInfo2ex(ex, e);


        String responseTxt = encodeJson(createErrResponseWzErrcode(ex));

        wrtRespErrNoex(exchange, responseTxt);
        return responseTxt;
    }
}

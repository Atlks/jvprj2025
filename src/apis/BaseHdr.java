package apis;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

import static biz.AccBiz.listAccLog;
import static util.Util2025.encodeJson;
import static util.util2026.*;

public  abstract class BaseHdr implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {
setcookie("uname","ttt",exchange);//for test
            handle2(exchange);


        } catch (Exception e) {
            e.printStackTrace();
         //   wrtResp(exchange, e.getMessage());
            wrtResp(exchange, getStackTraceAsString(e));

            throw new RuntimeException(e);

        }
    }

    abstract void handle2(HttpExchange exchange) throws Exception;

    public boolean isLogined(HttpExchange exchange) {
        String uname = getcookie("uname", exchange);
        return  !uname.equals("");
     //   return  true;
    }

    public boolean isNotLogined(HttpExchange exchange) {
        String uname = getcookie("uname", exchange);
        return  uname.equals("");
        //   return  true;
    }


}

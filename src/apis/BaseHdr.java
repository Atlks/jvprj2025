package apis;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

import static biz.AccBiz.listAccLog;
import static util.Util2025.encodeJson;
import static util.util2026.getcookie;
import static util.util2026.wrtResp;

public  abstract class BaseHdr implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {


            handle2(exchange);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    abstract void handle2(HttpExchange exchange) throws Exception;


}

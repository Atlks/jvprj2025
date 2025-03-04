package api.wlt;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;


import static util.AccBiz.listAccLog;
import static util.Util2025.encodeJson;
import static util.util2026.*;

public class ListAcclogHdr implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {


            String uname = getcookie("uname", exchange);
            if (uname.equals("")) {
                //need login
                wrtResp(exchange, "needLogin");
                return;
            }

            //blk login ed
            var lst = listAccLog("");
            wrtResp(exchange, encodeJson(lst));


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}

package apis;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

import static biz.AccBiz.addAccLog;
import static biz.AccBiz.listAccLog;
import static util.Util2025.encodeJson;
import static util.dbutil.addObj;
import static util.util2026.getcookie;
import static util.util2026.wrtResp;

public class AddAcclogHdr extends BaseHdr {
    @Override
    public void handle2(HttpExchange exchange) throws Exception {


        String uname = getcookie("uname", exchange);
        if (uname.equals("")) {
            //need login
            wrtResp(exchange, "needLogin");
            return;
        }

        //blk login ed
        Object acclog = null;
        addObj(acclog, "acc", "/db2026/");
        wrtResp(exchange, "ok");


    }


}

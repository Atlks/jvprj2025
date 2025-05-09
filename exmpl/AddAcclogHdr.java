package apiAcc;

import apis.BaseHdr;
import com.sun.net.httpserver.HttpExchange;

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
        addObj(acclog,   "/db2026/acc");
        wrtResp(exchange, "ok");


    }


}

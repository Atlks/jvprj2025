package apiOrdBet;

import apis.BaseHdr;
import com.sun.net.httpserver.HttpExchange;

import java.util.Map;


import static apiOrdBet.QryOrdBetHdr.saveUrlOrdBet;
import static java.time.LocalTime.now;
import static util.dbutil.addObj;
import static util.util2026.*;

/**
 * http://localhost:8889/AddOrdBetHdr?bettxt=龙湖和
 */
public class AddOrdBetHdr extends BaseHdr {
    @Override
    public void handle2(HttpExchange exchange) throws Exception {



        if (isNotLogined(exchange)) {
            //need login
            wrtResp(exchange, "needLogin");
            return;
        }

        //blk login ed
        String uname = getcookie("uname", exchange);
        Map<String, String> queryParams = parseQueryParams(exchange.getRequestURI());
        String now = String.valueOf(now());
        queryParams.put("datetime_utc", now);
        queryParams.put("datetime_local", getLocalTimeString());
        queryParams.put("timezone", now);
        queryParams.put("timestamp", String.valueOf(System.currentTimeMillis()));
        queryParams.put("uname",uname);
        queryParams.put("id","ordBet"+getFilenameFrmLocalTimeString());
        addObj(queryParams,   saveUrlOrdBet);
        wrtResp(exchange, "ok");


    }



    public static boolean isLogined44(String uname) {
        return uname.equals("");
    }


}

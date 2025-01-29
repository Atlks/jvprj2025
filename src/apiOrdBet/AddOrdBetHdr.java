package apiOrdBet;

import apis.BaseHdr;
import com.alibaba.fastjson2.JSON;
import com.sun.net.httpserver.HttpExchange;

import java.util.Map;


import static apiOrdBet.QryOrdBetHdr.saveUrlOrdBet;
import static java.time.LocalTime.now;
import static util.ToXX.toObjFrmMap;
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
        OrdBet ord=toObjFrmMap(queryParams,OrdBet.class);
//        String now = String.valueOf(now());
//        queryParams.put("datetime_utc", now);
//        queryParams.put("datetime_local", getLocalTimeString());
//        queryParams.put("timezone", now);
        ord.timestamp=System.currentTimeMillis();
        ord.uname=uname;
        ord.id="ordBet"+getFilenameFrmLocalTimeString();
        addObj(queryParams,   saveUrlOrdBet);
        wrtResp(exchange, "ok");


    }


    public static void main(String[] args) throws Exception {

        iniCfgFrmCfgfile();;
        OrdBet ord=new OrdBet();
//        String now = String.valueOf(now());
//        queryParams.put("datetime_utc", now);
//        queryParams.put("datetime_local", getLocalTimeString());
//        queryParams.put("timezone", now);
        ord.timestamp=System.currentTimeMillis();
        ord.uname="007";
        ord.bettxt="龙湖和";
        ord.id="ordBet"+getFilenameFrmLocalTimeString();
        addObj(ord,saveUrlOrdBet,OrdBet.class);
    }

}

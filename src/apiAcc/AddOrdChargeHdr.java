package apiAcc;

import apis.BaseHdr;
import biz.OrdChrg;
import com.sun.net.httpserver.HttpExchange;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static java.time.LocalTime.now;
import static util.dbutil.addObj;
import static util.dbutil.drvMap;
import static util.util2026.*;

/**
 * http://localhost:8889/AddOrdChargeHdr?amt=888
 */
public class AddOrdChargeHdr extends BaseHdr {
    public static  String saveUrlOrdChrg;

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



        OrdChrg ord=new OrdChrg();
        ord.uname=uname;
        ord.amt=new BigDecimal(queryParams.get("amt"));
        ord.timestamp=System.currentTimeMillis();
        ord.id="ordChrg"+getFilenameFrmLocalTimeString();
        addOrdChg(ord);
        wrtResp(exchange, "ok");


    }



    private static void addOrdChg( OrdChrg ord) throws Exception {
        String now = String.valueOf(now());
//        queryParams.put("datetime_utc", now);
//        queryParams.put("datetime_local", getLocalTimeString());
//        queryParams.put("timezone", now);
//        queryParams.put("timestamp", System.currentTimeMillis());
//        queryParams.put("uname", uname);
//        queryParams.put("id","ordChrg"+getFilenameFrmLocalTimeString());
        addObj(ord,   saveUrlOrdChrg);
    }

    public static void main(String[] args) throws Exception {
           iniCfgFrmCfgfile();
        drvMap.put("com.mysql.cj.jdbc.Driver","org.h2.Driver");
        Map<String, Object> queryParams=new HashMap<>();
        queryParams.put("amt",new BigDecimal("888") );
        OrdChrg ord=new OrdChrg();
        ord.uname="007";
        ord.amt=new BigDecimal("888");
       ord.timestamp=System.currentTimeMillis();
       ord.id="ordChrg"+getFilenameFrmLocalTimeString();
        addOrdChg(ord);
    }


}

package apis;

import com.sun.net.httpserver.HttpExchange;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static java.time.LocalTime.now;
import static util.dbutil.addObj;
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

        Map<String, Object> queryParamsWztype=new HashMap<>();
        queryParamsWztype.put("uname",uname);
        queryParamsWztype.put("amt",new BigDecimal(queryParams.get("amt")));
        addOrdChg(queryParamsWztype, uname);
        wrtResp(exchange, "ok");


    }



    private static void addOrdChg(Map<String, Object> queryParams, String uname) throws Exception {
        String now = String.valueOf(now());
        queryParams.put("datetime_utc", now);
        queryParams.put("datetime_local", getLocalTimeString());
        queryParams.put("timezone", now);
        queryParams.put("timestamp", System.currentTimeMillis());
        queryParams.put("uname", uname);
        queryParams.put("id","ordChrg"+getFilenameFrmLocalTimeString());
        addObj(queryParams,   saveUrlOrdChrg);
    }

    public static void main(String[] args) throws Exception {
           iniCfgFrmCfgfile();
        Map<String, Object> queryParams=new HashMap<>();
        queryParams.put("amt",new BigDecimal("888") );
        addOrdChg(queryParams,"007");
    }


}

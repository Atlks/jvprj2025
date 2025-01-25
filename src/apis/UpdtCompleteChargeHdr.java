package apis;

import com.alibaba.fastjson2.JSONObject;
import com.sun.net.httpserver.HttpExchange;

import java.math.BigDecimal;
import java.util.Map;
import java.util.SortedMap;

import static apis.AddOrdChargeHdr.saveUrlOrdChrg;
import static apis.QryOrdBetHdr.saveUrlOrdBet;
import static apis.RegHandler.saveDirUsrs;
import static com.alibaba.fastjson2.util.TypeUtils.toBigDecimal;
import static java.time.LocalTime.now;
import static util.dbutil.*;
import static util.util2026.*;

/**
 * http://localhost:8889/AddOrdBetHdr?bettxt=龙湖和
 */
public class UpdtCompleteChargeHdr extends BaseHdr {
    public static void main(String[] args) throws Exception {
      iniCfgFrmCfgfile();
        updateCmpltOrdChg("ordChrg2025-01-25T15-16-13");
    }

    private static void updateCmpltOrdChg(String id) throws Exception {
        //update chr ord stat
        SortedMap<String, Object> objChrg = getObjIni(id, saveUrlOrdChrg);
        objChrg.put("stat", "ok");
        addObj(objChrg, saveUrlOrdChrg);

        //add blance
        SortedMap<String, Object> objU = getObjIni((String) objChrg.get("uname"), saveDirUsrs);
        if(objU.get("id")==null)
        {
            objU.put("id",(String) objChrg.get("uname"));
            objU.put("uname",(String) objChrg.get("uname"));
        }


        BigDecimal nowAmt= getFieldAsBigDecimal(objU,"balance",0);
        BigDecimal newBls=nowAmt.add(toBigDecimal(objChrg.get("amt")));
        objU.put("balance",newBls);
        addObj(objU,saveDirUsrs);
    }

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
        updateCmpltOrdChg(queryParams.get("id"));
        wrtResp(exchange, "ok");


    }


}

package apis;

import com.sun.net.httpserver.HttpExchange;

import java.math.BigDecimal;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static apis.AddOrdChargeHdr.saveUrlOrdChrg;
import static apis.RegHandler.saveDirUsrs;
import static com.alibaba.fastjson2.util.TypeUtils.toBigDecimal;
import static java.time.LocalTime.now;
import static util.dbutil.*;
import static util.util2026.*;
import static yonjin.Cms.calcCms2;

/**
 * http://localhost:8889/AddOrdBetHdr?bettxt=龙湖和
 */
public class UpdtCompleteChargeHdr extends BaseHdr {
    public static String saveUrlLogBalance;


    public static void main(String[] args) throws Exception {
      iniCfgFrmCfgfile();
        updateCmpltOrdChg("ordChrg2025-01-25T15-16-13");
    }

    private static void updateCmpltOrdChg(String id) throws Exception {
        //update chr ord stat
        SortedMap<String, Object> objChrg = getObjIni(id, saveUrlOrdChrg);
        String stat= (String) getField2025(objChrg,"stat","");
        BigDecimal amt=   getFieldAsBigDecimal(objChrg,"amt",0);
        if(stat.equals("ok"))
        {
            System.out.println("alread cpmlt ord,id="+id);
            return;
        }

        if(stat.equals(""))
          objChrg.put("stat", "ok");
        addObj(objChrg, saveUrlOrdChrg);

        //----add blance
        String uname = (String) objChrg.get("uname");


        SortedMap<String, Object> objU = updtBlsAddChrg(uname, amt);

        //calc yonjin
        calcCms2(objU,amt);
       // calcCms(uname,amt);
    }

    private static SortedMap<String, Object> updtBlsAddChrg(String uname, BigDecimal amt) throws Exception {
        SortedMap<String, Object> objU = getObjIni(uname, saveDirUsrs);
        if(objU.get("id")==null)
        {
            objU.put("id", uname);
            objU.put("uname", uname);
        }


        BigDecimal nowAmt= getFieldAsBigDecimal(objU,"balance",0);
        BigDecimal newBls=nowAmt.add(amt);
        objU.put("balance",newBls);
        addObj(objU,saveDirUsrs);

        //add balanceLog
        SortedMap<String, Object> logBalance=new TreeMap<>();
        logBalance.put("id","LogBalance"+getFilenameFrmLocalTimeString());
        logBalance.put("uname", uname);
        logBalance.put("change","增加");
        logBalance.put("amt", amt);
        logBalance.put("amtBefore",nowAmt);
        logBalance.put("amtAfter",newBls);
        addObj(logBalance,saveUrlLogBalance);
        return objU;
    }




    private static void calcCms(String uname, BigDecimal amt) {
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

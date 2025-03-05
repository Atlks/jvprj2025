package api.wlt;

import biz.BaseHdr;
import cfg.MyCfg;
import com.sun.net.httpserver.HttpExchange;
import entityx.Usr;
import util.auth.AuthService;
import util.auth.IsEmptyEx;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static api.wlt.CompleteChargeCallbackHdr.saveUrlLogBalance;
import static com.alibaba.fastjson2.util.TypeUtils.toBigDecimal;
import static util.algo.ToXX.parseQueryParams;
import static util.tx.dbutil.addObj;
import static util.tx.dbutil.getObjIni;
import static util.misc.util2026.*;

/**
 * http://localhost:8889/AddOrdBetHdr?bettxt=龙湖和
 */
public class UpdtBlsHdr extends BaseHdr<Usr, Usr> {


    public static void main(String[] args) throws Exception {
      MyCfg.iniCfgFrmCfgfile();
        Map<String, String> queryParams=new HashMap<>();
        queryParams.put("adjst","add");  //sub
        queryParams.put("amt","9");  //sub
        updtBls("007","add", BigDecimal.valueOf(9),queryParams);
    }

    private static void updtBls(String uname,String adjstOp,BigDecimal amt,  Map<String, String> queryParams ) throws Exception {

        //add blance
        SortedMap<String, Object> objU = getObjIni(uname, saveDirUsrs);
        if(objU.get("id")==null)
        {
            objU.put("id",uname);
            objU.put("uname",uname);
        }


        BigDecimal nowAmt= getFieldAsBigDecimal(objU,"balance",0);
       //def is add
        BigDecimal newBls=nowAmt.add(amt);
        if(adjstOp.equals("sub"))
            newBls=nowAmt.subtract(amt);
        objU.put("balance",newBls);
        addObj(objU,saveDirUsrs);

        //add balanceLog
        SortedMap<String, Object> logBalance=new TreeMap<>();
        logBalance.put("id","LogBalance"+getFilenameFrmLocalTimeString());
        logBalance.put("uname",uname);
        logBalance.put("change","增加");
        if(adjstOp.equals("sub"))
            logBalance.put("change","减少");
        logBalance.put("amt",amt);
        logBalance.put("amtBefore",nowAmt);
        logBalance.put("amtAfter",newBls);
        addObj(logBalance,saveUrlLogBalance);
    }

    @Override
    public void handle2(HttpExchange exchange) throws Exception, IsEmptyEx {


        if (AuthService.isNotLogined(exchange)) {
            //need login
            wrtResp(exchange, "needLogin");
            return;
        }

        //blk login ed
        String uname = getcookie("uname", exchange);
        Map<String, String> queryParams = parseQueryParams(exchange.getRequestURI());
        updtBls(uname,queryParams.get("adjstOp"),toBigDecimal(queryParams.get("amt")) ,queryParams );
        wrtResp(exchange, "ok");


    }


}

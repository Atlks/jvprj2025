package apiAcc;

import apis.BaseHdr;
import com.sun.net.httpserver.HttpExchange;

import java.math.BigDecimal;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static apiUsr.RegHandler.saveDirUsrs;
import static apiAcc.UpdtCompleteChargeHdr.saveUrlLogBalance;
import static com.alibaba.fastjson2.util.TypeUtils.toBigDecimal;
import static util.dbutil.addObj;
import static util.dbutil.getObjIni;
import static util.util2026.*;

/**从本机钱包转账到盈利钱包
 * http://localhost:8889/AddOrdBetHdr?bettxt=龙湖和
 */
public class TransHdr extends BaseHdr {

    public static String saveUrlLogBalanceYinliWlt;

    public static void main(String[] args) throws Exception {
      iniCfgFrmCfgfile();
        transToYinliWlt(100.5,"007");
    }

    private static void transToYinliWlt(double amt, String uname) throws Exception {




        //add blance
        SortedMap<String, Object> objU = getObjIni(uname, saveDirUsrs);
        if(objU.get("id")==null)
        {
            objU.put("id",uname);
            objU.put("uname",uname);
        }

     //  放在一起一快存储，解决了十五问题事务。。。
        BigDecimal nowAmt= getFieldAsBigDecimal(objU,"balance",0);
        BigDecimal newBls=nowAmt.subtract(toBigDecimal(amt));
        objU.put("balance",newBls);

        BigDecimal nowAmt2= getFieldAsBigDecimal(objU,"balanceYinliwlt",0);
        BigDecimal newBls2=nowAmt2.add(toBigDecimal(amt));
        objU.put("balanceYinliwlt",newBls2);
        addObj(objU,saveDirUsrs);

        //add balanceLog
        SortedMap<String, Object> logBalance=new TreeMap<>();
        logBalance.put("id","LogBalance"+getFilenameFrmLocalTimeString());
        logBalance.put("uname",uname);
        logBalance.put("change","减去");
        logBalance.put("amt",amt);
        logBalance.put("amtBefore",nowAmt);
        logBalance.put("amtAfter",newBls);
        addObj(logBalance,saveUrlLogBalance);



        //add logBlsYinliWlt
        SortedMap<String, Object> logBalance2=new TreeMap<>();
        logBalance2.put("id","LogBalanceYinliWlt"+getFilenameFrmLocalTimeString());
        logBalance2.put("uname",uname);
        logBalance2.put("change","增加");
        logBalance2.put("amt",amt);
        logBalance2.put("amtBefore",nowAmt2);
        logBalance2.put("amtAfter",newBls2);
        addObj(logBalance2,saveUrlLogBalanceYinliWlt);



        //adjst yinliwlt balnce

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
        transToYinliWlt(Double.parseDouble(queryParams.get("amt")),uname);
        wrtResp(exchange, "ok");


    }


}

package apiWltYinli;

import apis.BaseHdr;
import biz.OrdWthdr;
import biz.Usr;
import com.sun.net.httpserver.HttpExchange;

import java.math.BigDecimal;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static apiUsr.RegHandler.saveDirUsrs;
import static com.alibaba.fastjson2.util.TypeUtils.toBigDecimal;
import static util.dbutil.*;
import static util.util2026.*;

/**提现，会导致有效金额变化，冻结金额也变化  ，总金额变化 ，
 * http://localhost:8889/AddOrdBetHdr?bettxt=龙湖和
 */
public class WithdrawHdr extends BaseHdr {


    public static String saveUrlOrdWthdr;

    public static void main(String[] args) throws Exception {
      iniCfgFrmCfgfile();
        Withdraw(8.5,"008");
    }

    private static void Withdraw(double amt, String uname) throws Exception {

        OrdWthdr ord=new OrdWthdr();
       // ord.put("datetime_utc", now);
       // ord.put("datetime_local", getLocalTimeString());
       //  ord.put("timezone", now);
        ord.timestamp=(System.currentTimeMillis());
        ord.uname=uname;
        ord.id="ordWthdr"+getFilenameFrmLocalTimeString();
        addObj(ord,   saveUrlOrdWthdr);

        //sub blsAvld   blsFreez++
        Usr objU = getObjById(uname, saveDirUsrs,Usr.class);
        if(objU.id==null)
        {
            objU.id= uname;
            objU.uname= uname;
        }
        BigDecimal nowAmt2= getFieldAsBigDecimal(objU,"balanceYinliwlt",0);
        BigDecimal newBls2=nowAmt2.subtract(toBigDecimal(amt));
        objU.balanceYinliwlt=newBls2;

        BigDecimal nowAmtFreez= getFieldAsBigDecimal(objU,"balanceYinliwltFreez",0);
        objU.balanceYinliwltFreez=nowAmtFreez.add(toBigDecimal(amt));
        updtObj(objU,saveDirUsrs);





        //adjst yinliwlt balnce

    }

    private static Object addBlsFrz4yinliWlt(double amt) {
        return null;
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
        Withdraw(Double.parseDouble(queryParams.get("amt")),uname);
        wrtResp(exchange, "ok");


    }


}

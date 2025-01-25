package apis;

import com.sun.net.httpserver.HttpExchange;

import java.math.BigDecimal;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static apis.RegHandler.saveDirUsrs;
import static apis.UpdtCompleteChargeHdr.saveUrlLogBalance;
import static com.alibaba.fastjson2.util.TypeUtils.toBigDecimal;
import static util.dbutil.addObj;
import static util.dbutil.getObjIni;
import static util.util2026.*;

/**提现，会导致有效金额变化，冻结金额也变化  ，总金额变化 ，
 * http://localhost:8889/AddOrdBetHdr?bettxt=龙湖和
 */
public class WithdrawHdr extends BaseHdr {


    public static String saveUrlOrdWthdr;

    public static void main(String[] args) throws Exception {
      iniCfgFrmCfgfile();
        Withdraw(8.5,"007");
    }

    private static void Withdraw(double amt, String uname) throws Exception {

        SortedMap<String, Object> ord=new TreeMap<>();
       // ord.put("datetime_utc", now);
        ord.put("datetime_local", getLocalTimeString());
       //  ord.put("timezone", now);
        ord.put("timestamp", String.valueOf(System.currentTimeMillis()));
        ord.put("uname", uname);
        ord.put("id","ordWthdr"+getFilenameFrmLocalTimeString());
        addObj(ord,   saveUrlOrdWthdr);

        //sub blsAvld   blsFreez++
        SortedMap<String, Object> objU = getObjIni(uname, saveDirUsrs);
        if(objU.get("id")==null)
        {
            objU.put("id",uname);
            objU.put("uname",uname);
        }
        BigDecimal nowAmt2= getFieldAsBigDecimal(objU,"balanceYinliwlt",0);
        BigDecimal newBls2=nowAmt2.subtract(toBigDecimal(amt));
        objU.put("balanceYinliwlt",newBls2);

        BigDecimal nowAmtFreez= getFieldAsBigDecimal(objU,"balanceYinliwltFreez",0);
        objU.put("balanceYinliwltFreez",nowAmtFreez.add(toBigDecimal(amt)));
        addObj(objU,saveDirUsrs);





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

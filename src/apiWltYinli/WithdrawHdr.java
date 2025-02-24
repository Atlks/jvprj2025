package apiWltYinli;

import biz.BaseHdr;
import cfg.MyCfg;
import entityx.OrdWthdr;
import entityx.Usr;
import com.sun.net.httpserver.HttpExchange;
import service.AuthService;

import java.math.BigDecimal;
import java.util.Map;

import static service.CmsBiz.toBigDcmTwoDot;
import static com.alibaba.fastjson2.util.TypeUtils.toBigDecimal;
import static util.ToXX.parseQueryParams;
import static util.dbutil.*;
import static util.util2026.*;

/**提现，会导致有效金额变化，冻结金额也变化  ，总金额变化 ，
 * http://localhost:8889/AddOrdBetHdr?bettxt=龙湖和
 */
public class WithdrawHdr extends BaseHdr {


    public static String saveUrlOrdWthdr;

    public static void main(String[] args) throws Exception {
      MyCfg.iniCfgFrmCfgfile();

        OrdWthdr ord=new OrdWthdr();
        // ord.put("datetime_utc", now);
        // ord.put("datetime_local", getLocalTimeString());
        //  ord.put("timezone", now);
     //   ord.timestamp=(System.currentTimeMillis());
        ord.uname="009";
        ord.amt=new BigDecimal(77);
      //  ord.id="ordWthdr"+getFilenameFrmLocalTimeString();
        Withdraw(ord);
    }

    private static void Withdraw( OrdWthdr ord) throws Exception {
        ord.timestamp=(System.currentTimeMillis());
        ord.id="ordWthdr"+getFilenameFrmLocalTimeString();
        addObj(ord,   saveUrlOrdWthdr,OrdWthdr.class);


        //sub blsAvld   blsFreez++
        String uname=ord.uname;
        Usr objU = getObjById(uname, saveDirUsrs,Usr.class);
        if(objU.id==null)
        {
            objU.id= uname;
            objU.uname= uname;
        }
        BigDecimal nowAmt2= getFieldAsBigDecimal(objU,"balanceYinliwlt",0);
        BigDecimal newBls2=nowAmt2.subtract(ord.getAmt());
        objU.balanceYinliwlt=toBigDcmTwoDot(newBls2);

        BigDecimal nowAmtFreez=toBigDcmTwoDot( getFieldAsBigDecimal(objU,"balanceYinliwltFreez",0)) ;
        objU.balanceYinliwltFreez=toBigDcmTwoDot(nowAmtFreez.add(ord.getAmt())) ;
        updtObj(objU,saveDirUsrs,Usr.class);





        //adjst yinliwlt balnce

    }

    private static Object addBlsFrz4yinliWlt(double amt) {
        return null;
    }

    @Override
    public void handle2(HttpExchange exchange) throws Exception {


        if (AuthService.isNotLogined(exchange)) {
            //need login
            wrtResp(exchange, "needLogin");
            return;
        }

        //blk login ed
        String uname = getcookie("uname", exchange);
        Map<String, String> queryParams = parseQueryParams(exchange.getRequestURI());
        OrdWthdr ord=new OrdWthdr();
        ord.uname=uname;
        ord.amt=new BigDecimal(queryParams.get("amt"));
        Withdraw(ord);
        wrtResp(exchange, "ok");


    }


}

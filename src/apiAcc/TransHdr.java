package apiAcc;

import apis.BaseHdr;
import biz.LogBls;
import biz.Usr;
import com.sun.net.httpserver.HttpExchange;

import java.math.BigDecimal;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static apiUsr.RegHandler.saveDirUsrs;
import static apiAcc.UpdtCompleteChargeHdr.saveUrlLogBalance;
import static com.alibaba.fastjson2.util.TypeUtils.toBigDecimal;
import static util.Util2025.encodeJson;
import static util.dbutil.*;
import static util.util2026.*;
import static apiWltYinli.CmsBiz.toBigDcmTwoDot;

/**从本机钱包转账到盈利钱包
 * http://localhost:8889/AddOrdBetHdr?bettxt=龙湖和
 */
public class TransHdr extends BaseHdr {

    public static String saveUrlLogBalanceYinliWlt;

    public static void main(String[] args) throws Exception {
      iniCfgFrmCfgfile();
        transToYinliWlt(100.5,"008");
    }

    private static void transToYinliWlt(double amt, String uname) throws Exception, BalanceNotEnghou {




        //add blance
        Usr objU = getObjById(uname, saveDirUsrs,Usr.class);
        if(objU.id==null)
        {
            objU.id= uname;
            objU.uname= uname;
        }


        //  放在一起一快存储，解决了十五问题事务。。。
        BigDecimal nowAmt= getFieldAsBigDecimal(objU,"balance",0);
       if(toBigDecimal(amt).compareTo(nowAmt)>0)
       {
           SortedMap<String, Object> m=new TreeMap<>();
           m.put("method","transToYinliWlt()");
           m.put("prm","amt="+amt+",uname="+uname);
           m.put("nowAmtBls",nowAmt);
           throw new  BalanceNotEnghou(encodeJson(m));
       }

        BigDecimal newBls=nowAmt.subtract(toBigDecimal(amt));
        objU.balance=newBls;

        BigDecimal nowAmt2= getFieldAsBigDecimal(objU,"balanceYinliwlt",0);
        BigDecimal newBls2=nowAmt2.add(toBigDecimal(amt));
        objU.balanceYinliwlt=newBls2;
        updtObj(objU,saveDirUsrs);

        //add balanceLog
        LogBls logBalance=new LogBls();
        logBalance.id="LogBalance"+getFilenameFrmLocalTimeString();
        logBalance.uname= uname;

        logBalance.changeAmount= BigDecimal.valueOf(amt);
        logBalance.amtBefore=toBigDcmTwoDot(nowAmt);
        logBalance.newBalance=toBigDcmTwoDot(newBls);

        logBalance.changeMode="减去";
        System.out.println(" add balanceLog ");
        addObj(logBalance,saveUrlLogBalance);




        //add logBlsYinliWlt
        LogBls logBalance2=new LogBls();
        logBalance2.id="LogBalanceYinliWlt"+getFilenameFrmLocalTimeString();
        logBalance2.uname=uname;
        logBalance2.changeMode="增加";
        logBalance2.changeAmount= BigDecimal.valueOf(amt);
        logBalance2.amtBefore=nowAmt2;
        logBalance2.newBalance=newBls2;
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

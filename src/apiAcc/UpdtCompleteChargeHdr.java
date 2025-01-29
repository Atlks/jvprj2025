package apiAcc;

import apiCms.CmsBiz;
import apis.BaseHdr;
import apiUsr.Usr;
import com.sun.net.httpserver.HttpExchange;

import java.math.BigDecimal;
import java.util.Map;

import static apiAcc.AddOrdChargeHdr.saveUrlOrdChrg;
import static apiUsr.RegHandler.saveDirUsrs;
import static com.alibaba.fastjson2.util.TypeUtils.toBigDecimal;
import static java.time.LocalTime.now;
import static util.dbutil.*;
import static util.util2026.*;
import static apiCms.CmsBiz.toBigDcmTwoDot;

/**
 * http://localhost:8889/UpdtCompleteChargeHdr?id=ordchg2222
 */
public class UpdtCompleteChargeHdr extends BaseHdr {
    public static String saveUrlLogBalance;


    public static void main(String[] args) throws Exception {
        iniCfgFrmCfgfile();
        ovrtTEst=true;
 //       drvMap.put("com.mysql.cj.jdbc.Driver", "org.h2.Driver");
        updateOrdChgSetCmplt("ordChrg2025-01-28T20-33-09");
    }
    static boolean ovrtTEst=false;
    private static void updateOrdChgSetCmplt(String id) throws Exception {
        //update chr ord stat
        OrdChrg objChrg = getObjById(id, saveUrlOrdChrg, OrdChrg.class);
        String stat = (String) getField2025(objChrg, "stat", "");
        BigDecimal amt = objChrg.amt;
        if (stat.equals("ok")) {
            System.out.println("alread cpmlt ord,id=" + id);
            if( ovrtTEst)
            {

            }else
            return;
        }
        if (stat.equals(""))
            objChrg.stat = "ok";
        updtObj(objChrg, saveUrlOrdChrg);

        //----add blance n log
        String uname = objChrg.uname;
        updtBlsByAddChrg(objChrg);

        //calc yonjin
        Usr u = new Usr();
        // u.invtr=objU.get("invtr").toString();
        //  calcCms4chrgU(u,amt);
        CmsBiz.calcCms4FrmOrdChrg(objChrg);
        // calcCms(uname,amt);
    }


    public static void updtBlsByAddChrg(OrdChrg objChrg) throws Exception {
        String uname = objChrg.uname;
        BigDecimal amt = objChrg.getAmt();


        Usr objU = getObjById(uname, saveDirUsrs, Usr.class);
        if (objU.id == null) {
            objU.id = uname;
            objU.uname = uname;
        }

        BigDecimal nowAmt = getFieldAsBigDecimal(objU, "balance", 0);

        BigDecimal newBls = nowAmt.add(amt);
        objU.balance = toBigDcmTwoDot(newBls);
        updtObj(objU, saveDirUsrs);

        //add balanceLog
        LogBls logBalance = new LogBls();
        logBalance.id = "LogBalance" + getFilenameFrmLocalTimeString();
        logBalance.uname = uname;
        logBalance.changeTime=System.currentTimeMillis();
        logBalance.changeType = "充值";  //充值增加
        logBalance.changeMode="增加";
        logBalance.changeAmount = amt;
        logBalance.amtBefore = toBigDcmTwoDot(nowAmt);
        logBalance.newBalance = toBigDcmTwoDot(newBls);
        System.out.println(" add balanceLog ");
        addObj(logBalance, saveUrlLogBalance);

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
        updateOrdChgSetCmplt(queryParams.get("id"));
        wrtResp(exchange, "ok");


    }


}

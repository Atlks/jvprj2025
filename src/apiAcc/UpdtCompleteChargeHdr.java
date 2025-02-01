package apiAcc;

import apiCms.CmsBiz;
import apis.BaseHdr;
import apiUsr.Usr;
import com.sun.net.httpserver.HttpExchange;
import org.hibernate.Session;
import utilBiz.OrmUtilBiz;

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
        updateOrdChgSetCmplt("ordChrg2025-01-30T01-06-59");
    }
    static boolean ovrtTEst=false;
    private static void updateOrdChgSetCmplt(String id) throws Exception {

        org.hibernate.Session session = OrmUtilBiz.openSession(saveUrlOrdChrg);

        //------------chge regch stat=ok
        //  om.jdbcurl=saveDirUsrs;
        //todo start tx
        session.beginTransaction();
        OrdChrg objChrg=  session.find(OrdChrg.class,id);
       //
        //update chr ord stat
     //   OrdChrg objChrg = find(id, saveUrlOrdChrg, OrdChrg.class);
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
        session.merge(objChrg);

        //----add blance n log
        String uname = objChrg.uname;
        updtBlsByAddChrg(objChrg,session);
        System.out.println("\n\r\n---------endblk  kmplt chrg");


        System.out.println("\n\r\n---------blk  calcCms4FrmOrdChrg");
        //--------------calc yonjin
        Usr u = new Usr();
        // u.invtr=objU.get("invtr").toString();
        //  calcCms4chrgU(u,amt);
        CmsBiz.calcCms4FrmOrdChrg(objChrg,session);
        // calcCms(uname,amt);
        session.getTransaction().commit();
        System.out.println("\n\r\n---------endblk  calcCms4FrmOrdChrg");
    }


    public static void updtBlsByAddChrg(OrdChrg objChrg, Session session) throws Exception {
        String uname = objChrg.uname;
        BigDecimal amt = objChrg.getAmt();


        Usr objU =  session.find(Usr.class,uname);
        if (objU.id == null) {
            objU.id = uname;
            objU.uname = uname;
        }

        BigDecimal nowAmt = getFieldAsBigDecimal(objU, "balance", 0);

        BigDecimal newBls = nowAmt.add(amt);
        objU.balance = toBigDcmTwoDot(newBls);
        session.merge(objU);
        session.flush();
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
        session.persist(logBalance);
        session.flush();

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

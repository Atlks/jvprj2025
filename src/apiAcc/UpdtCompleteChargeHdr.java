package apiAcc;

import cfg.MyCfg;
import service.AuthService;
import service.CmsBiz;
import biz.BaseHdr;
import entityx.LogBls;
import entityx.ChgOrd;
import entityx.Usr;
import com.sun.net.httpserver.HttpExchange;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.math.BigDecimal;
import java.util.Map;

import static com.alibaba.fastjson2.util.TypeUtils.toBigDecimal;
import static java.time.LocalTime.now;
import static util.ColorLogger.*;
import static util.HbntUtil.*;
import static util.ToXX.parseQueryParams;
import static util.Util2025.encodeJson;
import static util.util2026.*;
import static service.CmsBiz.toBigDcmTwoDot;

/**  ivk by
 * http://localhost:8889/UpdtCompleteChargeHdr?id=ordchg2222
 */
public class UpdtCompleteChargeHdr extends BaseHdr<Usr, Usr> {
    public static String saveUrlLogBalance;

    public UpdtCompleteChargeHdr(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        super.sessionFactory = sessionFactory;
    }

    public static void main(String[] args) throws Exception {
        MyCfg.iniCfgFrmCfgfile();
        ovrtTEst = true;
        //       drvMap.put("com.mysql.cj.jdbc.Driver", "org.h2.Driver");
        //  updateOrdChgSetCmplt("ordChrg2025-02-18T21-14-59");
    }

    static boolean ovrtTEst = false;

    private   void updateOrdChgSetCmpltBiz(String id) throws Exception {

        org.hibernate.Session session = sessionFactory.getCurrentSession();

        //------------blk chge regch stat=ok
        //  om.jdbcurl=saveDirUsrs;
        //todo start tx

        ChgOrd objChrg = findByHbnt(ChgOrd.class, id, session);


        System.out.println("\r\n----blk updt chg ord stat=ok");
        //update chr ord stat
        //   OrdChrg objChrg = find(id, saveUrlOrdChrg, OrdChrg.class);
        String stat = (String) getField2025(objChrg, "stat", "");
        BigDecimal amt = objChrg.amt;
        if (stat.equals("ok")) {
            System.out.println("alread cpmlt ord,id=" + id);
            if (ovrtTEst) {

            } else
                return;
        }
        if (stat.equals(""))
            objChrg.stat = "ok";
        mergeByHbnt(objChrg, session);
        System.out.println("----endblk updt chg ord stat=ok");
        //  session.merge(objChrg);

        //----add blance n log  ..blk
        String uname = objChrg.uname;
        updtBlsByAddChrg(objChrg, session);
        System.out.println("\n\r\n---------endblk  kmplt chrg");


        System.out.println("\n\r\n---------blk  calcCms4FrmOrdChrg");
        //--------------calc yonjin
        Usr u = new Usr();
        // u.invtr=objU.get("invtr").toString();
        //  calcCms4chrgU(u,amt);
        CmsBiz.calcCms4FrmOrdChrg(objChrg, session);
        // calcCms(uname,amt);

        System.out.println("\n\r\n---------endblk  calcCms4FrmOrdChrg");
    }


    public   void updtBlsByAddChrg(ChgOrd objChrg, Session session) throws Exception {
        printLn("\n▶️fun updtBlsByAddChrg(", BLUE);
        printLn("objChrg= " + encodeJson(objChrg), GREEN);
        System.out.println(")");
        //  printlnx();
        //   System.out.println("\r\n ▶fun updtBlsByAddChrg(objChrg= "+encodeJson(objChrg));

        String uname = objChrg.uname;
        BigDecimal amt = objChrg.getAmt();


        Usr objU = findByHbnt(Usr.class, uname, session);
        if (objU.id == null) {
            objU.id = uname;
            objU.uname = uname;
        }

        BigDecimal nowAmt = getFieldAsBigDecimal(objU, "balance", 0);

        BigDecimal newBls = nowAmt.add(amt);
        objU.balance = toBigDcmTwoDot(newBls);
        mergeByHbnt(objU, session);

        //add balanceLog
        LogBls logBalance = new LogBls();
        logBalance.id = "LogBalance" + getFilenameFrmLocalTimeString();
        logBalance.uname = uname;
        logBalance.changeTime = System.currentTimeMillis();
        logBalance.changeType = "充值";  //充值增加
        logBalance.changeMode = "增加";
        logBalance.changeAmount = amt;
        logBalance.amtBefore = toBigDcmTwoDot(nowAmt);
        logBalance.newBalance = toBigDcmTwoDot(newBls);
        System.out.println(" add balanceLog ");
        persistByHibernate(logBalance, session);

        System.out.println("✅endfun updtBlsByAddChrg()");
    }


//    private static void calcCms(String uname, BigDecimal amt) {
//    }

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
        updateOrdChgSetCmpltBiz(queryParams.get("id"));
        wrtResp(exchange, "ok");


    }


}

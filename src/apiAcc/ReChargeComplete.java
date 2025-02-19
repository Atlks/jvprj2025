package apiAcc;

import apiCms.CmsBiz;
import entityx.LogBls;
import entityx.OrdChrg;
import entityx.Usr;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.math.BigDecimal;

import static apiCms.CmsBiz.toBigDcmTwoDot;
import static apis.BaseHdr.iniCfgFrmCfgfile;
import static util.HbntUtil.*;
import static util.TransactMng.beginTransaction;
import static util.dbutil.setField;
import static util.util2026.*;

/**  ivk by
 *  UpdtCompleteChargeHdr?id=ordchg2222
 */
@Component
public class ReChargeComplete extends AopBase  {
    public static String saveUrlLogBalance;

    @Autowired
    public ReChargeComplete(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    //    super.sessionFactory = sessionFactory;
    }

    public static void main(String[] args) throws Exception {
        iniCfgFrmCfgfile();
        ovrtTEst = true;
        //       drvMap.put("com.mysql.cj.jdbc.Driver", "org.h2.Driver");
        //  updateOrdChgSetCmplt("ordChrg2025-02-18T21-14-59");
    }
    @Autowired
    @Inject
    public WltService  WltService1;
    @Autowired
    public void setWltService1(WltService wltService1) {
        WltService1 = wltService1;
    }

    public   void updateOrdChgSetCmpltBiz(String id) throws Exception {

        Session session = sessionFactory.getCurrentSession();
        beginTransaction(session);
        //------------blk chge regch stat=ok
        System.out.println("\r\n\n\n=============blk 设置订单状态=完成");
        //  om.jdbcurl=saveDirUsrs;
        //todo start tx
     //   session.beginTransaction();
        OrdChrg objChrg = findByHbnt(OrdChrg.class, id, session);


        System.out.println("\r\n----blk updt chg ord set stat=ok");
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
     //   System.out.println("----endblk updt chg ord stat=ok");
        //  session.merge(objChrg);

        //----add blance n log  ..blk
        System.out.println("\r\n\n\n=============blk 主钱包加钱");
        String uname = objChrg.uname;
        WltService1.AddBlsAddChrg(objChrg);
      //  System.out.println("\n\r\n---------endblk  kmplt chrg");


        System.out.println("\n\r\n---------blk  calcCms4FrmOrdChrg");
        //--------------calc yonjin

      //  System.out.println("\n\r\n---------calc cms计算佣金");
        Usr u = new Usr();
        // u.invtr=objU.get("invtr").toString();
        //  calcCms4chrgU(u,amt);
        CmsBiz.calcCms4FrmOrdChrg(objChrg, session);
        // calcCms(uname,amt);
      //  session.getTransaction().commit();
        System.out.println("\n\r\n---------endblk  calcCms4FrmOrdChrg");
    }





//    private static void calcCms(String uname, BigDecimal amt) {
//    }

//    @Override
//    public void handle2(HttpExchange exchange) throws Exception {
//
//
//        if (isNotLogined(exchange)) {
//            //need login
//            wrtResp(exchange, "needLogin");
//            return;
//        }
//
//        //blk login ed
//        String uname = getcookie("uname", exchange);
//        Map<String, String> queryParams = parseQueryParams(exchange.getRequestURI());
//        updateOrdChgSetCmpltBiz(queryParams.get("id"));
//        wrtResp(exchange, "ok");
//
//
//    }


}

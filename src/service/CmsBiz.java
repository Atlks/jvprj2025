package service;


import entityx.wlt.LogCms;
import entityx.usr.Usr;
import entityx.wlt.LogBls4YLwlt;
import model.OpenBankingOBIE.Accounts;
import model.OpenBankingOBIE.Transactions;
import model.agt.Agent;

import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.SortedMap;
import java.util.TreeMap;


import static cfg.MyCfg.iniContnr4cfgfile;
//import static apiAcc.TransHdr.saveUrlLogBalanceYinliWlt;

import static util.acc.AccUti.getAccId4ylwlt;
import static util.tx.HbntUtil.findByHerbinate;
import static util.tx.HbntUtil.mergeByHbnt;
import static util.tx.HbntUtil.persistByHibernate;
import static util.misc.Util2025.encodeJson;
import static util.misc.util2026.*;

public class CmsBiz {

    public static void main(String[] args) throws Exception {
        iniContnr4cfgfile();
        SortedMap<String, Object> objU=new TreeMap<>();
        objU.put("uname","008");
        objU.put("invtr","007");
        Usr u=new Usr();
        u.uname="008";
        u.invtr="007";

        Transactions ord=new Transactions();
        ord.uname="008";
       ord.amount =new BigDecimal(100);
     //   calcCms4FrmOrdChrg(ord);


          LogCms log=new LogCms();
        log.id="LogCms"+getFilenameFrmLocalTimeString();
        log.uname= "008";
        log.commssionAmt=toBigDcmTwoDot(BigDecimal.valueOf(100));
     //   addLogCms(log, session);
    }
    public static String saveUrlLogCms;

    /**
     * 给充值订单的人的推荐者 佣金
     *
     * @param objChrg
     * @param session
     */
    public static void calcCms4FrmOrdChrg(Transactions objChrg, Session session) throws Throwable {
        System.out.println( "fun calcCms4FrmOrdChrg（");
        System.out.println(encodeJson(objChrg));
        System.out.println(")");

        Usr u= session.find( Usr.class,objChrg.uname);
        String invtr= toStr( u.invtr);
        BigDecimal cmsMny=toBigDcmTwoDot(objChrg.getAmount().multiply( new BigDecimal(0.05)) );
        if(invtr.equals(""))
            return;



        //-------updt invtr ttl cms amd and cmslog
        System.out.println("\r\n-------------blk updt invtr ttl cms amd and cmslog");

        updtTotalCmsAddamt4invtr(invtr,cmsMny,session);
        session.flush();
      //  System.out.println("end  invtr ttl cms amd and cmslog");

        //------------
        System.out.println("---------------endblk  updt invtr ttl cms amd and cmslog");


        System.out.println("\r\n-----------------blk updtBlsYinliwlt");
        updtBlsYinliwlt(invtr,cmsMny,session);//balanceYinliwlt
        System.out.println("-----------------endblk updtBlsYinliwlt");
        System.out.println( "endfun calcCms4FrmOrdChrg");
    }

    private static String toStr(String invtr) {
   if(invtr==null)
       return  "";
   return  invtr;
    }

//    @Deprecated
//    public static void calcCms4FrmOrdChrg(Usr u, BigDecimal amtChrg) throws Exception {
//        String invtr= u.invtr;
//        BigDecimal cmsMny=amtChrg.multiply( new BigDecimal(0.05));
//       if(invtr.equals(""))
//           return;
//        addLogCms(invtr,cmsMny);
//        updtTotalCmsAddamt(invtr,cmsMny);
//        updtBlsYinliwlt(invtr,cmsMny);//balanceYinliwlt
//    }




    public static void updtBlsYinliwlt(String uname, BigDecimal amt, Session session) throws Exception {
        String methodname="updtBlsYinliwlt";
        System.out.println("\r\n\r\n");
        System.out.println("fun "+methodname+"(uname="+uname+",amt="+amt);
        Accounts objU = session.find(Accounts.class,getAccId4ylwlt(uname) );
//        if(objU.id==null)
//        {
//            objU.id= uname;
//            objU.uname= uname;
//        }


        BigDecimal nowAmt= getFieldAsBigDecimal(objU,"balanceYinliwlt",0);
        BigDecimal newBls=nowAmt.add(amt);
        objU.availableBalance=toBigDcmTwoDot (newBls);
        mergeByHbnt(objU,session);  ;


        //add balanceLog yonjin wlt
        LogBls4YLwlt logBalanceYlWlt=new LogBls4YLwlt();
        logBalanceYlWlt.id="LogBalanceYinliwlt"+getFilenameFrmLocalTimeString();
        logBalanceYlWlt.uname=uname;
        logBalanceYlWlt.changeMode="增加";
        logBalanceYlWlt.changeAmount=toBigDcmTwoDot(amt);
        logBalanceYlWlt.amtBefore=toBigDcmTwoDot(nowAmt);
        logBalanceYlWlt.newBalance=toBigDcmTwoDot(newBls);
        persistByHibernate(logBalanceYlWlt,session);


        System.out.println("endfun "+methodname+"()");
    }


    private static void updtTotalCmsAddamt4invtr(String uname_invtr, BigDecimal cmsMny, Session session) throws Throwable {

        System.out.println("\r\n");
        System.out.println("fun updtTotalCmsAddamt4invtr(uname="+uname_invtr+",cms money="+cmsMny+")");



        //---updt ttl cms
        Usr objU = session.find(Usr.class,uname_invtr);
        if(objU.id==null)
        {
            objU.id= uname_invtr;
            objU.uname= uname_invtr;
        }

        Agent agt=findByHerbinate(Agent.class, objU.id, session);
        BigDecimal nowAmt=  (agt.getTotalCommssionAmt());
        BigDecimal newBls=nowAmt.add(cmsMny);
        agt.totalCommssionAmt =toBigDcmTwoDot (newBls);
        mergeByHbnt(agt,session);



       //add
        LogCms log=new LogCms();
        log.id="LogCms"+getFilenameFrmLocalTimeString();
        log.uname= uname_invtr;
        log.commssionAmt=cmsMny;
        addLogCms(log,session);
        session.flush();
        System.out.println("endfun updtTotalCmsAddamt4invtr()");
    }
    private static void addLogCms(LogCms log, Session session) throws Exception {
        System.out.println("\nfun addLogCms(log="+encodeJson(log));

        //add balanceLog
      //  LogCms log=new LogCms();
        log.id="LogCms"+getFilenameFrmLocalTimeString();
//        log.uname= uname;
//        log.commssionAmt=toBigDcmTwoDot(amtCmsMny);
        //  log.put("amtBefore",nowAmt);
        //  log.put("amtAfter",newBls);
        persistByHibernate(log,session);
        System.out.println("\uD83D\uDED1endfun addLogCms");

    }



    public static BigDecimal toBigDcmTwoDot(BigDecimal amtCmsMny) {

        BigDecimal bd =amtCmsMny;
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return  bd;
    }
}

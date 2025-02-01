package apiCms;

import apiAcc.OrdChrg;
import apiUsr.Usr;
import apiWltYinli.LogBlsLogYLwlt;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.SortedMap;
import java.util.TreeMap;


import static apis.BaseHdr.iniCfgFrmCfgfile;
//import static apiAcc.TransHdr.saveUrlLogBalanceYinliWlt;
import static util.util2026.*;

public class CmsBiz {

    public static void main(String[] args) throws Exception {
        iniCfgFrmCfgfile();
        SortedMap<String, Object> objU=new TreeMap<>();
        objU.put("uname","008");
        objU.put("invtr","007");
        Usr u=new Usr();
        u.uname="008";
        u.invtr="007";

        OrdChrg ord=new OrdChrg();
        ord.uname="008";
       ord.amt=new BigDecimal(100);
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
    public static void calcCms4FrmOrdChrg(OrdChrg objChrg, Session session) throws Exception {
        Usr u= session.find( Usr.class,objChrg.uname);
        String invtr= toStr( u.invtr);
        BigDecimal cmsMny=toBigDcmTwoDot(objChrg.getAmt().multiply( new BigDecimal(0.05)) );
        if(invtr.equals(""))
            return;

        LogCms log=new LogCms();
        log.id="LogCms"+getFilenameFrmLocalTimeString();
        log.uname= u.invtr;
        log.commssionAmt=cmsMny;

        //-------updt invtr ttl cms amd and cmslog
        System.out.println("\r\n-----------------updt invtr ttl cms amd and cmslog");
        addLogCms(log,session);
        session.flush();
        updtTotalCmsAddamt4invtr(invtr,cmsMny,session);
        session.flush();
        System.out.println("end  invtr ttl cms amd and cmslog");

        //------------
        System.out.println("\r\n-----------------updt invtr yl wlt ,,,bls n log");
        updtBlsYinliwlt(invtr,cmsMny,session);//balanceYinliwlt

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
        Usr objU = session.find(Usr.class,uname);
        if(objU.id==null)
        {
            objU.id= uname;
            objU.uname= uname;
        }


        BigDecimal nowAmt= getFieldAsBigDecimal(objU,"balanceYinliwlt",0);
        BigDecimal newBls=nowAmt.add(amt);
        objU.balanceYinliwlt=toBigDcmTwoDot (newBls);
        System.out.println(session.merge(objU));  ;
        session.flush();

        //add balanceLog yonjin wlt
        LogBlsLogYLwlt logBalanceYlWlt=new LogBlsLogYLwlt();
        logBalanceYlWlt.id="LogBalanceYinliwlt"+getFilenameFrmLocalTimeString();
        logBalanceYlWlt.uname=uname;
        logBalanceYlWlt.changeMode="增加";
        logBalanceYlWlt.changeAmount=toBigDcmTwoDot(amt);
        logBalanceYlWlt.amtBefore=toBigDcmTwoDot(nowAmt);
        logBalanceYlWlt.newBalance=toBigDcmTwoDot(newBls);
        session.persist(logBalanceYlWlt);
        session.flush();

        System.out.println("endfun "+methodname+"()");
    }


    private static void updtTotalCmsAddamt4invtr(String uname, BigDecimal cmsMny, Session session) throws Exception {

        System.out.println("\r\n");
        System.out.println("fun updtTotalCmsAddamt4invtr(uname="+uname+",cms money="+cmsMny+")");
        Usr objU = session.find(Usr.class,uname);
        if(objU.id==null)
        {
            objU.id= uname;
            objU.uname= uname;
        }


        BigDecimal nowAmt=  (objU.getTotalCommssionAmt());
        BigDecimal newBls=nowAmt.add(cmsMny);
        objU.totalCommssionAmt =toBigDcmTwoDot (newBls);
        session.merge(objU);
        session.flush();
        System.out.println("endfun updtTotalCmsAddamt4invtr()");
    }
    private static void addLogCms(LogCms log, Session session) throws Exception {


        //add balanceLog
      //  LogCms log=new LogCms();
        log.id="LogCms"+getFilenameFrmLocalTimeString();
//        log.uname= uname;
//        log.commssionAmt=toBigDcmTwoDot(amtCmsMny);
        //  log.put("amtBefore",nowAmt);
        //  log.put("amtAfter",newBls);
        session.persist(log);


    }



    public static BigDecimal toBigDcmTwoDot(BigDecimal amtCmsMny) {

        BigDecimal bd =amtCmsMny;
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return  bd;
    }
}

package apiCms;

import apiAcc.LogBls;
import apiAcc.OrdChrg;
import apiUsr.Usr;

import java.math.BigDecimal;
import java.util.SortedMap;
import java.util.TreeMap;


import static apis.BaseHdr.iniCfgFrmCfgfile;
import static apiUsr.RegHandler.saveDirUsrs;
//import static apiAcc.TransHdr.saveUrlLogBalanceYinliWlt;
import static biz.BaseBiz.saveUrlLogBalanceYinliWlt;
import static util.dbutil.*;
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
        addLogCms(log);
    }
    public static String saveUrlLogCms;

    /**
     * 给充值订单的人的推荐者 佣金
     * @param objChrg
     */
    public static void calcCms4FrmOrdChrg(OrdChrg objChrg) throws Exception {
        Usr u=getObjById(objChrg.uname,saveDirUsrs, Usr.class);
        String invtr= toStr( u.invtr);
        BigDecimal cmsMny=toBigDcmTwoDot(objChrg.getAmt().multiply( new BigDecimal(0.05)) );
        if(invtr.equals(""))
            return;
        addLogCms(invtr,cmsMny);
        updtTotalCmsAddamt(invtr,cmsMny);
        updtBlsYinliwlt(invtr,cmsMny);//balanceYinliwlt

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




    public static void updtBlsYinliwlt(String uname, BigDecimal amt) throws Exception {
        String methodname="updtBlsYinliwlt";
        System.out.println("\r\n\r\n");
        System.out.println("fun "+methodname+"(uname="+uname+",amt="+amt);
        Usr objU = getObjById(uname, saveDirUsrs,Usr.class);
        if(objU.id==null)
        {
            objU.id= uname;
            objU.uname= uname;
        }


        BigDecimal nowAmt= getFieldAsBigDecimal(objU,"balanceYinliwlt",0);
        BigDecimal newBls=nowAmt.add(amt);
        objU.balanceYinliwlt=toBigDcmTwoDot (newBls);
        System.out.println(updtObj(objU,saveDirUsrs));  ;

        //add balanceLog yonjin wlt
        LogBls logBalanceYlWlt=new LogBls();
        logBalanceYlWlt.id="LogBalanceYinliwlt"+getFilenameFrmLocalTimeString();
        logBalanceYlWlt.uname=uname;
        logBalanceYlWlt.changeMode="增加";
        logBalanceYlWlt.changeAmount=toBigDcmTwoDot(amt);
        logBalanceYlWlt.amtBefore=toBigDcmTwoDot(nowAmt);
        logBalanceYlWlt.newBalance=toBigDcmTwoDot(newBls);
        System.out.println(addObj(logBalanceYlWlt,saveUrlLogBalanceYinliWlt));

        System.out.println("endfun "+methodname);
    }


    private static void updtTotalCmsAddamt(String uname, BigDecimal cmsMny) throws Exception {

        Usr objU = getObjById(uname, saveDirUsrs,Usr.class);
        if(objU.id==null)
        {
            objU.id= uname;
            objU.uname= uname;
        }


        BigDecimal nowAmt=objU.totalCommssionAmt;
        BigDecimal newBls=nowAmt.add(cmsMny);
        objU.totalCommssionAmt =toBigDcmTwoDot (newBls);
        updtObj(objU,saveDirUsrs);


    }
    private static void addLogCms(LogCms log) throws Exception {


        //add balanceLog
      //  LogCms log=new LogCms();
        log.id="LogCms"+getFilenameFrmLocalTimeString();
//        log.uname= uname;
//        log.commssionAmt=toBigDcmTwoDot(amtCmsMny);
        //  log.put("amtBefore",nowAmt);
        //  log.put("amtAfter",newBls);
        addObj(log,saveUrlLogCms,LogCms.class);


    }

    private static void addLogCms(String uname, BigDecimal amtCmsMny) throws Exception {


        //add balanceLog
        LogCms log=new LogCms();
        log.id="LogCms"+getFilenameFrmLocalTimeString();
        log.uname= uname;
        log.commssionAmt=toBigDcmTwoDot(amtCmsMny);
        //  log.put("amtBefore",nowAmt);
        //  log.put("amtAfter",newBls);
        addObj(log,saveUrlLogCms);


    }

    public static BigDecimal toBigDcmTwoDot(BigDecimal amtCmsMny) {

        BigDecimal bd =amtCmsMny;
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return  bd;
    }
}

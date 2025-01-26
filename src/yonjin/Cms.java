package yonjin;

import java.math.BigDecimal;
import java.util.SortedMap;
import java.util.TreeMap;


import static apis.BaseHdr.iniCfgFrmCfgfile;
import static apis.RegHandler.saveDirUsrs;
import static apis.TransHdr.saveUrlLogBalanceYinliWlt;
import static util.dbutil.*;
import static util.util2026.*;

public class Cms {

    public static void main(String[] args) throws Exception {
        iniCfgFrmCfgfile();
        SortedMap<String, Object> objU=new TreeMap<>();
        objU.put("uname","008");
        objU.put("invtr","007");
        calcCms4chrgU(objU,new BigDecimal(100));
    }
    public static String saveUrlLogCms;
    public static void calcCms4chrgU(SortedMap<String, Object> objU, BigDecimal amt) throws Exception {
        String invtr= (String) getField2025(objU,"invtr","");
        BigDecimal cmsMny=amt.multiply( new BigDecimal(0.05));
       if(invtr.equals(""))
           return;
        addLogCms(invtr,cmsMny);
        updtTotalCmsAddamt(invtr,cmsMny);
        updtBlsYinliwlt(invtr,cmsMny);//balanceYinliwlt
    }


    public static SortedMap<String, Object> updtBlsYinliwlt(String uname, BigDecimal amt) throws Exception {
        SortedMap<String, Object> objU = getObjIni(uname, saveDirUsrs);
        if(objU.get("id")==null)
        {
            objU.put("id", uname);
            objU.put("uname", uname);
        }


        BigDecimal nowAmt= getFieldAsBigDecimal(objU,"balanceYinliwlt",0);
        BigDecimal newBls=nowAmt.add(amt);
        objU.put("balanceYinliwlt",toBigDcmTwoDot (newBls));
        updtObj(objU,saveDirUsrs);

        //add balanceLog
        SortedMap<String, Object> logBalance=new TreeMap<>();
        logBalance.put("id","LogBalanceYinliwlt"+getFilenameFrmLocalTimeString());
        logBalance.put("uname", uname);
        logBalance.put("change","增加");
        logBalance.put("amt",toBigDcmTwoDot(amt));
        logBalance.put("amtBefore",toBigDcmTwoDot(nowAmt));
        logBalance.put("amtAfter",toBigDcmTwoDot(newBls));
        addObj(logBalance,saveUrlLogBalanceYinliWlt);
        return objU;
    }


    private static void updtTotalCmsAddamt(String uname, BigDecimal cmsMny) throws Exception {

        SortedMap<String, Object> objU = getObjIni(uname, saveDirUsrs);
        if(objU.get("id")==null)
        {
            objU.put("id", uname);
            objU.put("uname", uname);
        }


        BigDecimal nowAmt= getFieldAsBigDecimal(objU,"commssionAmt",0);
        BigDecimal newBls=nowAmt.add(cmsMny);
        objU.put("commssionAmt",toBigDcmTwoDot (newBls));
        updtObj(objU,saveDirUsrs);


    }


    private static void addLogCms(String uname, BigDecimal amtCmsMny) throws Exception {


        //add balanceLog
        SortedMap<String, Object> log=new TreeMap<>();
        log.put("id","LogCms"+getFilenameFrmLocalTimeString());
        log.put("uname", uname);
        log.put("commssionAmt",toBigDcmTwoDot(amtCmsMny) );
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

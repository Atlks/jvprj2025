package yonjin;

import java.math.BigDecimal;
import java.util.SortedMap;
import java.util.TreeMap;


import static util.dbutil.addObj;
import static util.util2026.getField2025;
import static util.util2026.getFilenameFrmLocalTimeString;

public class Cms {

    public static String saveUrlLogCms;
    public static void calcCms2(SortedMap<String, Object> objU, BigDecimal amt) throws Exception {
        String invtr= (String) getField2025(objU,"invtr","");
        BigDecimal cmsMny=amt.multiply( new BigDecimal(0.05));
        addLogCms(invtr,cmsMny);
    }


    private static void addLogCms(String uname, BigDecimal amtCmsMny) throws Exception {


        //add balanceLog
        SortedMap<String, Object> log=new TreeMap<>();
        log.put("id","LogCms"+getFilenameFrmLocalTimeString());
        log.put("uname", uname);
        log.put("change","增加");
        log.put("amt", amtCmsMny);
        //  log.put("amtBefore",nowAmt);
        //  log.put("amtAfter",newBls);
        addObj(log,saveUrlLogCms);


    }
}

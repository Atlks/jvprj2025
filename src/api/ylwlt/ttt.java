package api.ylwlt;

import entityx.LogBls4YLwlt;

import java.math.BigDecimal;


import static service.CmsBiz.toBigDcmTwoDot;
import static cfg.MyCfg.iniContnr4cfgfile;
import static biz.BaseBiz.saveUrlLogBalanceYinliWlt;
import static util.tx.dbutil.addObj;
import static util.misc.util2026.getFilenameFrmLocalTimeString;

public class ttt {

    public static void main(String[] args) throws Exception {
        iniContnr4cfgfile();
        //add balanceLog yonjin wlt
        LogBls4YLwlt logBalanceYlWlt=new LogBls4YLwlt();
        logBalanceYlWlt.id="LogBalanceYinliwlt"+getFilenameFrmLocalTimeString();
        logBalanceYlWlt.uname="007";
        logBalanceYlWlt.changeMode="增加";
        logBalanceYlWlt.amtBefore=toBigDcmTwoDot(BigDecimal.valueOf(8));
        logBalanceYlWlt.changeAmount=toBigDcmTwoDot(BigDecimal.valueOf(88));

        logBalanceYlWlt.newBalance=toBigDcmTwoDot(logBalanceYlWlt.amtBefore.add(logBalanceYlWlt.changeAmount));
        System.out.println(addObj(logBalanceYlWlt,saveUrlLogBalanceYinliWlt, LogBls4YLwlt.class));

    }
}

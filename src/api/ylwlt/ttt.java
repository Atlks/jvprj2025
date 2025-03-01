package api.ylwlt;

import entityx.LogBlsLogYLwlt;

import java.math.BigDecimal;


import static service.CmsBiz.toBigDcmTwoDot;
import static cfg.MyCfg.iniCfgFrmCfgfile;
import static biz.BaseBiz.saveUrlLogBalanceYinliWlt;
import static util.dbutil.addObj;
import static util.util2026.getFilenameFrmLocalTimeString;

public class ttt {

    public static void main(String[] args) throws Exception {
        iniCfgFrmCfgfile();
        //add balanceLog yonjin wlt
        LogBlsLogYLwlt logBalanceYlWlt=new LogBlsLogYLwlt();
        logBalanceYlWlt.id="LogBalanceYinliwlt"+getFilenameFrmLocalTimeString();
        logBalanceYlWlt.uname="007";
        logBalanceYlWlt.changeMode="增加";
        logBalanceYlWlt.amtBefore=toBigDcmTwoDot(BigDecimal.valueOf(8));
        logBalanceYlWlt.changeAmount=toBigDcmTwoDot(BigDecimal.valueOf(88));

        logBalanceYlWlt.newBalance=toBigDcmTwoDot(logBalanceYlWlt.amtBefore.add(logBalanceYlWlt.changeAmount));
        System.out.println(addObj(logBalanceYlWlt,saveUrlLogBalanceYinliWlt,LogBlsLogYLwlt.class));

    }
}

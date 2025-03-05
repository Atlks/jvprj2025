package cfg;

import api.wlt.RechargeCallbackHdr;
import api.bet.QryOrdBetHdr;
import api.usr.RegHandler;
import api.ylwlt.WithdrawHdr;
import biz.BaseBiz;
import biz.BaseHdr;
import service.CmsBiz;
import util.oo.UserBiz;


import java.util.Map;
import java.util.Objects;

import static api.wlt.RechargeHdr.saveUrlOrdChrg;
import static util.misc.util2026.parse_ini_fileNosec;

public class MyCfg {

    public static void iniCfgFrmCfgfileMltDb() {

        //test
        //   openMap4test();
        //    dbutil.  drvMap.put("com.mysql.cj.jdbc.Driver","org.h2.Driver");


        // 获取类加载器 /C:/Users/attil/IdeaProjects/jvprj2025/out/production/jvprj2025/
        String rootPath = Objects.requireNonNull(UserBiz.class.getClassLoader().getResource("")).getPath();
        Map cfg = parse_ini_fileNosec(rootPath + "cfg/dbcfg.ini");
        RegHandler.saveDirUsrs = (String) cfg.get("saveDirUsrs");
        // saveDirAcc= (String) cfg.get("saveDirAcc");
        //   savedirOrd= (String) cfg.get("savedirOrd");
        //QryOrdBetHdr.saveUrlOrdBet
        QryOrdBetHdr.saveUrlOrdBet = (String) cfg.get("saveUrlOrdBet");
        saveUrlOrdChrg = RegHandler.saveDirUsrs;
        //(String) cfg.get("saveUrlOrdChrg");
        RechargeCallbackHdr.saveUrlLogBalance = (String) cfg.get("saveUrlLogBalance");
        BaseBiz.saveUrlLogBalanceYinliWlt = (String) cfg.get("saveUrlLogBalanceYinliWlt");
        WithdrawHdr.saveUrlOrdWthdr = (String) cfg.get("saveUrlOrdWthdr");
        CmsBiz.saveUrlLogCms = (String) cfg.get("saveUrlLogCms");
        System.out.println("ini cfg finish..");
    }
    public static void iniCfgFrmCfgfile() {

        //test
        //   openMap4test();
        //    dbutil.  drvMap.put("com.mysql.cj.jdbc.Driver","org.h2.Driver");


        // 获取类加载器 /C:/Users/attil/IdeaProjects/jvprj2025/out/production/jvprj2025/
        String rootPath = UserBiz.class.getClassLoader().getResource("").getPath();
        Map cfg = parse_ini_fileNosec(rootPath + "../../cfg/dbcfg.ini");
        BaseHdr.saveDirUsrs = (String) cfg.get("saveDirUsrs");
        // saveDirAcc= (String) cfg.get("saveDirAcc");
        //   savedirOrd= (String) cfg.get("savedirOrd");
        //QryOrdBetHdr.saveUrlOrdBet
        QryOrdBetHdr.saveUrlOrdBet = RegHandler.saveDirUsrs;
        saveUrlOrdChrg = RegHandler.saveDirUsrs;
        //(String) cfg.get("saveUrlOrdChrg");
        RechargeCallbackHdr.saveUrlLogBalance = RegHandler.saveDirUsrs;
        BaseBiz.saveUrlLogBalanceYinliWlt = RegHandler.saveDirUsrs;
        WithdrawHdr.saveUrlOrdWthdr = RegHandler.saveDirUsrs;
        CmsBiz.saveUrlLogCms = RegHandler.saveDirUsrs;
        System.out.println("ini cfg finish..");
    }
}

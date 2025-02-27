package cfg;

import apiAcc.CompleteChargeCallbackHdr;
import apiOrdBet.QryOrdBetHdr;
import apiUsr.RegHandler;
import apiWltYinli.WithdrawHdr;
import biz.BaseBiz;
import biz.BaseHdr;
import service.CmsBiz;
import test.UserBiz;

import java.util.Map;

import static apiAcc.RechargeHdr.saveUrlOrdChrg;
import static util.util2026.parse_ini_fileNosec;

public class MyCfg {

    public static void iniCfgFrmCfgfileMltDb() {

        //test
        //   openMap4test();
        //    dbutil.  drvMap.put("com.mysql.cj.jdbc.Driver","org.h2.Driver");


        // 获取类加载器 /C:/Users/attil/IdeaProjects/jvprj2025/out/production/jvprj2025/
        String rootPath = UserBiz.class.getClassLoader().getResource("").getPath();
        Map cfg = parse_ini_fileNosec(rootPath + "cfg/dbcfg.ini");
        RegHandler.saveDirUsrs = (String) cfg.get("saveDirUsrs");
        // saveDirAcc= (String) cfg.get("saveDirAcc");
        //   savedirOrd= (String) cfg.get("savedirOrd");
        //QryOrdBetHdr.saveUrlOrdBet
        QryOrdBetHdr.saveUrlOrdBet = (String) cfg.get("saveUrlOrdBet");
        saveUrlOrdChrg = RegHandler.saveDirUsrs;
        //(String) cfg.get("saveUrlOrdChrg");
        CompleteChargeCallbackHdr.saveUrlLogBalance = (String) cfg.get("saveUrlLogBalance");
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
        CompleteChargeCallbackHdr.saveUrlLogBalance = RegHandler.saveDirUsrs;
        BaseBiz.saveUrlLogBalanceYinliWlt = RegHandler.saveDirUsrs;
        WithdrawHdr.saveUrlOrdWthdr = RegHandler.saveDirUsrs;
        CmsBiz.saveUrlLogCms = RegHandler.saveDirUsrs;
        System.out.println("ini cfg finish..");
    }
}

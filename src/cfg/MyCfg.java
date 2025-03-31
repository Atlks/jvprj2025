package cfg;

import api.bet.ListBetsHdr;
import api.wlt.RechargeCallbackHdr;

import api.usr.RegHandler;
import api.ylwlt.WithdrawHdr;
import biz.BaseBiz;
import biz.BaseHdr;
import service.CmsBiz;
import util.oo.UserBiz;


import java.util.Map;
import java.util.Objects;

import static api.wlt.RechargeHdr.saveUrlOrdChrg;
import static util.algo.IsXXX.isblank;
import static util.algo.JarClassScanner.getPrjPath;
import static util.algo.JarClassScanner.getTargetPath;
import static util.misc.util2026.*;

import java.io.*;
import java.nio.file.*;

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
        ListBetsHdr.saveUrlOrdBet = (String) cfg.get("saveUrlOrdBet");
        saveUrlOrdChrg = RegHandler.saveDirUsrs;
        //(String) cfg.get("saveUrlOrdChrg");
        RechargeCallbackHdr.saveUrlLogBalance = (String) cfg.get("saveUrlLogBalance");
        BaseBiz.saveUrlLogBalanceYinliWlt = (String) cfg.get("saveUrlLogBalanceYinliWlt");
        WithdrawHdr.saveUrlOrdWthdr = (String) cfg.get("saveUrlOrdWthdr");
        CmsBiz.saveUrlLogCms = (String) cfg.get("saveUrlLogCms");
        System.out.println("ini cfg finish..");
    }

    /**
     * maven编译后目录结构是这样的
     * /target/cfg/xx.cfg
     * /target/jvprj.jar
     * jvprj.jar 里面有个mainapi类，这个类要如何代码里面要要如何读取xx.cfg
     */
    public static void iniCfgFrmCfgfile() throws FileNotFoundException {

        //test
        //   openMap4test();
        //    dbutil.  drvMap.put("com.mysql.cj.jdbc.Driver","org.h2.Driver");


        // 获取类加载器 /C:/Users/attil/IdeaProjects/jvprj2025/out/production/jvprj2025/
        //  \target\lib\bcpkix-jdk15on-1.64.jar!\META-INF\versions\9\..\..\cfg\dbcfg.ini
        String rootPath = UserBiz.class.getClassLoader().getResource("").getPath();
        String dbcfgName = "dbcfg.ini";
        String path = dbcfgName;

        InputStream inputStream = null;
        // 使用相对路径读取文件
        //   Path path = Paths.get("cfg/dbcfg.ini");
        //   String content = new String(Files.readAllBytes(path));
        String sysPropDbcfg = System.getProperty("dbcfg");
        if (!isblank(sysPropDbcfg)) {
            System.out.println(" iniCfgFrmCfgfile(),rd sys prop,dbcfg="+sysPropDbcfg);
            inputStream = new FileInputStream(sysPropDbcfg);
        }
        else {
            String rootDirMode = "/cfg/" + dbcfgName;
            if (new File(rootDirMode).exists()) {
                System.out.println(" iniCfgFrmCfgfile().rootDir,,dbcfg="+rootDirMode);
                inputStream = new FileInputStream(rootDirMode);
            }
            else {
                String prjDirMode = getPrjPath() + "/cfg/" + dbcfgName;
                if (new File(prjDirMode).exists()) {
                    System.out.println(" iniCfgFrmCfgfile().prjDirMode blk,,dbcfg="+prjDirMode);
                    inputStream = new FileInputStream(prjDirMode);
                } else {
                    String  targetDirMode= getPrjPath() + "/target/cfg/" + dbcfgName;
                    System.out.println(" iniCfgFrmCfgfile().targetDirMode blk,,dbcfg="+targetDirMode);
                    if (new File(targetDirMode).exists()) {
                        inputStream = new FileInputStream( targetDirMode);
                    }
                }
            }
        }

        Map cfg = parse_ini_fileNosecByStream(inputStream);
        //rootPath + "../../cfg/dbcfg.ini");
        BaseHdr.saveDirUsrs = (String) cfg.get("saveDirUsrs");
        // saveDirAcc= (String) cfg.get("saveDirAcc");
        //   savedirOrd= (String) cfg.get("savedirOrd");
        //QryOrdBetHdr.saveUrlOrdBet
        ListBetsHdr.saveUrlOrdBet = RegHandler.saveDirUsrs;
        saveUrlOrdChrg = RegHandler.saveDirUsrs;
        //(String) cfg.get("saveUrlOrdChrg");
        RechargeCallbackHdr.saveUrlLogBalance = RegHandler.saveDirUsrs;
        BaseBiz.saveUrlLogBalanceYinliWlt = RegHandler.saveDirUsrs;
        WithdrawHdr.saveUrlOrdWthdr = RegHandler.saveDirUsrs;
        CmsBiz.saveUrlLogCms = RegHandler.saveDirUsrs;
        System.out.println("ini cfg finish..");
    }
}

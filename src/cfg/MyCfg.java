package cfg;

import api.bet.ListBetsHdr;
import api.wlt.RechargeCallbackHdr;

import api.usr.RegHandler;
import api.ylwlt.WithdrawHdr;
import biz.BaseBiz;
import biz.BaseHdr;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.jetbrains.annotations.Nullable;
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

        InputStream inputStream = getCfgInputStream(dbcfgName);

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


    /**
     ** 该方法会依次检查以下路径来寻找配置文件：
     *      * 1. JVM 系统属性 "dbcfg" 指定的路径,例如命令行参数   -Ddbcfg=/cfg/dbcfg.ini
     *      * 2. /根目录下的 "cfg" 目录
     *      * 3. 项目目录路径下的 "cfg" 目录
     *      * 4. 编译后 target 目录下的 "cfg" 目录
     *      * 5. 作为资源从 classpath 加载
     * @param cfgFileName
     * @return
     * @throws FileNotFoundException
     */
    @NotNull
    private static InputStream getCfgInputStream(@NotBlank String cfgFileName) throws FileNotFoundException {
        InputStream inputStream = null;
        // 使用相对路径读取文件
        //   Path path = Paths.get("cfg/dbcfg.ini");
        //   String content = new String(Files.readAllBytes(path));
        // 获取系统属性中定义的 dbcfg 配置文件路径
        String sysPropDbcfg = System.getProperty("dbcfg");
        if (!isblank(sysPropDbcfg)) {
            System.out.println(" iniCfgFrmCfgfile(),rd sys prop,dbcfg=" + sysPropDbcfg);
            inputStream = new FileInputStream(sysPropDbcfg);
        } else {
            //-root dir mode
            String rootDirMode = "/cfg/" + cfgFileName;
            if (new File(rootDirMode).exists()) {
                System.out.println(" iniCfgFrmCfgfile().rootDir,,dbcfg=" + rootDirMode);
                inputStream = new FileInputStream(rootDirMode);
            } else {
                //---prj path
                // 尝试从项目路径的 cfg 目录获取配置文件
                String prjDirMode = getPrjPath() + "/cfg/" + cfgFileName;
                if (new File(prjDirMode).exists()) {
                    System.out.println(" iniCfgFrmCfgfile().prjDirMode blk,,dbcfg=" + prjDirMode);
                    inputStream = new FileInputStream(prjDirMode);
                } else {
                    //--target dir mode
                    String targetDirMode = getTargetPath() + "/cfg/" + cfgFileName;
                    System.out.println(" iniCfgFrmCfgfile().targetDirMode blk,,dbcfg=" + targetDirMode);
                    if (new File(targetDirMode).exists()) {
                        inputStream = new FileInputStream(targetDirMode);
                    } else {
                        // 尝试从 classpath 加载资源文件
                        //-----jar mode
                        //  inputStream = new FileInputStream( targetDirMode);
                        inputStream = MyCfg.class.getClassLoader().getResourceAsStream(cfgFileName);
                    }
                }
            }
        }
        if(inputStream==null)
            throw  new RuntimeException("cant find cfg");
        return inputStream;
    }
}

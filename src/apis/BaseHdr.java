package apis;

import apiAcc.UpdtCompleteChargeHdr;
import apiOrdBet.QryOrdBetHdr;
import apiUsr.RegHandler;
import apiWltYinli.WithdrawHdr;
import biz.BaseBiz;
import test.UserBiz;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import apiCms.CmsBiz;

import java.io.IOException;
import java.util.Map;

import static apiAcc.AddOrdChargeHdr.saveUrlOrdChrg;
//import static apiAcc.TransHdr.saveUrlLogBalanceYinliWlt;

import static util.util2026.*;

public abstract class BaseHdr implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {
            setcookie("uname", "007", exchange);//for test
            handle2(exchange);


        } catch (Exception e) {
            e.printStackTrace();
            //   wrtResp(exchange, e.getMessage());
            wrtResp(exchange, getStackTraceAsString(e));

            throw new RuntimeException(e);

        }
    }

    public static void iniCfgFrmCfgfile() {

        //test
        //   openMap4test();
        //    dbutil.  drvMap.put("com.mysql.cj.jdbc.Driver","org.h2.Driver");


        // 获取类加载器 /C:/Users/attil/IdeaProjects/jvprj2025/out/production/jvprj2025/
        String rootPath = UserBiz.class.getClassLoader().getResource("").getPath();
        Map cfg = parse_ini_fileNosec(rootPath + "../../cfg/dbcfg.ini");
        RegHandler.saveDirUsrs = (String) cfg.get("saveDirUsrs");
        // saveDirAcc= (String) cfg.get("saveDirAcc");
        //   savedirOrd= (String) cfg.get("savedirOrd");
        //QryOrdBetHdr.saveUrlOrdBet
        QryOrdBetHdr.saveUrlOrdBet = RegHandler.saveDirUsrs;
        saveUrlOrdChrg = RegHandler.saveDirUsrs;
        //(String) cfg.get("saveUrlOrdChrg");
        UpdtCompleteChargeHdr.saveUrlLogBalance = RegHandler.saveDirUsrs;
        BaseBiz.saveUrlLogBalanceYinliWlt = RegHandler.saveDirUsrs;
        WithdrawHdr.saveUrlOrdWthdr = RegHandler.saveDirUsrs;
        CmsBiz.saveUrlLogCms = RegHandler.saveDirUsrs;
        System.out.println("ini cfg finish..");
    }


    public static void iniCfgFrmCfgfileMltDb() {

        //test
        //   openMap4test();
        //    dbutil.  drvMap.put("com.mysql.cj.jdbc.Driver","org.h2.Driver");


        // 获取类加载器 /C:/Users/attil/IdeaProjects/jvprj2025/out/production/jvprj2025/
        String rootPath = UserBiz.class.getClassLoader().getResource("").getPath();
        Map cfg = parse_ini_fileNosec(rootPath + "../../../cfg/dbcfg.ini");
        RegHandler.saveDirUsrs = (String) cfg.get("saveDirUsrs");
        // saveDirAcc= (String) cfg.get("saveDirAcc");
        //   savedirOrd= (String) cfg.get("savedirOrd");
        //QryOrdBetHdr.saveUrlOrdBet
        QryOrdBetHdr.saveUrlOrdBet = (String) cfg.get("saveUrlOrdBet");
        saveUrlOrdChrg = RegHandler.saveDirUsrs;
        //(String) cfg.get("saveUrlOrdChrg");
        UpdtCompleteChargeHdr.saveUrlLogBalance = (String) cfg.get("saveUrlLogBalance");
        BaseBiz.saveUrlLogBalanceYinliWlt = (String) cfg.get("saveUrlLogBalanceYinliWlt");
        WithdrawHdr.saveUrlOrdWthdr = (String) cfg.get("saveUrlOrdWthdr");
        CmsBiz.saveUrlLogCms = (String) cfg.get("saveUrlLogCms");
        System.out.println("ini cfg finish..");
    }

    protected abstract void handle2(HttpExchange exchange) throws Exception;

    public boolean isLogined(HttpExchange exchange) {
        String uname = getcookie("uname", exchange);
        return !uname.equals("");
        //   return  true;
    }

    public boolean isNotLogined(HttpExchange exchange) {
        String uname = getcookie("uname", exchange);
        return uname.equals("");
        //   return  true;
    }


}

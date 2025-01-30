package apis;

import apiAcc.UpdtCompleteChargeHdr;
import apiOrdBet.QryOrdBetHdr;
import apiUsr.Err;
import apiUsr.RegHandler;
import apiUsr.ThrowableX;
import apiUsr.UnameOrPwdErrEx;
import apiWltYinli.WithdrawHdr;
import biz.BaseBiz;
import biz.existUserEx;
import com.alibaba.fastjson2.JSON;
import test.UserBiz;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import apiCms.CmsBiz;

import java.io.IOException;
import java.util.Map;

import static apiAcc.AddOrdChargeHdr.saveUrlOrdChrg;
//import static apiAcc.TransHdr.saveUrlLogBalanceYinliWlt;

import static util.Util2025.encodeJson;

import static util.util2026.*;

public abstract class BaseHdr implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {
            setcookie("uname", "007", exchange);//for test
            handle2(exchange);


        } catch (Throwable e) {
            //  e.getStackTrace()
            e.printStackTrace();

            //nml err
            ThrowableX ex = new ThrowableX(e.getMessage());

            //my throw ex.incld funprm
            if (e instanceof ThrowableX) {
                ex = (ThrowableX) e;
                ex.errcode=e.getClass().getName();
            }

            String stackTraceAsString = getStackTraceAsString(e);
            ex.stackTrace = stackTraceAsString;
            wrtRespErr(exchange, encodeJson(ex));


            // throw new RuntimeException(e);

        }
    }

    /**
     * 使用fastjson2，，将jsonstr转换为err对象
     *
     * @param jsonStr
     * @return
     */
    private Err toERR(String jsonStr) {

        try {
            if (jsonStr == null || jsonStr.isEmpty()) {
                return new Err("", "", "", null);
            }
            return JSON.parseObject(jsonStr, Err.class);
        } catch (Exception e) {
            //not errmsg,just str msg ,or another type
            return new Err(jsonStr, "", "", null);
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

    protected abstract void handle2(HttpExchange exchange) throws Throwable;

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

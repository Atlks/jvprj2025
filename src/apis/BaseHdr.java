package apis;

import biz.UserBiz;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.Map;

import static apis.AddOrdChargeHdr.saveUrlOrdChrg;
import static biz.AccBiz.listAccLog;
import static util.Util2025.encodeJson;
import static util.util2026.*;

public  abstract class BaseHdr implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {
setcookie("uname","ttt",exchange);//for test
            handle2(exchange);


        } catch (Exception e) {
            e.printStackTrace();
         //   wrtResp(exchange, e.getMessage());
            wrtResp(exchange, getStackTraceAsString(e));

            throw new RuntimeException(e);

        }
    }
    public static void iniCfgFrmCfgfile( ) {
        // 获取类加载器 /C:/Users/attil/IdeaProjects/jvprj2025/out/production/jvprj2025/
        String rootPath = UserBiz.class.getClassLoader().getResource("").getPath();
        Map cfg = parse_ini_fileNosec(rootPath + "../../../cfg/dbcfg.ini");
        RegHandler.saveDirUsrs = (String) cfg.get("saveDirUsrs");
        // saveDirAcc= (String) cfg.get("saveDirAcc");
        //   savedirOrd= (String) cfg.get("savedirOrd");
        //QryOrdBetHdr.saveUrlOrdBet
        QryOrdBetHdr.  saveUrlOrdBet = (String) cfg.get("saveUrlOrdBet");
        saveUrlOrdChrg = (String) cfg.get("saveUrlOrdChrg");
        System.out.println("ini cfg finish..");
    }
    abstract void handle2(HttpExchange exchange) throws Exception;

    public boolean isLogined(HttpExchange exchange) {
        String uname = getcookie("uname", exchange);
        return  !uname.equals("");
     //   return  true;
    }

    public boolean isNotLogined(HttpExchange exchange) {
        String uname = getcookie("uname", exchange);
        return  uname.equals("");
        //   return  true;
    }


}

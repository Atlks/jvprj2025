package apiUsr;

import com.alibaba.fastjson2.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;


import static apiUsr.RegHandler.saveDirUsrs;
import static util.dbutil.getObjDocdb;
import static util.util2026.*;

public class LoginHdr  implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String uname=    getRequestParameter(exchange,"uname");
        String pwd=    getRequestParameter(exchange,"pwd");
        if(login(uname,pwd))
        {
            setcookie("uname",uname,exchange);
            wrtResp(exchange, "ok");
        }
    }



    public static boolean login(String uname, String pwd) {
        JSONObject jo = getObjDocdb(uname,   saveDirUsrs);
        if (jo.getString("pwd").equals(pwd))
            return true;
        return false;
    }

}

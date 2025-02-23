package apiUsr;

import biz.BaseHdr;
import com.alibaba.fastjson2.JSONObject;
import com.sun.net.httpserver.HttpExchange;


import static util.dbutil.*;
import static util.util2026.wrtResp;

public class ResetPwdHdr extends BaseHdr {
    @Override
    public void handle2(HttpExchange exchange) throws Exception {




        //blk login ed
//        Object acclog = null;
//        addObj(acclog, "acc", "/db2026/");
        wrtResp(exchange, "ok");


    }

    public static String resetPwd(String uname, String pwd) {
        JSONObject jo = getObjDocdb(uname,  saveDirUsrs);
        jo.put("pwd", pwd);
        updateObjIni(jo, saveDirUsrs);
        return "";
    }
}

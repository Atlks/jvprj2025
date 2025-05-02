//package api.usr;
//
//// cfg.BaseHdr;
//import com.alibaba.fastjson2.JSONObject;
//import com.sun.net.httpserver.HttpExchange;
//import entityx.usr.Usr;
//
//
//import static cfg.Containr.saveDirUsrs;
//import static util.tx.dbutil.*;
//import static util.misc.util2026.wrtResp;
//
//public class ResetPwdHdr extends BaseHdr<Usr, Usr> {
//    @Override
//    public void handle2(HttpExchange exchange) throws Exception {
//
//
//
//
//        //blk login ed
////        Object acclog = null;
////        addObj(acclog, "acc", "/db2026/");
//        wrtResp(exchange, "ok");
//
//
//    }
//
//    public static String resetPwd(String uname, String pwd) {
//        JSONObject jo = getObjDocdb(uname,  saveDirUsrs);
//        jo.put("pwd", pwd);
//        updateObjIni(jo, saveDirUsrs);
//        return "";
//    }
//}

package apis;

import com.alibaba.fastjson2.JSONObject;
import com.sun.net.httpserver.HttpExchange;

import static biz.BaseBiz.saveDirUsrs;
import static util.dbutil.addObj;
import static util.dbutil.getObjDocdb;
import static util.util2026.*;

public class UpdtPwdHdr extends BaseHdr {
    @Override
    public void handle2(HttpExchange exchange) throws Exception {


        String uname = getcookie("uname", exchange);

        if (uname.equals("")) {
            //need login
            wrtResp(exchange, "needLogin");
            return;
        }
        String oldpwd=    getRequestParameter(exchange,"oldpwd");
        String pwd=    getRequestParameter(exchange,"pwd");
        JSONObject jo = getObjDocdb(uname,  saveDirUsrs);
        if (jo.getString("pwd").equals(oldpwd))
        {
            // 创建 User 对象
            RegHandler.User user = new RegHandler.User(uname, uname, pwd, 1);
            //   saveDir = saveDir;
            addObj(user, saveDirUsrs +"usrs");
            wrtResp(exchange, "ok");
        }else{
            wrtResp(exchange, "powNotMatch");
        }
        //blk login ed
//        Object acclog = null;
//        addObj(acclog, "acc", "/db2026/");



    }


}

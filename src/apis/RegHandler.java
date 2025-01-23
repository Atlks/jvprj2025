package apis;

import biz.UserBiz;
import com.alibaba.fastjson2.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

import static biz.BaseBiz.saveDir;

import static util.dbutil.addObj;
import static util.dbutil.getObjDocdb;
import static util.util2026.getRequestParameter;
import static util.util2026.wrtResp;

// 自定义的请求处理器
public class RegHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // 检查请求方法
     //   if ("GET".equals(exchange.getRequestMethod())) {

        String uname=    getRequestParameter(exchange,"uname");
        String pwd=    getRequestParameter(exchange,"pwd");
        System.out.println(uname);

        String responseTxt = "";
        try {
            responseTxt = reg(uname,pwd);
            wrtResp(exchange, responseTxt);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //    }
    }


    public static String reg(String uname, String pwd) throws Exception {


        if (existUser(uname)) {
            return "existUser";
        }
        //  if(!existUser(uname))

        // 创建 User 对象
        User user = new User(uname, uname, pwd, 1);
        //   saveDir = saveDir;
        addObj(user, saveDir+"usrs");
        //  addObj(user, "u","jdbc:sqlite:/db2026/usrs.db");
        return "ok";


    }


    public static boolean existUser(User user) {
        return existUser(user.uname);
    }





    public record User(String id, String uname, String pwd, int age) {

        // record 自动生成构造函数、getters、equals、hashCode 和 toString 方法
    }

    public static boolean existUser(String uname) {

        JSONObject jo = getObjDocdb(uname, "usrs", saveDir);
        // 空安全处理，直接操作结果
        if (jo.isEmpty()) {
            return false;
        } else
            return true;
    }
}

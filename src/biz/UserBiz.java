package biz;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static biz.UserBiz.reg;
import static util.Util2025.encodeJson;
import static util.dbutil.addObj;
import static util.dbutil.getObjDocdb;
import static util.util2026.getField2025;
import static util.util2026.wrtResp;

public class UserBiz {

    public static void main(String[] args) throws Exception {
        String responseTxt = reg("unam2e", "pp");
        System.out.println(responseTxt);
        // 定义一个 Record
        //   record User(String username, int age) {}
    }

    public static String reg(String uname, String pwd) throws Exception {


        if (existUser(uname)) {
            return "existUser";
        }
        //  if(!existUser(uname))

        // 创建 User 对象
        User user = new User(uname, uname, pwd);
        addObj(user, "usrs", "/db2026/");
        //  addObj(user, "u","jdbc:sqlite:/db2026/usrs.db");
        return "ok";


    }


    public record User(String id, String uname, String pwd) {

        // record 自动生成构造函数、getters、equals、hashCode 和 toString 方法
    }

    private static boolean existUser(String uname) {

        JSONObject jo = getObjDocdb(uname, "usrs", "/db2026/");
        if (jo.getString("id") != null)
            return true;
        else
            return false;
    }
}

package biz;

import com.alibaba.fastjson2.JSONObject;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.SortedMap;
import java.util.function.Predicate;

import static biz.UserBiz.reg;

import static util.Fltr.fltr2501;
import static util.Util2025.encodeJson;
import static util.dbutil.*;
import static util.util2026.getField2025;
import static util.util2026.wrtResp;

public class UserBiz {

    public static void main(String[] args) throws Exception {
        String responseTxt = reg("unam2e", "pp");
         reg("unm1", "pp");
        reg("unm3", "pp");
        reg("unm2", "pp");

        var lst2 = getObjsDocdb( "usrs", "/db2026/");


        // 定义过滤条件：只保留 age > 25 的记录
        Predicate<SortedMap<String, Object>> flt1 = map -> {
            String unm = (String) map.get("uname");
            if(unm.equals("unm2"))
                  return  true;
            return false;
        };

        Predicate<SortedMap<String, Object>> flt2 = map -> {
            String unm = (String) map.get("pwd");
            if(unm.equals("ppi"))
                return  true;
            return false;
        };
       var fltList = new ArrayList<Predicate<SortedMap<String, Object>>>();
        fltList.add(flt1); fltList.add(flt2);
        var rzt=   fltr2501(lst2,fltList);
     //   List<SortedMap<String, Object>>  rzt=   fltr2501(lst2,flt1);

     //   search("unm2")
        System.out.println(responseTxt);
        // 定义一个 Record
        //   record User(String username, int age) {}
    }

    public static boolean login(String uname, String pwd)
    {
        JSONObject jo = getObjDocdb(uname, "usrs", "/db2026/");
        if(jo.getString("pwd").equals(pwd))
            return  true;
        return  false;
    }
    public static String logOut()
    {}
    public static String updtPwd()
    {}
    public static String resetPwd(String uname, String pwd)
    {
        JSONObject jo = getObjDocdb(uname, "usrs", "/db2026/");
        jo.put("pwd",pwd);
        updateObjDocdb(jo,"usrs","/db2026/");
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
        // 空安全处理，直接操作结果
        if (jo.isEmpty()) {
            return true;
        else
            return false;
    }
}

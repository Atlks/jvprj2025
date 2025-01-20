package biz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static biz.UserBiz.reg;
import static util.Util2025.encodeJson;
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

        addRowUser(user, "u");
        return "ok";


    }

    private static void addRowUser(User user, String collName) throws Exception {
        Class.forName("org.sqlite.JDBC");

        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + collName + ".db");
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS tab1 (k TEXT PRIMARY KEY, v TEXT)");
        String us = encodeJson(user);
        String sql = "INSERT INTO tab1 (k, v) VALUES ('" + user.id + "', '" + us + "')";
        System.out.println(sql);
        stmt.execute(sql);


    }

    public record User(String id, String uname, String pwd) {

        // record 自动生成构造函数、getters、equals、hashCode 和 toString 方法
    }

    private static boolean existUser(String uname) {
        return false;
    }
}

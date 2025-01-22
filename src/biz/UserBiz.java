package biz;

import com.alibaba.fastjson2.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.function.Predicate;
import java.io.*;
import java.util.*;
import static biz.UserBiz.reg;

import static util.Fltr.fltr2501;
import static util.Util2025.encodeJson;
import static util.dbutil.*;
import static util.util2026.getField2025;
import static util.util2026.wrtResp;

public class UserBiz {

    private static String saveDir="/db2026/";

    static {
        // 获取类加载器 /C:/Users/attil/IdeaProjects/jvprj2025/out/production/jvprj2025/
        String rootPath = UserBiz.class.getClassLoader().getResource("").getPath();
       Map cfg=parse_ini_file(rootPath+"../../../cfg/dbcfg.ini");
        saveDir= (String) cfg.get("saveDir");
    }

    /**
     * 解析简单的 INI 配置文件（没有节）
     *
     * @param filePath 配置文件的路径
     * @return 解析后的 Map，键值对的形式
     */
    private static Map<String, String> parse_ini_file(String filePath) {
        Map<String, String> result = new HashMap<>();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // 忽略空行和注释行
                if (line.isEmpty() || line.startsWith(";") || line.startsWith("#")) {
                    continue;
                }

                // 处理键值对，格式为 key = value
                if (line.contains("=")) {
                    String[] parts = line.split("=", 2);
                    if (parts.length == 2) {
                        String key = parts[0].trim();
                        String value = parts[1].trim();
                        result.put(key, value);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public static void main(String[] args) throws Exception {
        String responseTxt = reg("unam2e", "pp");
        reg("unm1", "pp");
        reg("unm3", "pp");
        reg("unm2", "pp");

        var lst2 = getObjsDocdb("usrs", saveDir);


        // 定义过滤条件：只保留 age > 25 的记录
        Predicate<SortedMap<String, Object>> flt1 = map -> {
            String unm = (String) map.get("uname");
            if (unm.equals("unm2"))
                return true;
            return false;
        };

        Predicate<SortedMap<String, Object>> flt2 = map -> {
            String unm = (String) map.get("pwd");
            if (unm.equals("ppi"))
                return true;
            return false;
        };
        var fltList = new ArrayList<Predicate<SortedMap<String, Object>>>();
        fltList.add(flt1);
        fltList.add(flt2);
        var rzt = fltr2501(lst2, fltList);
        //   List<SortedMap<String, Object>>  rzt=   fltr2501(lst2,flt1);

        //   search("unm2")
        System.out.println(responseTxt);
        // 定义一个 Record
        //   record User(String username, int age) {}
    }

    public static boolean login(String uname, String pwd) {
        JSONObject jo = getObjDocdb(uname, "usrs", saveDir);
        if (jo.getString("pwd").equals(pwd))
            return true;
        return false;
    }

    public static String logOut() {
        return "";
    }

    public static String updtPwd() {
        return "";
    }

    public static String resetPwd(String uname, String pwd) {
        JSONObject jo = getObjDocdb(uname, "usrs", saveDir);
        jo.put("pwd", pwd);
        updateObjDocdb(jo, "usrs", saveDir);
        return "";
    }


    public static String reg(String uname, String pwd) throws Exception {


        if (existUser(uname)) {
            return "existUser";
        }
        //  if(!existUser(uname))

        // 创建 User 对象
        User user = new User(uname, uname, pwd);
        saveDir = saveDir;
        addObj(user, "usrs", saveDir);
        //  addObj(user, "u","jdbc:sqlite:/db2026/usrs.db");
        return "ok";


    }


    public record User(String id, String uname, String pwd) {

        // record 自动生成构造函数、getters、equals、hashCode 和 toString 方法
    }

    private static boolean existUser(String uname) {

        JSONObject jo = getObjDocdb(uname, "usrs", saveDir);
        // 空安全处理，直接操作结果
        if (jo.isEmpty()) {
            return true;
        } else
            return false;
    }
}

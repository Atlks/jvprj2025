package biz;

import com.alibaba.fastjson2.JSONObject;

import java.util.*;
import java.util.function.Predicate;

import static util.ArrUtil.sortWithSpEL;
import static util.Fltr.*;
import static util.Util2025.encodeJson;
import static util.dbutil.*;
import static util.util2026.*;

public class UserBiz {

    private static String saveDir = "";

    static {
        // 获取类加载器 /C:/Users/attil/IdeaProjects/jvprj2025/out/production/jvprj2025/
        String rootPath = UserBiz.class.getClassLoader().getResource("").getPath();
        Map cfg = parse_ini_fileNosec(rootPath + "../../../cfg/dbcfg.ini");
        saveDir = (String) cfg.get("savedir");
    }


    public static void main(String[] args) throws Exception, existUserEx {


        // 创建 User 对象
        User user = new User("u1", "u1", "", 1);
//        reg( new User("u1", "u1", "",1));
//        reg( new User("u2", "u2", "",2));
//        reg( new User("u3", "u3", "",3));
        // var lst2 = getObjsDocdb("usrs", saveDir);


        // var rzt = flt1tmp(lst2);


        String expression = "uname == 'unm2' && pwd == 'pp'";
        expression = " #this['uname'] == 'unm2' && #this['pwd'] == 'pp'  ";
        expression = "";

        //   var rzt=filterWithSpEL(lst2, expression);

        var rzt = filterWithSpEL2501(saveDir + "usrs", expression);
        System.out.println("rztFlted=" + encodeJson(rzt));

        var rztSted = sortWithSpEL(rzt, "#map1['age'] < #map2['age']");
        System.out.println("rztSorted=" + encodeJson(rztSted));
        //   List<SortedMap<String, Object>>  rzt=   fltr2501(lst2,flt1);

        //   search("unm2")
        //   System.out.println(responseTxt);
        // 定义一个 Record
        //   record User(String username, int age) {}
    }


//    @SuppressWarnings("unchecked")
//    private static List<SortedMap<String, Object>> filterWithSpEL(List<SortedMap<String, Object>> list1) {
//        // 创建 SpEL 解析器
//        ExpressionParser parser = new SpelExpressionParser();
//
//        // 定义过滤表达式，使用 SpEL 的集合操作
//        String expression = "#list1.?[uname == 'unm2' && pwd == 'pp']";
//
//        // 创建上下文并设置变量
//        StandardEvaluationContext context = new StandardEvaluationContext();
//        context.setVariable("list1", list1); // 将 list1 设置为变量
//
//        // 解析并返回结果
//        return (List<SortedMap<String, Object>>) parser.parseExpression(expression).getValue(context);
//    }

    /**
     * @param list2
     * @return
     */
    private static List<SortedMap<String, Object>> filter_tmp1(List<SortedMap<String, Object>> list2) {
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
        var rzt = fltr2501(list2, fltList);
        return rzt;
    }


    private static List<SortedMap<String, Object>> filter_tmp2(List<SortedMap<String, Object>> list2) {

        // 定义过滤条件：只保留 符合条件的的记录
        Predicate<SortedMap<String, Object>> filter1 = map -> {
            String unm = (String) map.get("uname");

            String pwd = (String) map.get("pwd");

            if (unm.equals("unm2") && pwd.equals("pp"))
                return true;
            return false;
        };


        var rzt = fltr2501(list2, filter1);
        return rzt;
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

    public static String reg(User user) throws Exception, existUserEx {


        if (existUser(user)) {
            throw new existUserEx(user.uname);
        }
        //  if(!existUser(uname))


        saveDir = saveDir;
        addObj(user, "usrs", saveDir);
        //  addObj(user, "u","jdbc:sqlite:/db2026/usrs.db");
        return "ok";


    }

    private static boolean existUser(User user) {
        return existUser(user.uname);
    }


    public static String reg(String uname, String pwd) throws Exception {


        if (existUser(uname)) {
            return "existUser";
        }
        //  if(!existUser(uname))

        // 创建 User 对象
        User user = new User(uname, uname, pwd, 1);
        saveDir = saveDir;
        addObj(user, "usrs", saveDir);
        //  addObj(user, "u","jdbc:sqlite:/db2026/usrs.db");
        return "ok";


    }


    public record User(String id, String uname, String pwd, int age) {

        // record 自动生成构造函数、getters、equals、hashCode 和 toString 方法
    }

    private static boolean existUser(String uname) {

        JSONObject jo = getObjDocdb(uname, "usrs", saveDir);
        // 空安全处理，直接操作结果
        if (jo.isEmpty()) {
            return false;
        } else
            return true;
    }
}

//RegHandler.java

package apiUsr;

import apis.BaseHdr;
import biz.existUserEx;
import com.sun.net.httpserver.HttpExchange;
import org.noear.solon.annotation.Component;
import util.util2026;
import utilBiz.OrmUtilBiz;


import java.util.function.Function;

import static util.AopUtil.invokeMethod2025;
import static util.EncodeUtil.encodeMd5;


import static util.dbutil.*;
import static util.util2026.*;

@Component
//  http://localhost:8889/reg?uname=008&pwd=000&invtr=007
// 自定义的请求处理器
public class RegHandler extends BaseHdr   {

  //  @Bean
    public RegHandler() { // 确保它是 Solon 代理的 Bean
        System.out.println("RegHandler 已创建");
    }
    /**
     * @param exchange
     * @throws Exception
     */
    @Override
    public void handle2(HttpExchange exchange) throws Exception, existUserEx {
        // 检查请求方法
        //   if ("GET".equals(exchange.getRequestMethod())) {

        String uname = getRequestParameter(exchange, "uname");
        String pwd = getRequestParameter(exchange, "pwd");
        String invtr = getRequestParameter(exchange, "invtr");
        System.out.println(uname);
        Usr u = new Usr();
        u.uname = uname;
        u.pwd = pwd;
        u.invtr = invtr;
        u.id = uname;
        String reg4bzRzt = "";


       // reg4bzRzt=  reg4bz(u);   // 自动获取方法名
        // 创建实例
        RegHandler handler = new RegHandler();
        // 自定义异常处理的函数接口
        Function<Usr, String> methodRef = user -> {
            try {
                return handler.reg4bz(user);  // 调用 reg4bz 方法并处理异常
            } catch (Exception e) {
                e.printStackTrace();
                return null; // 或者返回默认值
            }
        };
      //   Function<Usr, String> methodRef = handler::reg4bz;
        String methodName = nameofSingleParam(methodRef);
        reg4bzRzt= (String) invokeMethod2025(this, methodName,u);
        System.out.println("reg4bzRzt="+reg4bzRzt);
        wrtResp(exchange, reg4bzRzt);

    }




    public static void main(String[] args) throws Exception, existUserEx {
        iniCfgFrmCfgfile();
        ovrwtest = true;
        //    drvMap.put("com.mysql.cj.jdbc.Driver","org.h2.Driver");
//        Usr u=new Usr();
//        u.uname="009";
//        u.pwd="pp";
//        u.invtr="007";
//
//        u.id=u.uname;
        System.out.println(drvMap);

        Usr u = new Usr();
        u.uname = "007";
        u.pwd = encodeMd5("pp");
        u.invtr = "";


        u.id = u.uname;
     //   reg(u);
       new RegHandler(). testAop(88888);
    }

    public   void testAop(int i) {
        System.out.println(i);
    }


    public static boolean ovrwtest = false;

    public   String reg4bz(Usr user) throws Exception, existUserEx {
       // System.out.println("fun reg4bz");
      //  System.out.println("▶\uFE0Ffun "+getCurrentMethodName()+"(u="+encodeJsonObj(user));
        if (existUser(user)) {
            if (ovrwtest) {
            } else {

                existUserEx e = new existUserEx("存在用户");
                e.fun = getCurrentMethodName();
                e.funPrm = user;
                throw e;
            }

        }
        //  if(!existUser(uname))

        //    OrmMysql om=new OrmMysql() ;
        org.hibernate.Session session = OrmUtilBiz.openSession(saveDirUsrs);
        //  om.jdbcurl=saveDirUsrs;
        //todo start tx
        session.beginTransaction();
        session.persist(user);
        session.getTransaction().commit();
        //finish tx
        //  addObj(user, saveDirUsrs, Usr.class);

        return "ok";


    }


//    public static String reg(String uname, String pwd,String invtr) throws Exception {
//
//
//        if (existUser(uname)) {
//            return "existUser";
//        }
//        //  if(!existUser(uname))
//
//        // 创建 User 对象
//        User user = new User(uname, uname, pwd,1, invtr);
//        //   saveDir = saveDir;
//        addObj(user, saveDirUsrs );
//        //  addObj(user, "u","jdbc:sqlite:/db2026/usrs.db");
//        return "ok";
//
//
//    }


    public   boolean existUser(Usr user) throws Exception {
        return existUser(user.uname);
    }


    public static String saveDirUsrs = "";


    public record User(String id, String uname, String pwd, int age, String invtr) {

        // record 自动生成构造函数、getters、equals、hashCode 和 toString 方法
    }

    public   boolean existUser(String uname) throws Exception {

        //    Usr jo = getObjById(uname, saveDirUsrs, Usr.class);

        org.hibernate.Session session = OrmUtilBiz.openSession(saveDirUsrs);
        //  om.jdbcurl=saveDirUsrs;
        //todo start tx
        // session.beginTransaction();
        Usr jo = session.find(Usr.class, uname);
        if (jo == null)
            return false;
        // 空安全处理，直接操作结果
        if (jo.uname.equals("")) {
            return false;
        } else
            return true;
    }
}

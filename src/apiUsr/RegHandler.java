package apiUsr;

import biz.Usr;
import biz.existUserEx;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.Map;


import static apis.BaseHdr.iniCfgFrmCfgfile;
import static util.dbutil.*;
import static util.util2026.*;


//  http://localhost:8889/reg?uname=008&pwd=000&invtr=007
// 自定义的请求处理器
public class RegHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // 检查请求方法
     //   if ("GET".equals(exchange.getRequestMethod())) {

        String uname=    getRequestParameter(exchange,"uname");
        String pwd=    getRequestParameter(exchange,"pwd");
        String invtr=    getRequestParameter(exchange,"invtr");
        System.out.println(uname);

        String responseTxt = "";
        try {
            responseTxt = reg(uname,pwd,invtr);
            wrtResp(exchange, responseTxt);
        } catch (Exception e) {
             throwEx(e);
        }

        //    }
    }

    public static void main(String[] args) throws Exception, existUserEx {
        iniCfgFrmCfgfile();
        Usr u=new Usr();
        u.uname="008";
        u.pwd="pp";
        u.invtr="007";

        u.id=u.uname;
        reg(u);
    }

    public static String reg(Usr user) throws Exception, existUserEx {


        if (existUser(user)) {
            throw new existUserEx("err=existUserEx,euname="+user.uname);
        }
        //  if(!existUser(uname))


        addObj(user,  saveDirUsrs);

        return "ok";


    }
    public static String reg(String uname, String pwd,String invtr) throws Exception {


        if (existUser(uname)) {
            return "existUser";
        }
        //  if(!existUser(uname))

        // 创建 User 对象
        User user = new User(uname, uname, pwd,1, invtr);
        //   saveDir = saveDir;
        addObj(user, saveDirUsrs );
        //  addObj(user, "u","jdbc:sqlite:/db2026/usrs.db");
        return "ok";


    }


    public static boolean existUser(Usr user) {
        return existUser(user.uname);
    }


    public static String saveDirUsrs = "";


    public record User(String id, String uname, String pwd, int age,String invtr) {

        // record 自动生成构造函数、getters、equals、hashCode 和 toString 方法
    }

    public static boolean existUser(String uname) {

        Map jo = getObj(uname,  saveDirUsrs);
        // 空安全处理，直接操作结果
        if (jo.isEmpty()) {
            return false;
        } else
            return true;
    }
}

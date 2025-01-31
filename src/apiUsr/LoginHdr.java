package apiUsr;

import apis.BaseHdr;
import biz.existUserEx;
import com.alibaba.fastjson2.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import utilBiz.OrmUtilBiz;

import java.io.IOException;
import java.sql.SQLException;


import static apiUsr.RegHandler.saveDirUsrs;
import static util.Util2025.encodeJson;
import static util.dbutil.getObjDocdb;
import static util.util2026.*;


//   http://localhost:8889/login?uname=008&pwd=000
public class LoginHdr  extends BaseHdr {


    /**
     * @param exchange
     * @throws Exception
     * @throws existUserEx
     */
    @Override
    protected void handle2(HttpExchange exchange) throws Exception, UnameOrPwdErrEx {
        String uname=    getRequestParameter(exchange,"uname");
        String pwd=    getRequestParameter(exchange,"pwd");
        login(uname,pwd);

            setcookie("uname",uname,exchange);
            wrtResp(exchange, "ok");

    }


    public static boolean login(String uname, String pwd) throws SQLException, UnameOrPwdErrEx {
        org.hibernate.Session session = OrmUtilBiz.openSession(saveDirUsrs);
        //  om.jdbcurl=saveDirUsrs;
        //todo start tx
        session.beginTransaction();
        Usr u=  session.find(Usr.class,uname);
        session.getTransaction().commit();
        if (u.pwd.equals(pwd))
            return true;
        Usr dto=new Usr();
        dto.uname=uname;
        dto.pwd=pwd;

        UnameOrPwdErrEx e = new UnameOrPwdErrEx("用户名或密码错误");
        e.fun=getCurrentMethodName();
        e.funPrm=new Usr(uname,pwd);

         throw e;
    }


}

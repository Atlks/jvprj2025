package apiUsr;

import apis.BaseHdr;
import com.sun.net.httpserver.HttpExchange;
import entityx.Usr;
import utilBiz.OrmUtilBiz;

import java.sql.SQLException;


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

        Usr u=  session.find(Usr.class,uname);

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

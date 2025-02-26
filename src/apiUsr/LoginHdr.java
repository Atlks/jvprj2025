package apiUsr;

import biz.BaseHdr;
import biz.PwdErrEx;
import biz.existUserEx;
import com.sun.net.httpserver.HttpExchange;
import entityx.Usr;
import jakarta.annotation.security.PermitAll;

import java.sql.SQLException;


import static util.util2026.*;

@PermitAll
//   http://localhost:8889/login?uname=008&pwd=000
public class LoginHdr extends BaseHdr<Usr, Usr> {


    /**
     * @param exchange
     * @throws Exception
     * @throws existUserEx
     */
    @Override
    protected void handle2(HttpExchange exchange) throws Exception, PwdErrEx {
        String uname = getRequestParameter(exchange, "uname");
        String pwd = getRequestParameter(exchange, "pwd");
        login(uname, pwd);

        setcookie("uname", uname, exchange);
        wrtResp(exchange, "ok");

    }


    public boolean login(String uname, String pwd) throws SQLException, PwdErrEx, UserNotExistEx {
        org.hibernate.Session session = sessionFactory.getCurrentSession();
        //  om.jdbcurl=saveDirUsrs;
        //todo start tx

        Usr u = session.find(Usr.class, uname);
        if(u==null)
        {  //u not exist
            UserNotExistEx e = new UserNotExistEx("用户名错误");
            e.fun = getCurrentMethodName();
            e.funPrm = new Usr(uname, pwd);
            throw e;
        }
        if (u.pwd.equals(pwd))
            return true;
        else{
            //pwd not eq
            Usr dto = new Usr();
            dto.uname = uname;
            dto.pwd = pwd;

            PwdErrEx e = new PwdErrEx("密码错误");
            e.fun = getCurrentMethodName();
            e.funPrm = new Usr(uname, pwd);

            throw e;
        }

    }


}

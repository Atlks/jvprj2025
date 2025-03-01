package cfg;

import biz.NeedLoginEx;
import biz.ParseEx;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import jakarta.annotation.security.PermitAll;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import org.aspectj.lang.annotation.Before;
import util.AtProxy4api;
import util.Icall;


import javax.inject.Inject;

import static util.EncryUtil.Key_a1235678;
import static util.EncryUtil.decryptDES;
import static util.Util2025.toExchgDt;
import static util.util2026.getCurrentMethodName;
import static util.util2026.getcookie;

public class AtProxy4apiCfg extends AtProxy4api implements Icall, HttpHandler {
    public AtProxy4apiCfg(Object target) {
        super(target);
    }
    HttpAuthenticationMechanism HttpAuthenticationMechanism1;
   // public  static
@Inject
@Before("")
    public void urlAuthChk(HttpExchange exchange) throws Exception {


//        if (AuthService.needLoginAuth(exchange.getRequestURI()))
        if (needLoginUserAuth((Class<? >) this.getClass())) {
            String uname = getcookie("uname", exchange);
            try{
                uname=decryptDES(uname,Key_a1235678);
            } catch (Exception e) {
                e.printStackTrace();
                ParseEx ex = new ParseEx("token session解析错误");

                ex.fun =this.getClass().getName()+"." + getCurrentMethodName();
                ex.funPrm = toExchgDt((HttpExchange) exchange);



                throw ex;
            }

            if(uname.equals("")){
                NeedLoginEx e = new NeedLoginEx("需要登录");

                e.fun = "BaseHdr." + getCurrentMethodName();
                e.funPrm = toExchgDt((HttpExchange) exchange);



                throw e;
            }



        }


    }



}

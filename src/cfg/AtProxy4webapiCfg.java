package cfg;

import biz.NeedLoginEx;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import jakarta.annotation.security.PermitAll;
import util.AtProxy4webapi;
import util.Icall;

import java.io.IOException;

import static util.Util2025.toExchgDt;
import static util.util2026.getCurrentMethodName;
import static util.util2026.getcookie;

public class AtProxy4webapiCfg  extends AtProxy4webapi implements Icall, HttpHandler {
    public AtProxy4webapiCfg(Object target) {
        super(target);
    }



    public void urlAuthChk(HttpExchange exchange) throws IOException, NeedLoginEx {


//        if (AuthService.needLoginAuth(exchange.getRequestURI()))
        if (needLoginUserAuth((Class<? >) this.getClass())) {
            String uname = getcookie("uname", exchange);
            if(uname.equals("")){
                NeedLoginEx e = new NeedLoginEx("需要登录");

                e.fun = "BaseHdr." + getCurrentMethodName();
                e.funPrm = toExchgDt((HttpExchange) exchange);

                //   addInfo2ex(e, null);

                throw e;
            }



        }


    }

    private boolean needLoginUserAuth(Class<? > aClass) {
        boolean annotationPresent = aClass.isAnnotationPresent(PermitAll.class);

        //if has anno ,not need login
        return !annotationPresent;
    }

}

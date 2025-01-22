package apis;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

import static biz.UserBiz.login;
import static util.util2026.*;

public class LoginHdr  implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String uname=    getRequestParameter(exchange,"uname");
        String pwd=    getRequestParameter(exchange,"pwd");
        if(login(uname,pwd))
        {
            setcookie("uname",uname,exchange);
            wrtResp(exchange, "ok");
        }
    }


}

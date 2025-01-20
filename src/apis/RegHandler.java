package apis;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

import static biz.UserBiz.reg;
import static util.util2026.getRequestParameter;
import static util.util2026.wrtResp;

// 自定义的请求处理器
public class RegHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // 检查请求方法
     //   if ("GET".equals(exchange.getRequestMethod())) {

        String uname=    getRequestParameter(exchange,"uname");
        String pwd=    getRequestParameter(exchange,"pwd");
        System.out.println(uname);

        String responseTxt = "";
        try {
            responseTxt = reg(uname,pwd);
            wrtResp(exchange, responseTxt);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //    }
    }




}

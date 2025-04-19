package cfg;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import util.auth.IsEmptyEx;

import java.io.IOException;

import static util.misc.util2026.*;

// 自定义的请求处理器
public class HelloHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {


        String uname= null;
        try {
            uname = getcookie("uname",exchange);
        } catch (IsEmptyEx e) {
            throw new RuntimeException(e);
        }
        if(uname.equals(""))
    {
        //need login
        wrtResp(exchange, "needLogin");
        return;
    }
        // 检查请求方法
     //   if ("GET".equals(exchange.getRequestMethod())) {

        String id=    getRequestParameter(exchange,"id");
        System.out.println(id);

        String responseTxt = "Hello, this is a REST responseTxt!";
        wrtResp(exchange, responseTxt);
        //    }
    }




}

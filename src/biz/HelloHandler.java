package biz;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

import static util.util2026.*;

// 自定义的请求处理器
public class HelloHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {


    String uname=    getcookie("uname",exchange);
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

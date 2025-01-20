package apis;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

import static util.util2026.getRequestParameter;
import static util.util2026.wrtResp;

// 自定义的请求处理器
public class HelloHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // 检查请求方法
     //   if ("GET".equals(exchange.getRequestMethod())) {

        String id=    getRequestParameter(exchange,"id");
        System.out.println(id);

        String responseTxt = "Hello, this is a REST responseTxt!";
        wrtResp(exchange, responseTxt);
        //    }
    }


}

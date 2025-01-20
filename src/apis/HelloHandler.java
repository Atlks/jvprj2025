package apis;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

import static util.util2026.getRequestParameter;

// 自定义的请求处理器
public class HelloHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // 检查请求方法
     //   if ("GET".equals(exchange.getRequestMethod())) {
            String response = "Hello, this is a REST response!";
        String id=    getRequestParameter(exchange,"id");
        System.out.println(id);
            exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=utf-8");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
    //    }
    }
}

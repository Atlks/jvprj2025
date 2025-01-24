import apis.HelloHandler;
import apis.LoginHdr;
import apis.QueryUsrHdr;
import apis.RegHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class MainApi {
    public static void main(String[] args) throws IOException {
        // 创建 HTTP 服务器，监听端口8080
        HttpServer server = HttpServer.create(new InetSocketAddress(8889), 0);

        // 定义一个上下文，绑定到 "/api/hello" 路径
        server.createContext("/hello", new HelloHandler());
        server.createContext("/reg", new RegHandler());
        server.createContext("/login", new LoginHdr());
        server.createContext("/QueryUsr", new QueryUsrHdr());

        // 启动服务器
        server.setExecutor(null); // 默认的线程池
        server.start();
        System.out.println("http://localhost:8889/QueryUsr");
        System.out.println("Server started on port 8080");
    }


}

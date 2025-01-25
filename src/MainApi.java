import apis.*;
import biz.UserBiz;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Map;

import static util.util2026.parse_ini_fileNosec;

public class MainApi {
    public static void main(String[] args) throws IOException {

        //--------ini saveurlFrm Cfg
        // 获取类加载器 /C:/Users/attil/IdeaProjects/jvprj2025/out/production/jvprj2025/
        String rootPath = UserBiz.class.getClassLoader().getResource("").getPath();
        Map cfg = parse_ini_fileNosec(rootPath + "../../../cfg/dbcfg.ini");
        RegHandler.  saveDirUsrs = (String) cfg.get("saveDirUsrs");
       // saveDirAcc= (String) cfg.get("saveDirAcc");
     //   savedirOrd= (String) cfg.get("savedirOrd");
        QryOrdBetHdr.saveUrlOrdBet= (String) cfg.get("saveUrlOrdBet");


        // 创建 HTTP 服务器，监听端口8080
        int port = 8889;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        // 定义一个上下文，绑定到 "/api/hello" 路径
        server.createContext("/hello", new HelloHandler());
        server.createContext("/reg", new RegHandler());
        server.createContext("/login", new LoginHdr());
        server.createContext("/QueryUsr", new QueryUsrHdr());
        server.createContext("/AddOrdBetHdr", new AddOrdBetHdr());
        server.createContext("/QryOrdBetHdr", new QryOrdBetHdr());

        // 启动服务器
        server.setExecutor(null); // 默认的线程池
        server.start();
        System.out.println("http://localhost:"+port+"/QueryUsr");
        System.out.println("Server started on port 8080");
    }




}

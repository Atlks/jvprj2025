package cfg;

import apiAcc.QueryOrdChrgHdr;
import apiAcc.RechargeHdr;
import apiCms.QryTeamHdr;
import apiOrdBet.QryOrdBetHdr;
import apiUsr.RegHandler;
import apiUsr.UserCentrHdr;
import biz.HelloHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.util.concurrent.Executors;

import static cfg.MyCfg.iniCfgFrmCfgfile;
import static util.SprUtil.getBeanFrmSpr;

public class WebSvr {


    public static void start() throws IOException, SQLException {
        //--------ini saveurlFrm Cfg
        //@NonNull
        iniCfgFrmCfgfile();


        // 创建 HTTP 服务器，监听端口8080
        int port = 8889;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        cfg.IocSpringCfg.iniIocContainr4spr();

        // 定义一个上下文，绑定到 "/api/hello" 路径
        server.createContext("/hello", new HelloHandler());
        // 设置静态资源目录 (例如: D:/myweb/static)
        String staticDir = "C:\\Users\\attil\\IdeaProjects\\jvprj2025\\static";
        server.createContext("/static", new StaticFileHandler(staticDir));
    //    http://localhost:8889/static/doc.htm



        cfgPath(server);
        //  http://localhost:8889/
        // 启动服务器
     //   server.setExecutor(null); // 默认的线程池  单线程
        // 设置 10 线程并发执行
         server.setExecutor(Executors.newFixedThreadPool(20));
      //  server.setExecutor(Executors.newSingleThreadExecutor());//每次新线程

        server.start();
        System.out.println("http://localhost:" + port + "/reg");
        System.out.println("Server started on port "+port);
    }
//    http://localhost:8889/QueryOrdChrgHdr

    //    MutablePicoContainer container = IocPicoCfg.iniIocContainr();
//             AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

    public static void cfgPath(HttpServer server) {


// container.getComponent(RegHandler.class)
        server.createContext("/reg",getBeanFrmSpr(RegHandler.class));
//        server.createContext("/login", getBeanFrmSpr(LoginHdr.class));
//        server.createContext("/QueryUsr",getBeanFrmSpr(QueryUsrHdr.class) );
//        server.createContext("/AddOrdBetHdr",
//                getBeanFrmSpr(AddOrdBetHdr.class));
        server.createContext("/QryOrdBetHdr", new QryOrdBetHdr());
        server.createContext("/QryTeamHdr", new QryTeamHdr());


//        AddOrdBetHdr bean = context.getBean(AddOrdBetHdr.class);


        server.createContext("/rechargeHdr",  getBeanFrmSpr(RechargeHdr.class) );
//        server.createContext("/rechargeHdr",
//                getBeanFrmSpr(RechargeHdr.class)
//        );
        server.createContext("/QueryOrdChrgHdr", new QueryOrdChrgHdr());
        server.createContext("/UserCentrHdr", new UserCentrHdr());
    }

}

import apiAcc.AddOrdChargeHdr;
import apiAcc.QueryOrdChrgHdr;
import apiCms.QryTeamHdr;
import apiOrdBet.AddOrdBetHdr;
import apiOrdBet.QryOrdBetHdr;
import apiUsr.LoginHdr;
import apiUsr.QueryUsrHdr;
import apiUsr.RegHandler;
import apiUsr.UserCentrHdr;
import apis.*;
import com.sun.net.httpserver.HttpServer;
import org.noear.solon.annotation.SolonMain;
import org.picocontainer.MutablePicoContainer;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import static apis.BaseHdr.iniCfgFrmCfgfile;
import static util.IocUtil.iniIocContainr;

@SolonMain
@ComponentScan("apiUsr")
public class MainApi {
    public static void main(String[] args) throws IOException {

        //--------ini saveurlFrm Cfg
        //@NonNull
        iniCfgFrmCfgfile();


        // 创建 HTTP 服务器，监听端口8080
        int port = 8889;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        MutablePicoContainer container = iniIocContainr();
        // 定义一个上下文，绑定到 "/api/hello" 路径
        server.createContext("/hello", new HelloHandler());



        server.createContext("/reg", container.getComponent(RegHandler.class));
        server.createContext("/login", container.getComponent(LoginHdr.class));
        server.createContext("/QueryUsr", new QueryUsrHdr());
        server.createContext("/AddOrdBetHdr", new AddOrdBetHdr());
        server.createContext("/QryOrdBetHdr", new QryOrdBetHdr());
        server.createContext("/QryTeamHdr", new QryTeamHdr());
        server.createContext("/AddOrdChargeHdr", new AddOrdChargeHdr());
        server.createContext("/QueryOrdChrgHdr", new QueryOrdChrgHdr());
        server.createContext("/UserCentrHdr", new UserCentrHdr());


        http:
//localhost:8889/QueryOrdChrgHdr

        //  http://localhost:8889/
        // 启动服务器
        server.setExecutor(null); // 默认的线程池
        server.start();
        System.out.println("http://localhost:" + port + "/QueryUsr");
        System.out.println("Server started on port 8080");
    }

    public static void openMap4test() {

        //  drvMap.put("com.mysql.cj.jdbc.Driver","org.h2.Driver");

    }

    static {
        iniCfgFrmCfgfile();
        //   saveUrlOrdChrg
    }


}

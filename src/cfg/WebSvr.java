package cfg;

import biz.HelloHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Path;
import org.springframework.web.bind.annotation.*;
import util.proxy.AtProxy4api;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.function.Consumer;


import static util.proxy.SprUtil.getBeanFrmSpr;
import static util.misc.util2026.scanAllClass;
import static util.proxy.SprUtil.getBeanByClzFrmSpr;


/**
 * ini web   \n
 * ini url-->bean.handler()  \n
 * ini containr
 */
public class WebSvr {




    public static void startWebSrv() throws IOException {
        int port = 8889;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
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


    // path,hdrClz
    public static Map<String,Class> pathMap=new HashMap<>();
    /**
     * ini rest url wz bean
     * @param server
     */
    public static void cfgPath(HttpServer server) {

        server.createContext("/users/get", exchange -> handleGetUser(exchange));

        server.createContext("/", exchange -> handleAllReq(exchange));



    }

    public static void iniRestPathMap() {
        Consumer<Class> fun=aClass-> {
                if(aClass.getName().startsWith("api"))
                {
                    //  var bean=getBeanFrmSpr(aClass);
                    var path=getPathFromBean(aClass);
//                    System.out.println("cftCtx(path="+path+",bean="+bean.toString());
                 //   server.createContext(path, (HttpHandler) bean);
                    pathMap.put(path,aClass);

                }
        };
        System.out.println("====start createContext");
        scanAllClass(fun);
        System.out.println("====end createContext");
    }

    private static void handleAllReq(HttpExchange exchange) throws IOException {
        URI requestURI = exchange.getRequestURI();
        System.out.println(""+ requestURI);

        @NotNull Class<?>  hdrclas=pathMap.get(requestURI.toString());
        if(hdrclas==null)
            throw  new RuntimeException("key is null,key="+requestURI);
        var  bean= getBeanByClzFrmSpr(hdrclas);
        @NotNull    HttpHandler  proxyObj = new AtProxy4api(bean);
        proxyObj.handle(exchange);

    }

//    server.createContext("/UserCentrHdr", new UserCentrHdr());
    // container.getComponent(RegHandler.class)
    //     server.createContext("/reg",getBeanFrmSpr(RegHandler.class));
    //     server.createContext("/login", getBeanFrmSpr(LoginHdr.class));
//        server.createContext("/QueryUsr",getBeanFrmSpr(QueryUsrHdr.class) );
//        server.createContext("/BetHdr",getBeanFrmSpr(BetHdr.class));
//                //IocSpringCfg.   context. getBean(BetHdr.class));
//        server.createContext("/QryOrdBetHdr", new QryOrdBetHdr());
//        server.createContext("/QryTeamHdr", new QryTeamHdr());


//        AddOrdBetHdr bean = context.getBean(AddOrdBetHdr.class);


    //    server.createContext("/rechargeHdr",  getBeanFrmSpr(RechargeHdr.class) );
//        server.createContext("/rechargeHdr",
//                getBeanFrmSpr(RechargeHdr.class)
//        );
//        server.createContext("/QueryOrdChrgHdr", new QueryOrdChrgHdr());
//
    // 读取类的path注解
    // 读取类的路径注解
    public static String getPathFromBean(Class<?> aClass) {


        if (aClass.isAnnotationPresent(Path.class)) {
            Path mapping = aClass.getAnnotation( Path.class);
            return  mapping.value();  // 可能有多个路径
        }

        // 查找 @RequestMapping（类级别）
        if (aClass.isAnnotationPresent(RequestMapping.class)) {
            RequestMapping mapping = aClass.getAnnotation(RequestMapping.class);
            return String.join(", ", mapping.value());  // 可能有多个路径
        }
        // 兼容 @GetMapping、@PostMapping 等（可选）
        return getPathFromAnnotations(aClass);
    }

    // 处理其他 Spring Mapping 注解
    private static String getPathFromAnnotations(AnnotatedElement element) {
        for (Annotation annotation : element.getAnnotations()) {
            if (annotation instanceof GetMapping) {
                return String.join(", ", ((GetMapping) annotation).value());
            }
            if (annotation instanceof PostMapping) {
                return String.join(", ", ((PostMapping) annotation).value());
            }
            if (annotation instanceof PutMapping) {
                return String.join(", ", ((PutMapping) annotation).value());
            }
            if (annotation instanceof DeleteMapping) {
                return String.join(", ", ((DeleteMapping) annotation).value());
            }
        }
        return "/defPathhhhhh";
    }


    private static void handleGetUser(HttpExchange exchange) {
    }

}

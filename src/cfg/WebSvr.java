package cfg;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Path;
import org.springframework.web.bind.annotation.*;
//import org.thymeleaf.context.Context;
import org.thymeleaf.context.Context;
import util.annos.Paths;
import util.serverless.ApiGateway;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.function.Consumer;


import static ztest.htmlTppltl.rend;
import static util.algo.JarClassScanner.getPrjPath;
import static util.algo.JarClassScanner.getTargetPath;
import static util.algo.NullUtil.isBlank;
import static util.misc.PathUtil.getDirTaget;
import static util.misc.util2026.*;
import static util.oo.WebsrvUtil.processNmlExptn;

import static util.proxy.SprUtil.getBeanFrmSpr;
import static util.proxy.SprUtil.getBeanByClzFrmSpr;


/**
 * ini web   \n
 * ini url-->bean.handler()  \n
 * ini containr
 */
public class WebSvr {


    public static void startWebSrv() throws Exception {
        int port = 8889;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        // 定义一个上下文，绑定到 "/api/hello" 路径
        server.createContext("/hello", new HelloHandler());


        // 设置静态资源目录 (例如: D:/myweb/static)
      //  String dirWzClassesDirSameLev = "static";
        String docRestApiDir = getdirRestapiDoc();
        System.out.println("docRestApiDir="+docRestApiDir);

        server.createContext("/static22", new StaticFileHandler(docRestApiDir+"/aa","/static"));
        server.createContext("/static", new StaticFileHandler(docRestApiDir,"/static"));
        server.createContext("/docRestApi", new StaticFileHandler(docRestApiDir,"/docRestApi"));

        server.createContext("/res/uploads", new StaticFileHandler(getPrjPath()+"/res/uploads", "/res/uploads"));
        //    http://localhost:8889/static/doc.htm
        server.createContext("/post2", exchange -> {
            try {
                handlePost2(exchange);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        server.createContext("/", exchange -> handleAllReq(exchange));
        //-------------------
        cfgPath(server);
        //  http://localhost:8889/
        // 启动服务器
        //   server.setExecutor(null); // 默认的线程池  单线程
        // 设置 10 线程并发执行
        server.setExecutor(Executors.newFixedThreadPool(20));
        //  server.setExecutor(Executors.newSingleThreadExecutor());//每次新线程

        server.start();
        System.out.println("http://localhost:" + port + "/reg");
        System.out.println("Server started on port " + port);
    }

    private static void handlePost2(HttpExchange exchange) throws Exception {
        if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
        
            // body:::     a=1&b=2

 // 读取请求体
 InputStream inputStream = exchange.getRequestBody();
 String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);


            // 解析 form 数据
            Map<String, String> params = parseFormData(body);
            String response = "Received: a=" + params.get("a") + ", b=" + params.get("b");

            // 发送响应
            exchange.sendResponseHeaders(200, response.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // 方法不允许
        }
    }

    private static Map<String, String> parseFormData(String body) throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap<>();
        for (String pair : body.split("&")) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                params.put(URLDecoder.decode(keyValue[0], "UTF-8"),
                        URLDecoder.decode(keyValue[1], "UTF-8"));
            }
        }
        return params;
    }

    @org.jetbrains.annotations.NotNull
    private static String getdirRestapiDoc() throws URISyntaxException {
        String dirTaget = getDirTaget();
        String staticDir = "C:\\Users\\attil\\IdeaProjects\\jvprj2025\\static";

        String docRestDir = "/docRestApi/";
        String prjDirMode = getPrjPath() + docRestDir;
        // if(new File("/staticSrc").exists())
        //     staticDir = "C:\\0prj\\jvprj2025\\static";
        String targetDirMode = getTargetPath() + docRestDir;
        if(isExistDir(prjDirMode))
        {            staticDir=prjDirMode;
        } else if(isExistDir(targetDirMode))
        {            staticDir=prjDirMode;
        }
        else {
            dirTaget = getDirTaget();
            staticDir = dirTaget + docRestDir;
        }
        return staticDir;
    }

    private static boolean isExistDir(String prjDirMode) {
   return  new File(prjDirMode).exists();
    }


//    http://localhost:8889/QueryOrdChrgHdr

    //    MutablePicoContainer container = IocPicoCfg.iniIocContainr();
//             AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);


    // path,hdrClz
    public static Map<String, Class> pathMap = new HashMap<>();

    /**
     * ini rest url wz bean
     *
     * @param server
     */
    public static void cfgPath(HttpServer server) {

        server.createContext("/users/get", exchange -> handleGetUser(exchange));




    }

    public static void iniRestPathMap() {
        Consumer<Class> fun = aClass -> {
            if (aClass.getName().startsWith("api")|| aClass.getName().startsWith("handler")) {
                //  var bean=getBeanFrmSpr(aClass);
                var path = getPathFromBean(aClass);
                System.out.println("pathMap(path=" + path + ",aClass=" + aClass.toString());
                //   server.createContext(path, (HttpHandler) bean);
                pathMap.put(path, aClass);
                if(aClass.getName().contains("RechargeHdr"))
                    System.out.println("D835");
                var path_pkgNclsname=getAutoRouterPath(aClass);
                pathMap.put(path_pkgNclsname, aClass);
                System.out.println("pathMap(path=" + path_pkgNclsname + ",aClass=" + aClass.toString());

                String[] getPathsFromBeanRzt=getPathsFromBean(aClass);
    for (String p : getPathsFromBeanRzt) {

        pathMap.put(p, aClass);
        System.out.println("pathMap(path=" + p + ",aClass=" + aClass.toString());

    }
            }
        };
        System.out.println("====start createContext");
        scanAllClass(fun);
        System.out.println("====end createContext");
    }

    /**
     * 获取自动化的路由路径，规则: 上一级包名/类名
     * 例如  /role/SaveRoleHdl
     * @param aClass 要处理的类
     * @return 自动生成的路由路径
     */
    public static String getAutoRouterPath(Class<?> aClass) {
        if (aClass == null) {
            return "";
        }

        Package pkg = aClass.getPackage();
        String packageName = pkg != null ? pkg.getName() : "";
        String[] parts = packageName.split("\\.");
        String lastPkg = parts.length > 0 ? parts[parts.length - 1] : "";
        return "/" + lastPkg + "/" + aClass.getSimpleName();
    }


    private static void handleAllReq(@NotNull HttpExchange exchange) throws IOException {
        try {
            URI requestURI = exchange.getRequestURI();
            System.out.println("" + requestURI);

            @NotNull String path1 = getPathNoQuerystring(exchange);
            if (isBlank(path1))
                throw new RuntimeException("path is blnk");
            if(path1.equals("/favicon.ico"))
                return;
            if(path1.endsWith(".htm")  || path1.endsWith(".html"))
            {
                Context context = new Context();

                //listAdm
                String tmpleFileName = path1.substring(0,path1.length()-4);

                if(path1.endsWith(".html"))
                    tmpleFileName = path1.substring(0,path1.length()-5);

                var rsp=rend(tmpleFileName, context );
                wrtRespHtml(exchange,rsp);
                return;
            }

            @NotNull Class<?> hdrclas = pathMap.get(path1);
            if (hdrclas == null)
                throw new RuntimeException("key is null,key=" + requestURI);
            var bean = getBeanByClzFrmSpr(hdrclas);
            @NotNull HttpHandler proxyObj = new ApiGateway(bean);
            proxyObj.handle(exchange);
        } catch (Exception e) {
            printLn("---------------statt epr int  ");
            e.printStackTrace();
            printLn("---------------endstatt eprint");
            processNmlExptn(exchange, e);
        }


    }

    /**
     * 获取path ，不要带Querystring
     *
     * @param exchange
     * @return
     */
    private static String getPathNoQuerystring(HttpExchange exchange) {
        URI uri = exchange.getRequestURI();
        String path = uri.getPath();
        return path;
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
            Path mapping = aClass.getAnnotation(Path.class);
            return mapping.value();  // 可能有多个路径
        }

        // 查找 @RequestMapping（类级别）
        if (aClass.isAnnotationPresent(RequestMapping.class)) {
            RequestMapping mapping = aClass.getAnnotation(RequestMapping.class);
            return String.join(", ", mapping.value());  // 可能有多个路径
        }
        // 兼容 @GetMapping、@PostMapping 等（可选）
        return getPathFromAnnotations(aClass);
    }

    public static String[] getPathsFromBean(Class<?> aClass) {


        if (aClass.isAnnotationPresent(Paths.class)) {
            Paths mapping = aClass.getAnnotation(Paths.class);
            assert mapping != null;
            return mapping.value();  // 可能有多个路径
        }

        return  new String[]{};


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

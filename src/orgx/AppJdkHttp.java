package orgx;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import io.javalin.http.ExceptionHandler;
import io.javalin.http.Handler;
import org.hibernate.SessionFactory;
import orgx.rest.JwtAuthenticator;
import orgx.u.Dto;
import orgx.u.USvs;
import orgx.uti.context.ProcessContext;
import orgx.uti.http.HttpUti;
import orgx.uti.http.RegMap;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.*;
import java.io.IOException;
import java.util.Map;

import static orgx.rest.AopFltr.newAopFltr;
import static orgx.CfgSvs.getSessionFactory;
import static orgx.uti.Uti.*;
import static orgx.uti.http.HttpUti.*;
import static orgx.uti.http.RegMap.registerMapping;


/**
 * 拦截错误
 * 你可以创建一个 通用拦截器，并添加到所有 createContext()：
 * 方法 2：使用单一 HttpHandler
 */
public class AppJdkHttp {
    public static void main(String[] args) throws IOException, NoSuchMethodException {

        //ini conn,cfg
        SessionFactory sessionFactory = getSessionFactory();
        HttpServer server = HttpServer.create(new InetSocketAddress(7070), 0);
        ProcessContext.httpServer = server;

        // @SneakyThrows
        setExit(server);

        // 示例路由
        /**
         *  registerMapping(String path, HttpHandler handler);
         *   registerMapping(String path, FunctionX<T, Object> fun)
         *   registerMapping(String path, Method m)
         *   registerMapping(String path, FaasHdler )
         *
         */
        server.createContext("/e", ctx -> {
            throw new RuntimeException("测试异常");
        }).getFilters().add(newAopFltr());
        ;

        //scan auth map


        //scan for annos from clzs...
        //regmapIcall(path,
        //recmap.faasItfs
        //recmap.faasNoItfs
        ///regmap.fun  n  method
        registerMapping("/sv", USvs::SvUHdl,server);
        registerMapping("/txt", USvs::TxtHdl,server);


        // scan add to auth map
        Method m3=USvs.class.getDeclaredMethod("auth",Object.class);
        m3.setAccessible(true);
        Annotation[] annos= m3.getAnnotations();
        ProcessContext.authMap.put("/auth",annos);
        registerMapping("/auth",USvs::auth,server);

        //-----------mthd
        // if method
        Method m4=USvs.class.getDeclaredMethod("Hdl1", Dto.class);
        registerMapping("/m",m4,server);
        Object cls=USvs.class;
        if(cls instanceof Class<?>){
            RegMap._registerMapping("/c", (Class) cls,server);
        }




        //---------set hdl ex auth ,
        Handler handlerBefore = null;
        ExceptionHandler<Exception> exceptionExceptionHandler = null;
        ProcessContext.excptnHdlrInWeb=exceptionExceptionHandler;
        ProcessContext.excptnHdlr= (e, ctx) -> {
            printStackTrace(e);
           throwX(e);
        };
        ProcessContext.authenticator=new JwtAuthenticator();


        // not use the all path mode 创建通用请求处理
        HttpHandler httpHandler = (HttpExchange exchange) -> {
            System.out.println("fun hdlAll");
            HttpUti.setThrdHttpContext(exchange);

            String path = exchange.getRequestURI().getPath();
            Method m = null;//hdlr
            try {
                RegMap.registerMapping(path,m, exchange);
            } catch (Throwable e) {
                 throwX(e);
            }

            System.out.println("endfun hdlAll");
        };
        server.createContext("/", httpHandler).getFilters().add(newAopFltr());
      //   registerMappingHttpHdlr("/",httpHandler);

        server.setExecutor(null);

        server.start();
        System.out.println("服务器已启动：访问 http://localhost:8080/hello 或 /status");
    }






//        server.setExecutor(command -> {
//            try {
//                command.run();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
    // 示例路由
//        server.createContext("/t", ctx -> {
//          //  HttpExchange ctx=   currHttpExchange.get();
//            if (ctx != null) {
//                byte[] response = s.getBytes(StandardCharsets.UTF_8);
//                ctx.sendResponseHeaders(200, response.length);
//                try (OutputStream os = ctx.getResponseBody()) {
//                    os.write(response);
//                }
//            }
//        });



    //            Method method = fun.getLambdaMethod();
//            method.setAccessible(true);
//            System.out.println(method);
//            System.out.println(method.getDeclaringClass());
//          //  if (hasAnno(Produces.class,method)) {
//                Produces prodsAnnotation = method.getAnnotation(Produces.class);
//                  cttType = prodsAnnotation.value()[0];
//         //   }

//            //  ctx.res.setCharacterEncoding("UTF-8");
//            Method m = fun.getLambdaMethod();
//            if (m.isAnnotationPresent(Produces.class)) {
//                Produces prodsAnnotation = m.getAnnotation(Produces.class);
//                String cttType = prodsAnnotation.value()[0];
//                setContentType(cttType + "; charset=UTF-8");
//                ctx_result(encodeJson(rzt));
//            } else {
//                setContentType(MediaType.APPLICATION_JSON + "; charset=UTF-8");
//                ctx_result(rzt.toString());
//            }


    private static void setExit(HttpServer server) {
        server.createContext("/exit", (HttpExchange exchange) -> {
            System.out.println(exchange.getRequestURI());
            Map<String, List<String>> paramMap = parseQueryParams(exchange);

            ExitDto dto = null;

            String pwd = getKeyFrmMp(paramMap, "pwd").orElse("000");
            if (pwd.equals("888")) {
                server.stop(0); // 优雅关闭服务器
                System.exit(0);
            }
        });
    }


    // 创建通用请求处理
//        server.createContext("/", exchange -> {
//            String path = exchange.getRequestURI().getPath();
//            FunctionX handler = routes.get(path);
//
//            String response = (handler != null) ? handler.handle() : "404 Not Found";
//            exchange.sendResponseHeaders(handler != null ? 200 : 404, response.getBytes().length);
//            try (OutputStream os = exchange.getResponseBody()) {
//                os.write(response.getBytes());
//            }
//        });

    // 绑定多个静态方法到路由
//        Map<String, FunctionX> routes = Map.of(
//                "/hello", FunctionalServer::helloWorld,
//                "/status", FunctionalServer::serverStatus
//        );


//    public static String helloWorld() {
//        return "你好，函数式接口!";
//    }
//
//    public static String serverStatus() {
//        return "{\"status\": \"running\"}";
//    }
}

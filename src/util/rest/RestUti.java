package util.rest;

import cfg.BreakToRet;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import jakarta.validation.constraints.NotNull;
import model.other.ContentType;
import util.model.Context;
import util.serverless.ApiGatewayResponse;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

import static util.algo.ToXX.toDtoFrmHttp;
import static util.misc.Util2025.encodeJson;
import static util.misc.util2026.setCrossDomain;
import static util.misc.util2026.wrtResp;
import static util.serverless.ApiGatewayResponse.createErrResponse;

public class RestUti {
    public static void setExecutorNewThrdAlwys(HttpServer httpServer) {
        httpServer.setExecutor( new ThreadPoolExecutor(
                0,                 // corePoolSize
                999,                // maximumPoolSize
                0L, TimeUnit.SECONDS,
                new SynchronousQueue<>()
        ));
    }

    public static void regMapOptionn(HttpExchange exchange) throws BreakToRet {
        String method = exchange.getRequestMethod();
        if ("OPTIONS".equalsIgnoreCase(method)) {
            try {
                handleOptions(exchange);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            throw new BreakToRet();
        }

    }
    public static void handleOptions(@NotNull HttpExchange exchange) throws Exception {
        setCrossDomain(exchange);
        exchange.getResponseHeaders().add("Allow", "GET, POST, PUT, DELETE, OPTIONS");


        //返回状态码 204（无内容）是标准做法。
        exchange.sendResponseHeaders(204, -1); // No Content
        exchange.close();
    }
    public  static ThreadLocal<Class> dtoClz=new ThreadLocal<>();

    public  static ThreadLocal<Context> contextThdloc=new ThreadLocal<>();

    public  static HttpServer httpSvr;
 //   public static FaasContext contextThdloc;

    /**
     * 设置路由,强制规范了使用rqdto，和返回dto
     * @param path
     * @param dtoClz
     * @param handlerFun
     * @param server
     */
    public static <T> void createContext4rest(String path, Class<T> dtoClz, Function<T, Object> handlerFun, HttpServer server) {
        System.out.println("\n\n\nfun createContext4rest(path="+path+",dtoClz="+dtoClz.getName()+",hdlr,svr)))");
        HttpHandler httpHandler = exchange -> {
            System.out.println("url="+exchange.getRequestURI());

            String respTxt = "";
            try {
                T dtoTmp =toDtoFrmHttp(exchange,dtoClz);
                Object respOBj = handlerFun.apply(dtoTmp);
                respTxt = encodeJson(new ApiGatewayResponse(respOBj));
            } catch (Exception e) {
                respTxt=encodeJson(createErrResponse(e));
            }
            wrtResp(exchange, respTxt, ContentType.APPLICATION_JSON.getValue());
            System.out.println("endfun createContext4rest()");
        };
        server.createContext(path, httpHandler);

    }

//    public static <T> void createContext4rest(@NotBlank String path, Class<T> queryDtoClass,@NotNull Object hdl9Fun) {
//    }


    // 处理带参数的 handler
    public static <T> void createContext4rest(String path, Class<T> dtoClz, Function<T, Object> handlerFun) {
        System.out.println("注册带参数路径：" + path + " 对应处理函数：" + handlerFun);


        HttpHandler httpHandler = exchange -> {
            System.out.println("url="+exchange.getRequestURI());

            String respTxt = "";
            try {
                T dtoTmp =toDtoFrmHttp(exchange,dtoClz);
                Object respOBj = handlerFun.apply(dtoTmp);
                respTxt = encodeJson(new ApiGatewayResponse(respOBj));
            } catch (Exception e) {
                respTxt=encodeJson(createErrResponse(e));
            }
            wrtResp(exchange, respTxt, ContentType.APPLICATION_JSON.getValue());
            System.out.println("endfun createContext4rest()");
        };
        httpSvr.createContext(path, httpHandler);
    }

    // 处理不带参数的 handler
    public static void createContext4rest(String path, Supplier<Object> handler) {
        System.out.println("注册无参数路径：" + path + " 对应处理函数：" + handler);


        HttpHandler httpHandler = exchange -> {
            System.out.println("url="+exchange.getRequestURI());

            String respTxt = "";
            try {

                Object respOBj = // 模拟调用
                        handler.get();
                respTxt = encodeJson(new ApiGatewayResponse(respOBj));
            } catch (Exception e) {
                respTxt=encodeJson(createErrResponse(e));
            }
            wrtResp(exchange, respTxt, ContentType.APPLICATION_JSON.getValue());
            System.out.println("endfun createContext4rest()");
        };
        httpSvr.createContext(path, httpHandler);
    }
}

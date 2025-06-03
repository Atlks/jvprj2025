package orgx.uti.http;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import orgx.uti.Uti;
import orgx.uti.context.ProcessContext;
import orgx.uti.orm.FunctionX;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import static org.springframework.util.ClassUtils.hasMethod;
import static orgx.rest.AopFltr.newAopFltr;
import static orgx.uti.Uti.*;
import static orgx.uti.http.HttpUti.*;
import static orgx.uti.http.HttpUti.toDto;
import static orgx.uti.orm.TxMng.callInTransaction;

public class RegMap {
    public static void registerMapping
            (String path, Class clz, HttpServer httpServer) throws IOException {


        HttpHandler handler = ctx -> {

            System.out.println("fun registerMapping(p=" + path + ",clz=" + clz + ")");
            setThrdHttpContext(ctx);
            try {
                _registerMapping(path, clz, ctx);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            } finally {
                ctx.close();
            }
            //not need close ctx.alread close in fltr

            System.out.println("endfun registerMapping");
        };
        //  httpServer.cre
        HttpContext context = httpServer.createContext(path, handler);
        // context.setHandler(handler);
        // com.sun.net.httpserver.HttpContext.setAuthenticator()
        //   context.setAuthenticator(new JwtAuthenticator());
        //   ProcessContext.httpContexts.add(context);
        context.getFilters().add(newAopFltr());


    }

    public static void registerMapping
            (String path, Method m, HttpServer httpServer) throws IOException {


        HttpHandler handler = ctx -> {

            System.out.println("fun registerMapping(p=" + path + ",fun=" + m + ")");
            setThrdHttpContext(ctx);
            try {
                _registerMapping(path, m, ctx);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            } finally {
                ctx.close();
            }
            //not need close ctx.alread close in fltr

            System.out.println("endfun registerMapping");
        };
        //  httpServer.cre
        HttpContext context = httpServer.createContext(path, handler);
        // context.setHandler(handler);
        // com.sun.net.httpserver.HttpContext.setAuthenticator()
        //   context.setAuthenticator(new JwtAuthenticator());
        //   ProcessContext.httpContexts.add(context);
        context.getFilters().add(newAopFltr());


    }

    @Deprecated
    public static void _registerMapping
            (String path, Class clz, HttpExchange httpExchange) throws Throwable {
        System.out.println("fun _registerMapping(p=" + path + ",m=" + clz + ")");
        String fname = clz.getName();
        Object rzt = null;

        if (hasMethod2("hdlll")) {
            Method m = clz.getMethod("hdll");
            _registerMapping(path, m, httpExchange);
            return;
        }


        boolean isImplementedIcall = Icall.class.isAssignableFrom(clz);
        boolean isImplementedHdlFaas = HttpHdlFaas.class.isAssignableFrom(clz);
        if (isImplementedIcall) {
            Class<?> dtoClz = null;
            Object dto = toDto(httpExchange, dtoClz);
            valdt(dto);
            rzt = ((Icall) newObjx(clz)).call(dto);
        } else if (isImplementedHdlFaas) {
            Class<?> dtoClz = null;
            Object dto = toDto(httpExchange, dtoClz);
            valdt(dto);
            rzt = ((HttpHdlFaas) newObjx(clz)).hdl(dto);
        }


        String mediaType = getMediaType(clz, Produces.class);
        if (MediaType.TEXT_PLAIN.equals(mediaType)) {
            writeTxt(rzt.toString(), httpExchange);
        } else if (mediaType.equals("image/png")) {
            // writeImg(rzt,httpExchange);
        } else
            writeJson(rzt, httpExchange);

        System.out.println("endfun _registerMapping");


    }

    private static boolean hasMethod2(String hdlll) {
        return false;
    }

    private static Object newObjx(Class clz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return clz.getConstructor().newInstance();
    }

    public static void _registerMapping
            (String path, Method m, HttpExchange httpExchange) throws Throwable {
        System.out.println("fun _registerMapping(p=" + path + ",m=" + m + ")");
        String fname = m.getName();
        Object rzt;
        if (m.getParameterTypes().length == 0) {
            rzt = callMthd(m);
        } else {
            Class<?> dtoClz = m.getParameterTypes()[0];
            Object dto = toDto(httpExchange, dtoClz);
            valdt(dto);
            rzt = callMthd(m, dto);
        }

        String mediaType = getMediaType(m, Produces.class);
        if (MediaType.TEXT_PLAIN.equals(mediaType)) {
            writeTxt(rzt.toString(), httpExchange);
        } else if (mediaType.equals("image/png")) {
            // writeImg(rzt,httpExchange);
        } else
            writeJson(rzt, httpExchange);
        System.out.println(path);
        System.out.println("endfun _registerMapping");
    }


    public static <T> void createContext(String path, FunctionX<T, Object> fun) {
        registerMapping(path, fun, ProcessContext.httpServer);
    }

    /**
     * deflt is jvlin
     *
     * @param path
     * @param fun
     * @param <T>
     */
    public static <T> void registerMapping(String path, FunctionX<T, Object> fun
    ) {
        //get raw fun(dto    name,type .to aop log

        Javalin javalinApp = ProcessContext.appJvl;
        Handler handler = ctx -> {

            Object rzt = null;
            System.out.println("fun setHttpGetHdl(p=" + path + ",fun=" + fun + ")");
            setThrdHttpContext(ctx);
            Class lambdaMethodParamFirstType = fun.getLambdaMethodParamFirstType();
            System.out.println("fun setHttpGetHdl(p=" + path + ",fun=" + fun.getLambdaMethodName() + "<" + lambdaMethodParamFirstType.getName() + ">");
            Context ct1 = (Context) ctx;
            //  ctx.map
            Map<String, List<String>> mp = ct1.queryParamMap();
            //auto tx ,commit n  roolback
            rzt = callInTransaction(em -> {
                T dto = (T) Uti. toDto(mp, lambdaMethodParamFirstType);
                valdt(dto);
                System.out.println("fun " + fun.getLambdaMethodName() + "(" + encodeJson(dto));
                Object apply = fun.apply(dto);
                System.out.println("endfun " + fun.getLambdaMethodName());
                return apply;
            });


            //  ctx.res.setCharacterEncoding("UTF-8");
            ctx.contentType("text/plain; charset=UTF-8");
            ctx.result("Hello, Javalin! rzt=" + rzt);
            System.out.println("endfun setHttpGetHdl");
        };

        javalinApp.get(path, handler);
    }


    public static void registerMappingHttpHdlr(String path, HttpHandler httpHandler) {
        ProcessContext.httpServer.createContext("/", httpHandler);
    }

    /**
     * @param path
     * @param fun
     * @param <T>
     */
    public static <T> void registerMapping(String path, FunctionX<T, Object> fun, HttpServer httpServer) {
        //get raw fun(dto    name,type .to aop log


        HttpHandler handler = httpExchange -> {

            //  System.out.println("fun setHttpGetHdl(p=" + path + ",fun=" + fun + ")");
            setThrdHttpContext(httpExchange);
            try {
                _registerMapping(path, fun, httpExchange);
            } catch (Throwable e) {
                throwX(e);
            }


            // System.out.println("endfun setHttpGetHdl");
        };

        httpServer.createContext(path, handler).getFilters().add(newAopFltr());
        ;
    }

    private static <T> void _registerMapping(String path, FunctionX<T, Object> fun, HttpExchange httpExchange) throws Throwable {
        Class lambdaMethodParamFirstType = fun.getLambdaMethodParamFirstType();
        String fname = fun.getLambdaMethodName();
        System.out.println("fun _registerMapping(p=" + path + ",fun=" + fname + "<" + lambdaMethodParamFirstType.getName() + ">");


        Object rzt = callFun(fun, lambdaMethodParamFirstType, fname, httpExchange);

        write(rzt, httpExchange);
        System.out.println("endfun _registerMapping");
    }

}

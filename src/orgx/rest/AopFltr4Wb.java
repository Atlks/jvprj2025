package orgx.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;
import io.javalin.http.ExceptionHandler;
import io.javalin.http.Handler;
import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.NotNull;
import orgx.uti.context.ThreadContext;
import orgx.uti.orm.FunctionX;
import orgx.uti.orm.TxMng;

import java.net.URI;

import static cfg.WebSvr.*;
import static orgx.rest.AopFuns.setExceptionHandler;
import static util.algo.NullUtil.isBlank;
import static util.misc.Util2025.readTxtFrmFil;
import static util.misc.util2026.*;
import static util.oo.WebsrvUtil.processNmlExptn;
import static util.rest.RestUti.handleOptions;

public class AopFltr4Wb extends Filter {

    @Override
    public void doFilter(HttpExchange httpExchange, Chain chain) throws JsonProcessingException {
        System.out.println("\n\n\nfun aop fltr");


        try {
    //------------pre process option
            String method = httpExchange.getRequestMethod();
            if ("OPTIONS".equalsIgnoreCase(method)) {
                handleOptions(httpExchange);
                return;
            }
            setCrossDomain(httpExchange);
            URI requestURI = httpExchange.getRequestURI();
            System.out.println("" + requestURI);

            @NotNull String path1 = getPathNoQuerystring(httpExchange);
            if (isBlank(path1))
                throw new RuntimeException("path is blnk");

            if (path1.endsWith(".htm") || path1.endsWith(".html")) {
                //  Context context = new Context();
                var rsp=readTxtFrmFil(webroot+path1);
                wrtRespHtml(httpExchange,rsp);
                return;
            }

            //---------------auth

            //-------------tx

            chain.doFilter(httpExchange); // 继续执行请求

        } catch (Throwable e) {
            printEx(  e);
            processNmlExptn(httpExchange, e);
           // setExceptionHandler(exchange, (Exception) e);
        } finally {
          //  exchange.close();

            httpExchange.close(); // ⚠️ 必须调用，否则会卡住
            //cls http conn
            TxMng.closeConn();
            ThreadContext.remoteUser.set(null);
            //rmt user thrd 可能复用，需要不能复用
        }
        System.out.println("endfun aopFltr");
    }

    public static void printEx(Throwable e)     {
        printLn("---------------statt epr int  ");
        e.printStackTrace();
        System.err.flush();
        try {
            Thread.sleep(20);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        printLn("---------------endstatt eprint");
    }
    /**
     * auth,tx ,log
     *
     * @param exchange
     * @param chain
     * @throws Throwable
     */
    private void doFlt(HttpExchange exchange, Chain chain) throws Throwable {
        if (ThreadContext.beforeHdl.get() != null)
            ThreadContext.beforeHdl.get().handle(new ContextImp4sdkwb());
        Handler handlerBefore = ctx -> {
            //适用于 日志记录、身份验证、
            //
            // 请求预处理。option请求
            // System.out.println("\n\n\n请求前拦截：" + ctx.path());
            /**
             * 请求前拦截：/sv
             * fun startHttpContextProcs  .....
             */
            // aop log,    bef fun exe ,chklst
            //  HttpExchange exchange=ThreadContext.currHttpExchange.get();
            System.out.println(exchange.getRequestURI() + ",start bef exeFun chklst ");

            //auth  use setAuthenticator
            new JwtAuthenticator().authenticateX(exchange);
        };
        //  ThreadContext.beforeHdl.set(handlerBefore);

        handlerBefore.handle(new ContextImp4sdkwb());

        //  tx
        callInTransaction(exchange, chain);
    }


    @Override
    public String description() {
        return "全局异常处理过滤器";
    }


    /**
     * err,log.tx,auth
     *
     * @return
     */
    public static Filter newAopFltr() {
        Handler handlerBefore = null;
        ExceptionHandler<Exception> exceptionExceptionHandler = null;
        AopFltr4Wb aopFltr = new AopFltr4Wb();
        aopFltr.before(handlerBefore);
        aopFltr.setAuthenticator(new JwtAuthenticator());
        aopFltr.exception(exceptionExceptionHandler);

        return aopFltr;
    }

    private void setAuthenticator(JwtAuthenticator jwtAuthenticator) {
    }

    private void exception(ExceptionHandler<Exception> exceptionExceptionHandler) {
    }

    private void before(Handler handlerBefore) {
    }

    public static void callInTransaction(HttpExchange exchange, Chain chain) {
        FunctionX<EntityManager, Object> entityManagerObjectFunctionX = em -> {


            //here exe auth chk in sdk wb
            /**
             * ✅ Filter 先执行 → 过滤器用于 日志记录、参数解析、请求修改 等预处理操作。 ✅ Authenticator 后执行 → 认证器用于 身份验证，决定请求是否被允许访问资
             * nt gd,bcs begin tx already start...
             */
            chain.doFilter(exchange); // 继续执行请求

            return null;
        };
        TxMng.callInTransaction(entityManagerObjectFunctionX);
    }
}

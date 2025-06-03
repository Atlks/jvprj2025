package orgx.rest;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;
import io.javalin.http.ExceptionHandler;
import io.javalin.http.Handler;
import jakarta.persistence.EntityManager;
import orgx.uti.context.ThreadContext;
import orgx.uti.orm.FunctionX;
import orgx.uti.orm.TxMng;

import static orgx.rest.AopFuns.*;

public class AopFltr extends Filter {

    @Override
    public void doFilter(HttpExchange exchange, Chain chain) {
        System.out.println("\n\n\nfun aop fltr");


        try {

            doFlt(exchange, chain);

        } catch (Throwable e) {
            setExceptionHandler(exchange, (Exception) e);
        } finally {
            exchange.close();
            //cls http conn
            TxMng.closeConn();
        }
        System.out.println("endfun aopFltr");
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
            //适用于 日志记录、身份验证、请求预处理。
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
        AopFltr aopFltr = new AopFltr();
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

    public static void callInTransaction(HttpExchange exchange, Filter.Chain chain) {
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

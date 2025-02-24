package biz;

import org.hibernate.Session;

import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ThreadLocalSessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.AuthService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import entityx.ExceptionBase;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

//import static apiAcc.TransHdr.saveUrlLogBalanceYinliWlt;

import static util.ColorLogger.*;
import static util.ExptUtil.addInfo2ex;
import static util.ExptUtil.curUrl;
import static util.TransactMng.commitTransactIfActv;
import static util.TransactMng.commitTransaction;

import static util.Util2025.*;
import static util.dbutil.setField;
import static util.util2026.*;

/**
 * aop  some log....aop auth ,,aop ex
 * 但如果你的需求是 基于抽象基类 来做 AOP，这种方式已经足够好用。
 * 模板方法模式（Template Method Pattern） 的思路。
 * aop
 * 日志（打印执行信息）
 * 权限校验（检查用户身份）
 * 异常处理（捕获异常并记录）
 * 事务
 */
@Component
public abstract class BaseHdr implements HttpHandler, Serializable {

    // 实现 Serializable 接口
    public static final long serialVersionUID = 1L; // 推荐加
    @Autowired
    public SessionFactory sessionFactory;


    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    ;

    //----------aop ex  and some log part
    //事务管理  全局异常
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        //wz qrystr
        //  printlnx();
        String mth = colorStr("handle", YELLOW_bright);
        String prmurl = colorStr(String.valueOf(exchange.getRequestURI()), GREEN);
        curUrl.set(encodeJson(exchange.getRequestURI()));
        System.out.println("▶\uFE0Ffun " + mth + "(url=" + prmurl);
        //   curUrlPrm.set(exchange.getrequ);
        var responseTxt="";
        ExceptionBase ex = new ExceptionBase("");
        try {
            //   setcookie("uname", "007", exchange);//for test

            //---------blk chk auth
            urlAuthChk(exchange);


            AopTtransActNlog(exchange);


            //      System.out.println("endfun handle2()");
            System.out.println("✅endfun handle()");
            return;

        } catch (java.lang.reflect.InvocationTargetException e) {
              responseTxt = processInvkExpt(exchange, e);

        } catch (Throwable e) {

              responseTxt =   processNmlExptn(exchange, e);
            // throw new RuntimeException(e);

        }
        //end catch

        //not ex ,just all ok blk
        //ex.fun  from stacktrace
        System.out.println("\uD83D\uDED1 endfun handle().ret=" + responseTxt);
    }

    private static String processNmlExptn(HttpExchange exchange, Throwable e)   {
        ExceptionBase ex;
//        System.out.println(
//                "⚠\uFE0F e="
//                        + e.getMessage() + "\nStackTrace="
//                        + getStackTraceAsString(e)
//                        + "\n end stacktrace......................"
//        );


        //my throw ex.incld funprm
        if (e instanceof ExceptionBase) {
            ex = (ExceptionBase) e;
            ex.errcode = e.getClass().getName();


        } else {
            //nml err
            ex = new ExceptionBase(e.getMessage());

            //cvt to cstm ex
            String message = e.getMessage();
            ex = new ExceptionBase(message);
            ex.cause = e;
            ex.errcode = e.getClass().getName();

        }

        addInfo2ex(ex, e);

        String responseTxt = encodeJson(ex);

        wrtRespErrNoex(exchange, responseTxt);
        return responseTxt;
    }

    private static String processInvkExpt(HttpExchange exchange, InvocationTargetException e) throws IOException {
        ExceptionBase ex;
        ex = new ExceptionBase(e.getMessage());
        ex.cause = e;
        Throwable cause = e.getCause();

        ex.errcode = cause.getClass().getName();
        ex.errmsg = e.getCause().getMessage();


        addInfo2ex(ex, e);

        String responseTxt = encodeJson(ex);

        wrtRespErr(exchange, responseTxt);

        return responseTxt;
    }

    private void AopTtransActNlog(HttpExchange exchange) throws Throwable {
        String prmurl;
        String mth;
        //basehdr.kt
        //-----------------stat trans action
        //  System.out.println("▶\uFE0Ffun handle2(HttpExchange)");
        //这里需要新开session。。因为可能复用同一个http线程
        Session session = sessionFactory.openSession();
        // 2. 手动将 Session 绑定到当前线程
        ThreadLocalSessionContext.bind(session);
        System.out.println("thrdid="+Thread.currentThread());
        System.out.println("openSession="+session);
        System.out.println("getCurrentSession="+sessionFactory.getCurrentSession());
       // commitTransactIfActv(session);
        session.beginTransaction();
        ThreadLocalSessionContext.unbind(sessionFactory);
        mth = colorStr("handle2", YELLOW_bright);
        prmurl = colorStr(encodeJson(toExchgDt(exchange)), GREEN);
        System.out.println("▶\uFE0Ffun " + mth + "(exchange=" + prmurl);

        handle2(exchange);
        System.out.println("✅endfun handle2()");
        commitTransaction(session);
        session.close();
        // session.getTransaction().commit();
    }

    //----------aop auth
    private void urlAuthChk(HttpExchange exchange) throws IOException, NeedLoginEx {
        if (AuthService.needLoginAuth(exchange.getRequestURI())) {
            String uname = getcookie("uname", exchange);
            //  uname="ttt";
            if (uname.equals("")) {
                //need login
                NeedLoginEx e = new NeedLoginEx("需要登录");

                e.fun = "BaseHdr." + getCurrentMethodName();
                e.funPrm = toExchgDt((HttpExchange) exchange);

                //   addInfo2ex(e, null);

                throw e;
            }

            //basehdr.kt
            //-----------------stat trans action
            //  System.out.println("▶\uFE0Ffun handle2(HttpExchange)");

        }


    }


    public static String saveDirUsrs = "";


    protected abstract void handle2(HttpExchange exchange) throws Throwable;


}

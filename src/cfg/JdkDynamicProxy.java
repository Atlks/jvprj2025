package cfg;

import apiAcc.RechargeHdr;
import biz.HttpHandlerX;
import biz.NeedLoginEx;
import com.sun.net.httpserver.HttpExchange;
import entityx.ExceptionBase;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static biz.BaseHdr.*;
import static cfg.AppConfig.sessionFactory;
import static util.ColorLogger.*;
import static util.TransactMng.commitTransaction;
import static util.Util2025.*;
import static util.dbutil.setField;
import static util.util2026.*;


//aop shuld log auth ,ex catch,,,pfm
public class JdkDynamicProxy implements InvocationHandler {
    private final Object target; // 目标对象

    public JdkDynamicProxy(Object target) {
        this.target = target;
    }

    public static void main(String[] args) throws Exception {
        Object obj1 = RechargeHdr.class.getConstructor().newInstance();
        setField(obj1, SessionFactory.class,  AppConfig. sessionFactory);
        //new RechargeHdr(); // 目标对象
        Object proxyObj =  JdkDynamicProxy.createProxy(obj1); // 创建
        HttpHandlerX hx= (HttpHandlerX) proxyObj;
        hx.handle(null);
    }

    private void aopEXhandler(HttpExchange exchange, Method method, Object[] args)   {
        ExceptionBase ex = new ExceptionBase("");
        try {
            curUrl.set(String.valueOf((exchange.getRequestURI())));
            //===================log chk
            urlAuthChk(exchange);

            //=========auth chk pass
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            method.invoke(target, args); // 调用目标方法

            commitTransaction(session);
        } catch (InvocationTargetException e) {

            ex = new ExceptionBase(e.getMessage());
            ex.cause = e;
            Throwable cause = e.getCause();

            ex.errcode = cause.getClass().getName();
            ex.errmsg = e.getCause().getMessage();


            addInfo2ex(ex, e);

            String responseTxt = encodeJson(ex);
            System.out.println("\uD83D\uDED1 endfun handlex().ret=" + responseTxt);
            wrtRespErrNoex(exchange, responseTxt);


        } catch (Throwable e) {


                System.out.println(
                        "⚠\uFE0F e="
                                + e.getMessage() + "\nStackTrace="
                                + getStackTraceAsString(e)
                                + "\n end stacktrace......................"
                );


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
                System.out.println("\uD83D\uDED1 endfun handlex().ret=" + responseTxt);
            wrtRespErrNoex(exchange, responseTxt);


        }

    }

    // 代理逻辑：拦截方法调用   aop log
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("日志记录: 调用方法 " + method.getName());
        //    Object result = method.invoke(target, args); // 调用目标方法
        String mth = colorStr(method.getName(), YELLOW_bright);
        String prmurl = colorStr(encodeJsonV2(args), GREEN);
        System.out.println("▶\uFE0Ffun " + mth + "(url=" + prmurl);
        //   curUrlPrm.set(exchange.getrequ);
        Object result = null;
        HttpExchange exchange = null;


        //   setcookie("uname", "007", exchange);//for test

        //---------blk chk auth
        if (method.getName().equals("handlerx")) {
            exchange = (HttpExchange) args[0];
            aopEXhandler(exchange, method, args);
        } else {
            result = method.invoke(target, args); // 调用目标方法
        }


        // session.getTransaction().commit();

        System.out.println("✅endfun " + method.getName() + "().ret=" + encodeJsonObj(result));


        System.out.println("方法调用完成" + method.getName());
        return result;
    }

    //----------aop auth
    private void urlAuthChk(HttpExchange exchange) throws IOException, NeedLoginEx {
        if (needLoginAuth(exchange.getRequestURI())) {
            String uname = getcookie("uname", exchange);
            //  uname="ttt";
            if (uname.equals("")) {
                //need login
                NeedLoginEx e = new NeedLoginEx("需要登录");

                e.fun = "BaseHdr." + getCurrentMethodName();
                e.funPrm = toExchgDt((HttpExchange) exchange);

             //   addInfo2ex(e, null);

                throw  e;
            }

            //basehdr.kt
            //-----------------stat trans action
            //  System.out.println("▶\uFE0Ffun handle2(HttpExchange)");

        }


    }

    // 生成代理对象
    public static Object createProxy(Object target) {
        Class<?>[] interfaces = target.getClass().getInterfaces();
        System.out.println("crtProxy().itfss="+encodeJsonObj(interfaces));
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),  // 类加载器

                interfaces,  // 代理需要实现的接口
                new JdkDynamicProxy(target)         // 代理逻辑
        );
    }
}

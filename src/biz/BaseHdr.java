package biz;

import jakarta.annotation.security.PermitAll;
import org.hibernate.Session;

import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ThreadLocalSessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import entityx.ExceptionBase;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

//import static apiAcc.TransHdr.saveUrlLogBalanceYinliWlt;

import static util.ColorLogger.*;
import static util.ExptUtil.addInfo2ex;
import static util.ExptUtil.curUrl;
import static util.QueryParamParser.toDto;
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
public abstract class BaseHdr<T, U> implements HttpHandler, Serializable {

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
        var responseTxt = "";
        ExceptionBase ex = new ExceptionBase("");
        try {
            //   setcookie("uname", "007", exchange);//for test

            //============aop trans begn
            openSessionBgnTransact();

            //---------blk chk auth
            urlAuthChk(exchange);
            AopNlog(exchange);


            //      System.out.println("endfun handle2()");
            System.out.println("✅endfun handle()");
            return;

        } catch (java.lang.reflect.InvocationTargetException e) {
            responseTxt = processInvkExpt(exchange, e);

        } catch (Throwable e) {

            responseTxt = processNmlExptn(exchange, e);
            // throw new RuntimeException(e);

        } finally {
            commitTsact();
        }
        //end catch

        //not ex ,just all ok blk
        //ex.fun  from stacktrace
        System.out.println("\uD83D\uDED1 endfun handle().ret=" + responseTxt);
    }

    private void commitTsact() {
        commitTransaction(sessionFactory.getCurrentSession());
        sessionFactory.getCurrentSession().close();
        ThreadLocalSessionContext.unbind(sessionFactory);
    }

    private void openSessionBgnTransact() {
        //这里需要新开session。。因为可能复用同一个http线程
        Session session = sessionFactory.openSession();
        // 2. 手动将 Session 绑定到当前线程
        ThreadLocalSessionContext.bind(session);
        System.out.println("thrdid=" + Thread.currentThread());
        System.out.println("openSession=" + session);
        System.out.println("getCurrentSession=" + sessionFactory.getCurrentSession());
        // commitTransactIfActv(session);
        session.beginTransaction();
    }

    private static String processNmlExptn(HttpExchange exchange, Throwable e) {
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

    private void AopNlog(HttpExchange exchange) throws Throwable {
        String prmurl;
        String mth;
        //basehdr.kt
        //-----------------stat trans action
        //  System.out.println("▶\uFE0Ffun handle2(HttpExchange)");


        //---------log
        Class cls=   getPrmClass(this,"handle3");
        T dto = (T) toDto(exchange, cls);
        String handle2 = "handle3";
        mth = colorStr(handle2, YELLOW_bright);
        prmurl = colorStr(encodeJson((dto)), GREEN);
        System.out.println("▶\uFE0Ffun " + mth + "(dto=" + prmurl);

      //  handle2(exchange);
        //会使用反射机制去查找控制器方法中的参数类型


      var rzt=  handle3(dto);
        wrtResp(exchange, encodeJsonObj(rzt) );
       System.out.println("✅endfun "+handle2+"()");

        /// ----------log


    }

    //查找对象的的handle3方法，获得参数（带有 ModelAttribute 标记 ） 类型，
    //子类方法是public void handle3(@ModelAttribute Usr dto)
    public static Class getPrmClass(Object obj, String methodName) {
        if (obj == null || methodName == null) {
            return null;
        }

        // 获取 obj 的所有方法
        Method[] methods = obj.getClass().getDeclaredMethods();

        for (Method method : methods) {
            // 确保方法名称匹配
            if (!method.getName().equals(methodName)) {
                continue;
            }

            // 遍历方法的所有参数
            for (Parameter parameter : method.getParameters()) {
                // 检查参数是否标记了 @ModelAttribute
                if (parameter.isAnnotationPresent(ModelAttribute.class)) {
                    Class<?> type = parameter.getType();
                    if(type==Object.class)
                        continue;
                    return type; // 返回参数的 Class 类型
                }
            }
        }

        return null; // 未找到匹配的方法或参数
    }

    /**
     * Spring MVC 的 @ModelAttribute 注解机制：
     * <p>
     * Spring MVC 会使用反射机制去查找控制器方法中的参数类型（例如 Usr）并将查询参数的值绑定到该类型的对象上。Spring 不需要在运行时获取泛型信息，而是通过反射将查询参数直接映射到类的字段或 getter/setter 方法。
     *
     * @param dto
     * @return
     */
    public Object handle3(T dto) throws Exception {
        System.out.println("baseCls.hd3("+encodeJson(dto));
        return null;
    }

    //----------aop auth
    private void urlAuthChk(HttpExchange exchange) throws IOException, NeedLoginEx {


//        if (AuthService.needLoginAuth(exchange.getRequestURI()))
        if (needLoginUserAuth((Class<? extends BaseHdr<T, U>>) this.getClass())) {
               String uname = getcookie("uname", exchange);
              if(uname.equals("")){
                  NeedLoginEx e = new NeedLoginEx("需要登录");

                  e.fun = "BaseHdr." + getCurrentMethodName();
                  e.funPrm = toExchgDt((HttpExchange) exchange);

                  //   addInfo2ex(e, null);

                  throw e;
              }



        }


    }

    private boolean needLoginUserAuth(Class<? extends BaseHdr<T, U>> aClass) {
        boolean annotationPresent = aClass.isAnnotationPresent(PermitAll.class);

        //if has anno ,not need login
        return !annotationPresent;
    }


    public static String saveDirUsrs = "";


    protected abstract void handle2(HttpExchange exchange) throws Throwable;



}

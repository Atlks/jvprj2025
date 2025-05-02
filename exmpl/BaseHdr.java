package cfg;

import jakarta.annotation.security.PermitAll;

import org.hibernate.SessionFactory;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import util.excptn.ExceptionObj;

import util.auth.IsEmptyEx;
import util.ex.NeedLoginEx;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

//import static apiAcc.TransHdr.saveUrlLogBalanceYinliWlt;

import static util.algo.AnnotationUtils.getCookieParams;
import static util.algo.ToXX.toDtoFrmHttp;
import static util.log.ColorLogger.*;
import static util.excptn.ExptUtil.curUrl;


import static util.oo.WebsrvUtil.processNmlExptn;
import static util.serverless.ApiGateway.processInvkExpt;

import static util.tx.TransactMng.*;
import static util.misc.Util2025.*;
import static util.tx.dbutil.setField;
import static util.misc.util2026.*;

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

public abstract class BaseHdr<T, U> implements HttpHandler {

    // 实现 Serializable 接口
    public static final long serialVersionUID = 1L; // 推荐加
//    
//    @Lazy
    public SessionFactory sessionFactory;

    //@Lazy
    //
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    ;
    public HttpExchange httpExchange;

    //----------aop ex  and some log part
    //事务管理  全局异常
    //@ExceptionHandler
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        //wz qrystr
        //  printlnx();
        httpExchange = exchange;
        String mth = colorStr("handle", YELLOW_bright);
        String prmurl = colorStr(String.valueOf(exchange.getRequestURI()), GREEN);
        curUrl.set(encodeJson(exchange.getRequestURI()));
        System.out.println("▶\uFE0Ffun " + mth + "(url=" + prmurl);
        //   curUrlPrm.set(exchange.getrequ);
        var responseTxt = "";
        ExceptionObj ex = new ExceptionObj("");
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



    private void AopNlog(HttpExchange exchange) throws Throwable {
        String prmurl;
        String mth;
        //basehdr.kt
        //-----------------stat trans action
        //  System.out.println("▶\uFE0Ffun handle2(HttpExchange)");
        String handlex = "handle3";
        mth = colorStr(handlex, YELLOW_bright);
        Object rzt;
        //---------log
        Class cls = getPrmClass(this, handlex);
        if (cls == null) {
            System.out.println("▶\uFE0Ffun " + mth + "(）");
            rzt = handle3();
        } else {
            T dto = (T) toDtoFrmHttp(exchange, cls);
            copyCookieToDto(getCookieParams(this.getClass(), handlex), dto);
            prmurl = colorStr(encodeJson((dto)), GREEN);
            System.out.println("▶\uFE0Ffun " + mth + "(dto=" + prmurl);
            rzt = handle3(dto);
        }


        wrtResp(exchange, encodeJsonObj(rzt));
        System.out.println("✅endfun " + handlex + "()");

        /// ----------log


    }

    public void copyCookieToDto(List<String> cookieParams, T dto) throws IsEmptyEx {
        for (String cknm : cookieParams) {
            String v = getcookie(cknm, httpExchange);
            setField(dto, cknm, v);
        }


    }

    //查找对象的的handle3方法，获得参数（带有 ModelAttribute 标记 ） 类型，
    //子类方法是public void handle3(@ModelAttribute Usr dto)
    public static Class getPrmClass(Object obj, String methodName) {
        if (obj == null || methodName == null) {
            return null;
        }

        // 获取 obj 的所有方法
        Method[] methods = obj.getClass().getMethods();

        for (Method method : methods) {
            // 确保方法名称匹配
            if (!method.getName().equals(methodName)) {
                continue;
            }

            // 遍历方法的所有参数
            Parameter[] parameters = method.getParameters();
            if(parameters.length==0)
                return  null;
            Parameter parameter=parameters[0];
            Class<?> type = parameter.getType();
            if (type == Object.class)
                continue;
            return type; // 返回参数的 Class 类型

//            for (Parameter parameter : parameters) {
//                if (parameter.isAnnotationPresent(BeanParam.class)) {
//                    Class<?> type = parameter.getType();
//                    if (type == Object.class)
//                        continue;
//                    return type; // 返回参数的 Class 类型
//                }
//                // 检查参数是否标记了 @ModelAttribute
//                if (parameter.isAnnotationPresent(ModelAttribute.class)) {
//                    Class<?> type = parameter.getType();
//                    if (type == Object.class)
//                        continue;
//                    return type; // 返回参数的 Class 类型
//                }
//            }
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
        System.out.println("baseCls.hd3(" + encodeJson(dto));
        return null;
    }

    public Object handle3() throws Exception {
        return null;
    }

    //----------aop auth
    // Advice
    private void urlAuthChk(HttpExchange exchange) throws IOException, NeedLoginEx, IsEmptyEx {


//        if (AuthService.needLoginAuth(exchange.getRequestURI()))
        if (needLoginUserAuth((Class<? extends BaseHdr<T, U>>) this.getClass())) {
            String uname = getcookie("uname", exchange);
            if (uname.equals("")) {
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


    protected void handle2(HttpExchange exchange) throws Throwable {
    }

    ;


}

package cfg;

import apiAcc.RechargeHdr;
import biz.BaseHdr;
import biz.HttpHandlerX;
import biz.NeedLoginEx;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import entityx.ExceptionBase;
import jakarta.annotation.security.PermitAll;
import org.hibernate.SessionFactory;
import util.Icall;

import java.io.IOException;
import java.util.List;

import static biz.BaseHdr.*;
import static util.AnnotationUtils.getCookieParams;
import static util.ColorLogger.*;
import static util.ExptUtil.curUrl;
import static util.QueryParamParser.toDto;
import static util.TransactMng.commitTsact;
import static util.TransactMng.openSessionBgnTransact;
import static util.Util2025.*;
import static util.dbutil.setField;
import static util.util2026.*;


//aop shuld log auth ,ex catch,,,pfm
public class AtProxy4webapi implements Icall,HttpHandler{
    private Icall target; // 目标对象

    public AtProxy4webapi(Object target) {
        this.target = (Icall) target;
    }

    public static void main(String[] args) throws Exception {
        Object obj1 = RechargeHdr.class.getConstructor().newInstance();
        setField(obj1, SessionFactory.class,  AppConfig. sessionFactory);
        //new RechargeHdr(); // 目标对象
        Object proxyObj = new AtProxy4webapi(obj1); // 创建
        HttpHandler hx= (HttpHandler) proxyObj;
        hx.handle(null);
    }



    /**
     * @param args
     * @return
     * @throws Exception
     */
    @Override
    public Object call(Object args) throws Exception {
        String mthFullname = target.getClass().getName() + ".call";
        System.out.println("日志记录: 调用方法 "+ mthFullname);
        //    Object result = method.invoke(target, args); // 调用目标方法

        String mth = colorStr(mthFullname, YELLOW_bright);
        String prmurl = colorStr(encodeJsonV2(args), GREEN);
        System.out.println("▶\uFE0Ffun " + mth + "(arg=" + prmurl);
        //   curUrlPrm.set(exchange.getrequ);

        HttpExchange exchange = null;

        //---------blk chk auth

        Object result =  target.call(args);

        // session.getTransaction().commit();

        System.out.println("✅endfun " + mthFullname + "()") ;


        System.out.println("方法调用完成" + mthFullname);

        return result;
    }

    // 生成代理对象
    public static Object createProxy4webapi(Object target) {
       // Class<?>[] interfaces = target.getClass().getInterfaces();
     //   System.out.println("crtProxy().itfss="+encodeJsonObj(interfaces));
        return new AtProxy4webapi(target);
    }
    public  ThreadLocal<HttpExchange> httpExchangeCurThrd=new ThreadLocal<>();

    /**
     * Handle the given request and generate an appropriate response.
     * See {@link HttpExchange} for a description of the steps
     * involved in handling an exchange.
     *
     * @param exchange the exchange containing the request from the
     *                 client and used to send the response
     * @throws NullPointerException if exchange is {@code null}
     * @throws IOException          if an I/O error occurs
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        httpExchangeCurThrd.set(exchange) ;
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
            handlexProcess(exchange);


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

    private void handlexProcess(HttpExchange exchange) throws Throwable {
        String prmurl;
        String mth;
        //basehdr.kt
        //-----------------stat trans action
        //  System.out.println("▶\uFE0Ffun handle2(HttpExchange)");

        Object rzt;
        //---------log
        Class cls=   getPrmClass(this.target,"call");
        if(cls==null)
        {

            rzt= call(null);
        }else{
            var dto = toDto(exchange, cls);
            copyCookieToDto(httpExchangeCurThrd.get(), getCookieParams(target.getClass(), "handlex"),dto);
            prmurl = colorStr(encodeJson((dto)), GREEN);

            rzt=  call(dto);
        }

        //  handle2(exchange);
        //会使用反射机制去查找控制器方法中的参数类型



        wrtResp(exchange, encodeJsonObj(rzt) );


        /// ----------log


    }



    private void urlAuthChk(HttpExchange exchange) throws IOException, NeedLoginEx {


//        if (AuthService.needLoginAuth(exchange.getRequestURI()))
        if (needLoginUserAuth((Class<? >) this.getClass())) {
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

    private boolean needLoginUserAuth(Class<? > aClass) {
        boolean annotationPresent = aClass.isAnnotationPresent(PermitAll.class);

        //if has anno ,not need login
        return !annotationPresent;
    }

    //----------aop auth
//    private void urlAuthChk(HttpExchange exchange) throws IOException, NeedLoginEx {
//        if (AuthService.needLoginAuth(exchange.getRequestURI())) {
//            String uname = getcookie("uname", exchange);
//            //  uname="ttt";
//            if (uname.equals("")) {
//                //need login
//                NeedLoginEx e = new NeedLoginEx("需要登录");
//
//                e.fun = "BaseHdr." + getCurrentMethodName();
//                e.funPrm = toExchgDt((HttpExchange) exchange);
//
//             //   addInfo2ex(e, null);
//
//                throw  e;
//            }
//
//            //basehdr.kt
//            //-----------------stat trans action
//            //  System.out.println("▶\uFE0Ffun handle2(HttpExchange)");
//
//        }
//
//
//    }

}

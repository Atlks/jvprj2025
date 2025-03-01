package util;

import biz.NeedLoginEx;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import entityx.ExceptionBase;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.IOException;
import java.util.List;

import static api.usr.LoginHdr.Key4pwd4aeskey;
import static biz.BaseHdr.*;
import static util.AnnotationUtils.getCookieParams;
import static util.AopUtil.ivk4log;
import static util.ColorLogger.*;

import static util.EncryUtil.decryptAesFromStrBase64;
import static util.ExptUtil.curUrl;
import static util.QueryParamParser.toDto;
import static util.SprUtil.injectAll4spr;
import static util.TransactMng.commitTsact;
import static util.TransactMng.openSessionBgnTransact;
import static util.Util2025.*;
import static util.dbutil.setField;
import static util.util2026.*;


//aop shuld log auth ,ex catch,,,pfm
public class AtProxy4api implements Icall, HttpHandler {
    private Icall target; // 目标对象

    public AtProxy4api(Object target) {
        this.target = (Icall) target;
    }

//    public static void main(String[] args) throws Exception {
//        Object obj1 = RechargeHdr.class.getConstructor().newInstance();
//        setField(obj1, SessionFactory.class,  AppConfig. sessionFactory);
//        //new RechargeHdr(); // 目标对象
//        Object proxyObj = new AtProxy4webapi(obj1); // 创建
//        HttpHandler hx= (HttpHandler) proxyObj;
//        hx.handle(null);
//    }


    /**
     * @param args
     * @return
     * @throws Exception
     */
    @Override
    public Object call(Object args) throws Exception {
        String mthFullname = target.getClass().getName() + ".call";


        //---------blk chk auth
        Object result = ivk4log(mthFullname, args, () -> {
            injectAll4spr(target);
            return target.call(args);
        });


        return result;
    }

    //    // 生成代理对象
//    public static Object createProxy4webapi(Object target) {
//       // Class<?>[] interfaces = target.getClass().getInterfaces();
//     //   System.out.println("crtProxy().itfss="+encodeJsonObj(interfaces));
//        return new AtProxy4webapi(target);
//    }
    public static ThreadLocal<HttpExchange> httpExchangeCurThrd = new ThreadLocal<>();

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
        httpExchangeCurThrd.set(exchange);
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
            urlAuthChkV2(exchange);
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

    @Inject
@Autowired
    @Qualifier("ChkLgnStatAuthenticationMechanism")
  public   HttpAuthenticationMechanism HttpAuthenticationMechanism1;

    //public  static
    private void urlAuthChkV2(HttpExchange exchange) throws AuthenticationException, NeedLoginEx {

        injectAll4spr(this);
        Class<?> aClass = this.getClass();
        if(aClass== AtProxy4api.class)
        {
            aClass= this.target.getClass();
        }
        if (needLoginUserAuth(aClass)) {
            AuthenticationStatus autoStt = HttpAuthenticationMechanism1.validateRequest(null, null, null);

            if (autoStt == AuthenticationStatus.SUCCESS) {
                //next prcs
            }else
                throw  new NeedLoginEx("需要登录");
        }

    }


    protected boolean needLoginUserAuth(Class<?> aClass) {
        boolean annotationPresent = aClass.isAnnotationPresent(PermitAll.class);

        //if has anno ,not need login
        return !annotationPresent;
    }
    SecurityContext SecurityContext1;
    private void handlexProcess(HttpExchange exchange) throws Throwable {
        String prmurl;
        String mth;
        //basehdr.kt
        //-----------------stat trans action
        //  System.out.println("▶\uFE0Ffun handle2(HttpExchange)");

        Object rzt;
        //---------log
        Class cls = getPrmClass(this.target, "call");
        if (cls == null) {

            rzt = call(null);
        } else {
            var dto = toDto(exchange, cls);
            //--------set cook to dto
            List<String> cookieParams = getCookieParams(target.getClass(), "call");
            for (String cknm : cookieParams) {
                String v = getcookie(cknm, httpExchangeCurThrd.get());
                if (cknm .equals("uname") )
                    v = decryptAesFromStrBase64(v, Key4pwd4aeskey);
                setField(dto, cknm, v);
            }
            // copyCookieToDto(httpExchangeCurThrd.get(), ckprms, dto);
            //   prmurl = colorStr(encodeJson((dto)), GREEN);

            rzt = call(dto);
        }

        //  默认返回 JSON，不需要额外加 @ResponseBody
        //  默认会将 String 直接作为 text/plain 处理：
        if (rzt.getClass() == String.class)
            wrtResp(exchange, (rzt.toString()));
        else
            wrtResp(exchange, encodeJsonObj(rzt));


        /// ----------log


    }

    //protected abstract void urlAuthChk(HttpExchange exchange) throws Exception;


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

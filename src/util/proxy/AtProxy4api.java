package util.proxy;

import annos.CookieParam;
import annos.JwtParam;
import biz.MinValidator;
import entityx.Non;
import jakarta.inject.Inject;
import org.springframework.beans.factory.annotation.Qualifier;
import service.auth.ISAM;
import util.ex.ValideTokenFailEx;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import util.excptn.ExceptionBase;
import jakarta.annotation.security.PermitAll;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.SecurityContext;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.internal.constraintvalidators.bv.NotBlankValidator;
import org.hibernate.validator.internal.constraintvalidators.bv.NotNullValidator;
import org.jetbrains.annotations.Nullable;
import util.excptn.ExptUtil;
import util.algo.Icall;
import util.auth.ChkLgnStatAuthenticationMechanism;
import util.auth.IsEmptyEx;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.lang.reflect.Field;
import java.lang.annotation.Annotation;
import java.util.Map;

import static biz.BaseHdr.*;
import static biz.Response.createErrResponseWzErrcode;
import static util.algo.AnnotationUtils.getCookieParamsV2;
import static util.algo.AnnotationUtils.getParams;
import static util.algo.ToXX.toDtoFrmQrystr;
import static util.excptn.ExptUtil.addInfo2ex;
import static util.proxy.AopUtil.ivk4log;
import static util.auth.AuthUtil.getCurrentUser;
import static util.log.ColorLogger.*;

import static util.excptn.ExptUtil.curUrl;

import static util.proxy.SprUtil.injectAll4spr;
import static util.tx.TransactMng.commitTsact;
import static util.tx.TransactMng.openSessionBgnTransact;
import static util.misc.Util2025.*;
import static util.tx.dbutil.setField;
import static util.misc.util2026.*;

/**
 * todo
 * 多个拦截器（责任链模式）
 * 如果需要多个拦截器，可以链式包装： 增强复用性
 * <p>
 * server.createContext("/test",
 * new AuthInterceptor(new LoggingInterceptor(new MyHandler())));
 */
//aop shuld log auth ,ex catch,,,pfm
public class AtProxy4api implements HttpHandler {
    private static final String ChkLgnStatSam ="ChkLgnStatSam" ;
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

    public Object invoke_call(Object args) throws Throwable {
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
        ExptUtil.lastExsList.set(new ArrayList<>());
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
            e.printStackTrace();
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
    @Qualifier(ChkLgnStatSam)  //"ChkLgnStatSam"
    public ISAM sam1;

//    @Inject
//    @Autowired
//    @Qualifier("ChkLgnStatAuthenticationMechanism")
//    public HttpAuthenticationMechanism HttpAuthenticationMechanism1;

    //public  static
    private void urlAuthChkV2(HttpExchange exchange) throws ValideTokenFailEx, AuthenticationException {

        injectAll4spr(this);

        if (needLoginUserAuth()) {
            sam1.validateRequest(null, null, null);
        }
    }
//            if (authStt == AuthenticationStatus.SUCCESS) {
//                //next prcs
//            } else {
//                ValideTokenFailEx 需要登录 = new ValideTokenFailEx("登录标识校验失败");
//                需要登录.lastExsList=ExptUtil.lastExsList.get();
//                throw  需要登录;
//            }

    protected boolean needLoginUserAuth() {

        Class<?> aClass = this.getClass();
        if (aClass == AtProxy4api.class) {
            aClass = this.target.getClass();
        }

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
            rzt = invoke_call(new Non());
        } else {
            var dto = toDto(exchange, cls);

            // addDeftParam(dto);
            validDto(dto);
            rzt = invoke_call(dto);
        }

        //  默认返回 JSON，不需要额外加 @ResponseBody
        //  默认会将 String 直接作为 text/plain 处理：
        if (rzt == null)
            rzt = "ok";
        if (rzt.getClass() == String.class)
            wrtResp(exchange, (rzt.toString()));
        else
            wrtResp(exchange, encodeJsonObj(rzt));


        /// ----------log


    }

    // @Nullable
    @NotNull
    private @NotNull Object toDto(HttpExchange exchange, @NotNull Class cls) throws Exception, IsEmptyEx {

        // 反射创建 DTO 实例
        Object dto = cls.getDeclaredConstructor().newInstance();
        addDeftParam(dto);
        var dtoQrystr = toDtoFrmQrystr(exchange, cls);
        copyProps(dtoQrystr, dto);

        //--------set cook to dto
        List<CookieParam> cookieParams = getCookieParamsV2(target.getClass(), "call");
        for (CookieParam cknm : cookieParams) {
            String v = getcookie(cknm.name(), httpExchangeCurThrd.get());
            if (cknm.value().equals("$curuser"))
                v = getCurrentUser();
            setField(dto, cknm.name(), v);
        }
        //------set jwt to dto
        List<JwtParam> JwtParams = getParams(target.getClass(), JwtParam.class);
        for (JwtParam jw : JwtParams) {
            if (jw.name().equals("uname"))
                setField(dto, jw.name(), getCurrentUser());
        }
        return dto;
    }

    //uname cookie
    private void addDeftParam(Object dto) {

        if (needLoginUserAuth())
            setField(dto, "uname", getCurrentUser());
        else
            setField(dto, "uname", "defusr");
    }

    private void validDto(Object dto) {

        var clazz = dto.getClass();
        // 遍历类的所有字段
        for (Field field : clazz.getDeclaredFields()) {
            System.out.println("字段: " + field.getName());

            // 遍历该字段上的所有注解
            for (Annotation annotation : field.getDeclaredAnnotations()) {
                System.out.println("  注解: " + annotation.annotationType().getSimpleName());
                if (annotation.annotationType() == Min.class) {
                    MinValidator vldr = new MinValidator();
                    Min annotation1 = (Min) annotation;
                    vldr.initialize(annotation1);
                    if (!vldr.isValid((BigDecimal) getField(dto, field.getName()), null)) {
                        Map<String, Object> m = new HashMap<>();
                        m.put("dto", dto);
                        m.put("fld", field.getName());
                        m.put("msg", "vldfail");
                        m.put("msgAnno", annotation1.message());
                        throw new RuntimeException(encodeJsonObj(m));
                    }
                    ;
                }

                if (annotation.annotationType() == NotBlank.class) {
                    NotBlankValidator vldr = new NotBlankValidator();
                    NotBlank annotation1 = (NotBlank) annotation;

                    String field1 = (String) getField(dto, field.getName());
                    if (!vldr.isValid(
                            field1, null
                    )) {
                        Map<String, Object> m = new HashMap<>();
                        m.put("dto", dto);
                        m.put("fld", field.getName());
                        m.put("msg", "vldfail");
                        m.put("msgAnno", annotation1.message());
                        throw new RuntimeException(encodeJsonObj(m));
                    }
                    ;
                }

                if (annotation.annotationType() == NotNull.class) {
                    NotNullValidator vldr = new NotNullValidator();
                    NotNull annotation1 = (NotNull) annotation;

                    if (!vldr.isValid(getField(dto, field.getName()), null)) {
                        Map<String, Object> m = new HashMap<>();
                        m.put("dto", dto);
                        m.put("fld", field.getName());
                        m.put("msg", "vldfail");
                        m.put("msgAnno", annotation1.message());
                        throw new RuntimeException(encodeJsonObj(m));
                    }
                    ;
                }
            }
        }

    }


    public static String processNmlExptn(HttpExchange exchange, Throwable e) {
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


        String responseTxt = encodeJson(createErrResponseWzErrcode(ex));

        wrtRespErrNoex(exchange, responseTxt);
        return responseTxt;
    }

    public static String processInvkExpt(HttpExchange exchange, InvocationTargetException e) throws IOException {
        ExceptionBase ex;
        ex = new ExceptionBase(e.getMessage());
        ex.cause = e;
        Throwable cause = e.getCause();

        ex.errcode = cause.getClass().getName();
        ex.errmsg = e.getCause().getMessage();


        addInfo2ex(ex, e);

        String responseTxt = encodeJson(createErrResponseWzErrcode(ex));
        //   String responseTxt = encodeJson(ex);

        wrtRespErr(exchange, responseTxt);

        return responseTxt;
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

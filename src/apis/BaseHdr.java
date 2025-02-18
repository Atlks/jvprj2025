package apis;

import apiAcc.UpdtCompleteChargeHdr;
import apiOrdBet.QryOrdBetHdr;
import apiUsr.*;
import apiWltYinli.WithdrawHdr;
import biz.BaseBiz;
import com.alibaba.fastjson2.JSON;
import org.hibernate.Session;
import org.noear.solon.annotation.Component;
import test.UserBiz;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import apiCms.CmsBiz;
import entityx.Err;
import entityx.ExceptionBase;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Map;
import java.util.Set;

import static apiAcc.AddOrdChargeHdr.saveUrlOrdChrg;
//import static apiAcc.TransHdr.saveUrlLogBalanceYinliWlt;

import static util.AopLogJavassist.getAClassExted;
import static util.ColorLogger.*;
import static util.Util2025.encodeJson;

import static util.dbutil.setField;
import static util.util2026.*;

@Component
public abstract class BaseHdr implements HttpHandler {

    public  org.hibernate.Session session;

    public void setSession(Session session) {
        this.session = session;
    }

    //wz qrystr
    public static ThreadLocal<String> curUrl = new ThreadLocal<>();
    public static ThreadLocal<String> curUrlPrm = new ThreadLocal<>();
    public static ThreadLocal<String> curFun4dbg = new ThreadLocal<>();

    public static ThreadLocal<Object> currFunPrms4dbg = new ThreadLocal<>();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        //wz qrystr
      //  printlnx();
       String mth= colorStr("handle",YELLOW_bright);
        String prmurl= colorStr(String.valueOf(exchange.getRequestURI()),GREEN);
        curUrl.set(encodeJson(exchange.getRequestURI()));
        System.out.println("▶\uFE0Ffun "+mth+"(url=" + prmurl);
        //   curUrlPrm.set(exchange.getrequ);

        ExceptionBase ex = new ExceptionBase("");
        try {
            setcookie("uname", "007", exchange);//for test

            //---------blk chk auth
            if (needLoginAuth(exchange.getRequestURI())) {
                String uname = getcookie("uname", exchange);
                //  uname="ttt";
                if (uname.equals("")) {
                    //need login
                    NeedLoginEx e = new NeedLoginEx("需要登录");
                    e.fun = "QueryUsrHdr。" + getCurrentMethodName();
                    e.funPrm = (exchange);

                    throw e;
                    //  wrtResp(exchange, "needLogin");
                    //  return;
                }
            }

          //  System.out.println("▶\uFE0Ffun handle2(HttpExchange)");
        //    handle2(exchange);

            //-------------------
            Class<?> aClass =this.getClass();
            Class<?> modifiedClass = getAClassExted(aClass);
            Object instance ;
            try{
                instance= modifiedClass.getConstructor().newInstance();
            } catch (NoSuchMethodException e) {
               // 没有无参构造函数
                Constructor<?> constructor = modifiedClass.getConstructor(Session.class); // 指定参数类型
                  instance = constructor.newInstance( session);

                  //other dync fore
            }

//            setField(instance,"session",new SessionProvider().provide());
            //new SessionProvider().provide()
            setField(instance, Session.class, session );
            Method method = modifiedClass.getMethod("handle2", HttpExchange.class);
            method.invoke(instance, exchange);

      //      System.out.println("endfun handle2()");
            System.out.println("✅endfun handle()");

        } catch (java.lang.reflect.InvocationTargetException e) {
            ex = new ExceptionBase(e.getMessage());
            ex.cause = e;
            Throwable cause = e.getCause();

            ex.errcode = cause.getClass().getName();
            ex.errmsg=e.getCause().getMessage();
            String stackTraceAsString = getStackTraceAsString(e);
            ex.stackTrace = stackTraceAsString;

            addInfo2ex(ex);

            String responseTxt = encodeJson(ex);
            System.out.println("\uD83D\uDED1 endfun handle().ret=" + responseTxt);
            wrtRespErr(exchange, responseTxt);

        } catch (Throwable e) {

            System.out.println(
                    "⚠\uFE0F e="
                            + e.getMessage() + "\nStackTrace="
                            + getStackTraceAsString(e)
                            + "\n end stacktrace......................"
            );

            String stackTraceAsString = getStackTraceAsString(e);
            ex.stackTrace = stackTraceAsString;

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

            addInfo2ex(ex);

            String responseTxt = encodeJson(ex);
            System.out.println("\uD83D\uDED1 endfun handle().ret=" + responseTxt);
            wrtRespErr(exchange, responseTxt);




            // throw new RuntimeException(e);

        }
        //end catch

        //not ex ,just all ok blk
       //ex.fun  from stacktrace

    }

    private static void addInfo2ex(ExceptionBase ex) {
        if(ex.fun.equals(""))
            ex.fun=curFun4dbg.get();
        if(ex.funPrm==null)
            ex.funPrm = currFunPrms4dbg.get();
        ex.url = curUrl.get();
        ex.urlprm = curUrlPrm.get();
    }

    private static final Set<String> NO_AUTH_PATHS = Set.of("/reg", "/login");

    /**
     * 判断是否需要登录的url    。。格式为  /reg
     *
     * @param requestURI
     * @return * @return `true` 需要登录，`false` 不需要登录
     */
    private boolean needLoginAuth(URI requestURI) {
        String path = requestURI.getPath(); // 只取路径部分，不包括查询参数
        return !NO_AUTH_PATHS.contains(path);
    }

    /**
     * 使用fastjson2，，将jsonstr转换为err对象
     *
     * @param jsonStr
     * @return
     */
    private Err toERR(String jsonStr) {

        try {
            if (jsonStr == null || jsonStr.isEmpty()) {
                return new Err("", "", "", null);
            }
            return JSON.parseObject(jsonStr, Err.class);
        } catch (Exception e) {
            //not errmsg,just str msg ,or another type
            return new Err(jsonStr, "", "", null);
        }


    }
    public static String saveDirUsrs = "";
    public static void iniCfgFrmCfgfile() {

        //test
        //   openMap4test();
        //    dbutil.  drvMap.put("com.mysql.cj.jdbc.Driver","org.h2.Driver");


        // 获取类加载器 /C:/Users/attil/IdeaProjects/jvprj2025/out/production/jvprj2025/
        String rootPath = UserBiz.class.getClassLoader().getResource("").getPath();
        Map cfg = parse_ini_fileNosec(rootPath + "../../cfg/dbcfg.ini");
        saveDirUsrs = (String) cfg.get("saveDirUsrs");
        // saveDirAcc= (String) cfg.get("saveDirAcc");
        //   savedirOrd= (String) cfg.get("savedirOrd");
        //QryOrdBetHdr.saveUrlOrdBet
        QryOrdBetHdr.saveUrlOrdBet = RegHandler.saveDirUsrs;
        saveUrlOrdChrg = RegHandler.saveDirUsrs;
        //(String) cfg.get("saveUrlOrdChrg");
        UpdtCompleteChargeHdr.saveUrlLogBalance = RegHandler.saveDirUsrs;
        BaseBiz.saveUrlLogBalanceYinliWlt = RegHandler.saveDirUsrs;
        WithdrawHdr.saveUrlOrdWthdr = RegHandler.saveDirUsrs;
        CmsBiz.saveUrlLogCms = RegHandler.saveDirUsrs;
        System.out.println("ini cfg finish..");
    }


    public static void iniCfgFrmCfgfileMltDb() {

        //test
        //   openMap4test();
        //    dbutil.  drvMap.put("com.mysql.cj.jdbc.Driver","org.h2.Driver");


        // 获取类加载器 /C:/Users/attil/IdeaProjects/jvprj2025/out/production/jvprj2025/
        String rootPath = UserBiz.class.getClassLoader().getResource("").getPath();
        Map cfg = parse_ini_fileNosec(rootPath + "../../../cfg/dbcfg.ini");
        RegHandler.saveDirUsrs = (String) cfg.get("saveDirUsrs");
        // saveDirAcc= (String) cfg.get("saveDirAcc");
        //   savedirOrd= (String) cfg.get("savedirOrd");
        //QryOrdBetHdr.saveUrlOrdBet
        QryOrdBetHdr.saveUrlOrdBet = (String) cfg.get("saveUrlOrdBet");
        saveUrlOrdChrg = RegHandler.saveDirUsrs;
        //(String) cfg.get("saveUrlOrdChrg");
        UpdtCompleteChargeHdr.saveUrlLogBalance = (String) cfg.get("saveUrlLogBalance");
        BaseBiz.saveUrlLogBalanceYinliWlt = (String) cfg.get("saveUrlLogBalanceYinliWlt");
        WithdrawHdr.saveUrlOrdWthdr = (String) cfg.get("saveUrlOrdWthdr");
        CmsBiz.saveUrlLogCms = (String) cfg.get("saveUrlLogCms");
        System.out.println("ini cfg finish..");
    }

    protected abstract void handle2(HttpExchange exchange) throws Throwable;

    public boolean isLogined(HttpExchange exchange) {
        String uname = getcookie("uname", exchange);
        return !uname.equals("");
        //   return  true;
    }

    public boolean isNotLogined(HttpExchange exchange) {
        String uname = getcookie("uname", exchange);
        return uname.equals("");
        //   return  true;
    }


}

package cfg;

import apiAcc.RechargeHdr;
import biz.HttpHandlerX;
import com.sun.net.httpserver.HttpExchange;
import org.hibernate.SessionFactory;
import util.Iservice;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static biz.BizUtil.createProxy4log;
import static cfg.AopLogJavassist.isObjectMethod;
import static cfg.AopLogJavassist.isObjectMethodEx;
import static util.ColorLogger.*;
import static util.Util2025.encodeJsonObj;
import static util.Util2025.encodeJsonV2;
import static util.dbutil.setField;


//aop shuld log auth ,ex catch,,,pfm
public class AtProxy4log implements Iservice {
    private   Iservice target; // 目标对象

    public AtProxy4log(Object target) {
        this.target = (Iservice) target;
    }

    public static void main(String[] args) throws Exception {
        Object obj1 = RechargeHdr.class.getConstructor().newInstance();
        setField(obj1, SessionFactory.class,  AppConfig. sessionFactory);
        //new RechargeHdr(); // 目标对象
        Object proxyObj =  createProxy4log(obj1); // 创建
        HttpHandlerX hx= (HttpHandlerX) proxyObj;
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
        System.out.println("▶\uFE0Ffun " + mth + "(url=" + prmurl);
        //   curUrlPrm.set(exchange.getrequ);

        HttpExchange exchange = null;


        //   setcookie("uname", "007", exchange);//for test

        //---------blk chk auth

        Object result = target.call(args);

        // session.getTransaction().commit();

        System.out.println("✅endfun " + mthFullname + "().ret=" + encodeJsonObj(result));


        System.out.println("方法调用完成" + mthFullname);
        return result;
    }

    // 生成代理对象
    public static Object createProxy4log(Object target) {
       // Class<?>[] interfaces = target.getClass().getInterfaces();
     //   System.out.println("crtProxy().itfss="+encodeJsonObj(interfaces));
        return new AtProxy4log(target);
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

package cfg;

import handler.pay.RechargeHdr;
import com.sun.net.httpserver.HttpExchange;
import org.hibernate.SessionFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import static cfg.BizUtil.createProxy4log;
import static cfg.AopLogJavassist.isObjectMethod;
import static cfg.AopLogJavassist.isObjectMethodEx;
import static util.log.ColorLogger.*;
import static util.misc.Util2025.*;
import static util.tx.dbutil.setField;


//aop shuld log auth ,ex catch,,,pfm
public class JdkDynamicProxy4log implements InvocationHandler {
    private final Object target; // 目标对象

    public JdkDynamicProxy4log(Object target) {
        this.target = target;
    }

    public static void main(String[] args) throws Exception {
        Object obj1 = RechargeHdr.class.getConstructor().newInstance();
        setField(obj1, SessionFactory.class,  AppConfig. sessionFactory);
        //new RechargeHdr(); // 目标对象
        Object proxyObj =  createProxy4log(obj1); // 创建
        HttpHandlerX hx= (HttpHandlerX) proxyObj;
       // hx.handle(null);
    }

    // 代理逻辑：拦截方法调用   aop log
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        String methodName=method.getName();

        //only ink ,not log if obj method
        if (isObjectMethod(methodName) || isObjectMethodEx(methodName)) {

            result = method.invoke(target, args); // 调用目标方法
            return result;
        }
        ;


        System.out.println("日志记录: 调用方法 " + method.getName());
        //    Object result = method.invoke(target, args); // 调用目标方法
        String mth = colorStr(method.getName(), YELLOW_bright);
        String prmurl = colorStr(encodeJsonV2(args), GREEN);
        System.out.println("▶\uFE0Ffun " + mth + "(url=" + prmurl);
        //   curUrlPrm.set(exchange.getrequ);

        HttpExchange exchange = null;


        //   setcookie("uname", "007", exchange);//for test

        //---------blk chk auth
        if (method.getName().equals("handlerx")) {
            exchange = (HttpExchange) args[0];
         //   aopEXhandler(exchange, method, args);
        } else {
            result = method.invoke(target, args); // 调用目标方法
        }


        // session.getTransaction().commit();

        System.out.println("✅endfun " + method.getName() + "().ret=" + encodeJsonObj(result));


        System.out.println("方法调用完成" + method.getName());
        return result;
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

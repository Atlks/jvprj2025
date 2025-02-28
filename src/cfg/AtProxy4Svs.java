package cfg;

import apiAcc.RechargeHdr;
import biz.HttpHandlerX;
import com.sun.net.httpserver.HttpExchange;
import org.hibernate.SessionFactory;
import util.Icall;

import static util.AopUtil.ivk4log;
import static util.ColorLogger.*;
import static util.Util2025.encodeJsonObj;
import static util.Util2025.encodeJsonV2;
import static util.dbutil.setField;


//aop shuld log auth ,ex catch,,,pfm
public class AtProxy4Svs implements Icall {
    private Icall target; // 目标对象

    public AtProxy4Svs(Object target) {
        this.target = (Icall) target;
    }

    public static void main(String[] args) throws Exception {
        Object obj1 = RechargeHdr.class.getConstructor().newInstance();
        setField(obj1, SessionFactory.class,  AppConfig. sessionFactory);
        //new RechargeHdr(); // 目标对象
        Object proxyObj =  createProxy4log(obj1); // 创建
        HttpHandlerX hx= (HttpHandlerX) proxyObj;
     //   hx.handle(null);
    }



    /**svs proxy
     * @param args
     * @return
     * @throws Exception
     */
    @Override
    public Object call(Object args) throws Exception {
        String mthFullname = target.getClass().getName() + ".call";

        Object result=ivk4log(mthFullname,args,()->{
            return  target.call(args);
        });
        return result;
    }

    // 生成代理对象
    public static Object createProxy4log(Object target) {
       // Class<?>[] interfaces = target.getClass().getInterfaces();
     //   System.out.println("crtProxy().itfss="+encodeJsonObj(interfaces));
        return new AtProxy4Svs(target);
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

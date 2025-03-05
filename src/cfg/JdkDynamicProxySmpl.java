package cfg;

import api.wlt.RechargeHdr;
import biz.HttpHandlerX;
import org.hibernate.SessionFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static util.misc.Util2025.*;
import static util.tx.dbutil.setField;

//for test
//aop shuld log auth ,ex catch,,,pfm
public class JdkDynamicProxySmpl implements InvocationHandler {
    private final Object target; // 目标对象

    public JdkDynamicProxySmpl(Object target) {
        this.target = target;
    }

    public static void main(String[] args) throws Exception {
        Object obj1 = RechargeHdr.class.getConstructor().newInstance();
        setField(obj1, SessionFactory.class,  AppConfig. sessionFactory);
        //new RechargeHdr(); // 目标对象
        Object proxyObj =  JdkDynamicProxySmpl.createProxy(obj1); // 创建
        HttpHandlerX hx= (HttpHandlerX) proxyObj;
      //  hx.handle(null);
    }


    // 代理逻辑：拦截方法调用   aop log
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("日志记录: 调用方法 " + method.getName());

        Object result = null;



        System.out.println("方法调用完成" + method.getName());
        return result;
    }


    // 生成代理对象
    public static Object createProxy(Object target) {
        Class<?>[] interfaces = target.getClass().getInterfaces();
        System.out.println("crtProxy().itfss="+encodeJsonObj(interfaces));
        Object proxy = Proxy.newProxyInstance(
                target.getClass().getClassLoader(),  // 类加载器

                interfaces,  // 代理需要实现的接口
                new JdkDynamicProxySmpl(target)         // 代理逻辑
        );
      //  target.setProxy(proxy); // 让 target 知道它的代理对象
        return proxy;
    }
}

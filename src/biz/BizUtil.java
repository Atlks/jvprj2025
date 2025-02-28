package biz;

import cfg.JdkDynamicProxy4log;

import java.lang.reflect.Proxy;

import static util.Util2025.encodeJsonObj;

public class BizUtil {

    // 生成代理对象
    public static Object createProxy4log(Object target) {
        Class<?>[] interfaces = target.getClass().getInterfaces();
        System.out.println("crtProxy().itfss="+encodeJsonObj(interfaces));
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),  // 类加载器

                interfaces,  // 代理需要实现的接口
                new JdkDynamicProxy4log(target)         // 代理逻辑
        );
    }

}

package test;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;


/**
 * 方法 3：改用 ByteBuddy（替代方案）
 * 如果你的项目在 Java 9+，可以改用 ByteBuddy，它不会依赖 ClassLoader.defineClass()。
 */
public class cglibProxy implements MethodInterceptor {

    public static void main(String[] args) {

    }

    public static <T> T createProxy(Class<T> clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(new cglibProxy());
        return (T) enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

        // 过滤掉 Object 类的方法
        if (method.getDeclaringClass() == Object.class) {
            return proxy.invokeSuper(obj, args);
        }

        Object result = proxy.invokeSuper(obj, args);

        System.out.println("[Log] " + method.getName() + " 方法执行完成");
        return result;
    }
}

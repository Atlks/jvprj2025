package cfg;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkDynamicProxy implements InvocationHandler {
    private final Object target; // 目标对象

    public JdkDynamicProxy(Object target) {
        this.target = target;
    }

    // 代理逻辑：拦截方法调用
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("日志记录: 调用方法 " + method.getName());
        Object result = method.invoke(target, args); // 调用目标方法
        System.out.println("方法调用完成" + method.getName());
        return result;
    }

    // 生成代理对象
    public static Object createProxy(Object target) {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),  // 类加载器
                target.getClass().getInterfaces(),  // 代理需要实现的接口
                new JdkDynamicProxy(target)         // 代理逻辑
        );
    }
}

package cfg;

import api.wlt.RechargeHdr;
import biz.HttpHandlerX;
import net.sf.cglib.proxy.*;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * ✅ 方案 2：使用 ByteBuddy 代替 CGLIB
 *
 * CGLIB 依赖反射修改 ClassLoader.defineClass，但 JDK 21 不允许。推荐使用 ByteBuddy，它兼容 JDK 21+。
 *
 * 是的，ByteBuddy 主要依赖 ASM（Java 字节码操作库）来动态生成类，而不会直接调用 ClassLoader#defineClass，所以它可以在 JDK 21 及更高版本正常工作。
 */
public class CglibProxy implements MethodInterceptor {
    private final Object target;

    public CglibProxy(Object target) {
        this.target = target;
    }

    public static Object createProxy(Object target) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(new CglibProxy(target));
        return enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        // 忽略 Object 类的方法
        if (method.getDeclaringClass() == Object.class) {
            return proxy.invokeSuper(obj, args);
        }

        System.out.println("CGLIB 代理拦截方法：" + method.getName());
        return proxy.invokeSuper(obj, args);
    }

    public static void main(String[] args) throws IOException {
        HttpHandlerX proxy = (HttpHandlerX) CglibProxy.createProxy(new RechargeHdr());
   //     proxy.handle(null);
    }
}

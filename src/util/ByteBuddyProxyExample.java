package util;


import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.implementation.bind.annotation.This;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import apiUsr.RegHandler;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class ByteBuddyProxyExample {

    public static <T> T createProxy(Class<T> clazz) throws Exception {
        // 使用 ByteBuddy 创建代理类
        ElementMatcher.Junction<MethodDescription> declaredBy = ElementMatchers.isDeclaredBy(Object.class);
        return new ByteBuddy()
                .subclass(clazz)  // 代理的类
                .method(ElementMatchers.any().and(ElementMatchers.not(ElementMatchers.isDeclaredBy(Object.class))))

              //  .method(ElementMatchers.not(declaredBy))  // Avoid Object methods like clone, toString, etc.
                .intercept(MethodDelegation.to(new MethodInterceptor()))  // 拦截方法调用
                .make()
                .load(clazz.getClassLoader())  // 使用类加载器加载代理类
                .getLoaded()  // 获取代理类
                .getDeclaredConstructor()
                .newInstance();  // 创建代理实例
    }



    // 方法拦截器  ,Method method, Object... args
    public  static class MethodInterceptor {
        public Object intercept( @SuperCall Callable<?> superCall,@This Object proxy,
                                 @Origin Method method,
                                 @AllArguments Object[] args) throws Exception {
         //   System.out.println("Before method");
         //   Method method=null;
            System.out.println("fun " + method.getName());
            try {
                // 你可以选择在这里调用原始方法
                // return superCall.call();  // Call the original method
                return superCall.call();  // Call the original method
            } catch (Exception e) {
                e.printStackTrace();
                throw  e;
            } finally {
               System.out.println("endfun " + method.getName());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        // 创建代理对象
        RegHandler proxy = createProxy(RegHandler.class);

        // 调用代理方法
        proxy.handle(null);  // 示例方法调用
    }
}





//    public static class MethodInterceptor {
//        public Object intercept(Method method, Object[] args, @SuperCall Callable<?> superCall) throws Throwable {
//            System.out.println("Before method: " + method.getName());
//            // You can call the original method with superCall.call() if needed
//            return superCall.call();  // Call the original method
//        }
//    }
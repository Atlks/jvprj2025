package utilDep;


import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.implementation.bind.annotation.This;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import api.usr.RegHandler;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

public class ByteBuddyProxyExample {

    public static <T> T createProxy(Class<T> clazz) throws Exception {
        // 使用 ByteBuddy 创建代理类
        ElementMatcher.Junction<MethodDescription> declaredBy = ElementMatchers.isDeclaredBy(Object.class);
        T prxyObj = new ByteBuddy()
                .subclass(clazz)  // 代理的类
                .method(ElementMatchers.any().and(ElementMatchers.not(ElementMatchers.isDeclaredBy(Object.class))))

                //  .method(ElementMatchers.not(declaredBy))  // Avoid Object methods like clone, toString, etc.
                .intercept(MethodDelegation.to(new MethodInterceptor()))  // 拦截方法调用

             //   .method(ElementMatchers.named("reg"))
           //     .intercept(MethodDelegation.to(new MethodInterceptor()))  //
                .make()
                .load(clazz.getClassLoader())  // 使用类加载器加载代理类
                .getLoaded()  // 获取代理类
                .getDeclaredConstructor()
                .newInstance();

        return prxyObj;  // 创建代理实例
    }


    /**
     *
     * 总结
     * 情况	解决方案
     * 方法有返回值	正常返回 superCall.call() 结果
     * 方法是 void	返回 null，避免影响逻辑
     * superCall.call() 返回 null	允许 null，只打印日志，不报错
     *
     * superCall.call() 是 ByteBuddy 提供的 代理调用，它表示 调用被拦截方法的原始实现。
     * method.invoke(proxy, args) 会导致代理对象再次进入 intercept 方法，形成死循环。
     * superCall.call() 直接调用 被代理类的真实方法，不会再经过拦截器。
     * 也就是说，它会执行 被代理类的原始方法，类似于 method.invoke(原始对象, args)。
     */
    // 方法拦截器  ,Method method, Object... args
    public  static class MethodInterceptor {
        public Object intercept( @SuperCall Callable<?> superCall,
                                 @This Object proxy,
                                 @Origin Method method,
                                 @AllArguments Object[] args) throws Exception {
         //   System.out.println("Before method");
         //   Method method=null;
             var mthName=method.getName();
            System.out.println("!fun " + method.getName());

            try {
                Object call = superCall.call();  // 调用原始方法


                if (call == null) {
                    System.out.println("⚠️ superCall.call() 返回 null，可能导致后续代码未执行");
                }
                // 处理返回值，如果方法是 void，返回 null
                // 兼容 void 方法，避免返回 `null`
                if (method.getReturnType().equals(Void.TYPE)) {
                    System.out.println("🔸 方法是 void，兼容返回值为空");
                    return null;
                }
                return call;  // 返回方法的执行结果
            } catch (Exception e) {
                System.err.println("调用 " + method.getName() + " 时发生异常: " + e.getMessage());
                e.printStackTrace();
                throw e;  // 重新抛出异常，保证不会吞掉异常
            }



   //      return    proxy.getClass().getMethod(mthName,   method.getParameterTypes()).invoke(proxy, args);



        }
    }




    //            try {
//                // 你可以选择在这里调用原始方法
//                // return superCall.call();  // Call the original method
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                throw  e;
//            } finally {
//               System.out.println("!!endfun " + method.getName());
//            }
    public static void main(String[] args) throws Exception {
        // 创建代理对象
        RegHandler proxy = createProxy(RegHandler.class);

        // 调用代理方法
    //    proxy.handle(null);  // 示例方法调用
    }
}





//    public static class MethodInterceptor {
//        public Object intercept(Method method, Object[] args, @SuperCall Callable<?> superCall) throws Throwable {
//            System.out.println("Before method: " + method.getName());
//            // You can call the original method with superCall.call() if needed
//            return superCall.call();  // Call the original method
//        }
//    }
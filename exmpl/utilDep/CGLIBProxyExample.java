package utilDep;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CGLIBProxyExample {



    // 获取代理对象
    public static <T> T getProxyObj(Class<T> aClass) throws InstantiationException, IllegalAccessException {

        String aClassName = aClass.getName();
        if(!aClassName.startsWith("apiUsr"))
        {
            return  aClass.newInstance();
        }

        // 创建 CGLIB Enhancer 对象
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(aClass);  // 设置父类为目标类
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                System.out.println("Before method: " + method.getName());
                // 调用目标类的实际方法
                Object result = proxy.invokeSuper(obj, args);
                System.out.println("After method: " + method.getName());
                return result;
            }
        });

        // 创建并返回代理对象
        return (T) enhancer.create();
    }



    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        // 创建目标对象
        //  RegHandler myService = new RegHandler();

        RegHandler proxy = getProxyObj(RegHandler.class);

        // 调用代理对象的方法
        //  proxy.reg()
    }
}


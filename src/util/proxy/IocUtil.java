package util.proxy;

import java.util.HashMap;
import java.util.Map;

public class IocUtil {

    public static   Map<String,Object> beansMap=new HashMap<>();
    public static void registerBeanAsObj2map(String simpleName, Object proxyObj) {
        beansMap.put(simpleName,proxyObj);
    }

    public static void registerBeanAsClz2map(String simpleName, Class proxyObj) {
        beansMap.put(simpleName,proxyObj);
    }

    public static <T>  T getBeanFrmBeanmap(String simpleName ) throws Throwable {
        T t = (T) beansMap.get(simpleName);

        if( t instanceof Class<?>)
        {
           Class c= (Class) t;
           return (T) c.getConstructor().newInstance();
        }

        //obj mode
        return t;
    }
}

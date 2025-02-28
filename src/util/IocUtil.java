package util;

import java.util.HashMap;
import java.util.Map;

public class IocUtil {

    public static   Map<String,Object> beansMap=new HashMap<>();
    public static void registerBean2map(String simpleName, Object proxyObj) {
        beansMap.put(simpleName,proxyObj);
    }

    public static <T>  T getBeanFrmBeanmap(String simpleName ) {
      return (T) beansMap.get(simpleName);
    }
}

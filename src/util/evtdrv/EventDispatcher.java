package util.evtdrv;

import util.algo.SupplierX;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

// static util.algo.GetUti.getMethod;
import static util.proxy.AopUtil.ivk4log;

public class EventDispatcher {
    private static Map<String, Set<EventListenerHdlr>> listenersMap = new HashMap<>();

    // 注册监听器
    public static void registerListener(String eventType, EventListenerHdlr listener) {
        listenersMap.computeIfAbsent(eventType, k -> new HashSet<>()).add(listener);
    }

    // 发布事件 eexe evt hdl
    public static void publishEvent(String eventType, Object dataArg) throws Throwable {
        Set<EventListenerHdlr> listeners = listenersMap.getOrDefault(eventType, Collections.emptySet());
        for (EventListenerHdlr listener : listeners) {


            SupplierX<Object> handleRequest1 = () -> {

                Object obj = newInsFromMethod(listener.handleRequestMthd);
                return listener.handleRequestMthd.invoke(obj, dataArg);

            };
            Object result = ivk4log(listener.funFullName, dataArg, handleRequest1);
        }
    }


    public static Object newInsFromMethod(Method method) {
        String fullname = "";
        try {
            // 获取方法所属的类
            Class<?> clazz = method.getDeclaringClass();
            fullname = clazz.getName() + "/" + method.getName();
            // 通过无参构造器创建实例
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("无法从方法创建实例,mth=" + fullname, e);
        }
    }
}

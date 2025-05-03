package util.ioc;

import com.fasterxml.jackson.databind.util.internal.PrivateMaxEntriesMap;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * ioc的实现，，，reg n getbean fun
 * ✅ 推荐设计：支持 Class 和 Object 双注册方式
 * 支持 singleton 和 prototype 两种模式
 */
public class SimpleContainer {
    private static final Map<String, Supplier<Object>> registryMap = new HashMap<>();
    public static final Map<String, Class> rgstMap4clz = new HashMap<>();


    /**
     * 注册 Class（类）【最常见】
     * @param beanNname
     * @param clazz
     */
    public static void rgstClz(String beanNname, Class clazz) {
        rgstMap4clz.put(beanNname, clazz);
    }

    // 注册对象构造方式（可支持懒加载）
    public static void registerInstance(String beanNname, Supplier<Object> creator) {
        registryMap.put(beanNname, creator);
    }

    // 获取对象实例（每次调用 new，或内部做缓存）
    @SuppressWarnings("unchecked")
    public static <T> T getObj(String beanname) {
        Supplier<Object> creator = registryMap.get(beanname);
        if (creator == null) throw new RuntimeException("No such bean: " + beanname);
        return (T) creator.get();
    }


    private Map<Class<?>, Object> singletonBeans = new HashMap<>();
    private Map<Class<?>, Class<?>> classRegistry = new HashMap<>();

    public void register(Class<?> iface, Class<?> impl) {
        classRegistry.put(iface, impl);
    }

    public void registerInstance(Class<?> clazz, Object instance) {
        singletonBeans.put(clazz, instance);
    }
    public <T> T resolve(Class<T> clazz) {
        // 已注册实例

        if (singletonBeans.containsKey(clazz)) {
            return clazz.cast(singletonBeans.get(clazz));
        }

        // 查找实现类
        Class<?> implClass = classRegistry.get(clazz);
        if (implClass == null) throw new RuntimeException("Not registered: " + clazz);

        // 递归创建构造函数参数
        Constructor<?> constructor = implClass.getConstructors()[0];
        Object[] args = Arrays.stream(constructor.getParameterTypes())
                .map(this::resolve)
                .toArray();

        try {
            T obj = clazz.cast(constructor.newInstance(args));
            singletonBeans.put(clazz, obj);
            return obj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


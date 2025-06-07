package util.misc;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class RflktUti {
    /**
     * 获取指定对象中名称为 methodName 的无参方法（支持 private、protected、public）
     *
     * @param obj        要查找方法的对象
     * @param methodName 方法名称
     * @return           反射得到的方法对象，找不到返回 null
     */
    public static Method getMethodByObj(Object obj, String methodName) {
        if (obj == null || methodName == null || methodName.isEmpty()) return null;
        Class<?> clazz = obj.getClass();

        //   while (clazz != null) {
        //      try {
        Method method = getMethod4faasFun(clazz, methodName);
        if (method != null) return method;

//            } catch (NoSuchMethodException e) {
//                // 向上查找父类
//                clazz = clazz.getSuperclass();
//            }
//        }

        return null; // 未找到
    }
    public static @Nullable List<Method> getMethods(@jakarta.validation.constraints.NotNull  Class clz, @NotBlank String methodName) {
        List<Method> msRtz=new ArrayList<>();
        Method[] ms=clz.getMethods();
        for(  Method method :ms)
        {
            if(method.getName().equals(methodName))
            {
                method.setAccessible(true); // 支持 private/protected 方法
                msRtz.add(method);
            }

        }



        return msRtz; // 未找到
    }


    public static @Nullable Method getMethod4faasFun(@jakarta.validation.constraints.NotNull  Class clz, @NotBlank String methodName) {
        List<Method> methods = getMethods(clz,methodName);
        if(methods.size()==1)
            return methods.get(0);

// mlt ms
        Method[] ms=clz.getMethods();
        for(  Method method :ms)
        {
            if(method.getName().equals(methodName))
            {
                if(method.getParameterTypes()[0].equals(Object.class))
                    continue;
                method.setAccessible(true); // 支持 private/protected 方法
                return method;
            }

        }



        return null; // 未找到
    }


//    @Nullable
//    private static Method getMethod(Class clz, String methodName) {
//        Method[] ms= clz.getMethods();
//        for(  Method method :ms)
//        {
//            if(method.getName().equals(methodName))
//            {
//                method.setAccessible(true); // 支持 private/protected 方法
//                return method;
//            }
//
//        }
//        return null;
//    }


    /**
     * 是否实现了某个接口
     *
     * @param target
     * @param ItfsClz
     * @return
     */
    public static boolean isImpltInterface(@NotNull Object target, @NotNull Class<?> ItfsClz) {
        if (target == null || ItfsClz == null || !ItfsClz.isInterface()) {
            return false;
        }
        Class<?> clazz = target.getClass();
        return isImpltItfs(clazz, ItfsClz);
    }

    public static boolean isImpltItfs(   Class<?> target, Class<?> ItfsClz) {

        while (target != null) {
            for (Class<?> iface : target.getInterfaces()) {
                if (iface.equals(ItfsClz)) {
                    return true;
                }
            }
            target = target.getSuperclass(); // 支持父类实现接口的情况
        }
        return false;
    }
}

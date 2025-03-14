package util.algo;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;



public class GetUti {
    public static Object getObjByMethod(Method m) {
        return getObject(m.getDeclaringClass());
    }


    //只针对api 和biz的开放注册修改class注入aop
    //clazz.getName() 只是获取类的全限定名（package.ClassName），不会触发类的静态初始化 或 类加载。

    @NotNull
    public static Object getObject(Class clazz) {
        Object obj1 = null;
        try {
            obj1 = clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return obj1;
    }
    /**
     * getType data type
     * ✅ 如果你想模仿 PHP，请使用 instanceof 方法。
     * ✅ 如果你只想要 Java 类型名，请使用 getClass().getSimpleName()。
     * @param obj
     * @return
     */
    public static String getType(Object obj) {
        if (obj == null) return "NULL";
        if (obj instanceof Integer) return "integer";
        if (obj instanceof Double || obj instanceof Float) return "double";
        if (obj instanceof String) return "string";
        if (obj instanceof Boolean) return "boolean";
        if (obj instanceof Character) return "char";
        if (obj instanceof Byte) return "byte";
        if (obj instanceof Short) return "short";
        if (obj instanceof Long) return "long";
        if (obj instanceof int[]) return "array (int[])";
        if (obj instanceof double[]) return "array (double[])";
        if (obj instanceof Object[]) return "array (Object[])";
        return "object"; // 其他对象类型
    }
}

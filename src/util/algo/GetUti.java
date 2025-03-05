package util.algo;

public class GetUti {

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

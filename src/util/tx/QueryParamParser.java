package util.tx;


import org.jetbrains.annotations.NotNull;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import static util.misc.Util2025.encodeJsonObj;

public class QueryParamParser {
//    public static <T> T toDto(HttpExchange exchange) {
//    //  Class<U> cls = null;
//        // Using reflection to get the class type (example, you might need a context to do this properly)
//        Type type = ((ParameterizedType) QueryParamParser.class.getGenericSuperclass()).getActualTypeArguments()[0];
//        Class<T> cls = (Class<T>) type; // You would need to handle Type properly here
//
//        Object o=toDto(exchange,cls);
//        return (T) o;
//    }


    public static void main(String[] args) {
        Map<String, String> paramMap=new HashMap<>();
       // paramMap.put("uname","33");
        paramMap.put("amt","33.23");
        // System.out.println(encodeJsonObj(toDto(paramMap, TransactionsPay.class)));
      //  System.out.println(encodeJsonObj(toDto(paramMap, Usr.class)));
    }


    @NotNull
    public static <T> T toDto(Map<String, String> paramMap, Class<T> usrClass){
        try {
            // 反射创建 DTO 实例
            T dto = usrClass.getDeclaredConstructor().newInstance();

            // 通过 JavaBean 反射机制设置属性值
            for (PropertyDescriptor pd : Introspector.getBeanInfo(usrClass).getPropertyDescriptors()) {
                String fieldName = pd.getName();
               // System.out.println("fld="+fieldName);
                if (paramMap.containsKey(fieldName)) {
                    String value = paramMap.get(fieldName);
                    Class<?> propertyType = pd.getPropertyType();
                    Object convertedValue = convertType(value, propertyType);  // 类型转换
                    //prop must has set mth,,beir err  ,get wrtmthd is null
                    pd.getWriteMethod().invoke(dto, convertedValue); // 反射调用 Setter
                }
            }
            return dto;

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException | IntrospectionException e) {
            throw new RuntimeException("解析查询参数失败: " + e.getMessage(), e);
        }
    }

    // 解析查询字符串为 Map
    private static Map<String, String> parseQueryParams(String query) {
        Map<String, String> paramMap = new HashMap<>();
        try {
            for (String param : query.split("&")) {
                String[] pair = param.split("=", 2);
                if (pair.length == 2) {
                    String key = URLDecoder.decode(pair[0], "UTF-8");
                    String value = URLDecoder.decode(pair[1], "UTF-8");
                    paramMap.put(key, value);
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("URL 解码失败", e);
        }
        return paramMap;
    }

    // 类型转换 (支持 int, long, double, boolean, String)
    private static Object convertType(@NotNull String value,@NotNull Class<?> targetType) {
        if (targetType == String.class) return value;
        if (targetType == int.class || targetType == Integer.class) return Integer.parseInt(value);
        if (targetType == long.class || targetType == Long.class) return Long.parseLong(value);
        if (targetType == BigDecimal.class) return new BigDecimal(value);
        if (targetType == double.class || targetType == Double.class) return Double.parseDouble(value);
        if (targetType == boolean.class || targetType == Boolean.class) return Boolean.parseBoolean(value);
        return null;
    }
}

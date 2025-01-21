package util;

import com.sun.net.httpserver.HttpExchange;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class util2026 {

    /**
     * 获取对象属性
     * @param obj
     * @param fieldName
     * @return
     */
    public static String getField2025(Object obj, String fieldName,String defval) {
        if (obj == null || fieldName == null || fieldName.isEmpty()) {
            return defval; // 防御性编程，处理无效参数
        }

        try {
            // 获取类定义
            Class<?> clazz = obj.getClass();

            // 查找字段，包括私有字段
            Field field = clazz.getDeclaredField(fieldName);

            // 如果是私有字段，取消访问限制
            field.setAccessible(true);

            // 获取字段值并转换为字符串
            Object value = field.get(obj);
            return value != null ? value.toString() : defval;
        } catch (NoSuchFieldException e) {
            System.err.println("Field '" + fieldName + "' not found in class: " + obj.getClass().getName());
        } catch (IllegalAccessException e) {
            System.err.println("Unable to access field '" + fieldName + "' in class: " + obj.getClass().getName());
        } catch (SecurityException e) {
            System.err.println("Access to field '" + fieldName + "' is restricted: " + e.getMessage());
        }
        return  defval;
    }
    public static void wrtResp(HttpExchange exchange, String responseTxt) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=utf-8");
        exchange.sendResponseHeaders(200, responseTxt.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(responseTxt.getBytes());
        os.close();
    }

    public static String getRequestParameter(HttpExchange exchange, String name) {
        Map<String, String> queryParams = parseQueryParams(exchange.getRequestURI());
        String id = queryParams.get(name); // 获取 id 参数
        return id;
    }

    // 解析查询参数的方法
    public static Map<String, String> parseQueryParams(URI uri) {
        Map<String, String> queryParams = new HashMap<>();
        String query = uri.getQuery(); // 获取查询字符串（例如 ?id=123）
        if (query != null) {
            String[] pairs = query.split("&"); // 按 & 分割多个参数
            for (String pair : pairs) {
                String[] keyValue = pair.split("=", 2); // 按 = 分割键值对
                if (keyValue.length == 2) {
                    queryParams.put(keyValue[0], keyValue[1]);
                } else {
                    queryParams.put(keyValue[0], ""); // 没有值时设为空字符串
                }
            }
        }
        return queryParams;
    }
}

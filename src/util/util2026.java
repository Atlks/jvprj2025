package util;

import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.net.HttpCookie;
import java.util.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class util2026 {

    public static String getcookie(String cookieName, HttpExchange exchange) {
        // 获取请求头中的 Cookie
        List<String> cookieHeaders = exchange.getRequestHeaders().get("Cookie");
        if (cookieHeaders == null || cookieHeaders.isEmpty()) {
            return ""; // 没有 Cookie
        }

        // 遍历 Cookie 头，查找指定名称的 Cookie
        for (String cookieHeader : cookieHeaders) {
            List<HttpCookie> cookies = HttpCookie.parse(cookieHeader);
            for (HttpCookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue(); // 找到指定名称的 Cookie，返回值
                }
            }
        }

        // 如果没有找到匹配的 Cookie
        return "";

    }


    /**
     * 解析简单的 INI 配置文件（没有节）
     *
     * @param filePath 配置文件的路径
     * @return 解析后的 Map，键值对的形式
     */
    public static Map<String, String> parse_ini_file(String filePath) {
        Map<String, String> result = new HashMap<>();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // 忽略空行和注释行
                if (line.isEmpty() || line.startsWith(";") || line.startsWith("#")) {
                    continue;
                }

                // 处理键值对，格式为 key = value
                if (line.contains("=")) {
                    String[] parts = line.split("=", 2);
                    if (parts.length == 2) {
                        String key = parts[0].trim();
                        String value = parts[1].trim();
                        result.put(key, value);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
    public static void setcookie(String name, String val,HttpExchange exchange) {
        // 创建 Set-Cookie 头部内容

     //   String cookie2 = "uname1=" + uname1 + "; Path=/; ";


        // 获取当前时间，并设置半年后的时间戳（以 Expires 为参考）
        long halfYearInSeconds = 182L * 24 * 60 * 60;
        long expiryTimeInMillis = System.currentTimeMillis() + (halfYearInSeconds * 1000);
        String expiresDate = new java.text.SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'")
                .format(new java.util.Date(expiryTimeInMillis));

        // 创建 Set-Cookie 头部内容
        //String cookie1 = "uname=" + uname + "; Path=/; HttpOnly;
        String cookie1 = name+"=" + val + "; Path=/;  Max-Age=" + halfYearInSeconds + "; Expires=" + expiresDate;
        // 设置响应头中的 Set-Cookie
        exchange.getResponseHeaders().add("Set-Cookie", cookie1);
     //   exchange.getResponseHeaders().add("Set-Cookie", cookie2);

    }
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

package util;

import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.HttpCookie;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class util2026 {

    /**
     * 解析 INI 配置文件
     *
     * @param filePath 配置文件的路径
     * @return 解析后的 Map，键是节名称，值是包含键值对的 Map
     */
    private static Map<String, Map<String, String>> parse_ini_file
    (String filePath) {
        Map<String, Map<String, String>> result = new HashMap<>();
        BufferedReader reader = null;
        String currentSection = null;
        Map<String, String> currentSectionMap = null;

        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // 忽略空行和注释行
                if (line.isEmpty() || line.startsWith(";") || line.startsWith("#")) {
                    continue;
                }

                // 处理节标头，节的格式是 [section]
                if (line.startsWith("[") && line.endsWith("]")) {
                    // 获取节名称
                    currentSection = line.substring(1, line.length() - 1).trim();
                    currentSectionMap = new HashMap<>();
                    result.put(currentSection, currentSectionMap);
                }
                // 处理键值对，格式为 key = value
                else if (line.contains("=")) {
                    String[] parts = line.split("=", 2);
                    if (parts.length == 2) {
                        String key = parts[0].trim();
                        String value = parts[1].trim();
                        if (currentSectionMap != null) {
                            currentSectionMap.put(key, value);
                        }
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

    /**
     * 获取当前本地时间，并格式化为字符串。
     *
     * @return 格式化的本地时间字符串
     */
    public static String getLocalTimeString() {
        // 获取当前本地时间
        LocalDateTime now = LocalDateTime.now();

        // 定义时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 格式化时间并返回
        return now.format(formatter);
    }

    /**
     * 将异常的堆栈跟踪转换为字符串
     *
     * @param e 异常对象
     * @return 堆栈跟踪的字符串表示
     */
    public static String getStackTraceAsString(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

    public static String getFilenameFrmLocalTimeString() {
        // 获取当前本地时间
        LocalDateTime now = LocalDateTime.now();

        // 定义时间格式
        // 定义时间格式，使用"-"代替":"以避免在文件名中使用非法字符
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm-ss");


        // 格式化时间并返回
        return now.format(formatter);
    }

    public static boolean isSqldb(String saveUrl) {

        if (saveUrl.startsWith("jdbc:mysql") || saveUrl.startsWith("jdbc:sqlite"))
            return true;
        return false;
    }

    public static String getcookie(String cookieName, HttpExchange exchange) {
        // 获取请求头中的 Cookie
        List<String> cookieHeaders = exchange.getRequestHeaders().get("Cookie");
        if (cookieHeaders == null || cookieHeaders.isEmpty()) {
            return ""; // 没有 Cookie
        }

        // 遍历 Cookie 头，查找指定名称的 Cookie
        //cookhe ==>  Phpstorm-5391d420=e327af09-5544-404b-9cfc-5fe811cc8b38; Idea-c6a5dffd=3faa2c4e-16f5-4b3e-83f3-41d9866af1e1; uname=ttt
        for (String cookieHeader : cookieHeaders) {

            List<HttpCookie> cookies = HttpCookie_parse(cookieHeader);
            for (HttpCookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue(); // 找到指定名称的 Cookie，返回值
                }
            }
        }

        // 如果没有找到匹配的 Cookie
        return "";

    }

    public static List<HttpCookie> HttpCookie_parse(String cookieHeader) {
        List<HttpCookie> list = new ArrayList<>();
        // 分割每个 Cookie 头中的多个 Cookie
        String[] cookies = cookieHeader.split(";\\s*");
        for (String cookie : cookies) {
            // 分割 Cookie 的名称和值
            String[] cookieParts = cookie.split("=", 2);
            if (cookieParts.length == 2) {
                String name = cookieParts[0];
                String value = cookieParts[1];
                HttpCookie ck = new HttpCookie(name, value);
                list.add(ck);
            }
        }
        return list;
    }


    /**
     * 解析简单的 INI 配置文件（没有节）
     *
     * @param filePath 配置文件的路径
     * @return 解析后的 Map，键值对的形式
     */
    public static Map<String, String> parse_ini_fileNosec(String filePath) {
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

    public static void setcookie(String name, String val, HttpExchange exchange) {
        // 创建 Set-Cookie 头部内容

        //   String cookie2 = "uname1=" + uname1 + "; Path=/; ";


        // 获取当前时间，并设置半年后的时间戳（以 Expires 为参考）
        long halfYearInSeconds = 182L * 24 * 60 * 60;
        long expiryTimeInMillis = System.currentTimeMillis() + (halfYearInSeconds * 1000);
        //周六, 26 7月 2025 13:14:05 GMT
        String expiresDate = new java.text.SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'")
                .format(new java.util.Date(expiryTimeInMillis));

        // 创建 Set-Cookie 头部内容
        //String cookie1 = "uname=" + uname + "; Path=/; HttpOnly;
        String cookie1 = name + "=" + val + "; Path=/;  Max-Age=" + halfYearInSeconds + ";";
        //    " Expires=" + expiresDate;
        // 设置响应头中的 Set-Cookie
        exchange.getResponseHeaders().add("Set-Cookie", cookie1);
        //   exchange.getResponseHeaders().add("Set-Cookie", cookie2);

    }

    public static void throwEx(Exception e) {
        if (e instanceof RuntimeException)
            throw (RuntimeException) e;
        throw new RuntimeException(e);

    }

    public static BigDecimal getFieldAsBigDecimal(Object obj, String fieldName, float defval) {
        Object o = getField2025(obj, fieldName, String.valueOf(defval));
        if (o.toString().equals(""))
            return new BigDecimal(defval);
        return new BigDecimal(o.toString());
    }

    /**
     * 获取对象属性
     *
     * @param obj
     * @param fieldName
     * @return
     */
    public static Object getField2025(Object obj, String fieldName, String defval) {
        if (obj == null || fieldName == null || fieldName.isEmpty()) {
            return defval; // 防御性编程，处理无效参数
        }
        if (obj instanceof Map) {
            Object o = ((Map) obj).get(fieldName);
            if (o == null)
                o = "";
            return o;
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
        return defval;
    }

    /**
     * 复制属性
     *
     * @param <T>
     */
    static <T> void copyProps(T source, T target) {

        if (target == null || source == null) {
            throw new IllegalArgumentException("目标对象和源对象不能为空");
        }

        Class<?> clazz = source.getClass();
        while (clazz != null) { // 处理继承层级
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    Object value = field.get(source);
                    field.set(target, value);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("无法访问字段: " + field.getName(), e);
                }
            }
            clazz = clazz.getSuperclass(); // 继续处理父类字段
        }
    }

    public static Object getField2025(Object obj, String fieldName) throws Exception {
        if (obj == null || fieldName == null || fieldName.isEmpty()) {
            // 防御性编程，处理无效参数
            throw new RuntimeException("some prm is null or empty");
        }
//        if(obj instanceof Map)
//        {
//            Object o = ((Map) obj).get(fieldName);
//            if(o==null)
//                o="";
//            return o;
//        }


        // 获取类定义
        Class<?> clazz = obj.getClass();

        // 查找字段，包括私有字段
        Field field = clazz.getDeclaredField(fieldName);

        // 如果是私有字段，取消访问限制
        field.setAccessible(true);

        // 获取字段值并转换为字符串
        Object value = field.get(obj);


        return value;
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

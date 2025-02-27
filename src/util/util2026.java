package util;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import javassist.*;

import java.io.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.HttpCookie;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

import static cfg.AopLogJavassist.lock;
import static util.ToXX.parseQueryParams;
import static util.Util2025.encodeJson;

public class util2026 {

    public static void main(String[] args) {
        System.out.println(encodeJson(11));
    }

    public static Object getField(Object instance, String fieldName) {

        if (instance == null || fieldName == null || fieldName.isEmpty()) {
            throw new IllegalArgumentException("实例或字段名不能为空");
        }
        try {
            Class<?> clazz = instance.getClass();
            Field field = clazz.getField(fieldName);
            field.setAccessible(true); // 允许访问私有字段
            return field.get(instance);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("获取字段失败: " + fieldName, e);
        }
    }
    public static void scanClasses(File dir, String basePath, List<Class<?>> classList) {
        File[] files = dir.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                scanClasses(file, basePath, classList);
            } else if (file.getName().endsWith(".class")) {
                String className = file.getAbsolutePath()
                        .replace(basePath, "")
                        .replace(".class", "")
                        .replace(File.separator, ".");
                if (className.startsWith(".")) {
                    className = className.substring(1);
                }
                try {
                    Class<?> clazz = Class.forName(className);
                    classList.add(clazz);
                } catch (ClassNotFoundException e) {
                    System.err.println("加载失败: " + className);
                } catch (Throwable e) {
                    System.err.println("加载失败: " + className);
                }
            }
        }
    }


    public static void removeFile(String fnamePath) {
        File file = new File(fnamePath);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("文件已成功删除: " + fnamePath);
            } else {
                System.err.println("文件删除失败: " + fnamePath);
            }
        } else {
            System.err.println("文件不存在: " + fnamePath);
        }
    }
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
     * 获取方法名称，，兼容实例方法
     * @param methodRef
     * @return
     * @param <T>
     * @param <R>
     */
    /**
     * 获取方法名称，兼容实例方法
     * @param methodRef
     * @return
     * @param <T>
     * @param <R>
     */
    /**
     * 获取方法名称，兼容实例方法

     * @return
     * @param <T>
     * @param <R>
     */
    public static <T, R> String nameofSingleParam(Function<T, R> function) {
        try {
            // 获取 SerializedLambda
            Method writeReplace = function.getClass().getDeclaredMethod("writeReplace");
            writeReplace.setAccessible(true);
            SerializedLambda serializedLambda = (SerializedLambda) writeReplace.invoke(function);

            // 获取方法名
            String implMethodName = serializedLambda.getImplMethodName();

            return implMethodName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//    public static <T, R> String nameofSnglPrm(Function<T, R> methodRef) {
//        try {
//            Method method = methodRef.getClass().getDeclaredMethod("writeReplace");
//            method.setAccessible(true);
//            SerializedLambda lambda = (SerializedLambda) method.invoke(methodRef);
//            return lambda.getImplMethodName();
//        } catch (Exception e) {
//            throw new RuntimeException("无法解析方法名称", e);
//        }
//    }
    public static <T> String nameofSnglPrmV2(Consumer<T> methodRef) {
        try {
            Method method = MethodHandles.lookup()
                    .revealDirect((MethodHandle) methodRef)
                    .reflectAs(Method.class, MethodHandles.lookup());
            return method.getName();
        } catch (Exception e) {
            throw new RuntimeException("无法获取方法名称", e);
        }
    }
    public static String nameof(Function<?, ?> methodRef) {
        try {
            Method method = methodRef.getClass().getDeclaredMethods()[0];
            return method.getName();
        } catch (Exception e) {
            throw new RuntimeException("无法获取方法名称", e);
        }
    }
    /**
     * 将异常的堆栈跟踪转换为字符串
     *
     * @param e 异常对象
     * @return 堆栈跟踪的字符串表示
     */
    public static String getStackTraceAsString(Throwable e) {
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

        if (saveUrl.startsWith("jdbc:") || saveUrl.startsWith("jdbc:"))
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
    //  这里没有增加  声明 serialVersionUID
    private static void srlzCtCls(CtClass ctClass, ClassPool pool) throws NotFoundException, CannotCompileException {
        boolean isSerializable = false;
        for (CtClass iface : ctClass.getInterfaces()) {
            if (iface.getName().equals("java.io.Serializable")) {
                isSerializable = true;
                break;
            }
        }

        // 如果未实现，则添加 Serializable 接口
        if (!isSerializable) {
            //  ctClass.addInterface(serializable);
            ctClass.addInterface(pool.get("java.io.Serializable"));
            System.out.println(ctClass.getName() + " 已添加 Serializable 接口");
        } else {
            System.out.println(ctClass.getName() + " 已经实现 Serializable，无需添加");
        }


        // **保持旧的 serialVersionUID**
        long serialVersionUIDValue = 9164029442537620054L; // 这里使用旧的值
        try {
            CtField uidField = ctClass.getDeclaredField("serialVersionUID");
            System.out.println("已有 serialVersionUID: " + uidField);
        } catch (NotFoundException e) {
            CtField serialVersionUID = new CtField(CtClass.longType, "serialVersionUID", ctClass);
            serialVersionUID.setModifiers(Modifier.PRIVATE | Modifier.STATIC | Modifier.FINAL);
            ctClass.addField(serialVersionUID, CtField.Initializer.constant(serialVersionUIDValue)); // 设定固定值
            System.out.println(ctClass.getName() + " 已添加 serialVersionUID = " + serialVersionUIDValue);
        }
    }

    public static void printLn(String msg) {
        synchronized (lock) {
            PrintWriter writer = new PrintWriter(System.out, true);  // 自动刷新

            writer.println(msg);
            System.out.flush();  // 刷新输出缓冲区
            System.err.flush();  // 刷新输出缓冲区
        }
    }
    // **序列化对象**
    private static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(obj);
        return bos.toByteArray();
    }

    // **反序列化对象**
    private static Object deserialize(byte[] data, ClassLoader classLoader) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        ObjectInputStream in = new ObjectInputStream(bis) {
            @Override
            protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
                return Class.forName(desc.getName(), true, classLoader);
            }
        };
        return in.readObject();
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
                .format(new Date(expiryTimeInMillis));

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
     * 复制属性  ing null prop
     *
     * @param <T>
     */
    public static <T> void copyProps(T source, T target) {

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
                    if(value!=null)
                    field.set(target, value);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("无法访问字段: " + field.getName(), e);
                }
            }
            clazz = clazz.getSuperclass(); // 继续处理父类字段
        }
    }
//    publicpublic static String getFieldAsStrFrmMap(Map<String, String> queryParams, String uname) {
//        return  queryParams.getOrDefault(uname,"");
//    }
    public static Object getField2025(Object obj, String fieldName) throws NoSuchFieldException,Exception {
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
    public static String getCurrentMethodName() {
        return StackWalker.getInstance()
                .walk(frames -> frames.skip(1).findFirst().get().getMethodName());
    }
    public static void wrtRespErrNoex(HttpExchange exchange, String responseTxt){
        try {
            wrtRespErr(exchange,responseTxt);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
    public static void wrtRespErr(HttpExchange exchange, String responseTxt) throws IOException {
        System.out.println("wrtRespErr(resptxt="+responseTxt);
        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=utf-8");
        byte[] responseBytes = responseTxt.getBytes(StandardCharsets.UTF_8);
        int statusCode=500;
        exchange.sendResponseHeaders(statusCode, responseBytes.length);

        try (OutputStream os = exchange.getResponseBody()) { // try-with-resources
            os.write(responseBytes);
        } catch (IOException e) {
            System.err.println("Error writing response: " + e.getMessage()); // Use System.err for errors
            // 或者使用日志框架进行更详细的日志记录
        }



    }



    public static void wrtResp(HttpExchange exchange, String responseTxt) throws IOException {

        System.out.println("wrtResp(resptxt="+responseTxt);
      if( responseTxt==null)
          responseTxt="";

        // 设置跨域响应头
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*"); // 允许所有来源
        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=utf-8");
        exchange.sendResponseHeaders(200, responseTxt.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(responseTxt.getBytes());
        os.close();
    }



    public static String getSqlPrmVal(Object o) {
        if(o.getClass()==String.class)
            return  "'"+o.toString()+"'";
        return  o.toString();
    }

    public static void iniHttpExchg(HttpExchangeImp he, Map<String, String> reqhdr, String ResponseBodyoutputStreamF) throws FileNotFoundException {
        he.setRequestHeaders(reqhdr);
        he.setResponseHeaders(new Headers());
        OutputStream outputStream = new FileOutputStream(ResponseBodyoutputStreamF); // 创建一个输出流
        he.setResponseBody(outputStream);
    }
    public static String getFieldAsStrFrmMap(Map<String, String> queryParams, String uname) {
        return  queryParams.getOrDefault(uname,"");
    }
    public static String getRequestParameter(HttpExchange exchange, String name) {
        Map<String, String> queryParams = parseQueryParams(exchange.getRequestURI());
        String id = queryParams.get(name); // 获取 id 参数
        return id;
    }

    public static Map<String, String> getRequestParameters(HttpExchange exchange) {
       return  parseQueryParams(exchange.getRequestURI());
               //getRequestParameters(exchange.getRequestURI());
    }



}

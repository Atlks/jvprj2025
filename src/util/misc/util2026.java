package util.misc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import util.algo.JarClassScanner;
import util.ex.PwdNotEqExceptn;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import jakarta.validation.constraints.NotNull;
import javassist.*;
import util.auth.IsEmptyEx;
import util.oo.UserBiz;

import java.io.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.HttpCookie;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.CodeSource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static biz.Containr.curCtrlCls;
import static cfg.AopLogJavassist.lock;
import static java.time.LocalTime.now;
import static util.algo.EncodeUtil.decodeUrl;
import static util.algo.EncodeUtil.encodeUrl;

import static util.algo.JarClassScanner.getCurrentJarPath;
import static util.algo.NullUtil.isBlank;
import static util.algo.ToXX.parseQueryParams;
import static util.misc.Util2025.encodeJson;
import static util.misc.Util2025.printlnx;
import static util.tx.dbutil.setField;

public class util2026 {
    public static String slt4pwd = "slt2025";

    public static void copyCookieToDto(HttpExchange HttpExchange1, List<String> cookieParams, Object dto) throws IsEmptyEx {
        for (String cknm : cookieParams) {
            String v = getcookie(cknm, HttpExchange1);
            setField(dto, cknm, v);
        }


    }

    public static void main(String[] args) {
        System.out.println(encodeJson(11));
    }

    public static Object getField(Object instance, String fieldName) {

        if (instance == null || fieldName == null || fieldName.isEmpty()) {
            throw new IllegalArgumentException("实例或字段名不能为空");
        }
        try {
            Class<?> clazz = instance.getClass();
            Field field = clazz.getField(fieldName);//only pub fld
            field.setAccessible(true); // 允许访问私有字段
            return field.get(instance);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("获取字段失败,must pub fld: " + fieldName, e);
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
     *
     * @param <T>
     * @param <R>
     * @return
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
            return "";
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

    public static String getcookieDecry(String cookieName, HttpExchange exchange) throws Exception {
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
                    String value = cookie.getValue();

                    return value; // 找到指定名称的 Cookie，返回值
                }
            }
        }

        // 如果没有找到匹配的 Cookie
        return "";

    }

    public static String getcookie(String cookieName, HttpExchange exchange) throws IsEmptyEx {
        String cookie = getcookieDp(cookieName, exchange);
        chkCantBeEmpty(cookieName, cookie);
        return cookie;
    }


    public static String getcookieDp(String cookieName, HttpExchange exchange) {
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

    //MutablePicoContainer

    /**
     * 扫描jar包里面  所有class，加入到容器
     * 要扫描 JAR 文件内部 的 class，需要使用 URLClassLoader 或 JarFile 来读取 jar 内部的 .class 文
     */
    public static <T> void scanAllClass(Consumer<T> csmr) {
        System.out.println("scannAllcls at " + now());
        // 获取当前 JAR 文件路径
        String jarPath = getCurrentJarPath();
        if (jarPath.endsWith(".jar"))
            scanAllClzByJarMode(csmr);
        else
            scanAllClassByClsesDir(csmr);
    }

    /**
     * need extednd libjar clz
     *
     * @param csmr
     * @param <T>
     */
    private static <T> void scanAllClzByJarMode(Consumer<T> csmr) {
        // 获取当前 JAR 文件路径
        String jarPath = getCurrentJarPath();
        if (jarPath == null) {
            System.err.println("无法获取当前 JAR 路径");
            return;
        }

        try (JarFile jarFile = new JarFile(jarPath)) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String nameEntry = entry.getName();
                if (nameEntry.endsWith(".class") &&

                        (!nameEntry.startsWith("jnr/")) &&
                        (!nameEntry.startsWith("ch/")) &&
                        (!nameEntry.startsWith("io/")) &&
                        (!nameEntry.startsWith("com/")) &&
                        (!nameEntry.startsWith("org/")) &&
                        (!nameEntry.startsWith("net/"))
                        &&
                        (!nameEntry.startsWith("jakarta/"))

                        &&
                        (!nameEntry.startsWith("javax/"))
                        &&
                        (!nameEntry.startsWith("META-INF/"))

                        &&
                        (!nameEntry.startsWith("kotlin/"))

                        &&
                        (!nameEntry.startsWith("kotlinx/"))
                        &&
                        (!nameEntry.startsWith("okhttp3/"))
                        &&
                        (!nameEntry.startsWith("okio/"))
                        &&
                        (!nameEntry.startsWith("javassist/"))

                        &&
                        (!nameEntry.startsWith("reactor/"))
                        &&


                        (!nameEntry.startsWith("javafx/"))
                        &&
                        (!nameEntry.startsWith("liquibase/"))


                ) {
                    String className = nameEntry
                            .replace("/", ".")
                            .replace(".class", "");
                    try {
                        Class<?> clazz = Class.forName(className);
                        csmr.accept((T) clazz);
                    } catch (Throwable e) {
                        System.out.println("waning:" + e.getMessage());
                        appendFile("scanAllClzByJarMode.log", "waning ex:  className=" + className);
                    }


                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throwEx(e);
        }
    }

    private static void appendFile(String file, String txt) {
        try {
            txt = txt + "\r\n";
            Files.write(Path.of(file), txt.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace(); // 或者日志记录
        }

    }


    /**
     * 递归扫描普通文件夹中的 class 文件
     *
     * @param csmr
     * @param <T>
     */
    public static <T> void scanAllClassByClsesDir(Consumer<T> csmr) {
        System.out.println("scannAllcls at " + now());
        try {
            // 获取 classes 目录
            String classpath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
            File classDir = new File(classpath);
            if (!classDir.exists() || !classDir.isDirectory()) {
                System.err.println("classes 目录不存在！");
                return;
            }

            // 递归扫描 .class 文件
            List<Class<?>> classList = new ArrayList<>();
            scanClasses(classDir, classDir.getAbsolutePath(), classList);

            // 注册到 Container
            for (Class<?> clazz : classList) {
                // try {
                csmr.accept((T) clazz);

            }
        } catch (Exception e) {
            e.printStackTrace();
            throwEx(e);
        }
    }


//                } catch (Exception e) {
//                    printLn("spr注册失败: " + clazz.getName());
//                    printLn("spr注册失败msg: " + e.getMessage());
//
//                }

    //-----------------jvvst mode new class
//                    Class<?> modifiedClass = getAClassAoped(clazz);
//                    context.registerBean(modifiedClass);
//                    context.registerBean(modifiedClass.getName(), modifiedClass);
////                   //  context.register(modifiedClass);  jeig bhao,,beanname not classname
    /**
     * 扫描classes路径所有class，
     */
    public static void scanAllClass(Function f) {
        try {
            // 获取 classes 目录
            String classpath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
            File classDir = new File(classpath);
            if (!classDir.exists() || !classDir.isDirectory()) {
                System.err.println("classes 目录不存在！");
                return;
            }

            // 递归扫描 .class 文件
            List<Class<?>> classList = new ArrayList<>();
            scanClasses(classDir, classDir.getAbsolutePath(), classList);

            // 注册到 PicoContainer
            for (Class<?> clazz : classList) {
                try {
                    f.apply(clazz);
                    //   container888.addComponent(clazz);
                    //   System.out.println("f.aply: " + clazz.getName());
                } catch (Exception e) {
                    System.err.println("apply失败: " + clazz.getName());
                    System.err.println("apply失败msg: " + e.getMessage());
                    //  System.err.println("注册失败: " + clazz.getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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


    //no delay
    public static void printLn(String msg) {
        //  printlnx();
        synchronized (System.out) {
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
                value = decodeUrl(value);
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

    public static Map<String, String> parse_ini_fileNosecByJarPath(String filePath) {
        System.out.println("fun parse_ini_fileNosecByJarPath(path=" + filePath);
        InputStream inputStream = UserBiz.class.getClassLoader().getResourceAsStream(filePath);

        return parse_ini_fileNosecByStream(inputStream);
    }


    public static Map<String, String> parse_ini_fileNosecByStream(@NotNull InputStream inputStream) {
        //  System.out.println("fun parse_ini_fileNosecByStream(path="+filePath);
        Map<String, String> result = new HashMap<>();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(inputStream));
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


    public static Map<String, String> parse_ini_fileNosecByPath(Path filePath) {
        System.out.println("fun parse ini by path(path=" + filePath);
        Map<String, String> result = new HashMap<>();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(String.valueOf(filePath)));
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
        String cookie1 = name + "=" + encodeUrl(val) + "; Path=/;  Max-Age=" + halfYearInSeconds + ";";
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
     * if fld prvt ,cant copy ,so must fld is public need
     *
     * @param <T>
     */
    public static <T> void copyProps(T source, T target) {

        if (target == null || source == null) {
            throw new IllegalArgumentException("目标对象和源对象不能为空");
        }

        Class<?> clazz = target.getClass();
        while (clazz != null) { // 处理继承层级
            Field[] fields = clazz.getFields();
            //fx here chg to fields,,bcs  page reqdto need extend
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                } catch (InaccessibleObjectException e) {
                    continue;
                }

                try {
                    if (field.getName().contains("password"))
                        System.out.println("D1241");
                    if (!isObjHasField(source, field.getName()))
                        continue;
                    Object valueFrmSourceObj = getField2025(source, field.getName());
                    if (valueFrmSourceObj == null)
                        continue;

                    Object fldTypeObj = toFldType(valueFrmSourceObj, field.getType());
                    if (fldTypeObj.getClass() == String.class) {
                        if (!isBlank(fldTypeObj))
                            field.set(target, fldTypeObj);
                    } else
                        field.set(target, fldTypeObj);


                } catch (Exception e) {
                    throw new RuntimeException("无法访问字段: " + field.getName(), e);
                }
            }
            clazz = clazz.getSuperclass(); // 继续处理父类字段
        }
    }

    //判断对象是不是有field属性
    private static <T> boolean isObjHasField(T target, String name) {

        // 获取目标对象的类
        Class<?> clazz = target.getClass();

        // 遍历类的所有字段
        try {

            if (name.equals("page"))
                System.out.println("d135");
            // 尝试获取指定名称的字段
            Field field = clazz.getField(name);
            return field != null;
        } catch (NoSuchFieldException e) {
            // 如果找不到该字段，则抛出异常，返回false
            return false;
        }
    }


    //将值转换为相关的类型
    private static Object toFldType(Object value, Class<?> type) {
        if (value == null) {
            return null;  // 如果值为空，返回null
        }

//        if (type.isAssignableFrom(value.getClass())) {
//            return value;  // 如果值的类型已经与目标类型兼容，直接返回
//        }

        if (type == Integer.class || type == int.class) {
            return Integer.parseInt(value.toString());
        } else if (type == Long.class || type == long.class) {
            return Long.parseLong(value.toString());
        } else if (type == Double.class || type == double.class) {
            return Double.parseDouble(value.toString());
        } else if (type == Float.class || type == float.class) {
            return Float.parseFloat(value.toString());
        } else if (type == Boolean.class || type == boolean.class) {
            return Boolean.parseBoolean(value.toString());
        } else if (type == String.class) {
            return value.toString();
        } else if (type == Date.class) {
            // 如果是日期类型，可以使用SimpleDateFormat进行转换
            try {
                return new SimpleDateFormat("yyyy-MM-dd").parse(value.toString());
            } catch (ParseException e) {
                throw new IllegalArgumentException("Invalid date format");
            }
        } else
            return value;

        // 如果无法识别的类型，抛出异常
//        throw new IllegalArgumentException("Unsupported target type: " + type.getName());
    }


    public static void chkCantBeEmpty(@NotNull String caller, @NotNull String v) throws IsEmptyEx {
        if (caller.equals("")) {
            //  NeedLoginEx e = new NeedLoginEx("","BaseHdr." + getCurrentMethodName(),credential2);

            throw new IsEmptyEx("var(" + caller + ") is blank");
        }
    }

    //    publicpublic static String getFieldAsStrFrmMap(Map<String, String> queryParams, String uname) {
//        return  queryParams.getOrDefault(uname,"");
//    }
    public static Object getField2025(Object obj, String fieldName) throws NoSuchFieldException, Exception {
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
        Field field = clazz.getField(fieldName);

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

    public static void hopePwdEq(String pwd, String encryPwdInCrdt) throws PwdNotEqExceptn {
        if (!pwd.equals(encryPwdInCrdt))
            throw new PwdNotEqExceptn("");
    }

    public static void wrtRespErrNoex(HttpExchange exchange, String responseTxt) {
        try {
            wrtRespErr(exchange, responseTxt);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public static void wrtRespErr(HttpExchange exchange, String responseTxt) throws IOException {
        System.out.println("wrtRespErr(resptxt=" + responseTxt);
        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=utf-8");
        byte[] responseBytes = responseTxt.getBytes(StandardCharsets.UTF_8);
        int statusCode = 500;
        exchange.sendResponseHeaders(statusCode, responseBytes.length);

        try (OutputStream os = exchange.getResponseBody()) { // try-with-resources
            os.write(responseBytes);
        } catch (IOException e) {
            System.err.println("Error writing response: " + e.getMessage()); // Use System.err for errors
            // 或者使用日志框架进行更详细的日志记录
        }


    }


    public static void sleep(int i) throws InterruptedException {
        Thread.sleep(i);
    }

    /**
     * if restcontrole ret json mode,,,   controle mode,ret html mode
     *
     * @param exchange
     * @param responseTxt
     * @throws IOException
     */
    public static void wrtResp(HttpExchange exchange, String responseTxt) throws IOException {

        System.out.println("wrtResp(resptxt=" + responseTxt);
        if (responseTxt == null)
            responseTxt = "";

        // 设置跨域响应头
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");

        Class ctrlCls = curCtrlCls.get();
        if (ctrlCls != null && ctrlCls.isAnnotationPresent(Controller.class)) {
            // 输出为html
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=utf-8");
        } else {
            //输出为json
            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        }


        exchange.sendResponseHeaders(200, responseTxt.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(responseTxt.getBytes());
        os.close();
    }


    public static String getSqlPrmVal(Object o) {
        if (o.getClass() == String.class)
            return "'" + o.toString() + "'";
        return o.toString();
    }

    public static void iniHttpExchg(HttpExchangeImp he, Map<String, String> reqhdr, String ResponseBodyoutputStreamF) throws FileNotFoundException {
        he.setRequestHeaders(reqhdr);
        he.setResponseHeaders(new Headers());
        OutputStream outputStream = new FileOutputStream(ResponseBodyoutputStreamF); // 创建一个输出流
        he.setResponseBody(outputStream);
    }

    public static String getFieldAsStrFrmMap(Map<String, String> queryParams, String uname) {
        return queryParams.getOrDefault(uname, "");
    }

    public static String getRequestParameter(HttpExchange exchange, String name) {
        Map<String, String> queryParams = parseQueryParams(exchange.getRequestURI());
        String id = queryParams.get(name); // 获取 id 参数
        return id;
    }

    public static Map<String, String> getRequestParameters(HttpExchange exchange) {
        return parseQueryParams(exchange.getRequestURI());
        //getRequestParameters(exchange.getRequestURI());
    }


}

package util.algo;

import com.sun.net.httpserver.HttpExchange;
import jakarta.validation.constraints.NotBlank;
import org.jetbrains.annotations.NotNull;
import org.springframework.retry.annotation.CircuitBreaker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;

import static util.algo.IndexOfUti.indexOfFirst;


public class GetUti {



    /**
     * 获取指定对象中名称为 methodName 的无参方法（支持 private、protected、public）
     *
     * @param obj        要查找方法的对象
     * @param methodName 方法名称
     * @return           反射得到的方法对象，找不到返回 null
     */
    public static Method getMethod(Object obj, String methodName) {
        if (obj == null || methodName == null || methodName.isEmpty()) return null;
        Class<?> clazz = obj.getClass();

        //   while (clazz != null) {
        //      try {
        Method[] ms=obj.getClass().getMethods();
        for(  Method method :ms)
        {
            if(method.getName().equals(methodName))
            {
                method.setAccessible(true); // 支持 private/protected 方法
                return method;
            }

        }

//            } catch (NoSuchMethodException e) {
//                // 向上查找父类
//                clazz = clazz.getSuperclass();
//            }
//        }

        return null; // 未找到
    }


    public static String  getUuid() {
        return java.util.UUID.randomUUID().toString();
    }

    public static String getImageMimeType(File file) {
        String fileName = file.getName().toLowerCase();
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (fileName.endsWith(".png")) {
            return "image/png";
        } else if (fileName.endsWith(".gif")) {
            return "image/gif";
        } else if (fileName.endsWith(".bmp")) {
            return "image/bmp";
        } else if (fileName.endsWith(".webp")) {
            return "image/webp";
        }
        return "application/octet-stream"; // 默认二进制流
    }
    public static Object getObjByMethod(Method m) {
        return getObject(m.getDeclaringClass());
    }
    @NotBlank
    public static String getUUid() {
        return UUID.randomUUID().toString();
    }

    @NotNull
    //@Retry @TimeLimiter
    @CircuitBreaker(maxAttempts = 3, openTimeout = 5000, resetTimeout = 10000)
    //name = "myService", fallbackMethod = "fallback"
    public static String getStrFrmUrl(@NotBlank String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() != 200) {
            throw new IOException("Failed to fetch data: HTTP error code " + conn.getResponseCode());
        }

        Scanner scanner = new Scanner(conn.getInputStream());
        StringBuilder jsonStr = new StringBuilder();
        while (scanner.hasNext()) {
            jsonStr.append(scanner.nextLine());
        }
        scanner.close();
        conn.disconnect();

        String string = jsonStr.toString();
        return string;
    }


    @NotBlank
    public static String getFilename(   @NotBlank String uploadDir,   @NotBlank   @NotBlank String prefix) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmssSSS").format(new Date());
        String newFileName =prefix + timeStamp + ".jpg";
        java.nio.file. Path filePath = Paths.get(uploadDir, newFileName);
        return newFileName;
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

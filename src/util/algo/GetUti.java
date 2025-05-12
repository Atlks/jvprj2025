package util.algo;

import com.sun.net.httpserver.HttpExchange;
//import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
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
    //  url格式是  jdbc:mysql://localhost:3306/prjdb?user=root&password=pppppp
    private static String getPwdFrmDburl(String url) {
        int pwdIndex = url.indexOf("password=");
        if (pwdIndex == -1) return null;
        int endIndex = url.indexOf('&', pwdIndex);
        return endIndex == -1
                ? url.substring(pwdIndex + 9)
                : url.substring(pwdIndex + 9, endIndex);
    }

    private static String getUnameFrmDburl(String url) {
        int userIndex = url.indexOf("user=");
        if (userIndex == -1) return null;
        int endIndex = url.indexOf('&', userIndex);
        return endIndex == -1
                ? url.substring(userIndex + 5)
                : url.substring(userIndex + 5, endIndex);
    }


    //查找对象的的handle3方法，获得参数（带有 ModelAttribute 标记 ） 类型，
    //子类方法是public void handle3(@ModelAttribute Usr dto)
    public static Class getPrmClass(Object obj, String methodName) {
        if (obj == null || methodName == null) {
            return null;
        }

        // 获取 obj 的所有方法
        Method[] methods = obj.getClass().getMethods();

        for (Method method : methods) {
            // 确保方法名称匹配
            if (!method.getName().equals(methodName)) {
                continue;
            }

            // 遍历方法的所有参数
            Parameter[] parameters = method.getParameters();
            if(parameters.length==0)
                return  null;
            Parameter parameter=parameters[0];
            Class<?> type = parameter.getType();
            if (type == Object.class)
                continue;
            return type; // 返回参数的 Class 类型

//            for (Parameter parameter : parameters) {
//                if (parameter.isAnnotationPresent(BeanParam.class)) {
//                    Class<?> type = parameter.getType();
//                    if (type == Object.class)
//                        continue;
//                    return type; // 返回参数的 Class 类型
//                }
//                // 检查参数是否标记了 @ModelAttribute
//                if (parameter.isAnnotationPresent(ModelAttribute.class)) {
//                    Class<?> type = parameter.getType();
//                    if (type == Object.class)
//                        continue;
//                    return type; // 返回参数的 Class 类型
//                }
//            }
        }

        return null; // 未找到匹配的方法或参数
    }

    public static String getTablename(Class<?> jpaModelClass){
     return  getTableName(jpaModelClass);
    }
    /**
     * 获取实体类 立马的 @table 表格名称。如果没有@table或者为空，则使用实体类名
     * @param jpaModelClass
     * @return
     */
    public static String getTableName(Class<?> jpaModelClass) {
        jakarta.persistence.Table tableAnnotation = jpaModelClass.getAnnotation(jakarta.persistence.Table.class);
        if (tableAnnotation != null && tableAnnotation.name() != null && !tableAnnotation.name().isEmpty()) {
            return tableAnnotation.name();
        }
        return jpaModelClass.getSimpleName();
    }
    @org.jetbrains.annotations.NotNull
    public static String getId(Object obj) throws NoSuchFieldException, IllegalAccessException {
        Field idField = obj.getClass().getDeclaredField("id");
        idField.setAccessible(true);
        String id = (String) idField.get(obj);
        if (id == null)
            throw new RuntimeException("id cantbe null");
        return id;
    }


    public static @Nullable Method getMethod(@jakarta.validation.constraints.NotNull  Class clz, @NotBlank String methodName) {

        Method[] ms=clz.getMethods();
        for(  Method method :ms)
        {
            if(method.getName().equals(methodName))
            {
                method.setAccessible(true); // 支持 private/protected 方法
                return method;
            }

        }



        return null; // 未找到
    }


    public static String getUuid2() {
        return java.util.UUID.randomUUID().toString();
    }

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
  //  @CircuitBreaker(maxAttempts = 3, openTimeout = 5000, resetTimeout = 10000)
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

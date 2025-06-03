package orgx.uti;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.persistence.Id;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import orgx.uti.orm.FunctionX;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static orgx.uti.LogUti.prtEndfun;
import static orgx.uti.LogUti.prtFun;


public class Uti {
    /**
     * // 获取 `user`，如果不存在则返回 "unknown"
     * String user = QueryParamParser.getQueryParam(paramMap, "user").orElse("unknown");
     * System.out.println("用户：" + user); // 输出: 用户：admin
     * 可以用 orElseThrow() 在关键参数缺失时抛异常
     *
     * @param paramMap
     * @param key
     * @return
     */
    public static Optional<String> getKeyFrmMp(Map<String, List<String>> paramMap, String key) {
        return Optional.ofNullable(paramMap.get(key).get(0));
    }
//    public static Optional<Object> getKeyFrmMp2(Map  paramMap, String key) {
//        return Optional.ofNullable(paramMap.get(key));
//    }


    public static <T> T[] getVFrmMpArr(Map<String, T[]> map, String key, T[] dflt) {
        T[] annoArr = map.get(key);
        if (annoArr == null) {
            return dflt;
        }
        return annoArr;
    }

    public static Annotation[] EmptyAnnotations() {
        return new Annotation[0];
    }

    /**
     * 手动校验 实体对象，满足jakarta.validation约束
     *
     * @param dto
     */
    public static <T> void valdt(T dto) {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(dto);

        if (!violations.isEmpty()) {
            String errmsg = "";
            for (ConstraintViolation<T> violation : violations) {
                errmsg += ("PropertyPath=" + violation.getPropertyPath() + ",msg=" + violation.getMessage());
            }
            throw new IllegalArgumentException("无效参数，cls=" + dto.getClass() + "," + errmsg);
        }

    }

    public static <T> Object callFunOrMthd(Object funOrMthd, Object dto, Class<T> dtoClz) throws Throwable {
        if (funOrMthd instanceof Method)
            return callMthd((Method) funOrMthd, dto);
        if (funOrMthd instanceof FunctionX<?, ?>) {
            FunctionX<T, Object> function = (FunctionX<T, Object>) funOrMthd;

            return callFun(function, dto, dtoClz);
        }
        return null;

    }

    public static Object callMthd(Method m) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        String fname = m.getName();
        LogUti.prtFun(fname);
        Object invoke = m.invoke(getObjByMethod(m));
        prtEndfun(fname);
        return invoke;


    }

    /**
     * if mlt prd anno
     * 1️⃣ 服务器按照 Accept 头匹配最佳格式 2️⃣ 如果没有匹配，返回默认格式（通常是第一个 Produces 声明的格式
     * @param m
     * @param producesClass
     * @return
     */
    public static String getMediaType(Method m, Class<Produces> producesClass) {
        Produces prdAnnotation = m.getAnnotation(Produces.class);
        if (prdAnnotation == null) {
            return MediaType.APPLICATION_JSON;
        }
        return prdAnnotation.value()[0];
    }

    public static String getMediaType(Class m, Class<Produces> producesClass) {
        Produces prdAnnotation = (Produces) m.getAnnotation(Produces.class);
        if (prdAnnotation == null) {
            return MediaType.APPLICATION_JSON;
        }
        return prdAnnotation.value()[0];
    }

    public static Object callMthd(Method m, Object dto) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        LogUti.prtFun(m.getName(), dto);
        Object objByMethod = getObjByMethod(m);
        //System.out.println(m);
        // System.out.println(dto);

        if (dto.getClass() == Class.class)
            throw new RuntimeException("dto not dto ..cant be clz");

        // 确保 dto 类型正确
        Class<?> expectedType = m.getParameterTypes()[0];
        //  System.out.println("expect type=" + expectedType);
        if (!expectedType.isInstance(dto)) {
            System.out.println("  type not" + expectedType);
            System.out.println(" dto type=" + dto.getClass());
        }

        Object invoke = m.invoke(objByMethod, dto);
        prtEndfun(m.getName());
        return invoke;


    }


    /**
     * 根据方法得到他所所属的类的实例，如果静态方法应该是返回null，如果动态方法，调用默认构造
     *
     * @param m
     * @return
     */
    private static Object getObjByMethod(Method m) throws
            NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clazz = m.getDeclaringClass();

        // 如果方法是静态的，返回 null
        if (Modifier.isStatic(m.getModifiers())) {
            return null;
        }

        // 创建实例（调用默认构造方法）
        return clazz.getDeclaredConstructor().newInstance();
    }

    private static <T> Object callFun(FunctionX<T, Object> function, Object dto, Class<T> dtoClz) throws
            Throwable {
        // 确保 dto 可以被正确转换为 T 类型
        if (!dtoClz.isInstance(dto)) {
            throw new ClassCastException("dto 不是类型 " + dtoClz.getName());
        }

        T dto1 = dtoClz.cast(dto); // 安全转换
        return function.apply(dto1);
    }

    public static <T> T toDto(Map httpMp, Class<T> clazz) throws Exception {

        if (clazz.equals(Object.class)) {
            return (T) httpMp; // 允许返回 Map
        }
        if (Map.class.isAssignableFrom(clazz)) {
            return clazz.cast(httpMp); // 直接转换为 Map
        }
        T instance = clazz.getDeclaredConstructor().newInstance(); // 创建对象实例
        for (Object entry1 : httpMp.entrySet()) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) entry1;
            Field field = clazz.getDeclaredField(entry.getKey()); // 获取字段
            field.setAccessible(true); // 允许访问私有字段
            Object value = entry.getValue();
            List<String> li = (List<String>) value;
            String value1 = li.get(0);
            fieldSetVal(value1, field, instance);
            // field.set(instance, value1); // 赋值
        }
        return instance;


    }

    private static boolean hasAnno(Class<Produces> producesClass, Method method) {
        Annotation[] annotations = method.getDeclaredAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation.annotationType() == Produces.class) {
                return true;
            }

        }
        return false;
    }

    private static <T> void fieldSetVal(String value1, Field field, T instance) throws IllegalAccessException {
        field.setAccessible(true); // 确保字段可以被访问

        // 获取字段的类型
        Class<?> fieldType = field.getType();

        // 根据类型转换值
        Object convertedValue = convertValue(value1, fieldType);

        // 设置值
        field.set(instance, convertedValue);
    }

    public static void throwX(Throwable e) {
        if (e instanceof RuntimeException) {
            throw (RuntimeException) e;
        }

        throw new RuntimeException(e);
    }

    private static Object convertValue(String value, Class<?> type) {
        if (type.equals(int.class) || type.equals(Integer.class)) {
            return Integer.parseInt(value);
        } else if (type.equals(double.class) || type.equals(Double.class)) {
            return Double.parseDouble(value);
        } else if (type.equals(boolean.class) || type.equals(Boolean.class)) {
            return Boolean.parseBoolean(value);

        } else if (type.equals(long.class) || type.equals(Long.class)) {
            return Long.parseLong(value);

        } else if (type.equals(BigDecimal.class) || type.equals(Long.class)) {
            return new BigDecimal(value);
        } else if (type.equals(String.class)) {
            return value;
        }
        throw new IllegalArgumentException("不支持的字段类型: " + type);
    }

    /**
     * 得到 fun的名称
     *
     * @param
     * @param <T>
     * @return
     */
    private static <T> String getFunName(FunctionX<T, Object> fun) {
        return fun.getLambdaMethodName();
    }


    public static Method getDeclaredMethod(Class<?> implClass, String implMethodName) {

        try {
            Method[] ms = implClass.getDeclaredMethods();
            for (Method m : ms) {
                if (m.getName().equals(implMethodName)) {
                    return m;
                }
            }
            return implClass.getDeclaredMethod(implMethodName);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("无法找到方法: " + implMethodName + " 在类: " + implClass.getName(), e);
        }

    }

    ;

    // 文件写入（使用NIO，自动创建父目录）
    public static void write(String file, String txt) {
        try {
            mkdir(file);
            // 确保父目录存在
            Files.createDirectories(Paths.get(file).getParent());
            // 写入文件（UTF-8编码）
            Files.write(Paths.get(file), txt.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            // 打印堆栈跟踪或记录日志
            e.printStackTrace();
            // 可根据需求抛出自定义异常
            throw new RuntimeException("写入文件失败: " + file, e);
        }
    }

    /**
     * 级联创建文件目录
     *
     * @param filePath /aa/bb/cc/dd.java
     */
    private static void mkdir(String filePath) {
        try {
            Path directoryPath = Paths.get(filePath).getParent(); // 获取父级目录
            if (directoryPath != null) {
                Files.createDirectories(directoryPath);
                System.out.println("Directories created successfully: " + directoryPath);
            }
        } catch (Exception e) {
            System.err.println("Failed to create directories: " + e.getMessage());

        }
    }

    // JSON序列化（使用gson）
    public static String encodeJsonDto(Object entity) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(entity); // 序列化对象为 JSON

    }

    // JSON序列化（使用Jackson）
    public static String encodeJson(Object entity) {
        try {
            if (entity == null) return "null";
            // 创建ObjectMapper实例（线程安全，可复用）
            ObjectMapper mapper = new ObjectMapper();
            // 序列化为JSON字符串
            return mapper.writeValueAsString(entity);
        } catch (Exception e) {
            // 打印堆栈跟踪或记录日志
            e.printStackTrace();
            // 返回空JSON对象或抛出异常
            return "{}";
        }
    }

    /**
     * @param object
     * @param idClass
     * @return
     */
    public static Field getField(Object object, Class<Id> idClass) {
        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.getAnnotation(idClass) != null) {
                return field;
            }
        }
        throw new IllegalArgumentException("对象未包含 @Id 注解的字段");
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 反序列化为实体
     *
     * @param objStrJson
     * @param <T>
     * @return
     */
    public static <T> T toClassObj(String objStrJson, Class<T> clazz) {

        try {
            return objectMapper.readValue(objStrJson, clazz);
        } catch (Exception e) {
            throw new RuntimeException("JSON 反序列化失败: " + e.getMessage());
        }

    }
}


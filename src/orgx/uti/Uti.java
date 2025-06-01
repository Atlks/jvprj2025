package orgx.uti;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Id;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import orgx.uti.orm.FunctionX;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Uti {

    /**
     * 手动校验 实体对象，满足jakarta.validation约束
     * @param dto
     */
    public  static <T> void valdt(T dto) {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(dto);

        if (!violations.isEmpty()) {
            String errmsg="";
            for (ConstraintViolation<T> violation : violations) {
                errmsg+=("PropertyPath=" + violation.getPropertyPath() + ",msg=" + violation.getMessage());
            }
            throw new IllegalArgumentException("无效参数，cls="+dto.getClass()+","+errmsg);
        }

    }

    public static <T> T toDto(Map HttpMp, Class<T> clazz) throws Exception {

        T instance = clazz.getDeclaredConstructor().newInstance(); // 创建对象实例
        for (Object entry1 : HttpMp.entrySet()) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) entry1;
            Field field = clazz.getDeclaredField(entry.getKey()); // 获取字段
            field.setAccessible(true); // 允许访问私有字段
            Object value = entry.getValue();
            List<String> li= (List<String>) value;
            String value1 = li.get(0);
            fieldSetVal(value1,field,instance);
           // field.set(instance, value1); // 赋值
        }
        return instance;


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
        if(e instanceof RuntimeException) {
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
            
        } else if (type.equals(long.class)|| type.equals(Long.class)) {
            return Long.parseLong(value);
            
        } else if (type.equals(BigDecimal.class) || type.equals(Long.class)) {
            return new BigDecimal(value);
        }
        else if (type.equals(String.class)) {
            return value;
        }
        throw new IllegalArgumentException("不支持的字段类型: " + type);
    }

    /**
     * 得到 fun的名称
     * @param
     * @return
     * @param <T>
     */
    private static <T> String getFunName(FunctionX<T, Object> fun) {
        return fun.getLambdaMethodName();}


    public  static  Method getDeclaredMethod(Class<?> implClass, String implMethodName){

        try {
           Method[] ms=   implClass.getDeclaredMethods();
           for(Method m:ms){
               if(m.getName().equals(implMethodName)){
                   return m;
               }
           }
            return implClass.getDeclaredMethod(implMethodName);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("无法找到方法: " + implMethodName + " 在类: " + implClass.getName(), e);
        }

    };

    // 文件写入（使用NIO，自动创建父目录）
    public  static void write(String file, String txt) {
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
     * @param filePath    /aa/bb/cc/dd.java
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

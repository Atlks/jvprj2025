package util.algo;

import jakarta.validation.constraints.NotBlank;

import java.util.HashMap;
import java.util.Map;

public class IsXXX {


    /**
     *  * 全面增加了空安全 检测 与自动转换机制
     * @param s1
     * @param s2
     * @return
     */
    public static boolean isEq(@NotBlank String s1,@NotBlank String s2) {


        if( s1==null || s2==null)
            return  false;
        if(s1.isBlank()|| s2.isBlank())
            return  false;
        return s2.equals(s1);


    }

    //is_numeric

    /**
     *Java 的对应方式
     * Java 没有 define() 这种全局常量定义方式，但可以用 final static 变量 作为常量，对应的 defined() 方法 可以用 try-catch 或 反射 来检查字段是否存在。
     * 方式 2：用 Map 作为可动态检查的“常量池”
     * 如果想要 动态定义 & 检查常量，可以用 Map<String, Object> 存储常量：
     */
    public static boolean isConstantDefined(String className, String fieldName) {
        try {
            Class<?> clazz = Class.forName(className);
            clazz.getDeclaredField(fieldName); // 反射查找字段
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public static final Map<String, Object> CONSTANTS = new HashMap<>();
    public static boolean defined(String name) {
        return CONSTANTS.containsKey(name);
    }
}

package util;

import jakarta.ws.rs.CookieParam;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class AnnotationUtils {

    public static List<String> getCookieParams(Class<?> clazz, String methodName) {
        try {
            List<String> params = new ArrayList<>();
            // 获取类中所有方法
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.getName().equals(methodName)) {


                    // 遍历方法anno

                    for (Annotation annotation : method.getAnnotations()) {
                            if (annotation instanceof CookieParam) {
                                String paramName = ((CookieParam) annotation).value();
                                params.add(paramName);
                            }
                        }



                    // 拼接为 " " 分隔的字符串
                  //  return String.join(" ", params);
                }
            }
            return  params;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}

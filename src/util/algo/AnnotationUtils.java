package util.algo;


import annos.CookieParam;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class AnnotationUtils {

    public static List<CookieParam> getCookieParamsV2(Class<?> clazz, String methodName) {
        try {
            List<CookieParam> params = new ArrayList<>();
            // 获取类中所有方法
            //  clazz.getAnnotations()
            for (Annotation annotation : clazz.getAnnotations()) {
                if (annotation instanceof CookieParam) {
                    //String paramName = ((CookieParam) annotation).name();
                    params.add((CookieParam)annotation);
                }
            }

            return  params;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

@Deprecated
    public static List<String> getCookieParams(Class<?> clazz, String methodName) {
        try {
            List<String> params = new ArrayList<>();
            // 获取类中所有方法
          //  clazz.getAnnotations()
            for (Annotation annotation : clazz.getAnnotations()) {
                if (annotation instanceof CookieParam) {
                    String paramName = ((CookieParam) annotation).name();
                    params.add(paramName);
                }
            }
//            for (Method method : clazz.getDeclaredMethods()) {
//                if (method.getName().equals(methodName)) {
//
//                 //   System.out.println(CookieParam);
//                    // 遍历方法anno
//
//
//
//
//
//                    // 拼接为 " " 分隔的字符串
//                  //  return String.join(" ", params);
//                }
//            }
            return  params;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


}

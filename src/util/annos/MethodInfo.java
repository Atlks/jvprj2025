package util.annos;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// 注解
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodInfo {
    String value() default "";
}

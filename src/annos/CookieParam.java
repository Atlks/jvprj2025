package annos;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CookieParam {


    String description() default "";

    String name();

    String decryKey() default "";

    String value();
}

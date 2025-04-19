package util.annos;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface Parameters {
    Parameter[] value();  // 存储多个 MyAnnotation
}

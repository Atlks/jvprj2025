package util.annos;

import java.lang.annotation.*;

@Target({  ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatUtil {
    Class<? > value();
}

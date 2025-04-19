package util.annos;

import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
//@Repeatable(Parameters.class)
@Inherited
public @interface JwtParam {
    String name();
}

package util.annos;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Documented

public @interface Paths {
    String[] value();
}

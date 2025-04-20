package util.annos;
import handler.admin.AuthFun4admin;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)

public @interface RequireAuth {
    String role() default "user";

    Class<AuthFun4admin> authFun();
}

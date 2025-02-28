package biz;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//



import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Operation {
    String method() default "";

    String[] tags() default {};

    String summary() default "";

    String description() default "";

    RequestBody requestBody() default @RequestBody;


    String operationId() default "";

    //Parameter[] parameters() default {};

    ApiResponse[] responses() default {};

    boolean deprecated() default false;

    SecurityRequirement[] security() default {};

    Server[] servers() default {};

    Extension[] extensions() default {};

    boolean hidden() default false;

    boolean ignoreJsonView() default false;

    String example();
}


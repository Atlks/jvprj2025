package util.annos;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//




import io.swagger.v3.oas.annotations.enums.Explode;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Parameters.class)
@Inherited
public @interface Parameter {


    ParameterIn in() default ParameterIn.DEFAULT;

    String description() default "";

    boolean required() default true;

    boolean deprecated() default false;

    boolean allowEmptyValue() default false;

    ParameterStyle style() default ParameterStyle.DEFAULT;

    Explode explode() default Explode.DEFAULT;

    boolean allowReserved() default false;

    Schema schema() default @Schema;

    ArraySchema array() default @ArraySchema;

    Content[] content() default {};

    boolean hidden() default false;

    ExampleObject[] examples() default {};

    String example() default "";

    Extension[] extensions() default {};


    String name() default "";

    String value() default "";
}


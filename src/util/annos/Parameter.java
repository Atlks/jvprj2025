package util.annos;

public @interface Parameter {
    String name();

    String description() default "";
}

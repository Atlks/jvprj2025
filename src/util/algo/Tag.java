package util.algo;

public @interface Tag {
    String name();

    String description() default "";
}

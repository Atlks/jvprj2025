package util.annos;



import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EvtLsnr4Condtion {
    String[] value() default {};
}

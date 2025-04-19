package util.annos;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

//package jakarta.enterprise.event;

import jakarta.enterprise.event.Reception;
import jakarta.enterprise.event.TransactionPhase;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.Set;


@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Observes {
    String[] value() default {};
    Reception notifyObserver() default Reception.ALWAYS;

    TransactionPhase during() default TransactionPhase.IN_PROGRESS;
}

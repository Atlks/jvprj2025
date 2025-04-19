package ztools;


import org.springframework.context.annotation.Condition;

import java.lang.annotation.*;

@Target({  ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ConditionalElse {
    Class<? extends Condition>[] value();
}

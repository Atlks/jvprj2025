package util.annos;

import util.evtdrv.Condition;
import util.evtdrv.CondtionEvtObj;

import java.lang.annotation.*;

@Target({  ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Conditional {


    Class<? >[] value();

}
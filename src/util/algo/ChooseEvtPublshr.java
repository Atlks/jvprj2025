package util.algo;

import api.usr.ConditionContextMockImp;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.Conditional;
import tools.ConditionImpt1;
import tools.ConditionalElse;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import static cfg.IocSpringCfg.getObject;
import static util.algo.GetUti.getObjByMethod;
import static util.misc.util2026.printLn;
import static util.misc.util2026.scanAllClass;
import static util.oo.ArrUtil.pushSet;

public class ChooseEvtPublshr implements ApplicationEventPublisher {
    public static Map<Class, Set<Method>> mapCls = new HashMap<>();
    public static Map<Class, Set<Method>> mapCls_CdtElseMth = new HashMap<>();

    public void publishEvent4exeCdtn(Class<ConditionImpt1> c) throws Exception {
        Condition cdtObj = (Condition) getObject(c);
        if (cdtObj.matches(new ConditionContextMockImp(), new api.usr.AnnotatedTypeMetadataImpMock())) {
            Set<Method> st = mapCls.get(c);
            for (Method Method1 : st) {
                System.out.println(Method1);
                Object objByMethod = getObjByMethod(Method1);

                Object[] args = {""};
                Method1.invoke(objByMethod, 1);
            }

        } else {
            Set<Method> st = mapCls_CdtElseMth.get(c);
            for (Method m : st) {
                System.out.println(" m503="+m);
                m.invoke(getObjByMethod(m),2);
            }
        }
        // traveMethodByClass(ifelseUtil.class);
    }

    public static void   iniCondtEvtMap(){
        Consumer<Class> csmr4log = clazz -> {

            if (!clazz.getName().startsWith("api") && !clazz.getName().startsWith("service")) {
                System.out.println("contine clz=" + clazz.getName());
                return;
            }
            printLn("\n开始注册evt" + clazz.getName());



            iniCondtEvtMap4sngClz(clazz);


        };
        scanAllClass(csmr4log);//  all add class  ...  mdfyed class btr
    }
    public static void iniCondtEvtMap4sngClz(Class clz) {

        Method[] ms = clz.getDeclaredMethods();
        for (Method m : ms) {
            if (m.isAnnotationPresent(Conditional.class)) {
                Class[] cdtClss = m.getAnnotation(Conditional.class).value();
                for (Class cdtClz : cdtClss) {
                    pushSet(mapCls, cdtClz, m);

//                    Condition cdtObj = (Condition) getObject(c);
//                    if (cdtObj.matches(null, null))
//                        m.invoke(getObjByMethod(m), null);
                }
            }

            if (m.isAnnotationPresent(ConditionalElse.class)) {
                Class[] cdtClss = m.getAnnotation(ConditionalElse.class).value();
                for (Class cdtClz : cdtClss) {
                    pushSet(mapCls_CdtElseMth, cdtClz, m);
//                    Condition cdtObj = (Condition) getObject(c);
//                    if (cdtObj.matches(null, null)) {
//
//                    } else {
//                        m.invoke(getObjByMethod(m), null);
//                    }

                }
            }
        }

    }


    /**
     * @param event
     */
    @Override
    public void publishEvent(@NotNull Object event) {

    }
}

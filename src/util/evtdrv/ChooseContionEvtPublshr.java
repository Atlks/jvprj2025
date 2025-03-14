package util.evtdrv;


import annos.Conditional;
import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.AssertTrue;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEventPublisher;


import tools.ConditionalElse;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Consumer;

import static cfg.IocSpringCfg.getObject;
import static util.algo.GetUti.getObjByMethod;
import static util.misc.util2026.printLn;
import static util.misc.util2026.scanAllClass;
import static util.oo.ArrUtil.pushSet;

public class ChooseContionEvtPublshr implements ApplicationEventPublisher {
    public static List<CondtionEvtObj> evtList = new ArrayList<>();

    public void publishEvent4exeCdtn(Class<Condition> c) throws Exception {

        Set<Method> st = qryEvtLst(c);
        for (Method Method1 : st) {
            System.out.println(Method1);
            Object objByMethod = getObjByMethod(Method1);
            Object[] args = {""};
            Method1.invoke(objByMethod, 1);
        }


        // traveMethodByClass(ifelseUtil.class);
    }

    private Set<Method> qryEvtLst(Class<Condition> c) {
        Condition cdtObj = (Condition) getObject(c);
        Object cdtResult = cdtObj.matches();
        Optional<CondtionEvtObj> first = evtList.stream()
                .filter(e -> e.cdt.equals(cdtObj) && Objects.equals(e.cdtResult, cdtResult))
                .findFirst();
        return first
                .map(obj -> {
                    System.out.println("找到对象：" + obj);
                    return obj.methodSet;
                })
                .orElse(Collections.emptySet());  // 如果未找到，返回空 Set 避免 NullPointerException

    }

//    public static Optional<CondtionEvtObj> findFirstByConditionAndResult(Condition cdt, Object cdtResult) {
//        Optional<CondtionEvtObj> first = evtList.stream()
//                .filter(e -> e.cdt.equals(cdt) && Objects.equals(e.cdtResult, cdtResult))
//                .findFirst();
//        return first;
//    }

//    public static void iniCondtEvtMap() {
//        Consumer<Class> csmr4log = clazz -> {
//
//            if (!clazz.getName().startsWith("api") && !clazz.getName().startsWith("service")) {
//                System.out.println("contine clz=" + clazz.getName());
//                return;
//            }
//            printLn("\n开始注册evt" + clazz.getName());
//
//
//            iniCondtEvtMap4sngClz(clazz);
//
//
//        };
//        scanAllClass(csmr4log);//  all add class  ...  mdfyed class btr
//    }

    public static void addCondtEvt2evtList4sngClz(Class clz) {

        Method[] ms = clz.getDeclaredMethods();
        for (Method m : ms) {
            if (m.isAnnotationPresent(Conditional.class)) {
                CondtionEvtObj evt = new CondtionEvtObj();
                Class[] cdtClss = m.getAnnotation(Conditional.class).value();
                for (Class cdtClz : cdtClss) {
                    if (cdtClz == AssertTrue.class) {
                        evt.cdtResult = true;
                        evtList.add(evt);
                    } else if (cdtClz == AssertFalse.class) {
                        evt.cdtResult = false;
                        evtList.add(evt);
                    } else
                        evt.cdt = cdtClz;
                    //  pushSet(mapCls, cdtClz, m);

                }
            }

            //endif
        }
        //endfor

    }

//                    Condition cdtObj = (Condition) getObject(c);
//                    if (cdtObj.matches(null, null))
//                        m.invoke(getObjByMethod(m), null);
//Condition cdtObj = (Condition) getObject(c);
//                    if (cdtObj.matches(null, null)) {
//
//                    } else {
//                        m.invoke(getObjByMethod(m), null);
//                    }

    /**
     * @param event
     */
    @Override
    public void publishEvent(@NotNull Object event) {

    }
}

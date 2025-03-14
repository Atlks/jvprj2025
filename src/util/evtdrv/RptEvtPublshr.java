package util.evtdrv;




import annos.Conditional;
import annos.Repeat;
import annos.RepeatUtil;
import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.AssertTrue;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEventPublisher;


import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;


import static util.algo.GetUti.getObjByMethod;
import static util.algo.GetUti.getObject;
import static util.misc.util2026.scanAllClass;
import static util.oo.ArrUtil.pushSet;

public class RptEvtPublshr implements ApplicationEventPublisher {
    public static List<CondtionEvtObj> evtList = new ArrayList<>();

    public void publishEvent4rpt4exeCdtn(Class<? extends Condition> evtClz, Object prm4cdt) throws Exception {

        Set<Method> st = qryEvtLst(evtClz, prm4cdt);
        for (Method Method1 : st) {
            System.out.println(Method1);
            Object objByMethod = getObjByMethod(Method1);
            Object[] args = {""};
            Method1.invoke(objByMethod, 1);
        }


        // traveMethodByClass(ifelseUtil.class);
    }

    private Set<Method> qryEvtLst(Class<?> evtClz, Object prm4cdt) {
        Condition cdtObj = (Condition) getObject(evtClz);
        Object cdtResult = cdtObj.matches(prm4cdt);
        List<CondtionEvtObj> evtLstSlkted = evtList.stream()
                .filter(e ->
                {
                    boolean equalsCls = e.conditionClz.equals(evtClz);
                    boolean eqRzt = Objects.equals(e.cdtResult, cdtResult);
                    return equalsCls && eqRzt;
                })
                .toList();
        System.out.println("sz123:" + evtLstSlkted.size());
        //从删选后的记录中，提取mthd属性，组合成set
        return evtLstSlkted.stream()
                .map(e -> e.mthd) // ✅ 直接使用 `map()` 提取 `mthd`
                .collect(Collectors.toSet()); // 转换成 Set

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


    public static void addRptEvt2evtList4sngClz(Class clz) {

        Method[] ms = clz.getDeclaredMethods();
        for (Method m : ms) {
            if (m.isAnnotationPresent(Repeat.class)) {
                CondtionEvtObj evt = new CondtionEvtObj();
                Class cdtClz = m.getAnnotation(Repeat.class).value();
               // for (Class cdtClz : cdtClss) {

                    evt.conditionClz = cdtClz;
                    evt.cdtResult = true;
                    evt.mthd = m;
                    evtList.add(evt);
                    //  pushSet(mapCls, cdtClz, m);

               // }
            }

            //endif
        }
        //endfor

        //--------------add util stmt ,last line
        for (Method m : ms) {
            if (m.isAnnotationPresent(RepeatUtil.class)) {
                CondtionEvtObj evt = new CondtionEvtObj();
                Class cdtClz = m.getAnnotation(RepeatUtil.class).value();
                // for (Class cdtClz : cdtClss) {

                evt.conditionClz = cdtClz;
                evt.cdtResult = true;
                evt.mthd = m;
                evtList.add(evt);
                //  pushSet(mapCls, cdtClz, m);

                // }
            }

            //endif
        }
    }

    public static void addCondtEvt2evtList4sngClz(Class clz) {

        Method[] ms = clz.getDeclaredMethods();
        for (Method m : ms) {
            if (m.isAnnotationPresent(Conditional.class)) {
                CondtionEvtObj evt = new CondtionEvtObj();
                Class[] cdtClss = m.getAnnotation(Conditional.class).value();
                for (Class cdtClz : cdtClss) {
                    if (cdtClz == AssertTrue.class) {
                        evt.cdtResult = true;
                        evt.mthd = m;
                        //  pushset(evt,m);
                        // pushSet();
                        evtList.add(evt);
                    } else if (cdtClz == AssertFalse.class) {
                        evt.cdtResult = false;
                        evt.mthd = m;
                        evtList.add(evt);
                    } else
                        evt.conditionClz = cdtClz;
                    //  pushSet(mapCls, cdtClz, m);

                }
            }

            //endif
        }
        //endfor

    }

//    private static void pushset(CondtionEvtObj evt, Method m) {
//
//        Set<Method> st =evt.methodSet;
//        if (st == null) {
//            st = new HashSet<>();
//            evt.methodSet=st;
//        }
//        st.add(m);
//      //  mapCls.put(pathKey, st);
//
//    }

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


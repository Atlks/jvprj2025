package tools;

import api.usr.ConditionContextMockImp;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static cfg.IocSpringCfg.getObject;

public class ifelseUtil {


    public static void main(String[] args) throws Exception {
        iniCondtEvtMap();
        iniCondtEvtMap4sngClz(ifelseUtil.class);
        new ifelseUtil().publishEvent4exeCdtn(ConditionImpt1.class);
    }

    private static void iniCondtEvtMap() {
    }

    public static Map<Class, Set<Method>> mapCls = new HashMap<>();
    public static Map<Class, Set<Method>> mapCls_CdtElseMth = new HashMap<>();

    private static void iniCondtEvtMap4sngClz(Class clz) {

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

    private static void pushSet(Map<Class, Set<Method>> mapCls, Class cdtClz, Method m) {
        Set<Method> st = mapCls.get(cdtClz);
        if (st == null) {
            st = new HashSet<>();

        }
        st.add(m);
        mapCls.put(cdtClz, st);

    }


    private void publishEvent4exeCdtn(Class<ConditionImpt1> c) throws Exception {
        Condition cdtObj = (Condition) getObject(c);
        if (cdtObj.matches(new ConditionContextMockImp(), new api.usr.AnnotatedTypeMetadataImpMock())) {
            Set<Method> st = mapCls.get(c);
            for (Method Method1 : st) {
                System.out.println(Method1);
                Object objByMethod = getObjByMethod(Method1);

                Object[] args = {""};
                Method1.invoke(objByMethod, args);
            }

        } else {
            Set<Method> st = mapCls_CdtElseMth.get(c);
            for (Method m : st) {
                m.invoke(getObjByMethod(m));
            }
        }
        // traveMethodByClass(ifelseUtil.class);
    }

//    private void traveMethodByClass(Class<ifelseUtil> ifelseUtilClass) throws Exception {
//        Method[] ms = ifelseUtilClass.getDeclaredMethods();
//        for (Method m : ms) {
//            if (m.isAnnotationPresent(Conditional.class)) {
//                Class[] cdtClss = m.getAnnotation(Conditional.class).value();
//                for (Class c : cdtClss) {
//                    Condition cdtObj = (Condition) getObject(c);
//                    if (cdtObj.matches(null, null))
//                        m.invoke(getObjByMethod(m), null);
//                }
//            }
//
//            if (m.isAnnotationPresent(ConditionalElse.class)) {
//                Class[] cdtClss = m.getAnnotation(ConditionalElse.class).value();
//                for (Class c : cdtClss) {
//                    Condition cdtObj = (Condition) getObject(c);
//                    if (cdtObj.matches(null, null)) {
//
//                    } else {
//                        m.invoke(getObjByMethod(m), null);
//                    }
//
//                }
//            }
//        }
//    }

    private Object getObjByMethod(Method m) {
        return getObject(m.getDeclaringClass());
    }

    // @ConditionalOnProperty(name = "my.feature.enabled", havingValue = "true")
    @ConditionalElse({ConditionImpt1.class})
    private void elseblk(Object... args) {
        System.out.println("else blk");
    }

    @Conditional({ConditionImpt1.class})
    public void ifblk(Object... args) {
        System.out.println("ifblk blk");
    }


    @Conditional({ConditionImpt1.class})
    public void ifblk2(Object... args) {
        System.out.println("ifblk blk stmt 2");
    }
}

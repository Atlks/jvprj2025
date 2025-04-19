package util.evtdrv;

import util.annos.Observes;
import org.springframework.context.event.EventListener;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Consumer;

import static util.algo.ChooseEvtPublshr.iniCondtEvtMap4sngClz;
import static util.evtdrv.ChooseContionEvtPublshr.addCondtEvt2evtList4sngClz;
import static util.misc.ReflectionUtils.getFirstParamClassFromMethod;
import static util.misc.util2026.printLn;
import static util.misc.util2026.scanAllClass;
import static util.oo.ArrUtil.pushSet;

public class EvtUtil {


    public static Map<String, Set<Method>> evtHdrMap4pathEvtMod = new HashMap<>();
    public static Map<String, Set<Method>> evtHdrMapStrStMod = new HashMap<>();
    public static Map<Class, Set<Method>> evtHdrMap = new HashMap<>();

    public static void iniEvtHdrCtnr() {
        //  Class clz = MyEventListener.class;
        Consumer<Class> csmr4log = clazz -> {

            if (!clazz.getName().startsWith("api") && !clazz.getName().startsWith("service")) {
                System.out.println("iniEvtHdrCtnr（）。contine clz=" + clazz.getName());
                return;
            }
            printLn("\n开始注册evt" + clazz.getName());

            iniEvtHdrCtnr(clazz);

            iniEvtHdrCtnr4strEVt(clazz);
            iniEvtHdrCtnr4pathEVt(clazz);
            iniCondtEvtMap4sngClz(clazz);
            addCondtEvt2evtList4sngClz(clazz);

        };
        scanAllClass(csmr4log);//  all add class  ...  mdfyed class btr


    }
    private static void iniEvtHdrCtnr4pathEVt(Class clazz) {
        Method[] mthds = clazz .getDeclaredMethods();
        for (Method mth : mthds) {
            if (mth.isAnnotationPresent(Observes.class)) {
                Observes ano = mth.getAnnotation(Observes.class);

                //set evt map by cls lsit
                String[] lstEvts = ano.value();
                for (String path : lstEvts) {
                    pushSet(evtHdrMap4pathEvtMod,path,mth);

                }
            }

        }
    }

    private static void iniEvtHdrCtnr4strEVt(Class clazz) {
        Method[] mthds = clazz .getDeclaredMethods();
        for (Method mth : mthds) {
            if (mth.isAnnotationPresent(Observes.class)) {
                Observes ano = mth.getAnnotation(Observes.class);

                //set evt map by cls lsit
                String[] lstEvts = ano.value();
                for (String evtClz : lstEvts) {
                    Set<Method> li_meth = evtHdrMapStrStMod.get(evtClz);
                    if (li_meth == null) {
                        li_meth = new HashSet<>();
                        evtHdrMapStrStMod.put(evtClz, li_meth);
                    }
                    li_meth.add(mth);
                    // evtHdrMap.put(evtClz, mth);
                }



            }

        }
    }

    private static void iniEvtHdrCtnr(Class clz) {
        Method[] mthds = clz.getDeclaredMethods();
        for (Method mth : mthds) {
            if (mth.isAnnotationPresent(EventListener.class)) {
                EventListener ano = mth.getAnnotation(EventListener.class);

                //set evt map by cls lsit
                Class<?>[] lstEvts = ano.value();
                for (Class evtClz : lstEvts) {
                    Set<Method> li_meth = evtHdrMap.get(evtClz);
                    if (li_meth == null) {
                        li_meth = new HashSet<>();
                        evtHdrMap.put(evtClz, li_meth);
                    }
                    li_meth.add(mth);
                    // evtHdrMap.put(evtClz, mth);
                }

                //
                setEVtMapByParam(mth);

            }

        }
    }

    private static void setEVtMapByParam(Method mth) {
        Class firstParamClass=getFirstParamClassFromMethod(mth);
        Class evtClz=firstParamClass;
        Set<Method> li_meth = evtHdrMap.get(evtClz);
        if (li_meth == null) {
            li_meth = new HashSet<>();
            evtHdrMap.put(evtClz, li_meth);
        }
        li_meth.add(mth);
    }

//    private static Class getFirstParamClassFromMethod(Method mth) {
//        Class<?>[] paramTypes = mth.getParameterTypes();
//        return paramTypes.length > 0 ? paramTypes[0] : null;
//    }

}

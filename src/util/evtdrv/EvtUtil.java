package util.evtdrv;

import org.springframework.context.event.EventListener;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static util.misc.util2026.printLn;
import static util.misc.util2026.scanAllClass;

public class EvtUtil {


    public static Map<Class, List<Method>> evtHdrMap = new HashMap<>();

    public static void iniEvtHdrCtnr() {
        //  Class clz = MyEventListener.class;
        Consumer<Class> csmr4log = clazz -> {

            if (!clazz.getName().startsWith("api") && !clazz.getName().startsWith("service")) {
                System.out.println("contine clz=" + clazz.getName());
                return;
            }
            printLn("\n开始注册evt" + clazz.getName());

            iniEvtHdrCtnr(clazz);


        };
        scanAllClass(csmr4log);//  all add class  ...  mdfyed class btr


    }

    private static void iniEvtHdrCtnr(Class clz) {
        Method[] mthds = clz.getDeclaredMethods();
        for (Method mth : mthds) {
            if (mth.isAnnotationPresent(EventListener.class)) {
                EventListener ano = mth.getAnnotation(EventListener.class);
                Class<?>[] lstEvts = ano.value();
                for (Class evtClz : lstEvts) {
                    List<Method> li_meth = evtHdrMap.get(evtClz);
                    if (li_meth == null) {
                        li_meth = new ArrayList<>();
                        evtHdrMap.put(evtClz, li_meth);
                    }
                    li_meth.add(mth);
                    // evtHdrMap.put(evtClz, mth);
                }

            }

        }
    }

}

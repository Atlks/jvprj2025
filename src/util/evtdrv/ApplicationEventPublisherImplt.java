//package util.evtdrv;
//
//// ch.qos.logback.classic.util.LevelToSyslogSeverity;
//
//import util.excptn.ExceptionBase;
//import util.excptn.ExceptionBaseRtm;
//
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.HashSet;
//import java.util.Set;
//
//import static util.evtdrv.EvtUtil.evtHdrMap;
//import static util.misc.util2026.copyProps;
//import static util.proxy.SprUtil.injectAll4spr;
//
//
//public class ApplicationEventPublisherImplt implements ApplicationEventPublisher {
//    /**
//     * @param event
//     */
//    @Override
//    public void publishEvent(ApplicationEvent event) {
//        ApplicationEventPublisher.super.publishEvent(event);
//    }
//
//    /**
//     * @param event
//     */
//    @Override
//    public void publishEvent(Object event) {
//        Class evtClz = event.getClass();
//        Set<Method> li_meth = evtHdrMap.get(evtClz);
//        if (li_meth == null) {
//            evtHdrMap.put(evtClz, new HashSet<>());
//            li_meth = evtHdrMap.get(evtClz);
//        }
//
//        //  assert li_meth != null;
//        for (Method m : li_meth) {
//            Object obj = null;
//            try {
//                obj = m.getDeclaringClass().getConstructor().newInstance();
//                injectAll4spr(obj);
//                System.out.println("▶\uFE0Ffun "+m.getName());
//                m.invoke(obj, event);
//                System.out.println( "✅endfun " +m.getName());
//
//            } catch (InstantiationException | IllegalAccessException |
//                     NoSuchMethodException e) {
//                throw new RuntimeException(e);
//            } catch (InvocationTargetException e) {
//
//                Throwable oriEx = e.getCause();
//                if (oriEx instanceof ExceptionBase oriEx1) {
//                    ExceptionBaseRtm ex = new ExceptionBaseRtm();
//
//                    copyProps(oriEx1, ex);
//                    ex.errmsg=oriEx1.getErrmsg();
//                    ex.errcode=oriEx1.getClass().getName();
//                    ex.type=oriEx1.getClass().getName();
//                    throw ex;
//
//                } else
//                    throw new RuntimeException(oriEx.getMessage(), oriEx);
//            }
//
//        }
//
//    }
//}

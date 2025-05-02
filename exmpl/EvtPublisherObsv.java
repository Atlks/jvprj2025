//package cfg;
//
//import org.jetbrains.annotations.NotNull;
//import util.excptn.ExceptionBase;
//import util.excptn.ExceptionBaseRtm;
//
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.Set;
//
//// org.springframework.context.ApplicationEventPublisher;
//
//import static util.evtdrv.EvtUtil.evtHdrMapStrStMod;
//import static util.misc.Util2025.encodeJson;
//import static util.misc.util2026.copyProps;
//import static util.proxy.SprUtil.injectAll4spr;
//
//public class EvtPublisherObsv implements ApplicationEventPublisher {
//    public void publishEvent(String evtName, Object prm) {
//
//        Set<Method> obsSet = evtHdrMapStrStMod.get(evtName);
//        for (Method m : obsSet) {
//            Object obj = null;
//            try {
//                obj = m.getDeclaringClass().getConstructor().newInstance();
//                injectAll4spr(obj);
//                System.out.println("▶\uFE0Ffun " + m.getName());
//                System.out.println("prm=" + encodeJson(prm));
//                m.invoke(obj, prm);
//                System.out.println("✅endfun " + m.getName());
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
//                    ex.errmsg = oriEx1.getErrmsg();
//                    ex.errcode = oriEx1.getClass().getName();
//                    ex.type = oriEx1.getClass().getName();
//                    ex.cause = e;
//                    throw ex;
//
//                } else if (e.getCause() instanceof RuntimeException) {
//                    throw (RuntimeException) e.getCause();
//                } else {
//                    throw new RuntimeException(oriEx.getMessage(), oriEx);
//                }
//
//            }
//            //end catch
//
//        }
//        //end for
//
//    }
//
//    /**
//     * @param event
//     */
//    @Deprecated
//    @Override
//    public void publishEvent(@NotNull Object event) {
//        throw new RuntimeException("not implt");
//
//    }
//}

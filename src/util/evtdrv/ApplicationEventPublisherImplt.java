package util.evtdrv;

import ch.qos.logback.classic.util.LevelToSyslogSeverity;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import util.excptn.ExceptionBase;
import util.excptn.ExceptionBaseRtm;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static util.evtdrv.EvtUtil.evtHdrMap;
import static util.misc.util2026.copyProps;


public class ApplicationEventPublisherImplt implements ApplicationEventPublisher {
    /**
     * @param event
     */
    @Override
    public void publishEvent(ApplicationEvent event) {
        ApplicationEventPublisher.super.publishEvent(event);
    }

    /**
     * @param event
     */
    @Override
    public void publishEvent(Object event) {
        Class evtClz = event.getClass();
        List<Method> li_meth = evtHdrMap.get(evtClz);
        if (li_meth == null) {
            evtHdrMap.put(evtClz, new ArrayList<>());
            li_meth = evtHdrMap.get(evtClz);
        }

        //  assert li_meth != null;
        for (Method m : li_meth) {
            Object obj = null;
            try {
                obj = m.getDeclaringClass().getConstructor().newInstance();
                m.invoke(obj, event);
            } catch (InstantiationException | IllegalAccessException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {

                Throwable oriEx = e.getCause();
                if (oriEx instanceof ExceptionBase oriEx1) {
                    ExceptionBaseRtm ex = new ExceptionBaseRtm();

                    copyProps(oriEx1, ex);
                    ex.errmsg=oriEx1.getErrmsg();
                    ex.errcode=oriEx1.getErrcode();
                    ex.type=oriEx1.getClass().getName();
                    throw ex;

                } else
                    throw new RuntimeException(oriEx.getMessage(), oriEx);
            }

        }

    }
}

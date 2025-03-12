package util.evtdrv;

import ch.qos.logback.classic.util.LevelToSyslogSeverity;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static util.evtdrv.MyEventPublisher.evtHdrMap;

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
        for (Method m : li_meth) {
            Object obj = null;
            try {
                obj = m.getDeclaringClass().getConstructor().newInstance();
                m.invoke(obj, event);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }

        }

    }
}

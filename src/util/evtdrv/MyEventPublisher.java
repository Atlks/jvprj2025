package util.evtdrv;

import cfg.AppConfig;
import cfg.IocSpringCfg;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

/**
 * 是的，ApplicationEventPublisher 可以被 Spring 自动注入，无需手动配置。Spring 在启动时会提供一个默认的 ApplicationEventPublisher 实现，并自动注册到 Spring 容器中，因此你可以直接在 构造函数或 @Autowired 注入。
 */
@Component
public class MyEventPublisher {
    public ApplicationEventPublisher publisher;

    // 通过构造函数自动注入
    public MyEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        context.registerBean("myEventListener", MyEventListener.class);
        context.registerBean("MyEventPublisher", MyEventPublisher.class);
        context.registerBean("MyEventPublisher", MyEventPublisher.class);
        //   context.refresh();
        iniEvtHdr();
        sleep(500);
        MyEventPublisher bean = (MyEventPublisher) context.getBean("MyEventPublisher");
        bean.publisher = new ApplicationEventPublisherImplt();
        bean.publish("msgxxx");
        sleep(5000);
    }

    static Map<Class, List<Method>> evtHdrMap = new HashMap<>();

    private static void iniEvtHdr() {
        Class clz = MyEventListener.class;
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


    public void publish(String message) {
        MyEvent event = new MyEvent(message);
        System.out.println("发布事件：" + message);
        publisher.publishEvent(event);

    }


    public static void main2(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        context.registerBean("myEventListener", MyEventListener.class);
        context.registerBean("MyEventPublisher", MyEventPublisher.class);
        //   context.refresh();

        MyEventPublisher bean = (MyEventPublisher) context.getBean("MyEventPublisher");
        bean.publish("msgxxx");
        sleep(5000);
    }
}

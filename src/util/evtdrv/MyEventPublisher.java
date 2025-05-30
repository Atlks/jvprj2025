//package util.evtdrv;
//
//import cfg.AppConfig;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//
//
//
//import static java.lang.Thread.sleep;
//import static util.evtdrv.EvtUtil.iniEvtHdrCtnr;
//import static util.misc.util2026.scanAllClass;
//import static util.proxy.SprUtil.registerBean;
//import static util.tx.dbutil.setField;
//
///**
// * 是的，ApplicationEventPublisher 可以被 Spring 自动注入，无需手动配置。Spring 在启动时会提供一个默认的 ApplicationEventPublisher 实现，并自动注册到 Spring 容器中，因此你可以直接在 构造函数或  注入。
// */
//
//public class MyEventPublisher {
//
//    
//    @Qualifier("applicationEventPublisher")
//    public ApplicationEventPublisher publisher;
//
//    // 通过构造函数自动注入
////    public MyEventPublisher(ApplicationEventPublisher publisher) {
////        this.publisher = publisher;
////    }
//
//    public static void main(String[] args) throws InterruptedException {
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
//
//        context.registerBean("myEventListener", MyEventListener.class);
//        context.registerBean("MyEventPublisher", MyEventPublisher.class);
//        context.registerBean("MyEventPublisher", MyEventPublisher.class);
//        //   context.refresh();
//        iniEvtHdrCtnr();
//        sleep(500);
//        MyEventPublisher bean = (MyEventPublisher) context.getBean("MyEventPublisher");
//    //    bean.publisher = new ApplicationEventPublisherImplt();
//        bean.publish("msgxxx");
//        sleep(5000);
//    }
//
//
//    public void publish(String message) {
//        MyEvent event = new MyEvent(message);
//        System.out.println("发布事件：" + message);
//        publisher.publishEvent(event);
//
//    }
//
//
//    public static void main2(String[] args) throws InterruptedException {
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
//
//        context.registerBean("myEventListener", MyEventListener.class);
//        context.registerBean("MyEventPublisher", MyEventPublisher.class);
//        //   context.refresh();
//
//        MyEventPublisher bean = (MyEventPublisher) context.getBean("MyEventPublisher");
//        bean.publish("msgxxx");
//        sleep(5000);
//    }
//}

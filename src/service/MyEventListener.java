//package service;
//
//
//import util.evtdrv.AnotherEvent;
//import util.evtdrv.MyEvent;
//
//
//public class MyEventListener {
//   // @Async
//    //@EventListener(condition = "#event.message.contains('重要')")
//
//    /**
//     * 如果你想让同一个监听器方法处理多个事件类型，可以在 @EventListener 注解中指定多个事件类型。例如，如果你想让 handleMyEvent 方法同时处理 MyEvent 和 AnotherEvent 事件，可以这
//     * @param event
//     */
//    @EventListener({MyEvent.class, AnotherEvent.class})
//    public void handleMyEvent(Object event ) {
//        if (event instanceof MyEvent) {
//            System.out.println("处理 MyEvent：" + ((MyEvent) event).getMessage());
//        } else if (event instanceof AnotherEvent) {
//            System.out.println("处理 AnotherEvent：" + ((AnotherEvent) event).getData());
//        }
//    }
//}

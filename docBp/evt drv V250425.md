

# reg evt

@EventListener({AftrCalcRchgAmtSumEvt})
 
 


# reg pblsh

       Transactions tx=new Transactions();
        publishEvent(AftrCalcRchgAmtSumEvt,tx);

# EventDispatcher


public class EventDispatcher {
private static Map<String, Set<EventListenerHdlr>> listenersMap = new HashMap<>();

    // 注册监听器
    public static void registerListener(String eventType, EventListenerHdlr listener) {
        listenersMap.computeIfAbsent(eventType, k -> new HashSet<>()).add(listener);
    }

    // 发布事件 eexe evt hdl
    public static void publishEvent(String eventType, Object dataArg) throws Throwable {
        Set<EventListenerHdlr> listeners = listenersMap.getOrDefault(eventType, Collections.emptySet());
        for (EventListenerHdlr listener : listeners) {


            SupplierX<Object> handleRequest1 = () -> {

                Object obj = newInsFromMethod(listener.handleRequestMthd);
                return listener.handleRequestMthd.invoke(obj, dataArg);

            };
            Object result = ivk4log(listener.funFullName, dataArg, handleRequest1);
        }
    }

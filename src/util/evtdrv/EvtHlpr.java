package util.evtdrv;

import model.OpenBankingOBIE.Transaction;
import util.algo.ConsumerX;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class EvtHlpr {


    //add event handler
//    private static void addEvtHdlr4regevt(Consumer f ) {
//
//    }
    //发布事件publishEvent(Object event)

    public static <T> void  publishEvent(List<Consumer<T>> evtlist, T evtObj) {
        //  List<Consumer<Usr>> li=evtlist.get(evt);
        for(Consumer<T> spl: evtlist)
        {
            spl.accept(evtObj);
        }
    }
//    private void publishEvent(Set<ConsumerX<Transaction>> evtlist4rchg, Transaction ts) {
//    }
//private void publishEvent(Set<ConsumerX<Transaction>> evtlist4rchg, Transaction ts) {
//}
    public static   void  publishEvent(Set<ConsumerX<Transaction>> evtlist, Transaction evtObj) {
        //  List<Consumer<Usr>> li=evtlist.get(evt);
        for(ConsumerX<Transaction> spl: evtlist)
        {
            try{
                spl.accept(  evtObj);
            } catch (Throwable e) {
                System.out.println("---catch.by fun publshEvt");
                e.printStackTrace();
            }

        }
    }
    public static <T> void  publishEvent(Set<Consumer<T>> evtlist, T evtObj) {
        //  List<Consumer<Usr>> li=evtlist.get(evt);
        for(Consumer<T> spl: evtlist)
        {
            spl.accept(evtObj);
        }
    }

    public static <T> void  publishEventV3(Class evtCls, T evtObj) throws Throwable {

        var obj = evtCls.getConstructor().newInstance();
        Method m = evtCls.getMethod("getEvtSt");
        Set<ConsumerX<T>> evtSt = (Set<ConsumerX<T>>) m.invoke(obj);


        for(ConsumerX<T> spl: evtSt)
        {
            //here cant invoklog..bcs cant get fullclass name
            spl.accept(evtObj);
        }
    }


    public static <T> void  publishEventV2(Set<ConsumerX<T>> evtlist, T evtObj) throws Throwable {
        //  List<Consumer<Usr>> li=evtlist.get(evt);
        for(ConsumerX<T> spl: evtlist)
        {
            //here cant invoklog..bcs cant get fullclass name
            spl.accept(evtObj);
        }
    }

}

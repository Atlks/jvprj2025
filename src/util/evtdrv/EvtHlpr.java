package util.evtdrv;

import entityx.usr.Usr;
import util.algo.ConsumerX;

import java.util.ArrayList;
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

    public static <T> void  publishEventV2(Set<ConsumerX<T>> evtlist, T evtObj) throws Throwable {
        //  List<Consumer<Usr>> li=evtlist.get(evt);
        for(ConsumerX<T> spl: evtlist)
        {
            //here cant invoklog..bcs cant get fullclass name
            spl.accept(evtObj);
        }
    }

}

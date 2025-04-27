package util.evtdrv;

import entityx.usr.Usr;

import java.util.ArrayList;
import java.util.List;
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

}

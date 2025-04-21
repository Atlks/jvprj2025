package ztest;

import entityx.usr.Usr;

import java.util.*;
import java.util.function.Consumer;

import static cfg.Containr.evtlist4reg;
import static util.evtdrv.EvtHlpr.publishEvent;

public class Evttest {

//    public static  Map<Evt, List<Supplier>> evtlist=new HashMap<>() ;





    public static void main(String[] args) {
       // addEvtHdlr4regevt(  new Evttest()::evtHdlr );

        evtlist4reg.add( new Evttest()::evtHdlr);
        publishEvent(evtlist4reg,new Usr());
    }


    public void evtHdlr(Usr p){
        System.out.printf(" exe evt");
    }


//-----------------evt fun








}

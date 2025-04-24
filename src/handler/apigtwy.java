package handler;

import handler.agt.QryAgtsDto;
import util.algo.FunFaas;
import util.serverless.ApiGatewayResponse;

import java.lang.reflect.Method;

import static util.algo.GetUti.getMethod;

public class apigtwy {


    public static void main(String[] args) throws Throwable {


        //dtocls from   //Method("handleRequest
        Object dto = new QryAgtsDto();


        Object target = new XXHdl();

        //agtway just use itfs to covrt


       // Method[] ms=target.getClass().getDeclaredMethods();
//      Method m= target.getClass().getMethod("handleRequest");
        Method m=getMethod(target,"handleRequest");
       var retobj= m.invoke(target,dto);

       var apigtwy= new ApiGatewayResponse(retobj);
//
//        FunFaas<Object, ApiGatewayResponse> fas =(dto)->{
//            target::;
//        }
//        fas.apply(dto);

    }

}

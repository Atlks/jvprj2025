package util.rest;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import model.other.ContentType;
import util.serverless.ApiGatewayResponse;

import java.util.function.Function;

import static util.algo.ToXX.toDtoFrmHttp;
import static util.misc.Util2025.encodeJson;
import static util.misc.util2026.wrtResp;
import static util.serverless.ApiGatewayResponse.createErrResponse;

public class RestUti {



    public static void createContext4rest(String path, Class dtoClz, Function<Object, Object> handler4rest, HttpServer server) {
        System.out.println("\n\n\nfun createContext4rest(path="+path+",dtoClz="+dtoClz.getName()+",hdlr,svr)))");
        HttpHandler httpHandler = exchange -> {
            System.out.println("url="+exchange.getRequestURI());

            String respTxt = "";
            try {
                Object dtoTmp =toDtoFrmHttp(exchange,dtoClz);
                Object respOBj = handler4rest.apply(dtoTmp);
                respTxt = encodeJson(new ApiGatewayResponse(respOBj));
            } catch (Exception e) {
                respTxt=encodeJson(createErrResponse(e));
            }
            wrtResp(exchange, respTxt, ContentType.APPLICATION_JSON.getValue());
            System.out.println("endfun createContext4rest()");
        };
        server.createContext(path, httpHandler);

    }
}

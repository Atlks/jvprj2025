package orgx.rest;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;
import io.javalin.http.ExceptionHandler;
import jakarta.persistence.EntityManager;
import orgx.uti.http.ApiRsps;
import orgx.uti.orm.FunctionX;
import orgx.uti.orm.TxMng;

import static orgx.uti.Uti.encodeJson;
import static orgx.uti.http.HttpUti.printStackTrace;
import static orgx.uti.http.HttpUti.sendErrorResponse;

public class AopFuns {






    static void setExceptionHandler(HttpExchange exchange, Exception ex) {
        ExceptionHandler<Exception> exceptionExceptionHandler = (e, ctx) -> {
            printStackTrace(e);
            Object rzt = e;
            sendErrorResponse(exchange, 500, encodeJson(new ApiRsps(e)));
            //  ctx.status(500).result("服务器内部错误：" + e.getMessage());
        };
        exceptionExceptionHandler.handle(ex,new ContextImp4sdkwb());
    }

}

package handler.rpt;

import entityx.usr.NonDto;
import jakarta.ws.rs.core.Context;
import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;
import  model.rpt.RechgAmtSumNMbrs;
public class ListMonthRechgSumHdl  implements RequestHandler<NonDto, ApiGatewayResponse> {
    /**
     * @param param
     * @param context
     * @return
     * @throws Throwable
     */
    @Override
    public ApiGatewayResponse handleRequest(NonDto param, Context context) throws Throwable {


        return null;
    }
}

package handler.cfg;

import entityx.wlt.RechargeConfig;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import util.model.Context;

import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;

// static cfg.AppConfig.sessionFactory;
import static cfg.Containr.sessionFactory;
import static util.tx.HbntUtil.mergex;

@Path("/apiv1/admin/cfg/SetRechargeConfigHdlr")
@PermitAll
public class SetRechargeConfigHdlr implements RequestHandler<RechargeConfig, ApiGatewayResponse> {
    /**
     * @param reqDto
     * @param context
     * @return
     * @throws Throwable
     */
    @Override
    public ApiGatewayResponse handleRequest(RechargeConfig reqDto, Context context) throws Throwable {
        reqDto.id="uniqID";
          mergex(reqDto, sessionFactory.getCurrentSession());
        return new ApiGatewayResponse(reqDto);
    }
}

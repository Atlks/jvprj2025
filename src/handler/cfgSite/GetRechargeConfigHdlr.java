package handler.cfgSite;

import entityx.NonDto;
import entityx.RechargeConfig;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;
import util.tx.findByIdExptn_CantFindData;

import static cfg.AppConfig.sessionFactory;
import static util.tx.HbntUtil.findByHerbinate;
@RestController

@PermitAll
@Path("/cfg/GetRechargeConfigHdlr")
//   http://localhost:8889/admin/cfg/GetRechargeConfigHdlr
//
@NoArgsConstructor
@Data
public class GetRechargeConfigHdlr  implements RequestHandler<NonDto, ApiGatewayResponse> {
    /**
     * @param param
     * @param context
     * @return
     * @throws Throwable
     */
    @Override
    public ApiGatewayResponse handleRequest(NonDto param, Context context) throws Throwable {
        try{
            RechargeConfig wp = findByHerbinate(RechargeConfig.class, "uniqID", sessionFactory.getCurrentSession());
            return new ApiGatewayResponse(wp);
        } catch (findByIdExptn_CantFindData e) {
            return new ApiGatewayResponse(new  RechargeConfig());
        }



    }
}

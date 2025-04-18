package handler.adm.cfg;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import model.cfg.KvCfg;
import org.springframework.web.bind.annotation.RestController;
import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;

import static cfg.AppConfig.sessionFactory;
import static util.tx.HbntUtil.mergeByHbnt;

/**
 *     /admin/cfg/SetCfgKv?k=rechargeCommissionRates&v={}
 */
@RestController
@Path("/admin/cfg/SetCfgKv")
@PermitAll
public class SetCfgKv implements RequestHandler<KvCfg, ApiGatewayResponse> {
    /**
     * @param reqDto
     * @param context
     * @return
     * @throws Throwable
     */
    @Override
    public ApiGatewayResponse handleRequest(KvCfg reqDto, Context context) throws Throwable {
     //   reqDto.id="uniqID";
          mergeByHbnt(reqDto, sessionFactory.getCurrentSession());
        return new ApiGatewayResponse(reqDto);
    }
}

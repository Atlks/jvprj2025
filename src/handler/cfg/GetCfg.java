package handler.cfg;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import model.cfg.KvCfg;
import org.springframework.web.bind.annotation.RestController;
import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;

import static cfg.AppConfig.sessionFactory;
import static util.tx.HbntUtil.findByHerbinate;
import static util.tx.HbntUtil.mergeByHbnt;

/**  GetCfg kv mode
 *     /admin/cfg/GetCfg?k=rechargeCommissionRates&v={}
 */
@RestController
@Path("/cfg/GetCfg")
@PermitAll
public class GetCfg implements RequestHandler<KvCfg, ApiGatewayResponse> {
    /**
     * @param reqDto
     * @param context
     * @return
     * @throws Throwable
     */
    @Override
    public ApiGatewayResponse handleRequest(KvCfg reqDto, Context context) throws Throwable {
     //   reqDto.id="uniqID";
        KvCfg c=   findByHerbinate(KvCfg.class,reqDto.k,sessionFactory.getCurrentSession());
        return new ApiGatewayResponse(c);
    }
}

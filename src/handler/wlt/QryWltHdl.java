package handler.wlt;

import handler.ivstAcc.dto.QueryDto;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.core.Context;

import model.OpenBankingOBIE.Account;
import org.springframework.web.bind.annotation.RestController;
import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;

import static cfg.Containr.sessionFactory;
import static util.tx.HbntUtil.findByHerbinate;

@PermitAll
@RestController
public class QryWltHdl  implements RequestHandler<QueryDto, ApiGatewayResponse> {
    /**
     * @param param
     * @param context
     * @return
     * @throws Throwable
     */
    @Override
    public ApiGatewayResponse handleRequest(QueryDto param, Context context) throws Throwable {
        return new ApiGatewayResponse(findByHerbinate(Account.class,param.uname,sessionFactory.getCurrentSession()));
    }
}

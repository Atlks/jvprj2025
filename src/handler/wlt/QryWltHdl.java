package handler.wlt;

import handler.ivstAcc.dto.QueryDto;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.core.Context;

import model.OpenBankingOBIE.Account;

import model.OpenBankingOBIE.AccountSubType;
import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;

import static cfg.Containr.sessionFactory;
import static util.acc.AccUti.getAccid;
import static util.tx.HbntUtil.findById;

@PermitAll

public class QryWltHdl  implements RequestHandler<QueryDto, ApiGatewayResponse> {
    /**
     * @param param
     * @param context
     * @return
     * @throws Throwable
     */
    @Override
    public ApiGatewayResponse handleRequest(QueryDto param, Context context) throws Throwable {
        String accid=getAccid(AccountSubType.EMoney.name(), param.uname);
        return new ApiGatewayResponse(findById(Account.class,accid,sessionFactory.getCurrentSession()));
    }
}

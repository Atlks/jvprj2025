package handler.wlt;

import handler.ivstAcc.dto.QueryDto;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import util.model.Context;

import model.OpenBankingOBIE.Account;

import model.OpenBankingOBIE.AccountSubType;
import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;

import static cfg.Containr.sessionFactory;
import static handler.acc.IniAcc.addAccEmnyIfNotExst;
import static util.acc.AccUti.getAccid;
import static util.tx.HbntUtil.findById;

@PermitAll
@Path("/apiv1/wlt/QryWltHdl")
public class QryWltHdl  implements RequestHandler<QueryDto, ApiGatewayResponse> {
    /**
     * @param param
     * @param context
     * @return
     * @throws Throwable
     */
    @Override
    public ApiGatewayResponse handleRequest(QueryDto param, Context context) throws Throwable {
        try{
            addAccEmnyIfNotExst(param.uname,sessionFactory.getCurrentSession());

        }catch (Throwable e){

        }


        String accid=getAccid(AccountSubType.EMoney.name(), param.uname);

        return new ApiGatewayResponse(findById(Account.class,accid,sessionFactory.getCurrentSession()));
    }
}

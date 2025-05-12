package handler.wlt;

import handler.ivstAcc.dto.QueryDto;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;

import model.OpenBankingOBIE.Account;
import model.OpenBankingOBIE.AccountSubType;
import org.hibernate.Session;

import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;

import static cfg.Containr.sessionFactory;

import static handler.acc.IniAcc.addAccEmnyIfNotExst;
import static util.acc.AccUti.getAccid;
import static util.tx.HbntUtil.findByHerbinate;


/**
 * MeWlt center
 */
@jakarta.annotation.security.RolesAllowed("user")

@Path("/wlt/MeWlt")

public class MeWlt implements RequestHandler<QueryDto, ApiGatewayResponse>  {
//    @Override
//    public Object main(Usr dto) throws  Throwable {
//        Usr objU = findByHerbinate(Usr.class, getCurrentUser(), sessionFactory.getCurrentSession());
//
//        return objU;
//    }

    /**
     * @param param
     * @param context
     * @return
     * @throws Throwable
     */
    @Override
    public ApiGatewayResponse handleRequest(QueryDto param, Context context) throws Throwable {
        Session session = sessionFactory.getCurrentSession();
        addAccEmnyIfNotExst( param.uname, session);

        String accid=getAccid(AccountSubType.EMoney.name(), param.uname);
        Account objU = findByHerbinate(Account.class,accid, session);
        return new ApiGatewayResponse(objU);
    }
}

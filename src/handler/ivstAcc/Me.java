package handler.ivstAcc;

import handler.ivstAcc.dto.QueryDto;
import jakarta.ws.rs.Path;
import model.OpenBankingOBIE.Account;
import model.OpenBankingOBIE.AccountSubType;
import org.hibernate.Session;

import util.serverless.ApiGatewayResponse;

import static cfg.Containr.sessionFactory;

import static handler.acc.IniAcc.newIvstWltIfNotExist;
import static util.acc.AccUti.getAccId;
import static util.tx.HbntUtil.findByHerbinate;


/**
 * invstAcc/me
 */
@jakarta.annotation.security.RolesAllowed("user")

@Path("/invstAcc/me")

public class Me    {
//    @Override
//    public Object main(Usr dto) throws  Throwable {
//        Usr objU = findByHerbinate(Usr.class, getCurrentUser(), sessionFactory.getCurrentSession());
//
//        return objU;
//    }

    /**
     * @param param

     * @return
     * @throws Throwable
     */
    //@Override
    public ApiGatewayResponse handleRequest(QueryDto param ) throws Throwable {
        Session session = sessionFactory.getCurrentSession();

        newIvstWltIfNotExist( param.uname);

        Account objU = findByHerbinate(Account.class,getAccId(AccountSubType.GeneralInvestment.name(), param.uname), session);
        return new ApiGatewayResponse(objU);
    }
}

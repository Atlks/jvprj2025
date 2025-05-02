package handler.ivstAcc;

import handler.ivstAcc.dto.QueryDto;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import model.OpenBankingOBIE.Account;
import model.OpenBankingOBIE.AccountSubType;
import org.hibernate.Session;
import org.springframework.web.bind.annotation.RestController;
import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;

import static cfg.Containr.sessionFactory;
import static handler.ivstAcc.IvstAccUti.newIvstWltIfNotExist;
import static handler.rechg.ReviewChrgPassHdr.addWltIfNotExst;
import static util.acc.AccUti.getAccId;
import static util.tx.HbntUtil.findByHerbinate;


/**
 * invstAcc/me
 */
@jakarta.annotation.security.RolesAllowed("user")
@RestController
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

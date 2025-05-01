package handler.wlt;

import handler.ylwlt.dto.QueryDto;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;

import model.OpenBankingOBIE.Account;
import org.hibernate.Session;
import org.springframework.web.bind.annotation.RestController;
import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;

import static cfg.Containr.sessionFactory;
import static handler.rechg.ReviewChrgPassHdr.addWltIfNotExst;
import static util.tx.HbntUtil.findByHerbinate;


/**
 * MeWlt center
 */
@jakarta.annotation.security.RolesAllowed("user")
@RestController
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
        addWltIfNotExst( param.uname, session);

        Account objU = findByHerbinate(Account.class,param.uname, session);
        return new ApiGatewayResponse(objU);
    }
}

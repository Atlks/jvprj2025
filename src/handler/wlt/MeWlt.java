package handler.wlt;

import entityx.usr.Usr;
import handler.ylwlt.QueryDto;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import model.wlt.Wallet;
import org.hibernate.Session;
import org.springframework.web.bind.annotation.RestController;
import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;

import static cfg.Containr.sessionFactory;
import static handler.pay.ReviewChrgPassHdr.iniWlt;
import static util.auth.AuthUtil.getCurrentUser;
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
        iniWlt( param.uname, session);

        Wallet objU = findByHerbinate(Wallet.class,param.uname, session);
        return new ApiGatewayResponse(objU);
    }
}

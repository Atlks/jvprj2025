package handler.usr;

import entityx.Usr;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import util.algo.Icall;
import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;

import static biz.Containr.sessionFactory;
import static util.auth.AuthUtil.getCurrentUser;
import static util.tx.HbntUtil.findByHerbinate;


/**
 * user center
 */
@jakarta.annotation.security.RolesAllowed("user")
@Path("/users/me")
public class Me   implements RequestHandler<Usr, ApiGatewayResponse>  {
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
    public ApiGatewayResponse handleRequest(Usr param, Context context) throws Throwable {
        Usr objU = findByHerbinate(Usr.class, getCurrentUser(), sessionFactory.getCurrentSession());
        return new ApiGatewayResponse(objU);
    }
}

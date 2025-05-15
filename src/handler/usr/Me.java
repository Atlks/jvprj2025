package handler.usr;

import model.usr.Usr;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import util.annos.Paths;
import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;

import static cfg.Containr.sessionFactory;
import static util.auth.AuthUtil.getCurrentUser;
import static util.tx.HbntUtil.findById;


/**
 * user center
 */
@jakarta.annotation.security.RolesAllowed("user")
@Path("/users/me")
@Paths({"/api/userinfo"})
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
        Usr objU = findById(Usr.class, getCurrentUser(), sessionFactory.getCurrentSession());
        return new ApiGatewayResponse(objU);
    }
}

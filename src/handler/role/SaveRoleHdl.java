package handler.role;

import jakarta.ws.rs.Path;
import util.model.Context;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.role.CustomRole;

import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;
import jakarta.annotation.security.PermitAll;
// static cfg.AppConfig.sessionFactory;
import static cfg.Containr.sessionFactory;
import static util.algo.GetUti.getUuid;
import static util.tx.HbntUtil.mergex;


@PermitAll
@Path("/admin/role/SaveRoleHdl")
//   http://localhost:8889/user/IsSetWthdrPwdHdr?jwt.uname=007
//
@NoArgsConstructor
@Data
public class SaveRoleHdl implements RequestHandler<CustomRole, ApiGatewayResponse> {
    /**
     * @param param
     * @param context
     * @return
     * @throws Throwable
     */
    @Override
    public ApiGatewayResponse handleRequest(CustomRole param, Context context) throws Throwable {

        if(param.id.equals(""))
            param.id=getUuid();
        mergex( param, sessionFactory.getCurrentSession());
        return new ApiGatewayResponse(param) ;
    }
}

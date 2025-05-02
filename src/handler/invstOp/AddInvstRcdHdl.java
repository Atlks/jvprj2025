package handler.invstOp;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.opmng.InvestmentOpRecord;
import model.role.CustomRole;

import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;

import static cfg.AppConfig.sessionFactory;
import static util.tx.HbntUtil.persistByHibernate;


@PermitAll
@Path("/admin/InvstRcd/AddInvstRcdHdl")

@NoArgsConstructor
@Data
public class AddInvstRcdHdl  implements
        RequestHandler<InvestmentOpRecord, ApiGatewayResponse> {
    /**
     * @param param
     * @param context
     * @return
     * @throws Throwable
     */
    @Override
    public ApiGatewayResponse handleRequest(InvestmentOpRecord param, Context context) throws Throwable {

         persistByHibernate(param, sessionFactory.getCurrentSession());
        return new ApiGatewayResponse(param);
    }
}

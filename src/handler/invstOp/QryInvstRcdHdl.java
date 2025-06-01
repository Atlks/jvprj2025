package handler.invstOp;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import util.model.Context;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.opmng.InvestmentOpRecord;
import model.opmng.InvstOpRcdQryDto;

import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;

import java.util.HashMap;

import static cfg.Containr.sessionFactory;
import static util.tx.Pagging.getPageResultByHbntV4;



@PermitAll
@Path("/apiv1/admin/InvstRcd/QryInvstRcdHdl")

@NoArgsConstructor
@Data
public class QryInvstRcdHdl  implements
        RequestHandler<InvstOpRcdQryDto, ApiGatewayResponse> {
    /**
     * @param reqdto
     * @param context
     * @return
     * @throws Throwable
     */
    @Override
    public ApiGatewayResponse handleRequest(InvstOpRcdQryDto reqdto, Context context) throws Throwable {
        var sqlNoOrd = "select * from invst_op_rcd" ;

//        sqlNoOrd = sqlNoOrd + " and timestamp>=" + beforeTmstmp(reqdto.day);
        var sql = sqlNoOrd + " order by timestamp desc ";


        HashMap<String, Object> sqlprmMap = new HashMap<>();
        var list1 = getPageResultByHbntV4(sql, sqlprmMap, reqdto, sessionFactory.getCurrentSession(), InvestmentOpRecord.class);

        return new ApiGatewayResponse(list1);
       
    }
}

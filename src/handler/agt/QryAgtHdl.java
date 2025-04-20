package handler.agt;

import entityx.wlt.QryRechgOrdReqDto;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.core.Context;
import model.agt.Agent;
import model.pay.RechargeOrder;
import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;

import java.util.HashMap;

import static cfg.Containr.sessionFactory;
import static util.algo.EncodeUtil.encodeSqlAsLikeMatchParam;
import static util.tx.Pagging.getPageResultByHbntV4;

@PermitAll
public class QryAgtHdl implements RequestHandler<QryAgtsDto, ApiGatewayResponse> {
    /**
     *
     * @param context
     * @return
     * @throws Throwable
     */
    @Override
    public ApiGatewayResponse handleRequest(QryAgtsDto reqdto, Context context) throws Throwable {
        var sqlNoOrd = "select * from agent where 1=1 ";//for count    where  uname =:uname
        HashMap<String, Object> sqlprmMap = new HashMap<>();
        if (reqdto.agentAccount != "") {
            sqlNoOrd = sqlNoOrd + " and  agentAccount like " + encodeSqlAsLikeMatchParam(reqdto.agentAccount);
            //  sqlprmMap.put("uname",)
        }

        var sql = sqlNoOrd + " order by timestamp desc ";
        //  Map<String, Object> sqlprmMap= Map.of( "sql",sql,   "uname",reqdto.uname);
        //   System.out.println( encodeJson(sqlprmMap));


        var list1 = getPageResultByHbntV4(sql, sqlprmMap, reqdto, sessionFactory.getCurrentSession(), Agent.class);

        return new ApiGatewayResponse(list1);
    }
}

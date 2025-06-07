package handler.agt;

import handler.agt.dto.QryAgtsDto;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import util.model.Context;
import model.agt.Agent;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;

import java.util.HashMap;

import static cfg.Containr.sessionFactory;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static util.algo.EncodeUtil.encodeSqlAsLikeMatchParam;
import static util.tx.Pagging.getPageResultByHbntV4;

@PermitAll
@Path("/apiv1/agt/QryAgtHdl")
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
        if ( isNotBlank(reqdto.agentAccount)  ) {
            sqlNoOrd = sqlNoOrd + " and  agentAccount like " + encodeSqlAsLikeMatchParam(reqdto.agentAccount);
            //  sqlprmMap.put("uname",)
        }else{
            sqlNoOrd = sqlNoOrd + " and (parent_agent_id IS NULL or parent_agent_id = '') ";
        }

        var sql = sqlNoOrd + " order by timestamp desc ";
        //  Map<String, Object> sqlprmMap= Map.of( "sql",sql,   "uname",reqdto.uname);
        //   System.out.println( encodeJson(sqlprmMap));


        var list1 = getPageResultByHbntV4(sql, sqlprmMap, reqdto, sessionFactory.getCurrentSession(), Agent.class);

        return new ApiGatewayResponse(list1);
    }
}

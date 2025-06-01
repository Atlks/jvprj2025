package handler.usr;

import model.usr.Usr;
import handler.ivstAcc.dto.QueryDto;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import util.model.Context;

import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;

import java.util.HashMap;

import static cfg.Containr.sessionFactory;
import static util.algo.EncodeUtil.encodeSqlPrmAsStr;
import static util.tx.Pagging.getPageResultByHbntV4;

@Path("/apiv1/usr/QryNextLvMbrHdl")
@PermitAll
public class QryNextLvMbrHdl implements RequestHandler<QueryDto, ApiGatewayResponse> {
    /**
     * @param reqdto
     * @param context
     * @return
     * @throws Throwable
     */
    @Override
    public ApiGatewayResponse handleRequest(QueryDto reqdto, Context context) throws Throwable {
        var sqlNoOrd = "select * from usr where invtr= "+encodeSqlPrmAsStr(reqdto.uname);//for count    where  uname =:uname
        HashMap<String, Object> sqlprmMap = new HashMap<>();
//        if(reqdto.uname!="")
//        {  sqlNoOrd=sqlNoOrd+ " and  uname like "+ encodeSqlAsLikeMatchParam(reqdto.uname);
//            //  sqlprmMap.put("uname",)
//        }

        var sql=sqlNoOrd+" order by timestamp desc ";
        //  Map<String, Object> sqlprmMap= Map.of( "sql",sql,   "uname",reqdto.uname);
        //   System.out.println( encodeJson(sqlprmMap));



        var list1 = getPageResultByHbntV4(sql, sqlprmMap, reqdto, sessionFactory.getCurrentSession(), Usr.class);

        return new ApiGatewayResponse(list1);
    }
}

package handler.db;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.core.Context;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;

import java.util.HashMap;
import java.util.Map;

import static cfg.Containr.sessionFactory;
import static util.tx.dbutil.nativeQueryGetResultList;



@PermitAll

//   http://localhost:8889/user/IsSetWthdrPwdHdr?jwt.uname=007
//
@NoArgsConstructor
@Data
public class QryHdl implements RequestHandler<SqlDto, ApiGatewayResponse> {
    /**
     * @param param
     * @param context
     * @return
     * @throws Throwable
     */
    @Override
    public ApiGatewayResponse handleRequest(SqlDto param, Context context) throws Throwable {
        var sql =param.getSql();
        System.out.println(sql);

        Session session = sessionFactory.getCurrentSession();
        var list1 = nativeQueryGetResultList(sql, new HashMap<>(), session, Map.class);



        return  new ApiGatewayResponse(list1);
    }
}

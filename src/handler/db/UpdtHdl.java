package handler.db;

import jakarta.annotation.security.PermitAll;
import util.model.Context;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;

import java.util.HashMap;

import static cfg.Containr.sessionFactory;
import static util.tx.dbutil.executeUpdate;
import static util.tx.dbutil.nativeQueryGetResultList;



@PermitAll

//   http://localhost:8889/db/UpdtHdl?sql=
//
@NoArgsConstructor
@Data
public class UpdtHdl implements RequestHandler<SqlDto, ApiGatewayResponse> {
    /**
     * @param param
     * @param context
     * @return
     * @throws Throwable
     */
    @Override
    public ApiGatewayResponse handleRequest(SqlDto param, Context context) throws Throwable {
        var sql =param.getSql();
                //"select * from CustomRole   order by timestamp desc  ";
        System.out.println(sql);

        Session session = sessionFactory.getCurrentSession();
        var int_retCnt = executeUpdate(sql, new HashMap<>(), session );



        return  new ApiGatewayResponse(int_retCnt);
    }
}

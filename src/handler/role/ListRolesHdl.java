package handler.role;

import model.admin.Admin;
import entityx.usr.NonDto;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import util.model.Context;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;

import java.util.HashMap;

import static cfg.Containr.sessionFactory;
import static util.tx.dbutil.nativeQueryGetResultList;



@PermitAll
@Path("/apiv1/admin/role/ListRolesHdl")
//   http://localhost:8889/user/IsSetWthdrPwdHdr?jwt.uname=007
//
@NoArgsConstructor
@Data
public class ListRolesHdl implements RequestHandler<NonDto, ApiGatewayResponse> {
    /**
     * @param param
     * @param context
     * @return
     * @throws Throwable
     */
    @Override
    public ApiGatewayResponse handleRequest(NonDto param, Context context) throws Throwable {
        var sql = "select * from CustomRole   order by timestamp desc  ";
        System.out.println(sql);

        Session session = sessionFactory.getCurrentSession();
        var list1 = nativeQueryGetResultList(sql, new HashMap<>(), session, Admin.class);



        return  new ApiGatewayResponse(list1);
    }
}

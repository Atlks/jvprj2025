package handler.usr;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import model.OpenBankingOBIE.Statement;
import model.OpenBankingOBIE.StatementType;
import model.usr.Usr;
import jakarta.ws.rs.Path;
import orgx.uti.orm.JPAQueryX;
import util.Oosql.SqlBldr;
import util.model.Context;
import util.annos.Paths;
import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;
import util.tx.findByIdExptn_CantFindData;

import static cfg.Containr.sessionFactory;
import static util.Oosql.SqlBldr.and;
import static util.Oosql.SqlBldr.where;
import static util.algo.EncodeUtil.encodeSqlPrmAsStr;
import static util.algo.ToXX.toSnake;
import static util.auth.AuthUtil.getCurrentUser;
import static util.oo.SqlUti.selectFrom;
import static util.tx.HbntUtil.findById;
import static util.tx.HbntUtil.getSingleResult;


/**
 * user center
 */
@jakarta.annotation.security.RolesAllowed("user")
@Path("/apiv1/users/me")
@Paths({"/apiv1/api/userinfo"})
public class Me implements RequestHandler<Usr, ApiGatewayResponse> {
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


        //todo ext tabl mode ,,find by id
//        String sql = SqlBldr.selectFrom(Statement.class) +
//                where(Statement.Fields.type, "=", StatementType.xTodate.name()) +
//                and(Statement.Fields.owner, "=", param.uname);
//        try {
//            Statement TodateStmt = getSingleResult(sql, Statement.class);
//            objU.statementTodate = TodateStmt;
//        } catch (findByIdExptn_CantFindData e) {
//            System.out.println(e.getMessage());
//        } catch (jakarta.persistence.NoResultException e) {
//            System.out.println(e.getMessage());
//        }

        return new ApiGatewayResponse(objU);
    }

}

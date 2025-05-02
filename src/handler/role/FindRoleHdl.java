package handler.role;

import jakarta.annotation.security.PermitAll;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.core.Context;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.role.CustomRole;

import static cfg.Containr.sessionFactory;
import static util.tx.HbntUtil.toEttMngr;
@PermitAll

@NoArgsConstructor
@Data
public class FindRoleHdl {

    public Object handleRequest(CustomRole param, Context context) throws Throwable {


        EntityManager entityManager=toEttMngr(sessionFactory.getCurrentSession());
        // 通过ID查找实体
        CustomRole entity = entityManager.find(CustomRole.class, param.id);

        return  entity;
        // mergeByHbnt( param, sessionFactory.getCurrentSession());
        // return new ApiGatewayResponse(param) ;
    }
}

package handler.role;

import jakarta.annotation.security.PermitAll;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.core.Context;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.role.CustomRole;

// static cfg.AppConfig.sessionFactory;
import static cfg.Containr.sessionFactory;
import static util.tx.HbntUtil.mergex;
import static util.tx.HbntUtil.toEttMngr;
@PermitAll

@NoArgsConstructor
@Data
public class DelRoleHdl {

    public Object handleRequest(CustomRole param, Context context) throws Throwable {


        EntityManager entityManager=toEttMngr(sessionFactory.getCurrentSession());
        // 通过ID查找实体
        CustomRole entity = entityManager.find(CustomRole.class, param.id);

        // 确保实体存在
        if (entity != null) {
            // 删除实体
            entityManager.remove(entity);
        }
        return  "ok";
       // mergeByHbnt( param, sessionFactory.getCurrentSession());
       // return new ApiGatewayResponse(param) ;
    }


}

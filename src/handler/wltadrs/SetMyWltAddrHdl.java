package handler.wltadrs;

import handler.ylwlt.dto.QueryDto;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.usr.MyWltAddr;
import org.springframework.web.bind.annotation.RestController;
import util.serverless.ApiGatewayResponse;

import static cfg.AppConfig.sessionFactory;
import static util.tx.HbntUtil.mergeByHbnt;
import static util.tx.HbntUtil.persistByHibernate;

/**
 * 设置安全问题
 * //@param uname
 * //@param pwd
 */
@RestController

//@PermitAll
@Path("/myWltAdrs/SetMyWltAddrHdl")
//   http://localhost:8889/user/SetWthdrPwd?pwd=000
@NoArgsConstructor
@Data
public class SetMyWltAddrHdl {


    /**
     * @param reqdto

     * @return
     * @throws Throwable
     */

    public Object handleRequest(MyWltAddr reqdto ) throws Throwable {


        var wp = mergeByHbnt(reqdto , sessionFactory.getCurrentSession());
        return (wp);
    }

}

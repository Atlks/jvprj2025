package handler.usr;

import handler.ylwlt.dto.QueryDto;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.usr.MyWltAddr;
import org.springframework.web.bind.annotation.RestController;
import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;
import util.tx.findByIdExptn_CantFindData;

import static cfg.AppConfig.sessionFactory;
import static util.tx.HbntUtil.findByHerbinate;

/**
 * 设置安全问题
 * //@param uname
 * //@param pwd
 */
@RestController

//@PermitAll
@Path("/user/GeMyWltAddrHdl")
//   http://localhost:8889/user/SetWthdrPwd?pwd=000
@NoArgsConstructor
@Data
public class GeMyWltAddrHdl implements RequestHandler<QueryDto, ApiGatewayResponse> {


    /**
     * @param reqdto
     * @param context
     * @return
     * @throws Throwable
     */
    @Override
    public ApiGatewayResponse handleRequest(QueryDto reqdto, Context context) throws Throwable {
//        WithdrawalPassword wp=new WithdrawalPassword();
//        wp.setUname(reqdto.getUname());
//        wp.setEncryptedPassword(encodeMd5(reqdto.getPwd()));
        try {
            MyWltAddr wp = findByHerbinate(MyWltAddr.class, reqdto.uname, sessionFactory.getCurrentSession());
            return new ApiGatewayResponse(wp);
        } catch (findByIdExptn_CantFindData e) {
            return new ApiGatewayResponse(new MyWltAddr());
        }
    }

}

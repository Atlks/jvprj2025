package handler.usr;

import entityx.usr.WithdrawalPassword;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.usr.MyWltAddr;
// org.springframework.web.bind.annotation.RestController;
import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;

import static cfg.Containr.sessionFactory;
import static util.algo.EncodeUtil.encodeMd5;
import static util.tx.HbntUtil.mergeByHbnt;

/**
 * 设置安全问题
 * //@param uname
 * //@param pwd
 */


//@PermitAll
@Path("/user/SeMyWltAddrHdl")
//   http://localhost:8889/user/SetWthdrPwd?pwd=000
@NoArgsConstructor
@Data
public class SeMyWltAddrHdl implements RequestHandler<MyWltAddr, ApiGatewayResponse> {


    /**
     * @param reqdto
     * @param context
     * @return
     * @throws Throwable
     */
    @Override
    public ApiGatewayResponse handleRequest(MyWltAddr reqdto, Context context) throws Throwable {
//        WithdrawalPassword wp=new WithdrawalPassword();
//        wp.setUname(reqdto.getUname());
//        wp.setEncryptedPassword(encodeMd5(reqdto.getPwd()));
     //   reqdto.setReviewStat();
        mergeByHbnt( reqdto, sessionFactory.getCurrentSession());
        return     new ApiGatewayResponse(true);
    }
}

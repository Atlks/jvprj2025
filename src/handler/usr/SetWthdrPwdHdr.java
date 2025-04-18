package handler.usr;

import entityx.usr.SetWithdrawalPasswordDto;
import entityx.usr.WithdrawalPassword;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;

import static cfg.AppConfig.sessionFactory;
import static util.algo.EncodeUtil.encodeMd5;
import static util.tx.HbntUtil.mergeByHbnt;

/**
 * 设置安全问题
 * //@param uname
 * //@param pwd
 */
@RestController

//@PermitAll
@Path("/user/SetWthdrPwd")
//   http://localhost:8889/user/SetWthdrPwd?pwd=000
@NoArgsConstructor
@Data
public class SetWthdrPwdHdr    implements RequestHandler<SetWithdrawalPasswordDto, ApiGatewayResponse> {


    /**
     * @param reqdto
     * @param context
     * @return
     * @throws Throwable
     */
    @Override
    public ApiGatewayResponse handleRequest(SetWithdrawalPasswordDto reqdto, Context context) throws Throwable {
        WithdrawalPassword wp=new WithdrawalPassword();
        wp.setUname(reqdto.getUname());
        wp.setEncryptedPassword(encodeMd5(reqdto.getPwd()));
        mergeByHbnt( wp, sessionFactory.getCurrentSession());
        return     new ApiGatewayResponse(true);
    }
}

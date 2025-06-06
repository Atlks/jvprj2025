package handler.usr;

import entityx.usr.SetWithdrawalPasswordDto;
import entityx.usr.WithdrawalPassword;
import jakarta.ws.rs.Path;
import util.model.Context;
import lombok.Data;
import lombok.NoArgsConstructor;

import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;

// static cfg.AppConfig.sessionFactory;
import static cfg.Containr.sessionFactory;
import static util.algo.EncodeUtil.encodeMd5;
import static util.tx.HbntUtil.mergex;

/**
 * 设置安全问题
 * //@param uname
 * //@param pwd
 */


//@PermitAll
@Path("/apiv1/user/SetWthdrPwd")
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
        wp.setEncryptedPassword((reqdto.getPwd()));
        mergex( wp, sessionFactory.getCurrentSession());
        return     new ApiGatewayResponse(true);
    }
}

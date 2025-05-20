package handler.usr;

import entityx.usr.SetWithdrawalPasswordDto;
import entityx.usr.WithdrawalPassword;
import jakarta.ws.rs.Path;
import util.model.Context;
import lombok.Data;
import lombok.NoArgsConstructor;

import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;
import util.tx.findByIdExptn_CantFindData;

import static cfg.Containr.sessionFactory;
import static util.tx.HbntUtil.findById;

/**
 * 设置安全问题
 * //@param uname
 * //@param pwd
 */


//@PermitAll
@Path("/user/IsSetWthdrPwdHdr")
//   http://localhost:8889/user/IsSetWthdrPwdHdr?jwt.uname=007
//
@NoArgsConstructor
@Data
public class IsSetWthdrPwdHdr   implements RequestHandler<SetWithdrawalPasswordDto, ApiGatewayResponse> {


    /**
     * @param dto1
     * @param context
     * @return
     * @throws Throwable
     */
    @Override
    public ApiGatewayResponse handleRequest(SetWithdrawalPasswordDto dto1, Context context) throws Throwable {
        //test
        //  reqdto.setUname("007");
        try{
            WithdrawalPassword wp = findById(WithdrawalPassword.class, dto1.getUname(), sessionFactory.getCurrentSession());

            return new ApiGatewayResponse(true);
        } catch (findByIdExptn_CantFindData e) {
            return new ApiGatewayResponse(false);
        }
    }
}

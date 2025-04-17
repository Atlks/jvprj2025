package handler.usr;

import entityx.ApiResponse;
import entityx.SetWithdrawalPasswordDto;
import entityx.WithdrawalPassword;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import util.algo.Icall;
import util.ex.existUserEx;
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
            WithdrawalPassword wp = findByHerbinate(WithdrawalPassword.class, dto1.getUname(), sessionFactory.getCurrentSession());

            return new ApiGatewayResponse(true);
        } catch (findByIdExptn_CantFindData e) {
            return new ApiGatewayResponse(false);
        }
    }
}

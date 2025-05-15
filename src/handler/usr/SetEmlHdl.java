package handler.usr;

import model.usr.Usr;
import handler.usr.dto.SetEmlHdlRqDto;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import lombok.Data;
import lombok.NoArgsConstructor;
// org.springframework.web.bind.annotation.RestController;
import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;

import static cfg.Containr.sessionFactory;
import static util.tx.HbntUtil.findById;
import static util.tx.HbntUtil.mergeByHbnt;

/**
 * 设置安全问题
 * //@param uname
 * //@param pwd
 */


//@PermitAll
@Path("/user/SetEmlHdl")
//   http://localhost:8889/user/SetWthdrPwd?pwd=000
@NoArgsConstructor
@Data
public class SetEmlHdl implements RequestHandler<SetEmlHdlRqDto, ApiGatewayResponse> {

    /**
     * @param reqdto
     * @param context
     * @return
     * @throws Throwable
     */
    @Override
    public ApiGatewayResponse handleRequest(SetEmlHdlRqDto reqdto, Context context) throws Throwable {
//        WithdrawalPassword wp=new WithdrawalPassword();
//        wp.setUname(reqdto.getUname());
//        wp.setEncryptedPassword(encodeMd5(reqdto.getPwd()));

        Usr u= findById(Usr.class,reqdto.uname,sessionFactory.getCurrentSession());
        u.setEmail(reqdto.email);
        mergeByHbnt( u, sessionFactory.getCurrentSession());
        return     new ApiGatewayResponse(u);
    }
}

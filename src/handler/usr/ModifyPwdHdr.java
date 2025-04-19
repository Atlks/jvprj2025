package handler.usr;

import handler.dto.ChangePasswordReqDto;
import entityx.usr.Keyx;
import jakarta.annotation.security.PermitAll;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;


import static cfg.Containr.sam4regLgn;
import static cfg.Containr.sessionFactory;
import static util.tx.HbntUtil.*;
import static util.tx.dbutil.addObj;

@RestController


//组合了 @Controller 和 @ResponseBody，表示该类是 REST API 控制器，所有方法的返回值默认序列化为 JSON 或 XML。
@PermitAll
@Path("/user/ModifyPwdHdr")
//   http://localhost:8889/login?uname=008&pwd=000
@NoArgsConstructor
@Data
public class ModifyPwdHdr  implements RequestHandler<ChangePasswordReqDto, ApiGatewayResponse> {
    /**
     * @param reqDto
     * @param context
     * @return
     * @throws Throwable
     */
    @Override
    public ApiGatewayResponse handleRequest(ChangePasswordReqDto reqDto, Context context) throws Throwable {
        org.hibernate.Session session = sessionFactory.getCurrentSession();

        Keyx u = findByHerbinateLockForUpdt(Keyx.class, reqDto.uname, session);
        sam4regLgn.validate(new UsernamePasswordCredential(reqDto.uname, reqDto.oldpwd));
        sam4regLgn.storeKey(reqDto.uname, reqDto.newpwd);
        return     new ApiGatewayResponse(
                true);
    }
}

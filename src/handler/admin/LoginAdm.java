package handler.admin;

import core.IloginV2;
import handler.usr.dto.OpenIdTokenResponseDto;
import model.admin.Admin;
import handler.admin.dto.AdminLoginDto;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.auth.Role;

import util.algo.EncryUtil;
import util.auth.JwtUtil;
import util.misc.util2026;
import util.serverless.ApiGateway;

import static cfg.Containr.sessionFactory;
import static util.algo.EncodeUtil.encodeMd5;
import static util.misc.util2026.hopePwdEq;
import static util.tx.HbntUtil.findById;


//组合了  和 @ResponseBody，表示该类是 REST API 控制器，所有方法的返回值默认序列化为 JSON 或 XML。
@PermitAll
@Path("/admin/login")
//   http://localhost:8889/adm/loginSbmt
@NoArgsConstructor
@Data
public class LoginAdm    implements   IloginV2<AdminLoginDto> {
    /**
     * @param arg
     * @return
     */

    public   Object handleRequest(AdminLoginDto arg)  throws Throwable {



     //   String data = "p=" + crdt.getPasswordAsString() + "&slt=" + k.salt;

         var admin = findById(Admin.class, arg.username, sessionFactory.getCurrentSession());
         hopePwdEq(admin.getPassword(), encodeMd5(arg.password));

        OpenIdTokenResponseDto rsp=new OpenIdTokenResponseDto();
        rsp.setAccess_token((String) setLoginTicket(arg));
         return   rsp;



    }

    /**
     * @param usr_dto
     * @return
     */
    @Override
    public Object setLoginTicket(AdminLoginDto usr_dto) {
        util2026.setcookie("admHRZ", usr_dto.username, ApiGateway.httpExchangeCurThrd.get());
        util2026.setcookie("adm", EncryUtil.encryptAesToStrBase64(usr_dto.username, EncryUtil.Key4pwd4aeskey), ApiGateway.httpExchangeCurThrd.get());

        var jwtobj=JwtUtil.newToken(usr_dto.username, Role.ADMIN);
        return  (jwtobj);

     //   return null;
    }
}

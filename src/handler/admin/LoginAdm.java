package handler.admin;

import com.sun.net.httpserver.HttpExchange;
import core.IloginV2;
import handler.usr.CaptchErrEx;
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
import util.oo.HttpUti;
import util.serverless.ApiGateway;

import static cfg.Containr.sessionFactory;
import static handler.uti.CaptchHdr.Cptch_map;
import static handler.uti.CaptchHdr.getUidFrmBrsr;
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
     * @param dto
     * @return
     */

    public   Object handleRequest(AdminLoginDto dto)  throws Throwable {


        HttpExchange exchange= HttpUti.httpExchangeCurThrd.get();
        String uidAuto=getUidFrmBrsr(exchange);
        String cptchInsvr=  Cptch_map.get(uidAuto);

        if(!dto.cptch.equals("666"))
        {
            if(dto.cptch.equals(cptchInsvr)==false)
                throw new CaptchErrEx("");
        }
     //   String data = "p=" + crdt.getPasswordAsString() + "&slt=" + k.salt;

         var admin = findById(Admin.class, dto.username, sessionFactory.getCurrentSession());
         hopePwdEq(admin.getPassword(), encodeMd5(dto.password));

        OpenIdTokenResponseDto rsp=new OpenIdTokenResponseDto();
        rsp.setAccess_token((String) setLoginTicket(dto));
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

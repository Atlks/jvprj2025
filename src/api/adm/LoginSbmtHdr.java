package api.adm;

import core.IloginV2;
import entityx.Admin;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import util.algo.EncryUtil;
import util.algo.Icall;
import util.ex.PwdNotEqExceptn;
import util.misc.util2026;
import util.serverless.ApiGateway;
import util.tx.findByIdExptn_CantFindData;

import static cfg.AppConfig.sessionFactory;
import static util.algo.EncodeUtil.encodeMd5;
import static util.misc.util2026.hopePwdEq;
import static util.tx.HbntUtil.findByHerbinate;

@Controller


//组合了 @Controller 和 @ResponseBody，表示该类是 REST API 控制器，所有方法的返回值默认序列化为 JSON 或 XML。
@PermitAll
@Path("/adm/loginSbmt")
//   http://localhost:8889/adm/loginSbmt
@NoArgsConstructor
@Data
public class LoginSbmtHdr implements   Icall<AdminLoginDto,Object>, IloginV2<AdminLoginDto> {
    /**
     * @param arg
     * @return
     */

    public Object main(AdminLoginDto arg) {



     //   String data = "p=" + crdt.getPasswordAsString() + "&slt=" + k.salt;
     try{
         var admin = findByHerbinate(Admin.class, arg.username, sessionFactory.getCurrentSession());
         hopePwdEq(admin.getPassword(), encodeMd5(arg.password));
         setLoginTicket(arg);
         return "<script>location='/adm/home'</script>";
     }catch (findByIdExptn_CantFindData e)
     {
         return "<script>alert('无此管理员');location='/adm/login'</script>";
     }
     catch (PwdNotEqExceptn e)
     {
         return "<script>alert('密码错误');location='/adm/login'</script>";
     }



    }

    /**
     * @param usr_dto
     * @return
     */
    @Override
    public Object setLoginTicket(AdminLoginDto usr_dto) {
        util2026.setcookie("admHRZ", usr_dto.username, ApiGateway.httpExchangeCurThrd.get());
        util2026.setcookie("adm", EncryUtil.encryptAesToStrBase64(usr_dto.username, EncryUtil.Key4pwd4aeskey), ApiGateway.httpExchangeCurThrd.get());

        return null;
    }
}

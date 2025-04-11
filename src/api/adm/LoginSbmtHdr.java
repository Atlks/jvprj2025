package api.adm;

import entityx.Admin;
import entityx.Keyx;
import entityx.NonDto;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import util.algo.Icall;
import util.ex.PwdNotEqExceptn;
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
public class LoginSbmtHdr implements Icall<AdminLoginDto, Object> {
    /**
     * @param arg
     * @return
     * @throws Throwable
     */
    @Override
    public Object main(AdminLoginDto arg) throws Throwable {



     //   String data = "p=" + crdt.getPasswordAsString() + "&slt=" + k.salt;
     try{
         var admin = findByHerbinate(Admin.class, arg.username, sessionFactory.getCurrentSession());
         hopePwdEq(admin.getPassword(), encodeMd5(arg.password));
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
}

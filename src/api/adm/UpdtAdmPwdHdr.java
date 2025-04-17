package api.adm;

import entityx.NonDto;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.thymeleaf.context.Context;
import util.algo.EncryUtil;
import util.algo.Icall;
import util.misc.util2026;
import util.serverless.ApiGateway;

import static test.htmlTppltl.rend;
@Controller
//@Controller 注解来标识处理请求并返回视图（HTML 页面）。
@PermitAll
@Path("/adm/changePwd")
//   http://localhost:8889/adm/login
@NoArgsConstructor
@Data
public class UpdtAdmPwdHdr implements Icall<NonDto, Object> {
    /**
     * @param arg
     * @return
     * @throws Throwable
     */
    @Override
    public Object main(NonDto arg) throws Throwable {


    var  adm=    util2026.getcookie("adm",  ApiGateway.httpExchangeCurThrd.get());
    adm=  EncryUtil.decryptAesFromStrBase64(adm, EncryUtil.Key4pwd4aeskey);


        Context context = new Context();
        context.setVariable("admin_uname", adm);
        String tmpleFileName = "adm/updtPwd";


        return  ( rend(tmpleFileName, context ));
       // return null;
    }
}

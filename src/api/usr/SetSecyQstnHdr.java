package api.usr;

import core.Ilogin;
import entityx.*;
import jakarta.annotation.security.PermitAll;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.SecurityContext;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import service.VisaService;
import util.algo.EncryUtil;
import util.algo.Icall;
import util.ex.PwdErrEx;
import util.ex.existUserEx;
import util.misc.util2026;
import util.proxy.AtProxy4api;

import static cfg.AppConfig.sessionFactory;
import static util.misc.Util2025.encodeJson;
import static util.misc.util2026.setcookie;
import static util.proxy.AtProxy4api.httpExchangeCurThrd;
import static util.tx.HbntUtil.persistByHibernate;

/**
 * 设置安全问题
 * //@param uname
 * //@param pwd
 */
@RestController

@PermitAll
@Path("/user/setScrQstn")
//   http://localhost:8889/user/setScrQstn?customQuestionText=???&answer=000
@NoArgsConstructor
@Data
public class SetSecyQstnHdr implements Icall<SecurityQuestion, Object> {
    /**
     * @return
     * @throws Exception
     * @throws existUserEx
     */
    @Override
    public Object main(@BeanParam SecurityQuestion usr_dto) throws Exception  {
        usr_dto.setUserName("00912");//for test
        persistByHibernate( usr_dto, sessionFactory.getCurrentSession());
        return     new ApiResponse(usr_dto);
    }




}

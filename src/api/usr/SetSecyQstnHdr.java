package api.usr;

import entityx.usr.SecurityQuestion;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Path;
import lombok.Data;
import lombok.NoArgsConstructor;
// org.springframework.web.bind.annotation.RestController;
import util.algo.Icall;
import util.auth.unameIsEmptyExcptn;
import util.auth.validateTokenExcptn;
import util.ex.existUserEx;
import util.serverless.ApiGatewayResponse;

import static cfg.Containr.sessionFactory;
import static util.tx.HbntUtil.mergex;

/**
 * 设置安全问题
 * //@param uname
 * //@param pwd
 */


//@PermitAll
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
    public Object main(@BeanParam SecurityQuestion usr_dto) throws Exception, validateTokenExcptn, unameIsEmptyExcptn {
     //   usr_dto.setUserName("00912");//for test
     //   usr_dto.setUserName( getUsernameFrmJwtToken(httpExchangeCurThrd.get()));
        mergex( usr_dto, sessionFactory.getCurrentSession());
        return     new ApiGatewayResponse(
                usr_dto);
    }




}

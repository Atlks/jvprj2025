package api.usr;

import entityx.usr.SecurityQuestion;
import jakarta.annotation.security.PermitAll;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import util.algo.Icall;
import util.entty.FgtPwdGetAnswerByUnameDto;
import util.serverless.ApiGatewayResponse;

import java.util.HashMap;

import static cfg.AppConfig.sessionFactory;
import static util.tx.HbntUtil.findByHerbinate;

/**
 * 忘记密码-输入 uname
 * //@param uname
 * //@param pwd
 */
@RestController

@PermitAll
@Path("/FgtPwdGetQuestionByUnameHdr")
//   http://localhost:8889/FgtPwdIptUname?uname=007
@NoArgsConstructor
@Data
@Transactional(Transactional.TxType.NOT_SUPPORTED)  //忽略事务
public class FgtPwdGetQuestionByUnameHdr implements Icall<FgtPwdGetAnswerByUnameDto, Object> {
    /**
     * @param arg
     * @return
     * @throws Throwable
     */
    @Override
    public Object main(FgtPwdGetAnswerByUnameDto arg) throws Throwable {

        SecurityQuestion sq=    findByHerbinate(SecurityQuestion.class,arg.uname,sessionFactory.getCurrentSession());
     //   sq.setAnswer("***");
        HashMap vo=new HashMap();
        vo.put("uname",sq.getUserName());
        vo.put("customQuestionText",sq.customQuestionText);
        return new ApiGatewayResponse(vo) ;
    }


}

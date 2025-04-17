package api.usr;

import entityx.FgtPwdRstPwdHdrDto;
import entityx.SecurityQuestion;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import util.algo.Icall;
import util.serverless.ApiGatewayResponse;

import static biz.Containr.sam4regLgn;
import static cfg.AppConfig.sessionFactory;
import static util.algo.EncodeUtil.encodeMd5;
import static util.tx.HbntUtil.findByHerbinate;

/**
 * 忘记密码-rest pwd
 * //@param uname
 * //@param pwd
 */
@RestController

 @PermitAll
@Path("/FgtPwdRstPwdHdr")
//   http://localhost:8889/FgtPwdRstPwdHdr?uname=007&answer=!!!&newpwd=000
@NoArgsConstructor
@Data
public class FgtPwdRstPwdHdr implements Icall<FgtPwdRstPwdHdrDto, Object> {
    /**
     * @param reqdto
     * @return
     * @throws Throwable
     */
    @Override
    public Object main(FgtPwdRstPwdHdrDto reqdto) throws Throwable {



        SecurityQuestion sq=    findByHerbinate(SecurityQuestion.class,reqdto.uname,sessionFactory.getCurrentSession());
        if(! (reqdto.answer) .equals(sq.answer))
            throw  new  AnswerErr("");
        //  sq.setAnswer("***");

//        Usr u=findByHerbinate(Usr.class,reqdto.uname,sessionFactory.getCurrentSession());
        sam4regLgn.storeKey(reqdto.uname, reqdto.newpwd);
        return new ApiGatewayResponse(sq);
    }
}

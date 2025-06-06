package api.usr;

import entityx.usr.FgtPwdRstPwdHdrDto;
import entityx.usr.SecurityQuestion;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import lombok.Data;
import lombok.NoArgsConstructor;

import util.algo.Icall;
import util.serverless.ApiGatewayResponse;

import static cfg.Containr.sam4regLgn;
import static cfg.Containr.sessionFactory;
import static util.tx.HbntUtil.findById;

/**
 * 忘记密码-rest pwd
 * //@param uname
 * //@param pwd
 */


 @PermitAll
@Path("/apiv1/FgtPwdRstPwdHdr")
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



        SecurityQuestion sq=    findById(SecurityQuestion.class,reqdto.uname,sessionFactory.getCurrentSession());
        if(! (reqdto.answer) .equals(sq.answer))
            throw  new  AnswerErr("");
        //  sq.setAnswer("***");

//        Usr u=findByHerbinate(Usr.class,reqdto.uname,sessionFactory.getCurrentSession());
        sam4regLgn.storeKey(reqdto.uname, reqdto.newpwd);
        return new ApiGatewayResponse(sq);
    }
}

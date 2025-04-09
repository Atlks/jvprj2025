package api.usr;

import entityx.FgtPwdRstPwdHdrDto;
import entityx.SecurityQuestion;
import entityx.Usr;
import jakarta.ws.rs.Path;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import util.algo.Icall;
import util.entty.FgtPwdIptUnameDto;

import static biz.Containr.sam4regLgn;
import static cfg.AppConfig.sessionFactory;
import static util.tx.HbntUtil.findByHerbinate;

/**
 * 忘记密码-输入 uname
 * //@param uname
 * //@param pwd
 */
@RestController

//@PermitAll
@Path("/FgtPwdRstPwdHdr")
//   http://localhost:8889/FgtPwdRstPwdHdr?uname=007&answer=!!!&newpwd=000
@NoArgsConstructor
@Data
public class FgtPwdRstPwdHdr implements Icall<FgtPwdRstPwdHdrDto, Object> {
    /**
     * @param arg
     * @return
     * @throws Throwable
     */
    @Override
    public Object main(FgtPwdRstPwdHdrDto arg) throws Throwable {



        SecurityQuestion sq=    findByHerbinate(SecurityQuestion.class,arg.uname,sessionFactory.getCurrentSession());
        if(!arg.answer.equals(arg.answer))
            throw  new  AnswerErr("");
        //  sq.setAnswer("***");

//        Usr u=findByHerbinate(Usr.class,arg.uname,sessionFactory.getCurrentSession());
        sam4regLgn.storeKey(arg.uname, arg.newpwd);
        return sq;
    }
}

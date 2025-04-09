package api.usr;

import entityx.SecurityQuestion;
import entityx.SetWithdrawalPasswordDto;
import entityx.Usr;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import util.algo.Icall;
import util.entty.FgtPwdIptUnameDto;

import java.util.HashMap;

import static biz.Containr.sam4regLgn;
import static cfg.AppConfig.sessionFactory;
import static util.tx.HbntUtil.findByHerbinate;

/**
 * 忘记密码-输入 uname
 * //@param uname
 * //@param pwd
 */
@RestController

@PermitAll
@Path("/FgtPwdIptUname")
//   http://localhost:8889/FgtPwdIptUname?uname=007
@NoArgsConstructor
@Data
public class FgtPwdIptUnameHdr implements Icall<FgtPwdIptUnameDto, Object> {
    /**
     * @param arg
     * @return
     * @throws Throwable
     */
    @Override
    public Object main(FgtPwdIptUnameDto arg) throws Throwable {

        SecurityQuestion sq=    findByHerbinate(SecurityQuestion.class,arg.uname,sessionFactory.getCurrentSession());
     //   sq.setAnswer("***");
        HashMap vo=new HashMap();
        vo.put("uname",sq.getUserName());
        vo.put("customQuestionText",sq.customQuestionText);
        return sq;
    }


}

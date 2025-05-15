package handler.pwd;

import api.usr.AnswerErr;
import entityx.usr.SecurityQuestion;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.resetpwd.PasswordResetConfirmDTO;
import util.ex.PwdNotEqExceptn;

import static cfg.Containr.sessionFactory;
import static util.tx.HbntUtil.findById;
import static cfg.Containr.sam4regLgn;
@Path("/password/reset/confirm")

@PermitAll
 
//   http://localhost:8889/FgtPwdIptUname?uname=007
@NoArgsConstructor
@Data
public class ResetConfirmHdl {

     public Object handleRequest(PasswordResetConfirmDTO reqdto) throws Throwable {
      
   
           SecurityQuestion sq=    findById(SecurityQuestion.class,reqdto.getUsername(),sessionFactory.getCurrentSession());
        if(! (reqdto.getAnswer()) .equals(sq.answer))
            throw  new  AnswerErr("");

            if(!reqdto.getNewPassword().equals(reqdto.getConfirmPassword()))
            throw  new  PwdNotEqExceptn("");

//        Usr u=findByHerbinate(Usr.class,reqdto.uname,sessionFactory.getCurrentSession());
        sam4regLgn.storeKey(reqdto.getAnswer(), reqdto.getNewPassword());
        
        return "ok" ;
        }

}

package handler.secury;

import entityx.usr.SecurityQuestion;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;

import static cfg.Containr.sessionFactory;
import static util.tx.HbntUtil.mergex;

@PermitAll
@Path("/api/security-question/setup")
public class SetSecuryQstnHdl {

 public Object handleRequest(SecurityQuestionSetupDTO reqdto) throws Throwable {
   
    SecurityQuestion sq=new SecurityQuestion();
    sq.answer=reqdto.getAnswer();
    sq.customQuestionText=reqdto.getQuestion();
    sq.userName=reqdto.getUserId();
    mergex( sq, sessionFactory.getCurrentSession());
    return     "ok";

 }
   
}

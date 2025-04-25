package handler.secury;

import entityx.usr.SecurityQuestion;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import model.resetpwd.PasswordResetConfirmDTO;
import util.serverless.ApiGatewayResponse;

import static cfg.AppConfig.sessionFactory;
import static util.tx.HbntUtil.mergeByHbnt;

@PermitAll
@Path("/api/security-question/setup")
public class SetSecuryQstnHdl {

 public Object handleRequest(SecurityQuestionSetupDTO reqdto) throws Throwable {
   
    SecurityQuestion sq=new SecurityQuestion();
    sq.answer=reqdto.getAnswer();
    sq.customQuestionText=reqdto.getQuestion();
    sq.userName=reqdto.getUserId();
    mergeByHbnt( sq, sessionFactory.getCurrentSession());
    return     "ok";

 }
   
}

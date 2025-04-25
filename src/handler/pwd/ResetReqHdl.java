package handler.pwd;

import java.util.HashMap;

import entityx.usr.SecurityQuestion;
import handler.secury.RstRqResponseDTO;
import jakarta.annotation.security.PermitAll;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;

import lombok.Data;
import lombok.NoArgsConstructor;
import model.resetpwd.PasswordResetRequestDTO;
import util.serverless.ApiGatewayResponse;
import static cfg.AppConfig.sessionFactory;
import static cfg.AppConfig.sessionFactory;
import static util.tx.HbntUtil.findByHerbinate;
@Path("/password/reset/request")

@PermitAll
 
//   http://localhost:8889/FgtPwdIptUname?uname=007
@NoArgsConstructor
@Data
@Transactional(Transactional.TxType.NOT_SUPPORTED)  //忽略事务  
public class ResetReqHdl {

        public Object handleRequest(PasswordResetRequestDTO reqdto) throws Throwable {
      
    SecurityQuestion sq=    findByHerbinate(SecurityQuestion.class,reqdto.getUsername(),sessionFactory.getCurrentSession());
     //   sq.setAnswer("***");
     RstRqResponseDTO rsp=new RstRqResponseDTO(sq.customQuestionText);
       
        return rsp ;
        }

}

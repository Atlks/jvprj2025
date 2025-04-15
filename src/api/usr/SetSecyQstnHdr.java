package api.usr;

import entityx.*;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Path;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import util.algo.Icall;
import util.ex.existUserEx;

import static cfg.AppConfig.sessionFactory;
import static util.tx.HbntUtil.persistByHibernate;

/**
 * 设置安全问题
 * //@param uname
 * //@param pwd
 */
@RestController

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
    public Object main(@BeanParam SecurityQuestion usr_dto) throws Exception  {
     //   usr_dto.setUserName("00912");//for test
        persistByHibernate( usr_dto, sessionFactory.getCurrentSession());
        return     new ApiResponse(usr_dto);
    }




}

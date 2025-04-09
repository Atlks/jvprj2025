package api.usr;

import entityx.ApiResponse;
import entityx.SecurityQuestion;
import entityx.SetWithdrawalPasswordDto;
import entityx.WithdrawalPassword;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Path;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import util.algo.Icall;
import util.ex.existUserEx;

import static cfg.AppConfig.sessionFactory;
import static util.algo.EncodeUtil.encodeMd5;
import static util.tx.HbntUtil.persistByHibernate;

/**
 * 设置安全问题
 * //@param uname
 * //@param pwd
 */
@RestController

//@PermitAll
@Path("/user/SetWthdrPwd")
//   http://localhost:8889/user/SetWthdrPwd?pwd=000
@NoArgsConstructor
@Data
public class SetWthdrPwd implements Icall<SetWithdrawalPasswordDto, Object> {
    /**
     * @return
     * @throws Exception
     * @throws existUserEx
     */
    @Override
    public Object main(@BeanParam SetWithdrawalPasswordDto reqdto) throws Exception  {
     //   reqdto.setUserName("00912");//for test
        WithdrawalPassword wp=new WithdrawalPassword();
        wp.setUname(reqdto.getUname());
        wp.setEncryptedPassword(encodeMd5(reqdto.getPwd()));
        persistByHibernate( wp, sessionFactory.getCurrentSession());
        return     new ApiResponse(wp);
    }




}

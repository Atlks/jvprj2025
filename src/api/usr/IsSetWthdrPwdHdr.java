package api.usr;

import entityx.ApiResponse;
import entityx.SetWithdrawalPasswordDto;
import entityx.WithdrawalPassword;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Path;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import util.algo.Icall;
import util.ex.existUserEx;
import util.tx.findByIdExptn_CantFindData;

import static cfg.AppConfig.sessionFactory;
import static util.tx.HbntUtil.findByHerbinate;

/**
 * 设置安全问题
 * //@param uname
 * //@param pwd
 */
@RestController

//@PermitAll
@Path("/user/IsSetWthdrPwdHdr")
//   http://localhost:8889/user/IsSetWthdrPwdHdr?jwt.uname=007
//
@NoArgsConstructor
@Data
public class IsSetWthdrPwdHdr implements Icall<SetWithdrawalPasswordDto, Object> {
    /**
     * @return
     * @throws Exception
     * @throws existUserEx
     */
    @Override
    public Object main(@BeanParam SetWithdrawalPasswordDto reqdto) throws Exception, findByIdExptn_CantFindData {

        WithdrawalPassword wp=      findByHerbinate(WithdrawalPassword.class,reqdto.getUname(),sessionFactory.getCurrentSession());

        return     new ApiResponse(wp.getUname());
    }




}

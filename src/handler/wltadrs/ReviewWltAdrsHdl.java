package handler.wltadrs;

import jakarta.ws.rs.Path;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.review.ReviewRqdto;
import model.usr.MyWltAddr;
import org.springframework.web.bind.annotation.RestController;
import util.serverless.ApiGatewayResponse;
import util.tx.findByIdExptn_CantFindData;

import static cfg.AppConfig.sessionFactory;
import static model.review.ReviewStat.fromCodeStr_ReviewStat;
import static util.tx.HbntUtil.findByHerbinate;

/**
 * 设置安全问题
 * //@param uname
 * //@param pwd
 */
@RestController

//@PermitAll
@Path("/myWltAdrs/ReviewWltAdrsHdl")
//   http://localhost:8889/user/SetWthdrPwd?pwd=000
@NoArgsConstructor
@Data
public class ReviewWltAdrsHdl {


    /**
     * @param reqdto

     * @return
     * @throws Throwable
     */

    public ApiGatewayResponse handleRequest(ReviewRqdto reqdto ) throws Throwable {


        try {
            MyWltAddr wp = findByHerbinate(MyWltAddr.class, reqdto.getId(), sessionFactory.getCurrentSession());
            wp.stat=fromCodeStr_ReviewStat(reqdto.stat) ;
            return new ApiGatewayResponse(wp);
        } catch (findByIdExptn_CantFindData e) {
            return new ApiGatewayResponse(new MyWltAddr());
        }

    }



}

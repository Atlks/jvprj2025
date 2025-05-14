package handler.wltadrs;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.review.ReviewRqdto;
import model.usr.MyWltAddr;

import static cfg.Containr.sessionFactory;
import static model.review.ReviewStat.fromCodeStr_ReviewStat;
import static util.tx.HbntUtil.findByHerbinate;

/**
 * 设置安全问题
 * //@param uname
 * //@param pwd
 */


@PermitAll
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

    public Object handleRequest(ReviewRqdto reqdto ) throws Throwable {



            MyWltAddr wp = findByHerbinate(MyWltAddr.class, reqdto.getId(), sessionFactory.getCurrentSession());
            wp.setReviewStat(fromCodeStr_ReviewStat(reqdto.stat)); ;

            return (wp);


    }



}

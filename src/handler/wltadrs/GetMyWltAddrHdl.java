package handler.wltadrs;

import handler.ivstAcc.dto.QueryDto;
import jakarta.ws.rs.Path;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.usr.MyWltAddr;

import util.tx.findByIdExptn_CantFindData;

import static cfg.Containr.sessionFactory;
import static util.tx.HbntUtil.findByHerbinate;

/**
 * 设置安全问题
 * //@param uname
 * //@param pwd
 */


//@PermitAll
@Path("/myWltAdrs/GetMyWltAddrHdl")
//   http://localhost:8889/user/SetWthdrPwd?pwd=000
@NoArgsConstructor
@Data
public class GetMyWltAddrHdl {


    /**
     * @param reqdto

     * @return
     * @throws Throwable
     */

    public Object handleRequest(QueryDto reqdto) throws Throwable {


        try {
            MyWltAddr wp = findByHerbinate(MyWltAddr.class, reqdto.uname, sessionFactory.getCurrentSession());
            return (wp);
        } catch (findByIdExptn_CantFindData e) {
            return (new MyWltAddr());
        }

    }

}

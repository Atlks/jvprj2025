package handler.wltadrs;

import jakarta.ws.rs.Path;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.usr.MyWltAddr;


import static cfg.Containr.sessionFactory;
import static util.tx.HbntUtil.mergex;

/**
 * 设置安全问题
 * //@param uname
 * //@param pwd
 */


//@PermitAll
@Path("/apiv1/myWltAdrs/SetMyWltAddrHdl")
//   http://localhost:8889/user/SetWthdrPwd?pwd=000
@NoArgsConstructor
@Data
public class SetMyWltAddrHdl {


    /**
     * @param reqdto

     * @return
     * @throws Throwable
     */

    public Object handleRequest(MyWltAddr reqdto ) throws Throwable {


        var wp = mergex(reqdto , sessionFactory.getCurrentSession());
        return (wp);
    }

}

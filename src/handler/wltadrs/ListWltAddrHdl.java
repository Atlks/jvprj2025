package handler.wltadrs;

import entityx.usr.NonDto;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.usr.MyWltAddr;

import java.util.List;

import static cfg.Containr.sessionFactory;
// static model.usr.MyWltAddr.MY_WLT_ADDR;
import static util.algo.GetUti.getTablename;
import static util.tx.HbntUtil.findById;
import static util.tx.HbntUtil.getListBySql;

/**
 * 设置安全问题
 * //@param uname
 * //@param pwd
 */


@PermitAll
@Path("/apiv1/myWltAdrs/ListWltAddrHdl")
//   http://localhost:8889/user/SetWthdrPwd?pwd=000
@NoArgsConstructor
@Data
public class ListWltAddrHdl {


    /**
     * @param reqdto

     * @return
     * @throws Throwable
     */

    public Object handleRequest(NonDto reqdto ) throws Throwable {




            List wp = getListBySql("select * from "+getTablename(MyWltAddr.class)+" order by timestamp desc", sessionFactory.getCurrentSession());
            return  (wp);


    }

}

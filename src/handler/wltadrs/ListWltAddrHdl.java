package handler.wltadrs;

import entityx.usr.NonDto;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.usr.MyWltAddr;

import util.serverless.ApiGatewayResponse;
import util.tx.findByIdExptn_CantFindData;

import java.util.List;

import static cfg.Containr.sessionFactory;
import static model.usr.MyWltAddr.MY_WLT_ADDR;
import static util.tx.HbntUtil.findByHerbinate;
import static util.tx.HbntUtil.getListBySql;

/**
 * 设置安全问题
 * //@param uname
 * //@param pwd
 */


@PermitAll
@Path("/myWltAdrs/ListWltAddrHdl")
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




            List wp = getListBySql("select * from "+MY_WLT_ADDR+" order by timestamp desc", sessionFactory.getCurrentSession());
            return  (wp);


    }

}

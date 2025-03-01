package api.ylwlt;

import annos.CookieParam;
import annos.Parameter;
import biz.BaseHdr;
import cfg.MyCfg;
import entityx.OrdWthdr;
import entityx.Usr;
import com.sun.net.httpserver.HttpExchange;
import entityx.WithdrawDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.LockModeType;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Path;
import org.hibernate.Session;
import service.AuthService;
import util.Icall;

import java.math.BigDecimal;
import java.util.Map;

import static cfg.AppConfig.sessionFactory;
import static service.CmsBiz.toBigDcmTwoDot;
import static com.alibaba.fastjson2.util.TypeUtils.toBigDecimal;
import static util.AuthUtil.getCurrentUser;
import static util.ColorLogger.RED_bright;
import static util.ColorLogger.colorStr;
import static util.HbntUtil.*;
import static util.ToXX.parseQueryParams;
import static util.dbutil.*;
import static util.util2026.*;

/**提现，会导致有效金额变化，冻结金额也变化  ，总金额变化 ，
 * http://localhost:8889/Withdraw?amount=7
 */
@Tag(name = "Ylwlt")
@Path("/Withdraw")
@Parameter(name = "amount",description = "金额")
@CookieParam(name = "uname")
public class WithdrawHdr  implements Icall<WithdrawDto, Object> {


    public static String saveUrlOrdWthdr;

//    public static void main(String[] args) throws Exception {
//      MyCfg.iniCfgFrmCfgfile();
//
//        OrdWthdr ord=new OrdWthdr();
//        // ord.put("datetime_utc", now);
//        // ord.put("datetime_local", getLocalTimeString());
//        //  ord.put("timezone", now);
//     //   ord.timestamp=(System.currentTimeMillis());
//        ord.uname="009";
//        ord.amt=new BigDecimal(77);
//      //  ord.id="ordWthdr"+getFilenameFrmLocalTimeString();
//        Withdraw(ord);
//    }



    private static Object addBlsFrz4yinliWlt(double amt) {
        return null;
    }

    @Override
    public Object call(@BeanParam WithdrawDto dto) throws Exception {

        dto.setUserId(getCurrentUser());


     //
      //======================add wth log
        OrdWthdr wdthRec=new OrdWthdr();
        copyProps(dto,wdthRec);
        wdthRec.timestamp=(System.currentTimeMillis());
        wdthRec.uname= getCurrentUser();
        wdthRec.id="ordWthdr"+getFilenameFrmLocalTimeString();
        Session session = sessionFactory.getCurrentSession();
        Object obj = persistByHibernate(wdthRec,  sessionFactory.getCurrentSession());


        //   //adjst yinliwlt balnce
        //----------------------sub blsAvld   blsFreez++
        String mthBiz=colorStr("减少盈利钱包的有效余额,增加冻结金额",RED_bright);
        System.out.println("\r\n\n\n=============⚡⚡bizfun  "+mthBiz);
        String uname=getCurrentUser();
        Usr objU = findByHbnt(Usr.class, uname, LockModeType.PESSIMISTIC_WRITE, sessionFactory.getCurrentSession());

        BigDecimal nowAmt2= objU.balanceYinliwlt ;
        BigDecimal newBls2=nowAmt2.subtract(dto.getAmount());
        objU.balanceYinliwlt=toBigDcmTwoDot(newBls2);

        BigDecimal nowAmtFreez=toBigDcmTwoDot(  objU.getBalanceYinliwltFreez() );
        objU.balanceYinliwltFreez=toBigDcmTwoDot(nowAmtFreez.add(dto.getAmount())) ;

       return mergeByHbnt(objU, session);



    }



}

package api.ylwlt;

import annos.CookieParam;
import annos.Parameter;
import biz.BalanceNotEnghou;
import biz.BaseHdr;
import cfg.MyCfg;
import entityx.LogBlsLogYLwlt;
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
import static service.YLwltSvs.AddMoney2YLWltService.addBlsLog4ylwlt;
import static util.AuthUtil.getCurrentUser;
import static util.ColorLogger.RED_bright;
import static util.ColorLogger.colorStr;
import static util.HbntUtil.*;
import static util.ToXX.parseQueryParams;
import static util.dbutil.*;
import static util.util2026.*;

/**
 * 提现，会导致有效金额变化，冻结金额也变化  ，总金额变化 ，
 * http://localhost:8889/Withdraw?amount=7
 */
@Tag(name = "Ylwlt")
@Path("/Withdraw")
@Parameter(name = "amount", description = "金额")
@CookieParam(name = "uname",value = "$curuser")
public class WithdrawHdr implements Icall<WithdrawDto, Object> {


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
    public Object call(  WithdrawDto dtoWithdrawDto) throws Exception {

        dtoWithdrawDto.setUserId(getCurrentUser());


        //
        //======================add wth log

        System.out.println("\r\n\n\n=============⚡⚡bizfun  " + colorStr("增加提现申请单", RED_bright));
        OrdWthdr wdthRec = new OrdWthdr();
        copyProps(dtoWithdrawDto, wdthRec);
        wdthRec.timestamp = (System.currentTimeMillis());
        wdthRec.uname = getCurrentUser();
        wdthRec.id = "ordWthdr" + getFilenameFrmLocalTimeString();
        Session session = sessionFactory.getCurrentSession();
        Object obj = persistByHibernate(wdthRec, sessionFactory.getCurrentSession());


        //   //adjst yinliwlt balnce
        //----------------------sub blsAvld   blsFreez++
        String mthBiz = colorStr("减少盈利钱包的有效余额,增加冻结金额", RED_bright);
        System.out.println("\r\n\n\n=============⚡⚡bizfun  " + mthBiz);
        String uname = getCurrentUser();
        Usr objU = findByHbnt(Usr.class, uname, LockModeType.PESSIMISTIC_WRITE, sessionFactory.getCurrentSession());

        BigDecimal nowAmt2 = objU.balanceYinliwlt;

        if (dtoWithdrawDto.getAmount().compareTo(nowAmt2) > 0) {
            BalanceNotEnghou ex = new BalanceNotEnghou("余额不足");
            ex.fun = this.getClass().getName() + "." + getCurrentMethodName();
            ex.funPrm = dtoWithdrawDto;
            ex.info = "nowAmtBls=" + nowAmt2;
            throw ex;
        }


        //=======================减少盈利钱包的有效余额,增加冻结金额
        BigDecimal newBls2 = nowAmt2.subtract(dtoWithdrawDto.getAmount());
        objU.balanceYinliwlt = toBigDcmTwoDot(newBls2);

        BigDecimal nowAmtFreez = toBigDcmTwoDot(objU.getBalanceYinliwltFreez());
        objU.balanceYinliwltFreez = toBigDcmTwoDot(nowAmtFreez.add(dtoWithdrawDto.getAmount()));

        Usr usr = mergeByHbnt(objU, session);



        //取款体现后  日志的变化  冻结金额 ，有效金额变化。。。
        //------------add balanceLog
        LogBlsLogYLwlt logBlsYinliWlt = new LogBlsLogYLwlt(dtoWithdrawDto,nowAmt2, newBls2,"减去");
        addBlsLog4ylwlt(logBlsYinliWlt, session);

        return usr;




    }


}

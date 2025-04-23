//package handler.wthdr;
//
//import api.ylwlt.ApiFun;
//import model.rechg.TransactionsWthdr;
//
//import util.algo.Tag;
//import util.annos.CookieParam;
//import util.annos.Parameter;
//import util.ex.BalanceNotEnghou;
//import entityx.wlt.LogBls4YLwlt;
//import entityx.usr.WithdrawDto;
////import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.persistence.LockModeType;
//import jakarta.security.enterprise.SecurityContext;
//import jakarta.ws.rs.BeanParam;
//import jakarta.ws.rs.Path;
//import lombok.extern.slf4j.Slf4j;
//import org.hibernate.Session;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.RestController;
//import util.algo.Icall;
//
//import java.math.BigDecimal;
//
//import static cfg.AppConfig.sessionFactory;
//import static service.CmsBiz.toBigDcmTwoDot;
//import static com.alibaba.fastjson2.util.TypeUtils.toBigDecimal;
//import static service.YLwltSvs.AddMoney2YLWltService.addBlsLog4ylwlt;
//import static util.auth.AuthUtil.getCurrentUser;
//import static util.log.ColorLogger.RED_bright;
//import static util.log.ColorLogger.colorStr;
//import static util.tx.HbntUtil.*;
//import static util.misc.util2026.*;
//
///**
// * here api service is same
// * 提现，会导致有效金额变化，冻结金额也变化  ，总金额变化 ，
// * http://localhost:8889/Withdraw?amount=7
// *
// * UPDATE accounts SET balance = balance - ? WHERE id = ? AND balance >= ?
// */
//@ApiFun
//@Tag(name = "Ylwlt")
//@Path("/Withdraw")
//@Parameter(name = "amount", description = "金额")
//@CookieParam(name = "uname",value = "$curuser")
//@RestController
//@PreAuthorize("user")
//@Slf4j
//@Deprecated   //cant drktl wdth,need app first
//public class WithdrawHdr implements Icall<WithdrawDto, Object> {
//
//
//    public static String saveUrlOrdWthdr;
//
//
//
//    SecurityContext SecurityContext1;
//
//    @Override
//    public Object main(@BeanParam  WithdrawDto dtoWithdrawDto) throws Exception {
//
//
//        System.out.println("\r\n\n\n=============⚡⚡bizfun  " + colorStr("检测余额", RED_bright));
//        dtoWithdrawDto.setUserId(getCurrentUser());
//        String uname = getCurrentUser();
//        YLwltAcc objU = findByHbntDep(YLwltAcc.class, uname, LockModeType.PESSIMISTIC_WRITE, sessionFactory.getCurrentSession());
//
//        BigDecimal nowAmt2 = objU.availableBalance;
//
//        if (dtoWithdrawDto.getAmount().compareTo(nowAmt2) > 0) {
//            BalanceNotEnghou ex = new BalanceNotEnghou("余额不足");
//            ex.fun = this.getClass().getName() + "." + getCurrentMethodName();
//            ex.funPrm = dtoWithdrawDto;
//            ex.info = "nowAmtBls=" + nowAmt2;
//            throw ex;
//        }
//
//        //
//
//
//        //======================add wth log
//        System.out.println("\r\n\n\n=============⚡⚡bizfun  " + colorStr("增加提现申请单", RED_bright));
//        TransactionsWthdr wdthRec = new TransactionsWthdr();
//        copyProps(dtoWithdrawDto, wdthRec);
//        wdthRec.timestamp = (System.currentTimeMillis());
//        wdthRec.uname = getCurrentUser();
//        wdthRec.id = "ordWthdr" + getFilenameFrmLocalTimeString();
//        Session session = sessionFactory.getCurrentSession();
//        Object obj = persistByHibernate(wdthRec, sessionFactory.getCurrentSession());
//
//
//
//
//
//        //=======================减少盈利钱包的有效余额,增加冻结金额
//        //   //adjst yinliwlt balnce
//        //----------------------sub blsAvld   blsFreez++
//        String mthBiz = colorStr("减少盈利钱包的有效余额,增加冻结金额", RED_bright);
//        System.out.println("\r\n\n\n=============⚡⚡bizfun  " + mthBiz);
//        BigDecimal newBls2 = nowAmt2.subtract(dtoWithdrawDto.getAmount());
//        objU.availableBalance = toBigDcmTwoDot(newBls2);
//
//        BigDecimal nowAmtFreez = toBigDcmTwoDot(objU.frozenAmount);
//        objU.frozenAmount = toBigDcmTwoDot(nowAmtFreez.add(dtoWithdrawDto.getAmount()));
//        YLwltAcc usr = mergeByHbnt(objU, session);
//
//
//
//        //取款体现后  日志的变化  冻结金额 ，有效金额变化。。。
//        System.out.println("\r\n\n\n=============⚡⚡bizfun  " + colorStr("增加余额变化了流水", RED_bright));
//        //------------add balanceLog
//        LogBls4YLwlt logBlsYinliWlt = new LogBls4YLwlt(dtoWithdrawDto,nowAmt2, newBls2,"减去");
//        addBlsLog4ylwlt(logBlsYinliWlt, session);
//        return usr;
//
//
//
//
//    }
//
//
//}
////    public static void main(String[] args) throws Exception {
////      MyCfg.iniCfgFrmCfgfile();
////
////        OrdWthdr ord=new OrdWthdr();
////        // ord.put("datetime_utc", now);
////        // ord.put("datetime_local", getLocalTimeString());
////        //  ord.put("timezone", now);
////     //   ord.timestamp=(System.currentTimeMillis());
////        ord.uname="009";
////        ord.amt=new BigDecimal(77);
////      //  ord.id="ordWthdr"+getFilenameFrmLocalTimeString();
////        Withdraw(ord);
////    }

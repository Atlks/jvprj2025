package service.wlt;
// static cfg.AppConfig.sessionFactory;


import model.OpenBankingOBIE.Account;
import entityx.wlt.LogBls;
import entityx.wlt.TransDto;
import model.usr.Usr;
import jdk.jfr.Name;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.OpenBankingOBIE.CreditDebitIndicator;
import model.OpenBankingOBIE.Transaction;
import org.hibernate.Session;

import java.math.BigDecimal;

import static cfg.Containr.sessionFactory;
import static service.CmsBiz.toBigDcmTwoDot;
import static util.tx.HbntUtil.*;
import static util.misc.util2026.getFilenameFrmLocalTimeString;


/**
 *  增加钱包余额服务
 *
 *  先添加日志可防止重复添加，在更新余额即可
 *  referenceId 关联充值订单，防止重复加钱，格式使用urlqrystr模式 ,序列化oo了
 * 防并发处理，确保 同一个 rechargeId 只处理一次
 *
 *   AddMoneyToWltService
 *  @author at

 *   @return 计算结果
 *   @throws Exception
 *  @code
 */
@Data
//
//@Lazy
//@Parameter(name = "amt", description = "金额")
//@Parameter(name = "uname",value = "$curuser")
//@Parameter(name = "lockAccObj")
@NoArgsConstructor
public class AccService {
    public AccService(BigDecimal amt, String uname, Usr lockAccObj) {
    }

    @Name("add")
    public Object main2(TransDto args) throws Exception{

        return null;
    }
    public static Object crdtFd(TransDto TransDto88 ) throws Exception {


        String uname = TransDto88.uname;
        BigDecimal amt = TransDto88.getAmt();
        Session session=sessionFactory.getCurrentSession();
        Account wlt1=TransDto88.lockAccObj;
        BigDecimal nowAmt =wlt1.interim_Available_Balance;
        BigDecimal newBls = nowAmt.add(amt);


        //==================add balanceLog
        LogBls logBalance = new LogBls();
        logBalance.id = "LogBalance" + getFilenameFrmLocalTimeString();
        logBalance.uname = uname;
        logBalance.changeTime = System.currentTimeMillis();
        logBalance.changeType = "充值";  //充值增加
        logBalance.changeMode = "增加";

        logBalance.adjustType = "充值";  //充值增加
        logBalance.changeAmount = amt;
        logBalance.amtBefore = toBigDcmTwoDot(nowAmt);
        logBalance.newBalance = toBigDcmTwoDot(newBls);
        logBalance.refUniqId=TransDto88.refUniqId;
        System.out.println(" add balanceLog ");
        persistByHibernate(logBalance, session);


        Transaction txx=new Transaction();
        txx.transactionId="add2wlt"+ TransDto88.refUniqId;
        txx.amount= TransDto88.getAmount();
        txx.creditDebitIndicator= CreditDebitIndicator.CREDIT;
        txx.accountId= wlt1.accountId;
        txx.accountOwner = uname;
        persistByHibernate(txx, session);

        //=================updt
        wlt1.interim_Available_Balance = toBigDcmTwoDot(newBls);
        mergeByHbnt(wlt1, session);


        
        
       // mm2();
        System.out.println("1217");

        //  System.out.println("✅endfun updtBlsByAddChrg()");
        return  wlt1;
    }

    public void mm2() {
    }


}

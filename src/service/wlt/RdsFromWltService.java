package service.wlt;

import jakarta.validation.constraints.NotNull;

import model.OpenBankingOBIE.Account;
import model.OpenBankingOBIE.CreditDebitIndicator;
import model.OpenBankingOBIE.TransactionStatus;
import model.OpenBankingOBIE.Transaction;
import util.ex.BalanceNotEnghou;
import entityx.wlt.TransDto;
import util.algo.Icall;

import java.math.BigDecimal;

import static handler.wlt.TransHdr.curLockAcc;
import static cfg.AppConfig.sessionFactory;
import static com.alibaba.fastjson2.util.TypeUtils.toBigDecimal;
import static util.algo.GetUti.getUuid;
import static util.tx.HbntUtil.*;
import static util.misc.util2026.*;

/**
 * 减去钱包余额服务
 */
public class RdsFromWltService  implements Icall<TransDto, Object> {
    /**
     * @param arg
     * @return
     * @throws Exception
     */
    @Override
    @NotNull
    public @NotNull Object main(@NotNull TransDto TransDto88) throws Exception {





        System.out.println("\n\n\n===========减去钱包余额");

        //  放在一起一快存储，解决了十五问题事务。。。
        Account acc=  curLockAcc.get();
        if(acc==null)
            acc=TransDto88.lockAccObj;

        BigDecimal nowAmt =acc.InterimAvailableBalance;
        if (TransDto88.getChangeAmount().compareTo(nowAmt) > 0) {
            BalanceNotEnghou ex = new BalanceNotEnghou("余额不足");
            ex.fun =this.getClass().getName()+"." + getCurrentMethodName();
            ex.funPrm =  TransDto88;
            ex.info="nowAmtBls="+nowAmt;
            throw  ex;
        }

        BigDecimal amt = TransDto88.getChangeAmount();
        BigDecimal newBls = nowAmt.subtract(toBigDecimal(amt));
        acc.InterimAvailableBalance = newBls;

        acc.InterimBookedBalance =acc.InterimAvailableBalance.add(acc.frozenAmount) ;
        acc.ClosingBookedBalance =acc.InterimBookedBalance;

        mergeByHbnt(acc, sessionFactory.getCurrentSession());

        //-----------add tx lg
        Transaction txx=new Transaction();
        txx.transactionId="rdsFromWlt"+getUuid();
        txx.creditDebitIndicator= CreditDebitIndicator.DEBIT;
        txx.accountId= acc.accountId;
        txx.accountOwner = acc.accountId;
        txx.amount= TransDto88.getChangeAmount();
        txx.refUniqId= String.valueOf(System.currentTimeMillis());
         txx.transactionStatus= TransactionStatus.BOOKED;
        persistByHibernate(txx, sessionFactory.getCurrentSession());


        //------------add balanceLog
//        LogBls logBalance = new LogBls();
//        logBalance.id = "LogBalance" + getFilenameFrmLocalTimeString();
//        logBalance.uname = acc.accountId;
//
//        logBalance.changeAmount = TransDto88.getChangeAmount();
//        logBalance.amtBefore = toBigDcmTwoDot(nowAmt);
//        logBalance.newBalance = toBigDcmTwoDot(newBls);
//        logBalance.refUniqId= String.valueOf(System.currentTimeMillis());
//        logBalance.changeMode = "减去";
//        System.out.println(" add balanceLog ");
//        //  addObj(logBalance,saveUrlLogBalance);
//        persistByHibernate(logBalance, sessionFactory.getCurrentSession());
        return acc;
    }
}

package service.wlt;

import jakarta.validation.constraints.NotNull;

import model.OpenBankingOBIE.Accounts;
import model.OpenBankingOBIE.CreditDebitIndicator;
import model.OpenBankingOBIE.TransactionStatus;
import model.OpenBankingOBIE.Transactions;
import util.ex.BalanceNotEnghou;
import entityx.wlt.LogBls;
import entityx.wlt.TransDto;
import util.algo.Icall;

import java.math.BigDecimal;

import static handler.wlt.TransHdr.curLockAcc;
import static cfg.AppConfig.sessionFactory;
import static com.alibaba.fastjson2.util.TypeUtils.toBigDecimal;
import static service.CmsBiz.toBigDcmTwoDot;
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
        Accounts acc=  curLockAcc.get();
        if(acc==null)
            acc=TransDto88.lockAccObj;

        BigDecimal nowAmt =acc.availableBalance;
        if (TransDto88.getChangeAmount().compareTo(nowAmt) > 0) {
            BalanceNotEnghou ex = new BalanceNotEnghou("余额不足");
            ex.fun =this.getClass().getName()+"." + getCurrentMethodName();
            ex.funPrm =  TransDto88;
            ex.info="nowAmtBls="+nowAmt;
            throw  ex;
        }

        BigDecimal amt = TransDto88.getChangeAmount();
        BigDecimal newBls = nowAmt.subtract(toBigDecimal(amt));
        acc.availableBalance = newBls;

        acc.currentBalance=acc.availableBalance.add(acc.frozenAmount) ;
        acc.totalBalance=acc.currentBalance;

        mergeByHbnt(acc, sessionFactory.getCurrentSession());

        //-----------add tx lg
        Transactions txx=new Transactions();
        txx.transactionId="rdsFromWlt"+getUuid();
        txx.creditDebitIndicator= CreditDebitIndicator.DEBIT;
        txx.accountId= acc.accountId;
        txx.uname= acc.accountId;
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

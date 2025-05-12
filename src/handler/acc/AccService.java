package handler.acc;

import entityx.wlt.TransDto;
import jakarta.validation.constraints.NotNull;
import model.OpenBankingOBIE.*;
import util.Oosql.SlctQry;
import util.ex.BalanceNotEnghou;
import util.tx.findByIdExptn_CantFindData;

import java.math.BigDecimal;

import static cfg.Containr.sessionFactory;
import static com.alibaba.fastjson2.util.TypeUtils.toBigDecimal;
import static handler.wlt.TransHdr.curLockAcc;
import static util.Oosql.SlctQry.newSelectQuery;
import static util.Oosql.SlctQry.toValStr;
import static util.acc.AccUti.getAccId;
import static util.acc.AccUti.sysusrName;
import static util.algo.GetUti.getTableName;
import static util.algo.GetUti.getUuid;
import static util.tx.HbntUtil.*;
import static util.tx.HbntUtil.persistByHibernate;

public class AccService {
    /**
     减去钱包余额服务
     * @return
     * @throws Exception
     */
    @NotNull
    public static @NotNull Object subBal(@NotNull TransDto TransDto88) throws Exception {





        System.out.println("\n\n\n===========减去钱包余额");

        //  放在一起一快存储，解决了十五问题事务。。。
        Account acc=  curLockAcc.get();
        if(acc==null)
            acc=TransDto88.lockAccObj;

        BigDecimal nowAmt =acc.interim_Available_Balance;
        if (TransDto88.getChangeAmount().compareTo(nowAmt) > 0) {
            throw new BalanceNotEnghou("余额不足"+"nowAmtBls="+nowAmt);

        }

        BigDecimal amt = TransDto88.getChangeAmount();
        BigDecimal newBls = nowAmt.subtract(toBigDecimal(amt));
        acc.interim_Available_Balance = newBls;

        acc.InterimBookedBalance =acc.InterimBookedBalance.subtract(acc.frozenAmount) ;
        // acc.ClosingBookedBalance =acc.InterimBookedBalance;

        mergeByHbnt(acc, sessionFactory.getCurrentSession());

        //-----------add tx lg
        Transaction txx=new Transaction();
        txx.transactionId="rdsFromWlt"+getUuid();
        txx.creditDebitIndicator= CreditDebitIndicator.DEBIT;
        txx.accountId= acc.accountId;
        txx.accountOwner = acc.accountOwner;
        txx.amount= TransDto88.getChangeAmount();
        txx.refUniqId= String.valueOf(System.currentTimeMillis());
        txx.status = TransactionStatus.BOOKED;
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

    //资金池余额
    public static BigDecimal getInsFdPlBal() {
        String accid = getAccId(String.valueOf(AccountSubType.insFdPl), sysusrName);
        Account acc = null;
        try {
            acc = findByHerbinate(Account.class, accid, sessionFactory.getCurrentSession());
            return acc.getInterim_Available_Balance();
        } catch (findByIdExptn_CantFindData e) {
            return BigDecimal.valueOf(0);
        }

    }

//总余额统计 本金钱包
    public static BigDecimal sumAllEmnyAccBal() {
        SlctQry query = newSelectQuery(getTableName(Account.class));
        query.select("sum("+Account.Fields.interim_Available_Balance +")");
        query.addConditions(Account.Fields.accountSubType + "=" + toValStr(AccountSubType.EMoney.name()));
        // query.addConditions("timestamp>"+ beforeTmstmp(reqdto.day));
        //    query.addOrderBy("timestamp desc");
        String sql = query.getSQL();  // ✅ 直接拿到 SQL 字符串
        System.out.println(sql);
        try {
            return (BigDecimal) getSingleResult(sql, sessionFactory.getCurrentSession());
        } catch (findByIdExptn_CantFindData e) {
            return BigDecimal.valueOf(0);
        }
    }
}

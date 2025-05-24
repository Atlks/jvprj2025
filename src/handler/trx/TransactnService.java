package handler.trx;

import model.OpenBankingOBIE.*;
import org.jetbrains.annotations.NotNull;
import util.Oosql.SlctQry;
import util.tx.findByIdExptn_CantFindData;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static cfg.Containr.sessionFactory;
import static util.Oosql.SlctQry.newSelectQuery;
import static util.Oosql.SlctQry.toValStr;
import static util.algo.GetUti.getTableName;
import static util.algo.GetUti.getUuid;
import static util.model.openbank.BalanceTypes.interimAvailable;
import static util.oo.DatetimeUti.getMonthEndDatetime;


import static util.oo.DatetimeUti.getMonthStartDatetime;
import static util.tx.HbntUtil.getSingleResult;
import static util.tx.HbntUtil.persist;

public class TransactnService {



    private static void insertTx(Transaction tx, Account acc1, Balance blsAvlb) {
        tx.setTransactionId(getUuid());
        tx.owner = acc1.owner;
        tx.accountId = acc1.accountId;


        tx.setBalanceAmount(blsAvlb.getAmount());
        tx.setBalanceType(interimAvailable);
        //  tx.setBalanceCreditDebitIndicator(CreditDebitIndicator.CREDIT.name());
        tx.setBalance(blsAvlb);
        //-----------stat bked
//        tx.status = TransactionStatus.BOOKED;
//        tx.setBookingDateTime(OffsetDateTime.now());
        persist(tx);
    }

    /**
     * alread set CreditDebitIndicator,amt,booked,
     * @param tx
     * @param acc1
     * @param blsAvlb
     */
    public static void insertTxSetAmtIdctrBooked_txcod(Transaction tx, Account acc1, Balance blsAvlb) {
        tx.setTransactionId(getUuid());
        tx.owner = acc1.owner;
        tx.accountId = acc1.accountId;


        tx.setBalanceAmount(blsAvlb.getAmount());
        tx.setBalanceType(interimAvailable);
        //  tx.setBalanceCreditDebitIndicator(CreditDebitIndicator.CREDIT.name());
        tx.setBalance(blsAvlb);
        //-----------stat bked
        tx.status = TransactionStatus.BOOKED;
        tx.setBookingDateTime(OffsetDateTime.now());
        persist(tx);
    }


    public static BigDecimal sumInvstProfit() {

        String trxType = TransactionCode.invstProfit.name();
        return getSumAmtByTrxtype(trxType);

    }

    public static long getCntByBkDttm(String startTime, String endTime) {

        SlctQry query = newSelectQuery(getTableName(Transaction.class));
        query.select("count(*)");

        query.addConditions(Transaction.Fields.transactionCode + "=" + toValStr(TransactionCode.payment_rechg.name()));
        query.addConditions(Transaction.Fields.bookingDateTime + ">=" + toValStr(startTime));
        query.addConditions(Transaction.Fields.bookingDateTime + "<=" + toValStr(endTime));

        String sql = query.getSQL();  // ✅ 直接拿到 SQL 字符串
        System.out.println(sql);
        try {
            return (long) getSingleResult(sql, sessionFactory.getCurrentSession());
        } catch (findByIdExptn_CantFindData e) {
            return 0;
        }

    }

    /**
     * @param yearMonth 202505
     * @return
     */
    public static BigDecimal getMonthRchgAmt(int yearMonth) {
        String startTime = getMonthStartDatetime(yearMonth);
        String endTime = getMonthEndDatetime(yearMonth);
        return getSumAmtByBkDttm(startTime, endTime);

    }
    public static long getMonthRchgUserCnt(int yearMonth) {

        String startTime = getMonthStartDatetime(yearMonth);
        String endTime = getMonthEndDatetime(yearMonth);
        return getCntByBkDttm(startTime, endTime);
    }

    @NotNull
    public static BigDecimal getSumAmtByBkDttm(String starttime, String endTime) {
        SlctQry query = newSelectQuery(getTableName(Transaction.class));
        query.select("sum(amount)");

        query.addConditions( Transaction.Fields.transactionCode + "=" + toValStr(TransactionCode.payment_rechg.name()));
        query.addConditions(Transaction.Fields.bookingDateTime+">="+toValStr(starttime));
        query.addConditions(Transaction.Fields.bookingDateTime+"<="+toValStr(endTime));

        String sql = query.getSQL();  // ✅ 直接拿到 SQL 字符串
        System.out.println(sql);
        try {
            return (BigDecimal) getSingleResult(sql, sessionFactory.getCurrentSession());
        } catch (findByIdExptn_CantFindData e) {
            return BigDecimal.valueOf(0);
        }
    }

    @NotNull
    public static BigDecimal getSumAmtByTrxtype(String trxType) {
        SlctQry query = newSelectQuery(getTableName(Transaction.class));
        query.select("sum(amount)");

        query.addConditions(Transaction.Fields.transactionCode , "=",(trxType));

        String sql = query.getSQL();  // ✅ 直接拿到 SQL 字符串
        System.out.println(sql);
        try {
            return (BigDecimal) getSingleResult(sql, sessionFactory.getCurrentSession());
        } catch (findByIdExptn_CantFindData e) {
            return BigDecimal.valueOf(0);
        }
    }

}

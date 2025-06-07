package handler.trx;

import model.OpenBankingOBIE.*;
import org.jetbrains.annotations.NotNull;
import util.Oosql.SlctQry;
import util.tx.findByIdExptn_CantFindData;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static cfg.Containr.sessionFactory;
import static handler.trx.TxDao.getSumAmtByBkDttm;
import static handler.trx.TxDao.getSumAmtByTrxtype;
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
        return getSumAmtByTrxtype(TransactionCode.invstProfit);

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
//    public static long getMonthRchgUserCnt(int yearMonth) {
//
//        String startTime = getMonthStartDatetime(yearMonth);
//        String endTime = getMonthEndDatetime(yearMonth);
//        return getCntByBkDttm(startTime, endTime);
//    }


}

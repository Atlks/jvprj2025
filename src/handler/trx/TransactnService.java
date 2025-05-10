package handler.trx;

import model.OpenBankingOBIE.Transaction;
import model.OpenBankingOBIE.TransactionCode;
import model.opmng.InvestmentOpRecord;
import org.jetbrains.annotations.NotNull;
import util.Oosql.SlctQry;
import util.tx.findByIdExptn_CantFindData;

import java.math.BigDecimal;

import static cfg.Containr.sessionFactory;
import static util.Oosql.SlctQry.newSelectQuery;
import static util.Oosql.SlctQry.toValStr;
import static util.algo.GetUti.getTableName;
import static util.tx.HbntUtil.getSingleResult;

public class TransactnService {

    public static BigDecimal sumInvstProfit() {

        String trxType = TransactionCode.invstProfit.name();
        return getSumAmtByTrxtype(trxType);

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

        query.addConditions(Transaction.Fields.transactionCode + "=" + toValStr(trxType));

        String sql = query.getSQL();  // ✅ 直接拿到 SQL 字符串
        System.out.println(sql);
        try {
            return (BigDecimal) getSingleResult(sql, sessionFactory.getCurrentSession());
        } catch (findByIdExptn_CantFindData e) {
            return BigDecimal.valueOf(0);
        }
    }

}

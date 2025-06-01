package handler.trx;

import cfg.Containr;
import handler.statmt.CrudFun;
import model.OpenBankingOBIE.Transaction;
import model.OpenBankingOBIE.TransactionCode;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.jetbrains.annotations.NotNull;
import util.Oosql.SlctQry;
import util.tx.findByIdExptn_CantFindData;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static util.Oosql.SlctQry.newSelectQuery;
import static util.algo.GetUti.getTableName;
import static util.oo.SqlUti.andCondt;
import static util.tx.HbntUtil.getSingleResult;
import static util.tx.HbntUtil.sessionFactory;

public class TxDao {
    public static long getCntByBkDttm(OffsetDateTime startTime, OffsetDateTime endTime) {

        SlctQry query = newSelectQuery(getTableName(Transaction.class));
        query.select("count(*)");

        query.addConditions(Transaction.Fields.transactionCode , "=" ,(TransactionCode.payment_rechg.name()));
        query.addConditions(Transaction.Fields.bookingDateTime ,">=",startTime);
        query.addConditions(Transaction.Fields.bookingDateTime , "<=" ,endTime);

        String sql = query.getSQL();  // ✅ 直接拿到 SQL 字符串
        System.out.println(sql);
        try {
            return (long) getSingleResult(sql, Containr.sessionFactory.getCurrentSession());
        } catch (findByIdExptn_CantFindData e) {
            return 0;
        }

    }
    @NotNull
    public static BigDecimal getSumAmtByBkDttm(String starttime, String endTime) {
        SlctQry query = newSelectQuery(getTableName(Transaction.class));
        query.select("sum(amount)");

        query.addConditions( Transaction.Fields.transactionCode ,"=" , (TransactionCode.payment_rechg.name()));
        query.addConditions(Transaction.Fields.bookingDateTime,">=",(starttime));
        query.addConditions(Transaction.Fields.bookingDateTime,"<=",(endTime));

        String sql = query.getSQL();  // ✅ 直接拿到 SQL 字符串
        System.out.println(sql);
        try {
            return (BigDecimal) getSingleResult(sql, Containr.sessionFactory.getCurrentSession());
        } catch (findByIdExptn_CantFindData e) {
            return BigDecimal.valueOf(0);
        }
    }

    @NotNull
    public static BigDecimal getSumAmtByTrxtype(TransactionCode trxType) {
        SlctQry query = newSelectQuery(getTableName(Transaction.class));
        query.select("sum(amount)");

        query.addConditions(Transaction.Fields.transactionCode , "=",(trxType.name()));

        String sql = query.getSQL();  // ✅ 直接拿到 SQL 字符串
        System.out.println(sql);
        try {
            return (BigDecimal) getSingleResult(sql, Containr.sessionFactory.getCurrentSession());
        } catch (findByIdExptn_CantFindData e) {
            return BigDecimal.valueOf(0);
        }
    }

    public static BigDecimal sumAmtWhrTxtypeNAccid
            (TransactionCode transactionCode, String accid, OffsetDateTime startDateTime, OffsetDateTime endDateTime) {
        String sql = "select sum(amount) from Transactions where Transaction_Code='" + transactionCode.name() + "' and account_id='" + accid + "'";
        sql+=andCondt(Transaction.Fields.bookingDateTime,">=",startDateTime);
        sql+=andCondt(Transaction.Fields.bookingDateTime,"<",endDateTime);
        Session session = sessionFactory.getCurrentSession();
        NativeQuery<?> query = session.createNativeQuery(sql);
        BigDecimal result = (BigDecimal) query.getSingleResult();
        return result;

    }
    @CrudFun
    public static BigDecimal sumAmtWhereTxtype(TransactionCode transactionCode, OffsetDateTime startDateTime, OffsetDateTime endDateTime) {
        String sql = "select sum(amount) from Transactions where Transaction_Code='" + transactionCode.name()+"' "  ;
        sql+=andCondt(Transaction.Fields.bookingDateTime,">=",startDateTime);
        sql+=andCondt(Transaction.Fields.bookingDateTime,"<",endDateTime);
        Session session = sessionFactory.getCurrentSession();
        NativeQuery<?> query = session.createNativeQuery(sql);
        BigDecimal result = (BigDecimal) query.getSingleResult();
        if(result==null)
            return BigDecimal.valueOf(0);
        return result;

    }
}

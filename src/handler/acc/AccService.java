package handler.acc;

import model.OpenBankingOBIE.Account;
import model.OpenBankingOBIE.AccountSubType;
import model.OpenBankingOBIE.TransactionCode;
import model.opmng.InvestmentOpRecord;
import util.Oosql.SlctQry;
import util.tx.findByIdExptn_CantFindData;

import java.math.BigDecimal;

import static cfg.Containr.sessionFactory;
import static util.Oosql.SlctQry.newSelectQuery;
import static util.Oosql.SlctQry.toValStr;
import static util.acc.AccUti.getAccId;
import static util.acc.AccUti.sysusrName;
import static util.algo.GetUti.getTableName;
import static util.tx.HbntUtil.findByHerbinate;
import static util.tx.HbntUtil.getSingleResult;

public class AccService {

    public static BigDecimal getInsFdPlBal() {
        String accid = getAccId(String.valueOf(AccountSubType.insFdPl), sysusrName);
        Account acc = null;
        try {
            acc = findByHerbinate(Account.class, accid, sessionFactory.getCurrentSession());
            return acc.getInterimAvailableBalance();
        } catch (findByIdExptn_CantFindData e) {
            return BigDecimal.valueOf(0);
        }

    }


    public static BigDecimal sumAllEmnyAccBal() {
        SlctQry query = newSelectQuery(getTableName(Account.class));
        query.select("sum("+Account.Fields.InterimAvailableBalance+")");
        query.addConditions(Account.Fields.accountSubType + "=" + toValStr(AccountSubType.EMoney.name()));
        // query.addConditions("timestamp>"+ beforeTmstmp(reqdto.day));..
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

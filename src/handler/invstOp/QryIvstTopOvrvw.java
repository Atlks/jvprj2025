package handler.invstOp;

import entityx.usr.NonDto;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.OpenBankingOBIE.Account;
import model.OpenBankingOBIE.AccountSubType;
import model.OpenBankingOBIE.TransactionCode;
import model.acc.GlbAcc;
import model.opmng.InvestmentOpRecord;
import org.jetbrains.annotations.NotNull;
import util.Oosql.SlctQry;
import util.acc.AccUti;
import util.cache.CacheFil;
import util.tx.HbntUtil;
import util.tx.findByIdExptn_CantFindData;

import java.math.BigDecimal;
import java.util.function.Supplier;

import static cfg.Containr.sessionFactory;
import static handler.acc.AccService.*;
import static handler.trx.TransactnService.sumInvstProfit;
import static util.Oosql.SlctQry.newSelectQuery;
import static util.Oosql.SlctQry.toValStr;
import static util.acc.AccUti.getAccId;
import static util.algo.GetUti.getTableName;
import static util.tx.HbntUtil.findById;
import static util.tx.HbntUtil.getSingleResult;


@PermitAll
@Path("/apiv1/admin/InvstRcd/QryIvstTopOvrvw")

@NoArgsConstructor
@Data
public class QryIvstTopOvrvw {


    public Object handleRequest(NonDto param) {

        Supplier<GlbAcc> glbAccSupplier = () -> {
            try {
                return getGlbAcc();
            } catch (findByIdExptn_CantFindData e) {
                throw new RuntimeException(e);
            }
        };
        return CacheFil.getOrLoad("glbAcc",glbAccSupplier,1);
      //  return CacheUtil.getOrLoad("glbAcc", glbAccSupplier);
    }

    @NotNull
    private GlbAcc getGlbAcc() throws findByIdExptn_CantFindData {
        GlbAcc a = new GlbAcc();
        a.setTotalEmoneyBalance(sumAmtFromAccWhereSubtypeEqEmoney());
        a.setInsFdPoolBalance(getInsFdPlBal());
        a.setTotalInvstProfit(sysInvstAccBal());
        a.setTotalInvstLoss(sumInvstLoss());
        return a;
    }

    public BigDecimal sysInvstAccBal() throws findByIdExptn_CantFindData {
        String accId_sysInvstAcc = getAccId(AccountSubType.GeneralInvestment.name(), AccUti.sysusrName);
        Account sysAccIvst= findById(Account.class, accId_sysInvstAcc, HbntUtil.sessionFactory.getCurrentSession());
        return  sysAccIvst.interim_Available_Balance;
    }

    //todo  这个是对的，，那个按照  总投资盈利amt 累计从账号里面获取是错误的
    private BigDecimal sumInvstLoss() {
        SlctQry query = newSelectQuery(getTableName(InvestmentOpRecord.class));
        query.select("sum(amount)");
        query.addConditions(InvestmentOpRecord.Fields.investmentType ,"=" , (TransactionCode.invstLoss.name()));
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

package handler.invstOp;

import entityx.usr.NonDto;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.OpenBankingOBIE.TransactionCode;
import model.acc.GlbAcc;
import model.opmng.InvestmentOpRecord;
import util.Oosql.SlctQry;
import util.cache.CacheFil;
import util.tx.findByIdExptn_CantFindData;

import java.math.BigDecimal;
import java.util.function.Supplier;

import static cfg.Containr.sessionFactory;
import static handler.acc.AccService.getInsFdPlBal;
import static handler.acc.AccService.sumAllEmnyAccBal;
import static handler.trx.TransactnService.sumInvstProfit;
import static util.Oosql.SlctQry.newSelectQuery;
import static util.Oosql.SlctQry.toValStr;
import static util.algo.GetUti.getTableName;
import static util.tx.HbntUtil.findById;
import static util.tx.HbntUtil.getSingleResult;


@PermitAll
@Path("/admin/InvstRcd/QryIvstTopOvrvw")

@NoArgsConstructor
@Data
public class QryIvstTopOvrvw {


    public Object handleRequest(NonDto param) {

        Supplier<GlbAcc> glbAccSupplier = () -> {
            GlbAcc a = new GlbAcc();
            a.setTotalEmoneyBalance(sumAllEmnyAccBal());
            a.setInsFdPoolBalance(getInsFdPlBal());
            a.setTotalInvstProfit(sumInvstProfit());
            a.setTotalInvstLoss(sumInvstLoss());
            return a;
        };
        return CacheFil.getOrLoad("glbAcc",glbAccSupplier,300);
      //  return CacheUtil.getOrLoad("glbAcc", glbAccSupplier);
    }


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

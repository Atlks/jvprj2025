package handler.agt;

import entityx.usr.Usr;
import handler.fundDetail.qryFdDtl.QryFundDetailRqdto;
import handler.ivstAcc.dto.QueryDto;
import model.OpenBankingOBIE.Transaction;
import model.OpenBankingOBIE.TransactionCode;
import model.agt.Agent;
import util.Oosql.SlctQry;
import util.annos.Paths;

import java.math.BigDecimal;

import static cfg.Containr.sessionFactory;
import static util.Oosql.SlctQry.newSelectQuery;
import static util.Oosql.SlctQry.toValStr;
import static util.algo.GetUti.getTableName;
import static util.tx.HbntUtil.*;

//  /agt/MyAgtInfo
public class MyAgtInfo {


    public Object handleRequest(QueryDto reqdto) throws Throwable {
        Agent agt= findByHerbinate(Agent.class, reqdto.uname, sessionFactory.getCurrentSession());
    return agt;
    }



    @Paths({"/agt/listMyMmbr"})
    public Object listMyMmbr(QueryDto reqdto) throws Throwable {
        SlctQry query = newSelectQuery(getTableName(Usr.class));
        //  query.select("*");
        query.addConditions(Usr.Fields.invtr + "=" + toValStr(reqdto.uname));


        String sql = query.getSQL();  // ✅ 直接拿到 SQL 字符串
        System.out.println(sql);

        return   getResultList(sql, Usr.class);

    }

}

package util.Oosql;

import handler.fundDetail.qryFdDtl.QryFundDetailRqdto;
import model.OpenBankingOBIE.Transaction;


import static util.Oosql.SlctQry.newSelectQuery;
import static util.Oosql.SlctQry.toValStr;
import static util.algo.GetUti.getTableName;
import static util.oo.TimeUti.beforeTmstmp;

public class testOosqlUt {


    public static void main(String[] args) {

        QryFundDetailRqdto reqdto =new QryFundDetailRqdto();
        reqdto.setUname("test");
        reqdto.day=1;


        SlctQry query = newSelectQuery(getTableName(Transaction.class));
        query.addConditions("accountOwner="+toValStr(reqdto.uname));
        query.addConditions("timestamp>"+ beforeTmstmp(reqdto.day));
        query.addOrderBy("timestamp desc");
        String sql = query.getSQL();  // ✅ 直接拿到 SQL 字符串
        System.out.println(sql);
    }


}

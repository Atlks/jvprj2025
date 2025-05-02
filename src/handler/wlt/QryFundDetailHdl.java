package handler.wlt;

import cfg.AppConfig;
import entityx.usr.Usr;
import handler.wlt.qryFdDtl.QryFundDetailRqdto;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import model.OpenBankingOBIE.Transaction;
import model.wlt.BalancesFundDetail;
import org.jooq.*;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;

import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;

import java.math.BigDecimal;
import java.util.HashMap;

import static cfg.Containr.sessionFactory;
import static cfg.MyCfg.iniContnr;
import static util.algo.EncodeUtil.encodeSqlPrm;
import static util.algo.GetUti.getTableName;
import static util.misc.Util2025.encodeJson;
import static util.oo.TimeUti.beforeTmstmp;
import static util.tx.Pagging.getPageResultByHbntV4;
import static util.tx.TransactMng.commitTsact;
import static util.tx.TransactMng.openSessionBgnTransact;

@Path("/wlt/QryFundDetailHdl")

public class QryFundDetailHdl  {
    /**
     * @param reqdto

     * @return
     * @throws Throwable
     */

    public Object handleRequest(QryFundDetailRqdto reqdto) throws Throwable {


        Table<?> USER = DSL.table(getTableName(Transaction.class));
        Field<String> accountOwner = DSL.field("accountOwner", String.class);
        Field<Long> timestamp = DSL.field("timestamp", Long.class);

        DSLContext create = DSL.using(SQLDialect.MYSQL);
        SelectQuery<?> query = create.selectQuery();

        query.addFrom(USER);
        query.addConditions(accountOwner.eq(reqdto.uname));

        query.addConditions(timestamp.gt( beforeTmstmp(reqdto.day)));
        query.addOrderBy(timestamp.desc());
        String sql = query.getSQL(ParamType.INLINED);  // ✅ 直接拿到 SQL 字符串
        System.out.println(sql);




        HashMap<String, Object> sqlprmMap = new HashMap<>();
        var list1 = getPageResultByHbntV4(sql, sqlprmMap, reqdto, sessionFactory.getCurrentSession(), Transaction.class);

        return (list1);
    }


    public static void main(String[] args) throws Throwable {
        new AppConfig().sessionFactory();//ini sessFctr
        //ini contnr 4cfg,, svrs
        iniContnr();

        //============aop trans begn
        openSessionBgnTransact();

        BalancesFundDetail o=new BalancesFundDetail();
        o.changeAmount= BigDecimal.valueOf(666666);
        o.amtBefore= BigDecimal.valueOf(0);
        o.amtAfter=o.amtBefore.add(o.changeAmount);
        o.uname="007";
      //  persistByHibernate(o, sessionFactory.getCurrentSession());

//        String f="C:\\0prj\\jvprj2025\\doc2504\\agt rechg cms ruler cfg.json";
//        String txt=readTxtFrmFil(f);
        QryFundDetailRqdto c=new QryFundDetailRqdto();
        c.uname="007";
        //  persistByHibernate(o, AppConfig.sessionFactory.getCurrentSession());
        System.out.println(encodeJson(new QryFundDetailHdl().handleRequest(c)));

        commitTsact();
    }


}

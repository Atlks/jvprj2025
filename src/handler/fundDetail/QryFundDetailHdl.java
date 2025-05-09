package handler.fundDetail;

import cfg.MainStart;
import handler.fundDetail.qryFdDtl.QryFundDetailRqdto;
import jakarta.ws.rs.Path;
import model.OpenBankingOBIE.Transaction;
import model.wlt.BalancesFundDetail;
import org.jooq.*;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static cfg.Containr.sessionFactory;
import static cfg.MainStart.iniContnr;
import static util.algo.GetUti.getTableName;
import static util.misc.Util2025.encodeJson;
import static util.oo.TimeUti.beforeTmstmp;
import static util.tx.HbntUtil.getResultListWzPageByHbntRtLstmap;
import static util.tx.Pagging.getPageResultByHbntV4;
import static util.tx.TransactMng.commitTsact;
import static util.tx.TransactMng.openSessionBgnTransact;
/**
 * menu::  admin/data rpt/fd dtl
 */
@Path("/wlt/QryFundDetailHdl")

public class QryFundDetailHdl  {
    /**
     * @param reqdto

     * @return
     * @throws Throwable
     */

    public Object handleRequest(QryFundDetailRqdto reqdto) throws Throwable {


        Table<?> Transaction1 = DSL.table(getTableName(Transaction.class));
        Field<String> accountOwner = DSL.field("accountOwner", String.class);
        Field<Long> timestamp = DSL.field("timestamp", Long.class);

        DSLContext create = DSL.using(SQLDialect.MYSQL);
        SelectQuery<?> query = create.selectQuery();

        query.addFrom(Transaction1);
        query.addConditions(accountOwner.eq(reqdto.uname));

        query.addConditions(timestamp.gt( beforeTmstmp(reqdto.day)));
        query.addOrderBy(timestamp.desc());
        String sql = query.getSQL(ParamType.INLINED);  // ✅ 直接拿到 SQL 字符串
        System.out.println(sql);




        HashMap<String, Object> sqlprmMap = new HashMap<>();
        Pageable pageable = PageRequest.of(2, 10);

        Page<Map> list1 = getResultListWzPageByHbntRtLstmap(sql, sqlprmMap, pageable);

        return (list1);
    }



    public static void main(String[] args) throws Throwable {
        new MainStart().sessionFactory();//ini sessFctr
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

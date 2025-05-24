package handler.fundDetail;

import cfg.MainStart;
import handler.fundDetail.qryFdDtl.QryFundDetailRqdto;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import model.OpenBankingOBIE.Transaction;
import model.wlt.BalancesFundDetail;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import util.Oosql.SlctQry;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static cfg.MainStart.iniContnr;
import static util.algo.GetUti.getTableName;
import static util.misc.Util2025.encodeJson;
import static util.oo.TimeUti.*;
import static util.tx.HbntUtil.getResultListWzPageByHbntRtLstmap;
import static util.tx.TransactMng.commitx;
import static util.tx.TransactMng.beginx;

/**
 * menu::  admin/data rpt/fd dtl
 */
@Path("/admin/QryFundDetailHdl")
@PermitAll
public class QryFundDetailHdl4adm {
    /**
     * @param reqdto

     * @return
     * @throws Throwable
     */

    public Object handleRequest(QryFundDetailRqdto reqdto) throws Throwable {



        SlctQry query = SlctQry.newSelectQuery(getTableName(Transaction.class));


        if(reqdto.uname!="")
        query.addConditions(Transaction.Fields.owner +"="+(reqdto.uname));

        query.addConditions(Transaction.Fields.timestamp+">"+beforeTmSqlPrmFmt( (reqdto.day)));
        query.addOrderBy(Transaction.Fields.timestamp+" desc");
        String sql = query.getSQL();  // ✅ 直接拿到 SQL 字符串
        System.out.println(sql);




        HashMap<String, Object> sqlprmMap = new HashMap<>();
        Pageable pageable = PageRequest.of(reqdto.page, reqdto.pagesize);

        Page<Map> list1 = getResultListWzPageByHbntRtLstmap(sql, sqlprmMap, pageable);

        return (list1);
    }



    public static void main(String[] args) throws Throwable {
        new MainStart().sessionFactory();//ini sessFctr
        //ini contnr 4cfg,, svrs
        iniContnr();

        //============aop trans begn
        beginx();

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
        System.out.println(encodeJson(new QryFundDetailHdl4adm().handleRequest(c)));

        commitx();
    }


}

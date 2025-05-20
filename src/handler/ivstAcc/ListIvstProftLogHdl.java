package handler.ivstAcc;


/**
 * BetWinLog
 */

import handler.ivstAcc.dto.QueryDto;
import model.OpenBankingOBIE.Transaction;
import model.OpenBankingOBIE.TransactionCode;
import org.hibernate.Session;
import util.Oosql.SlctQry;
import util.annos.NoDftParam;
import entityx.ylwlt.BetWinLog;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import util.model.Context;

import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;
import util.tx.findByIdExptn_CantFindData;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static cfg.Containr.sessionFactory;
import static cfg.MainStart.iniContnr;

import static util.Oosql.SlctQry.newSelectQuery;
import static util.algo.EncodeUtil.encodeSqlPrmAsStr;
import static util.algo.EncodeUtil.toStr4sqlprm;
import static util.algo.GetUti.getTableName;
import static util.algo.ToXX.toSnake;
import static util.misc.Util2025.encodeJson;
import static util.tx.HbntUtil.getSingleResult;
import static util.tx.Pagging.getPageResultByHbntRtLstmap;
import static util.tx.TransactMng.commitTsact;
import static util.tx.TransactMng.openSessionBgnTransact;

/**
 *
 */

@Path("/wlt/ListIvstProfitHdl")
@PermitAll
@NoDftParam
public class ListIvstProftLogHdl implements RequestHandler<QueryDto, ApiGatewayResponse> {
    /**
     * @param reqdto
     * @param context
     * @return
     * @throws Throwable
     */
    @Override
    public ApiGatewayResponse handleRequest(QueryDto reqdto, Context context) throws Throwable {



        SlctQry query = newSelectQuery(getTableName(Transaction.class));
        query.select("*");
        addCdtn(query,  reqdto);   query.addOrderBy("timestamp desc");
        String sql=query.getSQL();

        Map<String,Object> sqlprmMap=new HashMap();
        var list1 = getPageResultByHbntRtLstmap(sql, sqlprmMap,reqdto, sessionFactory.getCurrentSession());
        list1.sum=getSum4div(reqdto);
        return new ApiGatewayResponse(list1);
    }

    private static void addCdtn(SlctQry query,  QueryDto reqdto) {
        query.addConditions(toSnake(Transaction.Fields.transactionCode) + "=" + toStr4sqlprm(TransactionCode.invstProfit.name()));


        if (reqdto.uname != "") {
            query.addConditions(Transaction.Fields.owner + "=" + encodeSqlPrmAsStr(reqdto.uname));
        }
    }

    private BigDecimal getSum4div(QueryDto reqdto)  {
          SlctQry query = newSelectQuery(getTableName(Transaction.class));
        query.select("sum(amount)");
        addCdtn(query,  reqdto);

        String sql=query.getSQL();

        Session session = sessionFactory.getCurrentSession();
//        Query query = session.createQuery(sql);
//      var  result = (BigDecimal) query.getSingleResult();
        try {
            return (BigDecimal) getSingleResult(sql,session);
        } catch (findByIdExptn_CantFindData e) {

            return BigDecimal.valueOf(0);
            // throw new RuntimeException(e);
        }


    }


    public static void main(String[] args) throws Throwable {

      //  new AppConfig().sessionFactory();//ini sessFctr
        //ini contnr 4cfg,, svrs
        iniContnr();

        //============aop trans begn
        openSessionBgnTransact();




        BetWinLog o=new BetWinLog();
        o.setId(getUUID());
        o.setUname("777");
        o.setAmt(new BigDecimal("9999"));
      //  persistByHibernate(o, AppConfig.sessionFactory.getCurrentSession());
        System.out.println(encodeJson(new ListIvstProftLogHdl().handleRequest(new QueryDto("777"),null)));

        commitTsact();
    }

    private static String getUUID() {
        return UUID.randomUUID().toString();
    }
}

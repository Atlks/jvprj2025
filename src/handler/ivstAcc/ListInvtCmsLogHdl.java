package handler.ivstAcc;


/**
 *  钱包  我的佣金 列表明细
 */

import cfg.MainStart;
import handler.ivstAcc.dto.QueryDto;
import model.OpenBankingOBIE.Transaction;
import model.OpenBankingOBIE.TransactionCode;
import util.Oosql.SlctQry;
import util.annos.NoDftParam;
import entityx.ylwlt.BetWinLog;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import util.model.Context;

import org.hibernate.Session;

import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;
import util.tx.findByIdExptn_CantFindData;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.UUID;

import static util.Oosql.SlctQry.newSelectQuery;
import static util.algo.EncodeUtil.encodeSqlPrmAsStr;
import static util.algo.GetUti.getTableName;
import static util.algo.ToXX.toSnake;
import static util.misc.Util2025.encodeJson;
import static util.tx.HbntUtil.getSingleResult;
import static util.tx.Pagging.getPageResultByHbntRtLstmap;
import static util.tx.TransactMng.commitx;
import static util.tx.TransactMng.beginx;
import static cfg.Containr.sessionFactory;
import static cfg.MainStart.iniContnr;

/**
 *
 */

@Path("/apiv1/wlt/ListInvtCmsLogHdl")
@PermitAll
@NoDftParam
public class ListInvtCmsLogHdl implements RequestHandler<QueryDto, ApiGatewayResponse> {
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
        setCdtn(reqdto, query);
        query.addOrderBy("timestamp desc");
       String sql=query.getSQL();
        System.out.println(sql);
 //--------------
        HashMap<String, Object> sqlprmMap = new HashMap<>();
        var list1 = getPageResultByHbntRtLstmap(sql, sqlprmMap,reqdto, sessionFactory.getCurrentSession());
        list1.sum=getSum4cms(reqdto,0);
        return new ApiGatewayResponse(list1);
    }

    private static void setCdtn(QueryDto reqdto, SlctQry query) {
        query.addConditions(toSnake(Transaction.Fields.transactionCode)+"=",(TransactionCode.Service_Cms_rechgCms.name()));
        //  query.addConditions(Transaction.Fields.transactionCode+"="+toStr4sqlprm(TransactionCode.Service_Cms_rechgCms.name()));

        if(reqdto.uname!="")
        {
            query.addConditions(Transaction.Fields.owner +"="+encodeSqlPrmAsStr(reqdto.uname));
        }
    }


    private BigDecimal getSum4cms(QueryDto reqdto, int i)  {
        //var sql = "select sum(amount) from Transactions     where  transactionCode= "  +encodeSqlPrmAsStr( TransactionCodes.Service_Cms_rechgCms.name());//for count    where  uname =:uname
        SlctQry query = newSelectQuery(getTableName(Transaction.class));
        query.select("sum(amount)");
        setCdtn(reqdto, query);
        var sql=query.getSQL();
        System.out.println(sql);
        Session session = sessionFactory.getCurrentSession();
        try {
            return (BigDecimal) getSingleResult(sql,session);
        } catch (findByIdExptn_CantFindData e) {
           return BigDecimal.valueOf(0);
        }


    }




    public static void main(String[] args) throws Throwable {

        new MainStart().sessionFactory();//ini sessFctr
        //ini contnr 4cfg,, svrs
        iniContnr();

        //============aop trans begn
        beginx();




        BetWinLog o=new BetWinLog();
        o.setId(getUUID());
        o.setUname("777");
        o.setAmt(new BigDecimal("9999"));
      //  persistByHibernate(o, AppConfig.sessionFactory.getCurrentSession());
        System.out.println(encodeJson(new ListInvtCmsLogHdl().handleRequest(new QueryDto("777"),null)));

        commitx();
    }

    private static String getUUID() {
        return UUID.randomUUID().toString();
    }
}

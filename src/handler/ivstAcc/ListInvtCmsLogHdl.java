package handler.ivstAcc;


/**
 * BetWinLog
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
import jakarta.ws.rs.core.Context;

import org.hibernate.Session;

import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;
import util.tx.findByIdExptn_CantFindData;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.UUID;

import static util.Oosql.SlctQry.newSelectQuery;
import static util.algo.EncodeUtil.encodeSqlPrmAsStr;
import static util.algo.EncodeUtil.toStr4sqlprm;
import static util.algo.GetUti.getTableName;
import static util.misc.Util2025.encodeJson;
import static util.tx.HbntUtil.getSingleResult;
import static util.tx.Pagging.getPageResultByHbntRtLstmap;
import static util.tx.TransactMng.commitTsact;
import static util.tx.TransactMng.openSessionBgnTransact;
import static cfg.Containr.sessionFactory;
import static cfg.MainStart.iniContnr;

/**
 *
 */

@Path("/wlt/ListInvtCmsLogHdl")
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
        query.addConditions(Transaction.Fields.transactionCode+"="+toStr4sqlprm(TransactionCode.Service_Cms_rechgCms.name()));

        if(reqdto.uname!="")
        {
            query.addConditions(Transaction.Fields.accountOwner+"="+encodeSqlPrmAsStr(reqdto.uname));
        }
        query.addOrderBy("timestamp desc");
       String sql=query.getSQL();
        System.out.println(sql);
 //--------------
        HashMap<String, Object> sqlprmMap = new HashMap<>();
        var list1 = getPageResultByHbntRtLstmap(sql, sqlprmMap,reqdto, sessionFactory.getCurrentSession());
        list1.sum=getSum4cms(reqdto,0);
        return new ApiGatewayResponse(list1);
    }



    private BigDecimal getSum4cms(QueryDto reqdto, int i)  {
        //var sql = "select sum(amount) from Transactions     where  transactionCode= "  +encodeSqlPrmAsStr( TransactionCodes.Service_Cms_rechgCms.name());//for count    where  uname =:uname
        SlctQry query = newSelectQuery(getTableName(Transaction.class));
        query.select("sum(amount)");
        query.addConditions(Transaction.Fields.transactionCode+"="+toStr4sqlprm(TransactionCode. Service_Cms_rechgCms.name()));

        if(reqdto.uname!="")
        {
            query.addConditions(Transaction.Fields.accountOwner+"="+encodeSqlPrmAsStr(reqdto.uname));
        }
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
        openSessionBgnTransact();




        BetWinLog o=new BetWinLog();
        o.setId(getUUID());
        o.setUname("777");
        o.setAmt(new BigDecimal("9999"));
      //  persistByHibernate(o, AppConfig.sessionFactory.getCurrentSession());
        System.out.println(encodeJson(new ListInvtCmsLogHdl().handleRequest(new QueryDto("777"),null)));

        commitTsact();
    }

    private static String getUUID() {
        return UUID.randomUUID().toString();
    }
}

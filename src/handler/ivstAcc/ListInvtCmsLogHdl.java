package handler.ivstAcc;


/**
 * BetWinLog
 */

import handler.ivstAcc.dto.QueryDto;
import util.annos.NoDftParam;
import cfg.AppConfig;
import entityx.ylwlt.BetWinLog;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import model.OpenBankingOBIE.TransactionCodes;

import org.hibernate.Session;
import org.hibernate.query.Query;

import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.UUID;
import static util.algo.EncodeUtil.encodeSqlPrmAsStr;
import static util.misc.Util2025.encodeJson;
import static util.tx.Pagging.getPageResultByHbntRtLstmap;
import static util.tx.TransactMng.commitTsact;
import static util.tx.TransactMng.openSessionBgnTransact;
import static cfg.Containr.sessionFactory;
import static cfg.MyCfg.iniContnr;

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
        var sqlNoOrd = "select * from Transactions where transactionCode= "+encodeSqlPrmAsStr( TransactionCodes.COM.name());//for count    where  uname =:uname
        HashMap<String, Object> sqlprmMap = new HashMap<>();
        if(reqdto.uname!="")
        {  sqlNoOrd=sqlNoOrd+ "and  uname = "+ encodeSqlPrmAsStr(reqdto.uname);
         //   sqlprmMap.put("uname",)
        }

        var sql=sqlNoOrd+" order by timestamp desc ";
        //  Map<String, Object> sqlprmMap= Map.of( "sql",sql,   "uname",reqdto.uname);
        //   System.out.println( encodeJson(sqlprmMap));

        var list1 = getPageResultByHbntRtLstmap(sql, sqlprmMap,reqdto, sessionFactory.getCurrentSession());
        list1.sum=getSum4cms(reqdto);
        return new ApiGatewayResponse(list1);
    }

    private BigDecimal getSum4cms(QueryDto reqdto) {
        var sql = "select sum(amount) from Transactions     where  transactionCode= "  +encodeSqlPrmAsStr( TransactionCodes.COM.name());//for count    where  uname =:uname
        if(reqdto.uname!="")
        {  sql=sql+ "and  uname = "+ encodeSqlPrmAsStr(reqdto.uname);
            // sqlprmMap.put("uname",)
        }

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
      var  result = (BigDecimal) query.getSingleResult();
      return  result;


    }




    public static void main(String[] args) throws Throwable {

        new AppConfig().sessionFactory();//ini sessFctr
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

package handler.ylwlt;


/**
 * BetWinLog
 */

import handler.ylwlt.dto.QueryDto;
import util.annos.NoDftParam;
import cfg.AppConfig;
import entityx.ylwlt.BetWinLog;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import org.springframework.web.bind.annotation.RestController;
import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.UUID;

import static cfg.Containr.sessionFactory;
import static cfg.MyCfg.iniContnr;
import static util.algo.EncodeUtil.encodeSqlPrmAsStr;
import static util.misc.Util2025.encodeJson;
import static util.tx.Pagging.getPageResultByHbntRtLstmap;
import static util.tx.TransactMng.commitTsact;
import static util.tx.TransactMng.openSessionBgnTransact;

/**
 *
 */
@RestController
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
        var sqlNoOrd = "select * from invt_cms_log where 1=1 ";//for count    where  uname =:uname
        HashMap<String, Object> sqlprmMap = new HashMap<>();
        if(reqdto.uname!="")
        {  sqlNoOrd=sqlNoOrd+ "and  uname = "+ encodeSqlPrmAsStr(reqdto.uname);
         //   sqlprmMap.put("uname",)
        }

        var sql=sqlNoOrd+" order by timestamp desc ";
        //  Map<String, Object> sqlprmMap= Map.of( "sql",sql,   "uname",reqdto.uname);
        //   System.out.println( encodeJson(sqlprmMap));

        var list1 = getPageResultByHbntRtLstmap(sql, sqlprmMap,reqdto, sessionFactory.getCurrentSession());

        return new ApiGatewayResponse(list1);
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

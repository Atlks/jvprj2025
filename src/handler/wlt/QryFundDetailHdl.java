package handler.wlt;

import cfg.AppConfig;
import dto.QryFundDetailRqdto;
import entityx.wlt.ReChgOrd;
import handler.cfg.SetCfg;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import model.cfg.CfgKv;
import model.wlt.FundDetail;
import org.springframework.web.bind.annotation.RestController;
import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;

import java.util.HashMap;

import static biz.Containr.sessionFactory;
import static cfg.MyCfg.iniContnr;
import static util.algo.EncodeUtil.encodeSqlPrm;
import static util.misc.Util2025.encodeJson;
import static util.misc.Util2025.readTxtFrmFil;
import static util.oo.TimeUti.beforeTmstmp;
import static util.tx.Pagging.getPageResultByHbntV4;
import static util.tx.TransactMng.commitTsact;
import static util.tx.TransactMng.openSessionBgnTransact;
@RestController
@Path("/wlt/QryFundDetailHdl")

public class QryFundDetailHdl implements RequestHandler<QryFundDetailRqdto, ApiGatewayResponse> {
    /**
     * @param reqdto
     * @param context
     * @return
     * @throws Throwable
     */
    @Override
    public ApiGatewayResponse handleRequest(QryFundDetailRqdto reqdto, Context context) throws Throwable {
        var sqlNoOrd = "select * from fund_detail where    uname=" + encodeSqlPrm(reqdto.uname);//for count    where  uname =:uname
        sqlNoOrd = sqlNoOrd + " and timestamp>=" + beforeTmstmp(reqdto.day);
        var sql = sqlNoOrd + " order by timestamp desc ";


        HashMap<String, Object> sqlprmMap = new HashMap<>();
        var list1 = getPageResultByHbntV4(sql, sqlprmMap, reqdto, sessionFactory.getCurrentSession(), FundDetail.class);

        return new ApiGatewayResponse(list1);
    }


    public static void main(String[] args) throws Throwable {
        new AppConfig().sessionFactory();//ini sessFctr
        //ini contnr 4cfg,, svrs
        iniContnr();

        //============aop trans begn
        openSessionBgnTransact();

//        String f="C:\\0prj\\jvprj2025\\doc2504\\agt rechg cms ruler cfg.json";
//        String txt=readTxtFrmFil(f);
        QryFundDetailRqdto c=new QryFundDetailRqdto();
        c.uname="777";
        //  persistByHibernate(o, AppConfig.sessionFactory.getCurrentSession());
        System.out.println(encodeJson(new QryFundDetailHdl().handleRequest(c,null)));

        commitTsact();
    }


}

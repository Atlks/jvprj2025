package handler.cfg;

import cfg.MainStart;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import model.rpt_dataSmry.DataSummaryVo;
import model.cfg.CfgKv;

import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;

// static cfg.AppConfig.sessionFactory;
import static cfg.Containr.sessionFactory;
// static cfg.IniCfg.iniContnr;
import static cfg.MainStart.iniContnr;
import static util.misc.Util2025.encodeJson;
import static util.misc.Util2025.readTxtFrmFil;
import static util.tx.HbntUtil.mergex;
import static util.tx.TransactMng.commitTsact;
import static util.tx.TransactMng.openSessionBgnTransact;

/**
 *     /admin/cfg/SetCfgKv?k=rechargeCommissionRates&v={}
 */

@Path("/admin/cfg/SetCfgKv")
@PermitAll
public class SetCfg implements RequestHandler<CfgKv, ApiGatewayResponse> {
    /**
     * @param reqDto
     * @param context
     * @return
     * @throws Throwable
     */
    @Override
    public ApiGatewayResponse handleRequest(CfgKv reqDto, Context context) throws Throwable {
     //   reqDto.id="uniqID";
          mergex(reqDto, sessionFactory.getCurrentSession());
        return new ApiGatewayResponse(reqDto);
    }

    public static void main(String[] args) throws Throwable {
        new MainStart().sessionFactory();//ini sessFctr
        //ini contnr 4cfg,, svrs
        iniContnr();

        //============aop trans begn
        openSessionBgnTransact();

        String f="C:\\0prj\\jvprj2025\\doc2504\\agt rechg cms ruler cfg.json";
        String txt=readTxtFrmFil(f);
       // CfgKv c=new CfgKv("rechargeCommissionRates",txt);
        CfgKv c=new CfgKv("DataSummary",new DataSummaryVo());


        //  persistByHibernate(o, AppConfig.sessionFactory.getCurrentSession());
        System.out.println(encodeJson(new SetCfg().handleRequest(c,null)));

        commitTsact();
    }
}

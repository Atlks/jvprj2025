package handler.cfg;

import entityx.usr.NonDto;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import util.model.Context;
import model.agt.CmsLv;
import model.rpt_dataSmry.DataSummaryVo;
import model.cfg.CfgKv;
import model.cfg.MbrVipCfg;

import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;
import util.tx.findByIdExptn_CantFindData;

import java.util.ArrayList;
import java.util.List;

import static cfg.Containr.sessionFactory;
import static util.tx.HbntUtil.*;


@Path("/apiv1/cfg/IniDataHdl")
@PermitAll
public class IniDataHdl implements RequestHandler<NonDto, ApiGatewayResponse> {
    /**
     * @param param
     * @param context
     * @return
     * @throws Throwable
     */
    @Override
    public ApiGatewayResponse handleRequest(NonDto param, Context context) throws Throwable {

        try{
            findById(CfgKv.class,"DataSummary", sessionFactory.getCurrentSession());
        } catch (findByIdExptn_CantFindData e) {
            CfgKv c=new CfgKv("DataSummary",new DataSummaryVo());
            persist(c, sessionFactory.getCurrentSession());
        }


        try{//MbrVipCfg
            findById(CfgKv.class,"MbrVipCfg", sessionFactory.getCurrentSession());
        } catch (findByIdExptn_CantFindData e) {
            List<MbrVipCfg> li=new ArrayList<>();
            MbrVipCfg cfg=new MbrVipCfg();
            li.add(cfg);
            CfgKv c=new CfgKv("MbrVipCfg",li);
            persist(c, sessionFactory.getCurrentSession());
        }



        try{//MbrVipCfg
            findById(CfgKv.class,"CmsLvCfg", sessionFactory.getCurrentSession());
        } catch (findByIdExptn_CantFindData e) {
            List<CmsLv> li=new ArrayList<>();
            CmsLv cfg=new CmsLv();
            li.add(cfg);
            CfgKv c=new CfgKv("CmsLvCfg",li);
            persist(c, sessionFactory.getCurrentSession());
        }

        return  new ApiGatewayResponse("ok");
    }
}

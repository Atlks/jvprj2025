package handler.cfg;

import entityx.usr.NonDto;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import model.DataSummary;
import model.cfg.CfgKv;
import model.cfg.MbrVipCfg;
import org.springframework.web.bind.annotation.RestController;
import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;
import util.tx.findByIdExptn_CantFindData;

import java.util.ArrayList;
import java.util.List;

import static cfg.AppConfig.sessionFactory;
import static util.misc.Util2025.encodeJson;
import static util.tx.HbntUtil.*;

@RestController
@Path("/cfg/IniDataHdl")
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
            findByHerbinate(CfgKv.class,"DataSummary", sessionFactory.getCurrentSession());
        } catch (findByIdExptn_CantFindData e) {
            CfgKv c=new CfgKv("DataSummary",new DataSummary());
            persistByHibernate(c, sessionFactory.getCurrentSession());
        }


        try{//MbrVipCfg
            findByHerbinate(CfgKv.class,"MbrVipCfg", sessionFactory.getCurrentSession());
        } catch (findByIdExptn_CantFindData e) {
            List<MbrVipCfg> li=new ArrayList<>();
            MbrVipCfg cfg=new MbrVipCfg();
            li.add(cfg);
            CfgKv c=new CfgKv("MbrVipCfg",li);
            persistByHibernate(c, sessionFactory.getCurrentSession());
        }





        return  new ApiGatewayResponse("ok");
    }
}

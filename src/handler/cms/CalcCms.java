package handler.cms;

import entityx.usr.Usr;
import handler.agt.AgtRegSubSttSvs;
import handler.agt.getSuperiors;
import handler.ylwlt.dto.QueryDto;
import jakarta.validation.constraints.NotNull;
import model.OpenBankingOBIE.Transactions;
import model.agt.Agent;
import org.springframework.context.event.EventListener;
import util.tx.findByIdExptn_CantFindData;

import java.math.BigDecimal;
import java.util.List;

import static cfg.AppConfig.sessionFactory;
import static util.algo.CallUtil.lambdaInvoke;
import static util.tx.HbntUtil.findByHerbinate;


public class CalcCms {

    @EventListener(AftrCalcRchgAmtSumEvt.class)
    public Object handleRequest(@NotNull Transactions tx) throws Throwable {

        List<Usr> sups=lambdaInvoke(getSuperiors.class,new QueryDto(tx.uname));

       for(Usr u:sups)
       {
           Agent agt=findByHerbinate(Agent.class, u.uname, sessionFactory.getCurrentSession());
           BigDecimal cms=agt.commissionRate.multiply(tx.amount) ;
           agt.balanceCms= agt.balanceCms.add(cms);
       }
       // new AgtRegSubSttSvs().updateIndirectSubordinatesOnNewUser(u.id);
        return "ok";
    }
}

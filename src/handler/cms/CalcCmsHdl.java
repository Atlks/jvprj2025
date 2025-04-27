package handler.cms;

import entityx.usr.Usr;
import handler.agt.getSuperiors;
import handler.ylwlt.dto.QueryDto;
import jakarta.validation.constraints.NotNull;
import model.OpenBankingOBIE.Transactions;
import model.agt.Agent;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.List;

import static cfg.AppConfig.sessionFactory;
import static handler.cms.AftrCalcRchgAmtSumEvt.evtlist4aftCalcRchgAmtSum;
import static util.algo.CallUtil.lambdaInvoke;
import static util.evtdrv.EvtHlpr.publishEventV2;
import static util.evtdrv.EvtUtil.iniEvtHdrCtnr;
import static util.tx.HbntUtil.findByHerbinate;
import static util.tx.HbntUtil.mergeByHbnt;

@EventListener(AftrCalcRchgAmtSumEvt.class)
public class CalcCmsHdl {


    public static void main(String[] args) {
        iniEvtHdrCtnr();

        Transactions tx=new Transactions();
        publishEventV2(evtlist4aftCalcRchgAmtSum,tx);
    }


    public Object handleRequest(@NotNull Transactions tx) throws Throwable {

        List<Usr> sups=lambdaInvoke(getSuperiors.class,new QueryDto(tx.uname));
        Session session = sessionFactory.getCurrentSession();
       for(Usr u:sups)
       {

           Agent agt=findByHerbinate(Agent.class, u.uname, session);
           BigDecimal cms=agt.commissionRate.multiply(tx.amount) ;
           agt.balanceCms= agt.balanceCms.add(cms);
           mergeByHbnt(agt,session);

       }
       // new AgtRegSubSttSvs().updateIndirectSubordinatesOnNewUser(u.id);
        return "ok";
    }
}

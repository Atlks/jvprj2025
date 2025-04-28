package handler.agt;

import entityx.usr.Usr;
import handler.ylwlt.dto.QueryDto;
import jakarta.validation.constraints.NotNull;
import model.OpenBankingOBIE.Transactions;
import model.agt.Agent;
import model.agt.ChgSubStt;
import org.hibernate.Session;
import org.springframework.context.ApplicationEventPublisher;
import util.annos.EventListener;
import util.model.EvtType;
import util.tx.findByIdExptn_CantFindData;

import java.math.BigDecimal;
import java.util.List;

import static cfg.AppConfig.sessionFactory;
//import static cfg.Containr.evtlist4aftCalcRchgAmtSum;
//import static handler.agt.AgtHdl.addAgtIfNotExst;
import static handler.agt.RegEvtHdl.addAgtIfNotExst;
import static org.apache.commons.lang3.math.NumberUtils.toInt;
import static util.algo.CallUtil.lambdaInvoke;
import static util.algo.EncodeUtil.encodeSqlPrmAsStr;
import static util.algo.NullUtil.isBlank;
import static util.evtdrv.EventDispatcher.publishEvent;
import static util.model.EvtType.AftrCalcRchgAmtSumEvt;
import static util.tx.HbntUtil.*;
import static util.tx.HbntUtil.getSingleResult;

@EventListener({EvtType.RchgEvt})
public class RchgEvtHdl {

    public Object handleRequest(@NotNull Transactions tx) throws Throwable {


        try {
            String uid = tx.uname;
            Session session = sessionFactory.getCurrentSession();
            Usr u = findByHerbinate(Usr.class, uid, session);
            if (isBlank(u.invtr))
                return 0;
            Agent agt;

            agt = addAgtIfNotExst(u.invtr, session);


            try {
                ChgSubStt css = new ChgSubStt();
                css.agtName = u.invtr;
                css.uname = u.uname;
                persistByHibernate(css, session);

            } catch (Throwable e) {

            }

            agt.rechargeMemberCount = toInt((String) getSingleResult("select count(*) from ChgSubStt where agtName= " + encodeSqlPrmAsStr(u.invtr), 0, session));


             lambdaInvoke(UpdtChainRchgAmtSumSttHdl.class,tx);

            BigDecimal thisCms = agt.commissionRate.multiply(tx.getAmount());
            agt.commissionAmount = agt.commissionAmount.add(thisCms);

            mergeByHbnt(agt, session);


        } catch (Throwable e) {
            e.printStackTrace();
            //  throw  e;  //for test
        }
       // updtRchgAmtSumForeachLev(tx);
        publishEvent(AftrCalcRchgAmtSumEvt,tx);
        return 0;
    }

}

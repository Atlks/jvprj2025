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
public class UpdtSubRchgAmtSumSttSvs {

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


            new UpdtSubRchgAmtSumSttSvs().updtRchgAmtSumForeachLev(tx, agt, u, session);


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
    /**
     * 更新所有充值总额按照层级
     * @param tx
     * @param agt
     * @param u
     * @param session
     * @throws Throwable
     */
    public   void updtRchgAmtSumForeachLev(Transactions tx, Agent agt, Usr u, Session session) throws Throwable {
        //更新直属下级充值总额
        //这个要算所有级别的   直属下级充值总额
        agt.rechargeAmount= agt.rechargeAmount.add(tx.getAmount());
        agt.levelOneRechargeAmount= agt.levelOneRechargeAmount.add(tx.getAmount());
        agt.subLevelRechargeAmount= agt.subLevelRechargeAmount.add(tx.getAmount());
        agt.  drctlSubRchgAmtSum=agt. drctlSubRchgAmtSum.add(tx.getAmount());

        //更新非直属下级充值总额
        updateIndirectSubdntRchgAmtOnNewUser(u.uname);

        //updt all agt totalRechargeAmount
        updateAllSupRchgAmt(tx, u, session);


        ApplicationEventPublisher eventPublisher;
        // 发布事件publishEvent(Object event)
        //   publishEvent(evtlist4aftCalcRchgAmtSum,tx);
    }


    //更新所有下级充值总额
    private   void updateAllSupRchgAmt(Transactions tx, Usr u, Session session) throws Throwable {
        List<Usr> agtIds=lambdaInvoke(getSuperiors.class,new QueryDto(u.uname));
        for(Usr uTmp:agtIds){
            Agent agtTmp=findByHerbinate(Agent.class,uTmp.uname, session);
            agtTmp.totalRechargeAmount=agtTmp.totalRechargeAmount.add(tx.getAmount());
        }
    }

    // 核心功能： ，更新所有非直属上级的间接下属充值
    public void updateIndirectSubdntRchgAmtOnNewUser( String newRechgUserId) throws Exception, findByIdExptn_CantFindData {

        var session=sessionFactory.getCurrentSession();
        List<Usr> superiors = new getNonDirectSuperiors().handleRequest(newRechgUserId);
        for (Usr superior : superiors) {
            Agent agt=findByHerbinate(Agent.class, superior.id, session);
            agt.levelOneRechargeAmount =agt.levelOneRechargeAmount.add(BigDecimal.valueOf(1)) ;
            agt.indrctlSubRchgAmtSum =agt.indrctlSubRchgAmtSum.add(BigDecimal.valueOf(1)) ;

            mergeByHbnt(agt, session);
        }
    }


}

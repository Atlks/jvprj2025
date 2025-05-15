package handler.agt;

import model.usr.Usr;
import handler.ivstAcc.dto.QueryDto;
import model.OpenBankingOBIE.Transaction;
import model.agt.Agent;
import org.hibernate.Session;

import util.tx.findByIdExptn_CantFindData;

import java.math.BigDecimal;
import java.util.List;

import static cfg.Containr.sessionFactory;
import static util.algo.CallUtil.lambdaInvoke;
import static util.tx.HbntUtil.findById;
import static util.tx.HbntUtil.mergeByHbnt;

public class UpdtChainRchgAmtSumSttHdl {


    /**
     * 更新所有充值总额按照层级
     * @param tx

     * @throws Throwable
     */
    public   void handleRequest(Transaction tx ) throws Throwable {

        Session session=sessionFactory.getCurrentSession();
        Usr u= findById(Usr.class,tx.accountOwner,session);
        updtChainDrktlSubRchgAmtSum(tx, u, session);


        //更新非直属下级充值总额
        updateIndirectSubdntRchgAmtSumOnNewUser(u.uname);

        //updt all agt totalRechargeAmount
        updateChainAllSubRchgAmtSum(tx, u, session);


       // ApplicationEventPublisher eventPublisher;
        // 发布事件publishEvent(Object event)
        //   publishEvent(evtlist4aftCalcRchgAmtSum,tx);
    }

    private static void updtChainDrktlSubRchgAmtSum(Transaction tx, Usr u, Session session) throws findByIdExptn_CantFindData {
        Agent agt= findById(Agent.class, u.invtr, session);

        //更新直属下级充值总额
        //这个要算所有级别的   直属下级充值总额
        agt.rechargeAmount= agt.rechargeAmount.add(tx.getAmount());
        agt.levelOneRechargeAmount= agt.levelOneRechargeAmount.add(tx.getAmount());
        agt.subLevelRechargeAmount= agt.subLevelRechargeAmount.add(tx.getAmount());
        agt.  drctlSubRchgAmtSum=agt. drctlSubRchgAmtSum.add(tx.getAmount());
        mergeByHbnt(agt, session);
    }


    //更新所有下级充值总额
    private   void updateChainAllSubRchgAmtSum(Transaction tx, Usr u, Session session) throws Throwable {
        List<Usr> agtIds=lambdaInvoke(getSuperiors.class,new QueryDto(u.uname));
        for(Usr uTmp:agtIds){
            Agent agtTmp= findById(Agent.class,uTmp.uname, session);
            agtTmp.totalRechargeAmount=agtTmp.totalRechargeAmount.add(tx.getAmount());
        }
    }

    // 核心功能： ，更新所有非直属上级的间接下属充值
    public void updateIndirectSubdntRchgAmtSumOnNewUser(String newRechgUserId) throws Exception, findByIdExptn_CantFindData {

        var session=sessionFactory.getCurrentSession();
        List<Usr> superiors = new getNonDirectSuperiors().handleRequest(newRechgUserId);
        for (Usr superior : superiors) {
            Agent agt= findById(Agent.class, superior.id, session);
            agt.levelOneRechargeAmount =agt.levelOneRechargeAmount.add(BigDecimal.valueOf(1)) ;
            agt.indrctlSubRchgAmtSum =agt.indrctlSubRchgAmtSum.add(BigDecimal.valueOf(1)) ;

            mergeByHbnt(agt, session);
        }
    }


}

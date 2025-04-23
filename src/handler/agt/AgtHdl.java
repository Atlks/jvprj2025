package handler.agt;

import entityx.usr.Usr;
import jakarta.validation.constraints.NotNull;
import model.OpenBankingOBIE.Transactions;
import model.agt.Agent;

import org.hibernate.Session;
import util.tx.findByIdExptn_CantFindData;

import java.math.BigDecimal;

import static cfg.AppConfig.sessionFactory;
import static util.algo.NullUtil.isBlank;
import static util.tx.HbntUtil.*;

public class AgtHdl {


    public void rchgEvtHdl(@NotNull Transactions tx) {
        try {
            String uid = tx.accountId;
            Session session = sessionFactory.getCurrentSession();
            Usr u = findByHerbinate(Usr.class, uid, session);
            if (isBlank(u.invtr))
                return;
            Agent agt;

            agt = addAgtIfNotExst(u.invtr, session);

            agt.rechargeMemberCount = agt.rechargeMemberCount + 1;
            mergeByHbnt(agt, session);

            //这个要算所有级别的
            agt.rechargeAmount=agt.rechargeAmount.add(tx.getAmount());
            agt.levelOneRechargeAmount=agt.levelOneRechargeAmount.add(tx.getAmount());

            agt.subLevelRechargeAmount=agt.subLevelRechargeAmount.add(tx.getAmount());

            agt.totalRechargeAmount=agt.totalRechargeAmount.add(tx.getAmount());


            BigDecimal thisCms=agt.commissionRate.multiply(tx.getAmount()) ;
            agt.commissionAmount=agt.commissionAmount.add(thisCms);






        } catch (Throwable e) {
            e.printStackTrace();
            //  throw  e;  //for test
        }

    }

    public void regEvtHdl(@NotNull Usr u) {

        if (isBlank(u.invtr))
            return;

        try {
            Agent agt;
            Session session = sessionFactory.getCurrentSession();
            agt = addAgtIfNotExst(u.invtr, session);

            agt.registeredMemberCount = agt.registeredMemberCount + 1;
            mergeByHbnt(agt, session);
        } catch (Exception e) {
            e.printStackTrace();
            //  throw  e;  //for test
        }


    }

    @org.jetbrains.annotations.NotNull
    private static Agent addAgtIfNotExst(String agtName, Session session) {
        Agent agt;
        try {
            agt = findByHerbinate(Agent.class, agtName, session);
        } catch (findByIdExptn_CantFindData e) {
            agt = new Agent(agtName);
            persistByHibernate(agt, session);
        }
        return agt;
    }


//    private boolean isBlank(String invtr) {
//      return  isBlank()
//    }
}

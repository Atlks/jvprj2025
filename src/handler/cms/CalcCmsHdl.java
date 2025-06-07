package handler.cms;


import model.usr.Usr;
import handler.agt.getSuperiors;
import handler.ivstAcc.dto.QueryDto;
import jakarta.validation.constraints.NotNull;
import model.OpenBankingOBIE.Transaction;
import model.agt.Agent;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.List;

import static cfg.Containr.sessionFactory;
import static util.algo.CallUtil.lambdaInvoke;
import static util.evtdrv.EventDispatcher.publishEvent;
import static util.evtdrv.EvtUtil.iniEvtHdrCtnr;
import static util.model.EvtType.AftrCalcRchgAmtSumEvt;
import static util.tx.HbntUtil.findById;
import static util.tx.HbntUtil.mergex;

//@EventListener({AftrCalcRchgAmtSumEvt})
public class CalcCmsHdl {


    public static void main(String[] args) throws Throwable {
        iniEvtHdrCtnr();

        Transaction tx = new Transaction();
        publishEvent(AftrCalcRchgAmtSumEvt, tx);

    }


    public Object handleRequest(@NotNull Transaction tx) throws Throwable {

        try{
            //  sups from low Sup 2 hi sups
            List<Usr> sups = lambdaInvoke(getSuperiors.class, new QueryDto(tx.owner));
            Session session = sessionFactory.getCurrentSession();
            Agent curSub = new Agent();
            curSub.commissionRate= BigDecimal.valueOf(1);
            for (Usr sup_usr : sups) {
                try{
                    Agent agt = findById(Agent.class, sup_usr.uname, session);
                    BigDecimal curRate=agt.commissionRate.multiply(curSub.commissionRate)   ;


                    BigDecimal cms = curRate.multiply(tx.amount);
                    agt.interimAvailableBalance = agt.interimAvailableBalance.add(cms);
                    mergex(agt, session);
                    curSub=agt;
                } catch (Exception e) {
                    System.out.println("---catch");
                    e.printStackTrace();
                }



            }
            // new AgtRegSubSttSvs().updateIndirectSubordinatesOnNewUser(u.id);

        } catch (Exception e) {
            e.printStackTrace();
           // throw new RuntimeException(e);
        }
        return "";
    }
}

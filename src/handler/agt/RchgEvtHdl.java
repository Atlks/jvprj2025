package handler.agt;

import model.usr.Usr;
import handler.cms.CalcCmsHdl;
import jakarta.validation.constraints.NotNull;
import model.OpenBankingOBIE.Transaction;
import model.agt.Agent;
import model.agt.ChgSubStt;
import org.hibernate.Session;
import util.annos.EventListener;
import util.model.EvtType;

import java.math.BigDecimal;

import static cfg.Containr.sessionFactory;
//import static cfg.Containr.evtlist4aftCalcRchgAmtSum;
//import static handler.agt.AgtHdl.addAgtIfNotExst;
import static handler.agt.RegEvtHdl.addAgtIfNotExst;
import static handler.usrStt.UamtSttSvs.updtUsrRpt4rechg;
import static org.apache.commons.lang3.math.NumberUtils.toInt;
import static util.algo.CallUtil.*;
import static util.algo.EncodeUtil.encodeSqlPrmAsStr;
import static util.algo.NullUtil.isBlank;
import static util.tx.HbntUtil.*;
import static util.tx.HbntUtil.getSingleResult;

@EventListener({EvtType.RchgEvt})
public class RchgEvtHdl {

    public Object handleRequest(@NotNull Transaction tx) throws Throwable {


        try {
            String uid = tx.owner;
            Session session = sessionFactory.getCurrentSession();
            Usr u = findById(Usr.class, uid, session);
            if (isBlank(u.invtr))
                return 0;
            Agent agt;

            agt = addAgtIfNotExst(u.invtr, session);


            callTry(()->{updtUsrRpt4rechg(tx);});

            try {
                ChgSubStt css = new ChgSubStt();
                css.agtName = u.invtr;
                css.uname = u.uname;
                persist(css, session);

            } catch (Throwable e) {

            }

            agt.rechargeMemberCount = toInt((String) getSingleResult("select count(*) from ChgSubStt where agtName= " + encodeSqlPrmAsStr(u.invtr), 0, session));

            BigDecimal thisCms = agt.commissionRate.multiply(tx.getAmount());
            agt.commissionAmount = agt.commissionAmount.add(thisCms);

            mergex(agt, session);



             lambdaInvoke(UpdtChainRchgAmtSumSttHdl.class,tx);


             lmdIvk(CalcCmsHdl.class,tx);
             //calc cms
           // publishEvent(AftrCalcRchgAmtSumEvt, tx);


        } catch (Throwable e) {
            e.printStackTrace();
            //  throw  e;  //for test
        }

        return 0;
    }




}

package handler.agt;

import model.usr.Usr;
import handler.cms.CalcCmsHdl;
import jakarta.validation.constraints.NotNull;
import model.OpenBankingOBIE.Transaction;
import model.agt.Agent;
import model.agt.ChgSubStt;
import org.hibernate.Session;
import util.Oosql.SqlBldr;
import util.annos.EventListener;
import util.model.EvtType;

import java.math.BigDecimal;

import static cfg.Containr.sessionFactory;
//import static cfg.Containr.evtlist4aftCalcRchgAmtSum;
//import static handler.agt.AgtHdl.addAgtIfNotExst;
import static handler.agt.RegEvtHdl.addAgtIfNotExst;
import static handler.usrStt.UamtSttSvs.updtUsrRpt4rechg;
import static org.apache.commons.lang3.math.NumberUtils.toInt;
import static util.Oosql.SqlBldr.where;
import static util.algo.CallUtil.*;
import static util.algo.EncodeUtil.encodeSqlPrmAsStr;
import static util.algo.NullUtil.isBlank;
import static util.misc.util2026.sleep;
import static util.tx.HbntUtil.*;
import static util.tx.HbntUtil.getSingleResult;

//@EventListener({EvtType.RchgEvt})
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
//
//            try {
//                ChgSubStt fdstt=findById(ChgSubStt.class,  u.uname, session);
//                ChgSubStt css = new ChgSubStt();
//                css.agtName = u.invtr;
//                css.uname = u.uname;
//                persist(css, session);
//
//            } catch (Throwable e) {
//
//            }

            //encodeSqlPrmAsStr
            String sql = "select count(*) " + SqlBldr.from(ChgSubStt.class) + where(" agtName", "= ", u.invtr);
            agt.rechargeMemberCount = getSingleResult(sql, Long.class);

            BigDecimal thisCms = agt.commissionRate.multiply(tx.getAmount());
            agt.commissionAmount = agt.commissionAmount.add(thisCms);

            try{
                mergex(agt, session);
            }catch(Exception e){
                System.out.println("----cata150");
                e.printStackTrace();
                System.out.println("----end cata150");
            }




             lambdaInvoke(UpdtChainRchgAmtSumSttHdl.class,tx);


             lmdIvk(CalcCmsHdl.class,tx);
             //calc cms
           // publishEvent(AftrCalcRchgAmtSumEvt, tx);


        } catch (Throwable e) {
            System.out.println("----catch...rchg evt");
            e.printStackTrace();
            sleep(20);
            System.out.println("----end catch...rchg evt");
            //  throw  e;  //for test
        }

        return 0;
    }




}

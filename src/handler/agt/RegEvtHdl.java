package handler.agt;

import model.OpenBankingOBIE.Account;
import model.OpenBankingOBIE.AccountSubType;
import model.usr.Usr;
import jakarta.validation.constraints.NotNull;
import model.agt.Agent;

import org.hibernate.Session;
import util.annos.EventListener;
import util.model.EvtType;
import util.tx.HbntUtil;
import util.tx.findByIdExptn_CantFindData;

// static cfg.AppConfig.sessionFactory;
import static cfg.Containr.sessionFactory;
import static org.apache.commons.lang3.math.NumberUtils.toInt;
import static util.acc.AccUti.getAccid;
import static util.algo.CallUtil.lambdaInvoke;
import static util.algo.NullUtil.isBlank;
import static util.misc.Util2025.ret2025;
import static util.tx.HbntUtil.*;

@EventListener({EvtType.RegEvt})
public class RegEvtHdl {

    public void handleRequest(@NotNull Usr u) {

        if (isBlank(u.invtr))
            return;

        try {
            Agent agt;
            Session session = sessionFactory.getCurrentSession();
            agt = addAgtIfNotExst(u.invtr, session);
            addAgtCmsAccIfNotExst(u.invtr, session);

            //直属下级数
           // agt.drctSub_registeredMemberCount = agt.drctSub_registeredMemberCount + 1;
           // mergeByHbnt(agt, session);

            lambdaInvoke(UpdtSubCntHdl.class, u);
            // new UpdtSubCntHdl().updtSubCnt(u, session);
        } catch (Exception e) {
            e.printStackTrace();
            //  throw  e;  //for test
        } catch (Throwable e) {
            e.printStackTrace();
        }


    }
    public static Account addAgtCmsAccIfNotExst(String agtName)  {
        if(isBlank(agtName))
            return null;
        return  addAgtCmsAccIfNotExst(agtName, HbntUtil.sessionFactory.getCurrentSession());

    }
    @org.jetbrains.annotations.NotNull
    public static Account addAgtCmsAccIfNotExst(String agtName, Session session)  {
        Account agtCmsAcc;
        String accid=getAccid(agtName, AccountSubType.agtCms);
        try {

            agtCmsAcc = findById(Account.class, accid, session);
        } catch (findByIdExptn_CantFindData e) {
            agtCmsAcc = new Account(accid);
            agtCmsAcc.setOwner(agtName);
            agtCmsAcc.setAccountSubTypeEnum(AccountSubType.agtCms);
            persist(agtCmsAcc, session);
        }
        return agtCmsAcc;
    }
    @org.jetbrains.annotations.NotNull
    static Agent addAgtIfNotExst(String agtName, Session session) throws findByIdExptn_CantFindData {
        Agent agt;
        try {
            agt = findById(Agent.class, agtName, session);
        } catch (findByIdExptn_CantFindData e) {
            agt = new Agent(agtName);
            Usr u=findById(Usr.class,agtName);
            agt.parent_agent_id=u.getInvtr();
            persist(agt, session);
        }
        return agt;
    }


//    private boolean isBlank(String invtr) {
//      return  isBlank()
//    }
}

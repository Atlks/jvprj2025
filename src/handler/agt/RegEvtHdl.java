package handler.agt;

import model.usr.Usr;
import jakarta.validation.constraints.NotNull;
import model.agt.Agent;

import org.hibernate.Session;
import util.annos.EventListener;
import util.model.EvtType;
import util.tx.findByIdExptn_CantFindData;

// static cfg.AppConfig.sessionFactory;
import static cfg.Containr.sessionFactory;
import static org.apache.commons.lang3.math.NumberUtils.toInt;
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

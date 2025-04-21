package handler.agt;

import entityx.usr.Usr;
import jakarta.validation.constraints.NotNull;
import model.agt.Agent;
import org.hibernate.Session;
import util.tx.findByIdExptn_CantFindData;

import static cfg.AppConfig.sessionFactory;
import static util.algo.NullUtil.isBlank;
import static util.tx.HbntUtil.*;

public class AgtHdl {


    public void regEvtHdl(@NotNull Usr u)   {

        if (isBlank(u.invtr))
            return;

        try {
            Agent agt;
            Session session = sessionFactory.getCurrentSession();
            try {
                agt = findByHerbinate(Agent.class, u.invtr, session);
            } catch (findByIdExptn_CantFindData e) {
                agt = new Agent(u.invtr);
                persistByHibernate(agt, session);

            }

            agt.registeredMemberCount = agt.registeredMemberCount + 1;
            mergeByHbnt(agt, session);
        } catch (Exception e) {
            e.printStackTrace();
          //  throw  e;  //for test
        }


    }

//    private boolean isBlank(String invtr) {
//      return  isBlank()
//    }
}

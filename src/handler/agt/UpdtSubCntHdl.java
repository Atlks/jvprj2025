package handler.agt;

import model.usr.Usr;
import handler.ivstAcc.dto.QueryDto;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.constraints.NotNull;
import model.agt.Agent;
import org.hibernate.Session;
import util.tx.findByIdExptn_CantFindData;

import java.util.List;

import static cfg.Containr.sessionFactory;
import static util.algo.CallUtil.lambdaInvoke;
import static util.tx.HbntUtil.findById;
import static util.tx.HbntUtil.mergex;

/**
 * drktlSub  indrkSub  allSub
 */
@PermitAll
//@EventListener({EvtType.RegEvt})
public class UpdtSubCntHdl {

    public Object handleRequest(@NotNull Usr u) throws Throwable {

        try{
            updtChainDrctSubCnt(u);

            updateChainIndrctSubdntCntOnNewUser(u.uname);

            //drktl sub reg cnt already up
            var session = sessionFactory.getCurrentSession();

            updateChainAllSubCnt(u, session);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //    new AgtRegSubSttSvs().updateIndirectSubordinatesOnNewUser(u.id);
        return 0;
    }

    private void updtChainDrctSubCnt(@NotNull Usr u) throws findByIdExptn_CantFindData {
        var session = sessionFactory.getCurrentSession();
        Agent sup = findById(Agent.class, u.invtr, session);
        sup.drctSub_registeredMemberCount += 1;
        mergex(sup, session);
    }


    // 核心功能：新用户注册，更新所有非直属上级的间接下属计数
    public void updateChainIndrctSubdntCntOnNewUser(String newUserId) throws Exception, findByIdExptn_CantFindData {

        var session = sessionFactory.getCurrentSession();
        List<Usr> superiors = new getNonDirectSuperiors().handleRequest(newUserId);
        for (Usr superior : superiors) {
            Agent agt = findById(Agent.class, superior.id, session);
            agt.indirectSubCount += 1;
            mergex(agt, session);
        }
    }


    //更新所有下级注册人数
    void updateChainAllSubCnt(Usr u, Session session) throws Throwable {
        List<Usr> agtIds = lambdaInvoke(getSuperiors.class, new QueryDto(u.uname));
        for (Usr uTmp : agtIds) {
            Agent agtTmp = findById(Agent.class, uTmp.uname, session);
            agtTmp.allMmbrCnt = agtTmp.allMmbrCnt + (1);
        }
    }


}

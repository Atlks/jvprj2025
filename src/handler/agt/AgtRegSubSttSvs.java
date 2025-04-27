package handler.agt;

import java.math.BigDecimal;
import java.util.List;

import entityx.usr.Usr;
import handler.ylwlt.dto.QueryDto;
import model.OpenBankingOBIE.Transactions;
import model.agt.Agent;
import org.hibernate.Session;
import util.tx.findByIdExptn_CantFindData;

import static util.algo.CallUtil.lambdaInvoke;
import static util.tx.HbntUtil.findByHerbinate;
import static util.tx.HbntUtil.mergeByHbnt;

import static cfg.AppConfig.sessionFactory;

public class AgtRegSubSttSvs {


    /**
     * drktlSub  indrkSub  allSub

     * @param u
     * @param session
     */
    public  void updtSubCnt(  Usr u, Session session) throws Throwable {

        //drktl sub reg cnt already up
        new AgtRegSubSttSvs().updateAllSupRchgAmt(u,session);
        updateIndirectSubordinatesOnNewUser(u.uname);
    }

          // 核心功能：新用户注册，更新所有非直属上级的间接下属计数
    public void updateIndirectSubordinatesOnNewUser( String newUserId) throws Exception, findByIdExptn_CantFindData {

        var session=sessionFactory.getCurrentSession();
        List<Usr> superiors = new getNonDirectSuperiors().handleRequest(newUserId);
        for (Usr superior : superiors) {
            Agent agt=findByHerbinate(Agent.class, superior.id, session);
            agt.indirectSubCount += 1;
            mergeByHbnt(agt, session);
        }
    }



    //更新所有下级注册人数
    private   void updateAllSupRchgAmt(  Usr u, Session session) throws Throwable {
        List<Usr> agtIds=lambdaInvoke(getSuperiors.class,new QueryDto(u.uname));
        for(Usr uTmp:agtIds){
            Agent agtTmp=findByHerbinate(Agent.class,uTmp.uname, session);
            agtTmp.allMmbrCnt=agtTmp.allMmbrCnt+(1);
        }
    }



}

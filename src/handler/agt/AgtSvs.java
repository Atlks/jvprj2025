package handler.agt;

import java.math.BigDecimal;
import java.util.List;

import entityx.usr.Usr;
import model.agt.Agent;
import util.tx.findByIdExptn_CantFindData;
import static util.tx.HbntUtil.findByHerbinate;
import static util.tx.HbntUtil.mergeByHbnt;

import java.util.List;
import static cfg.AppConfig.sessionFactory;
import static util.tx.HbntUtil.findByHerbinate;

public class AgtSvs {


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


    // 核心功能：新用户注册，更新所有非直属上级的间接下属计数
    public void updateIndirectSubdntRchgAmtOnNewUser( String newRechgUserId) throws Exception, findByIdExptn_CantFindData {

        var session=sessionFactory.getCurrentSession();
        List<Usr> superiors = new getNonDirectSuperiors().handleRequest(newRechgUserId);
        for (Usr superior : superiors) {
            Agent agt=findByHerbinate(Agent.class, superior.id, session);
            agt.levelOneRechargeAmount =agt.levelOneRechargeAmount.add(BigDecimal.valueOf(1)) ;
            mergeByHbnt(agt, session);
        }
    }


}

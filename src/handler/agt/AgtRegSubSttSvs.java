package handler.agt;

import java.util.List;

import entityx.usr.Usr;
import handler.ylwlt.dto.QueryDto;
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
//    public  void updtSubCnt(  Usr u, Session session) throws Throwable {
//
//        //drktl sub reg cnt already up
//        new AgtRegSubSttSvs().updateAllSupCnt(u,session);
//        updateIndirectSubordinatesOnNewUser(u.uname);
//    }






}

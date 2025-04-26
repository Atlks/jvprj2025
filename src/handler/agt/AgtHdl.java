package handler.agt;

import entityx.usr.Usr;
import handler.ylwlt.dto.QueryDto;
import jakarta.validation.constraints.NotNull;
import model.OpenBankingOBIE.Transactions;
import model.agt.Agent;
import model.agt.ChgSubStt;

import org.hibernate.Session;
import util.tx.findByIdExptn_CantFindData;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;

import static cfg.AppConfig.sessionFactory;
import static org.apache.commons.lang3.math.NumberUtils.toInt;
import static util.algo.CallUtil.lambdaInvoke;
import static util.algo.EncodeUtil.encodeSqlPrmAsStr;
import static util.algo.GetUti.getMethod;
import static util.algo.NullUtil.isBlank;
import static util.misc.Util2025.ret2025;
import static util.tx.HbntUtil.*;

public class AgtHdl {


    public void rchgEvtHdl(@NotNull Transactions tx) {
        try {
            String uid = tx.uname;
            Session session = sessionFactory.getCurrentSession();
            Usr u = findByHerbinate(Usr.class, uid, session);
            if (isBlank(u.invtr))
                return;
            Agent agt;

            agt = addAgtIfNotExst(u.invtr, session);


            try{
                ChgSubStt css=new ChgSubStt();
                css.agtName=u.invtr;
                css.uname=u.uname;
                persistByHibernate(css, session);
    
            }catch(Throwable e)
            {

            }
           
             agt.rechargeMemberCount =toInt((String) getSingleResult("select count(*) from ChgSubStt where agtName= "+encodeSqlPrmAsStr(u.invtr),0,session )) ;


            //这个要算所有级别的
            agt.rechargeAmount=agt.rechargeAmount.add(tx.getAmount());
            agt.levelOneRechargeAmount=agt.levelOneRechargeAmount.add(tx.getAmount());

            agt.subLevelRechargeAmount=agt.subLevelRechargeAmount.add(tx.getAmount());

            //updt all agt totalRechargeAmount
            List<Usr> agtIds= (List<Usr>) lambdaInvoke(getSuperiors.class,new QueryDto(u.uname));
            for(Usr uTmp:agtIds){
                Agent agtTmp=findByHerbinate(Agent.class,uTmp.uname,session);
                agtTmp.totalRechargeAmount=agtTmp.totalRechargeAmount.add(tx.getAmount());
            }



            BigDecimal thisCms=agt.commissionRate.multiply(tx.getAmount()) ;
            agt.commissionAmount=agt.commissionAmount.add(thisCms);

            mergeByHbnt(agt, session);




        } catch (Throwable e) {
            e.printStackTrace();
            //  throw  e;  //for test
        }

    }



    public void regEvtHdl(@NotNull Usr u) {

        if (isBlank(u.invtr))
            return;

        try {
            Agent agt;
            Session session = sessionFactory.getCurrentSession();
            agt = addAgtIfNotExst(u.invtr, session);

            agt.registeredMemberCount = agt.registeredMemberCount + 1;
            mergeByHbnt(agt, session);
        } catch (Exception e) {
            e.printStackTrace();
            //  throw  e;  //for test
        }


    }

    @org.jetbrains.annotations.NotNull
    private static Agent addAgtIfNotExst(String agtName, Session session) {
        Agent agt;
        try {
            agt = findByHerbinate(Agent.class, agtName, session);
        } catch (findByIdExptn_CantFindData e) {
            agt = new Agent(agtName);
            persistByHibernate(agt, session);
        }
        return agt;
    }


//    private boolean isBlank(String invtr) {
//      return  isBlank()
//    }
}

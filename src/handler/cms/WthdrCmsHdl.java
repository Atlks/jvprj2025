package handler.cms;

import jakarta.validation.constraints.NotNull;
import model.OpenBankingOBIE.Accounts;
import model.OpenBankingOBIE.CreditDebitIndicator;
import model.OpenBankingOBIE.Transactions;
import model.agt.Agent;
import org.hibernate.Session;
import org.springframework.context.event.EventListener;

import static cfg.AppConfig.sessionFactory;
import static util.acc.AccUti.getAccId4ylwlt;
import static util.tx.HbntUtil.*;

public class WthdrCmsHdl {
@EventListener
    public Object handleRequest(@NotNull WthdCmsRqdto dto) throws Throwable {


        String agtAccId=dto.uname+"_agt";
        Session session = sessionFactory.getCurrentSession();
        Accounts agtAcc=
                findByHerbinate(Accounts.class, agtAccId, session);
        mergeByHbnt(agtAcc,session);



        String accId4ylwlt = getAccId4ylwlt(dto.uname);
        Accounts agtAccYlwlt=  findByHerbinate(Accounts.class ,accId4ylwlt, session);

        agtAcc.availableBalance= agtAcc.availableBalance.subtract(dto.amt);
        agtAccYlwlt.availableBalance= agtAccYlwlt.availableBalance.add(dto.amt);
        mergeByHbnt(agtAccYlwlt,session);

        Transactions tx=new Transactions(accId4ylwlt,dto.uname, CreditDebitIndicator.CREDIT,dto.amt);
        persistByHibernate(tx,session);
        return  "ok";

    }

}
package handler.cms;

import jakarta.validation.constraints.NotNull;
import model.OpenBankingOBIE.Account;
import model.OpenBankingOBIE.CreditDebitIndicator;
import model.OpenBankingOBIE.Transaction;
import org.hibernate.Session;


import static cfg.Containr.sessionFactory;
import static util.acc.AccUti.getAccId4ylwlt;
import static util.tx.HbntUtil.*;

public class WthdrCmsHdl {
//@EventListener
    public Object handleRequest(@NotNull WthdCmsRqdto dto) throws Throwable {


        String agtAccId=dto.uname+"_agt";
        Session session = sessionFactory.getCurrentSession();
        Account agtAcc=
                findByHerbinate(Account.class, agtAccId, session);
        mergeByHbnt(agtAcc,session);



        String accId4ylwlt = getAccId4ylwlt(dto.uname);
        Account agtAccYlwlt=  findByHerbinate(Account.class ,accId4ylwlt, session);

        agtAcc.interim_Available_Balance = agtAcc.interim_Available_Balance.subtract(dto.amt);
        agtAccYlwlt.interim_Available_Balance = agtAccYlwlt.interim_Available_Balance.add(dto.amt);
        mergeByHbnt(agtAccYlwlt,session);

        Transaction tx=new Transaction(accId4ylwlt,dto.uname, CreditDebitIndicator.CREDIT,dto.amt);
        persistByHibernate(tx,session);
        return  "ok";

    }

}
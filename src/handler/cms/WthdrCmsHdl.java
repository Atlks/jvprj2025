package handler.cms;

import jakarta.validation.constraints.NotNull;
import model.OpenBankingOBIE.*;
import org.hibernate.Session;


import static cfg.Containr.sessionFactory;
import static util.acc.AccUti.getAccId;
import static util.acc.AccUti.getAccId4ylwlt;
import static util.tx.HbntUtil.*;

//   cms/WthdrCmsHdl
public class WthdrCmsHdl {
//@EventListener
    public Object handleRequest(@NotNull WthdCmsRqdto dto) throws Throwable {




        Session session = sessionFactory.getCurrentSession();




        //---add to ylwlt
        String accId4ylwlt = getAccId4ylwlt(dto.uname);
        Account agtAccYlwlt=  findByHerbinate(Account.class ,accId4ylwlt, session);


        agtAccYlwlt.interim_Available_Balance = agtAccYlwlt.interim_Available_Balance.add(dto.amt);
        mergeByHbnt(agtAccYlwlt,session);

        Transaction tx=new Transaction(accId4ylwlt,dto.uname, CreditDebitIndicator.CREDIT,dto.amt);
        tx.transactionCode= TransactionCode.Service_Cms_rechgCms.name();
        persistByHibernate(tx,session);



        //redis agt cms acc
        String agtAccId=getAccId(AccountSubType.agtCms.name(), dto.uname);
        Account agtAcc=
                findByHerbinate(Account.class, agtAccId, session);
        agtAcc.interim_Available_Balance = agtAcc.interim_Available_Balance.subtract(dto.amt);

        mergeByHbnt(agtAcc,session);
        Transaction tx2=new Transaction(agtAccId,dto.uname, CreditDebitIndicator.DEBIT,dto.amt);
        tx2.transactionCode= TransactionCode.Service_Cms_rechgCms.name();
        persistByHibernate(tx2,session);


        return  "ok";

    }

}
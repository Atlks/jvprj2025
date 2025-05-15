package handler.accStat;

import model.usr.Usr;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import model.OpenBankingOBIE.Account;
import model.OpenBankingOBIE.AccountSubType;
import model.OpenBankingOBIE.Balance;
import org.hibernate.Session;
import util.annos.EventListener;
import util.model.EvtType;
import util.tx.findByIdExptn_CantFindData;

import static cfg.Containr.sessionFactory;
import static util.acc.AccUti.getAccId;
import static util.algo.CallUtil.callTry;
import static util.tx.HbntUtil.findById;
import static util.tx.HbntUtil.persistByHibernate;

@EventListener({EvtType.LoginEvt,EvtType.RegEvt})
public class NewStatAcc {

    public void handleRequest(@NotNull Usr u) {
        callTry(()->{
            addUsrStatAccIfNotExist(u.uname);
        });


    }

    private void addUsrStatAccIfNotExist(@NotBlank @Valid String uname) {

        Session session = sessionFactory.getCurrentSession();

        String accid=getAccId(String.valueOf(AccountSubType.usrExt),uname) ;

        try {
            Account a= findById(Account.class,accid,session);
        } catch (findByIdExptn_CantFindData e) {
            Account acc = new Account(accid);
            Balance bls=new Balance(accid);
            persistByHibernate(acc,session);
            persistByHibernate(bls,session);
        }




    }
}

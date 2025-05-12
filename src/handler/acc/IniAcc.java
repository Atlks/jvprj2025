package handler.acc;

import model.OpenBankingOBIE.Account;
import model.OpenBankingOBIE.AccountSubType;
import org.hibernate.Session;
import util.tx.findByIdExptn_CantFindData;

import static cfg.Containr.sessionFactory;

import static handler.wthdr.WthdReqHdl.iniIvstAcc;
import static util.acc.AccUti.getAccId;
import static util.tx.HbntUtil.findByHerbinate;
import static util.tx.HbntUtil.persistByHibernate;

public class IniAcc {

    public static void iniTwoWlt( String uname) throws findByIdExptn_CantFindData {

        try{
            addAccEmnyIfNotExst(uname,sessionFactory.getCurrentSession());

        }catch (Throwable e){

        }

        try{

            newIvstWltIfNotExist(uname);
        }catch (Throwable e){

        }

    }


    public static void newIvstWltIfNotExist(String uname1) {

        String accId4ylwlt = getAccId(AccountSubType.GeneralInvestment.name(), uname1);
        try{
            var wlt=findByHerbinate(Account.class, accId4ylwlt , sessionFactory.getCurrentSession());
        } catch (findByIdExptn_CantFindData e) {

            iniIvstAcc(  uname1);
        }
    }

    public static void addAccEmnyIfNotExst(String uname, Session session) throws findByIdExptn_CantFindData {
        String accid=getAccId(AccountSubType.EMoney.name(), uname);
        try{
            var wlt=findByHerbinate(Account.class, accid, session);
        } catch (findByIdExptn_CantFindData e) {
            //ini wlt
            Account wlt=new Account(accid);
            wlt.accountId = accid;
            wlt.accountOwner=uname;
            persistByHibernate(wlt, session);
            //  transDto.lockAccObj=findByHerbinate(Wallet.class, uname, session);
        }
    }

}

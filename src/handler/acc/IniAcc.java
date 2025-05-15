package handler.acc;

import model.OpenBankingOBIE.Account;
import model.OpenBankingOBIE.AccountSubType;
import org.hibernate.Session;
import util.tx.findByIdExptn_CantFindData;

import static cfg.Containr.sessionFactory;


import static handler.balance.BlsSvs.iniBlss;
import static util.acc.AccUti.getAccId;
import static util.tx.HbntUtil.findById;
import static util.tx.HbntUtil.persistByHibernate;

public class IniAcc {

    public static void iniTwoWlt( String uname) throws findByIdExptn_CantFindData {

        System.out.println( "fun iniTwoWlt(uname: " + uname);
        try{
            addAccEmnyIfNotExst(uname,sessionFactory.getCurrentSession());

        }catch (Throwable e){

        }

        try{

            newIvstWltIfNotExist(uname);
        }catch (Throwable e){

        }
        System.out.println("endfun initwo wlt()");

    }

    public static void iniIvstAcc(String uname1) {

        String accid=getAccId(AccountSubType.GeneralInvestment.name(), uname1);
        Account acc1=new Account(accid );
        //  acc1.userId= uname1;
        acc1.accountOwner =uname1;
        acc1.accountSubType= AccountSubType.GeneralInvestment.name();
        persistByHibernate(acc1,sessionFactory.getCurrentSession());
        iniBlss(acc1);

    }




    public static void newIvstWltIfNotExist(String uname1) {
        System.out.println("fun newIvstWltIfNotExist(uname1: " + uname1 + "");
        String accId4ylwlt = getAccId(AccountSubType.GeneralInvestment.name(), uname1);
        try{
            var wlt= findById(Account.class, accId4ylwlt , sessionFactory.getCurrentSession());
        } catch (findByIdExptn_CantFindData e) {

            iniIvstAcc(  uname1);
        }
    }

    public static void addAccEmnyIfNotExst(String uname, Session session) throws findByIdExptn_CantFindData {
        System.out.println("fun addAccEmnyIfNotExst(uname: " + uname + " ");
        String accid=getAccId(AccountSubType.EMoney.name(), uname);
        try{
            var wlt= findById(Account.class, accid, session);
        } catch (findByIdExptn_CantFindData e) {
            //ini acc1
            Account acc1=new Account(accid);
            acc1.accountId = accid;
            acc1.accountOwner=uname;
            persistByHibernate(acc1, session);
            iniBlss(acc1);
            //  transDto.lockAccObj=findByHerbinate(Wallet.class, uname, session);
        }
    }

}

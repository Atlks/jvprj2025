//package apiWltYinli;

package service.YLwltSvs;

import entityx.wlt.LogBls4YLwlt;
import lombok.Data;

import model.OpenBankingOBIE.Account;
import model.OpenBankingOBIE.Transaction;
import model.OpenBankingOBIE.TransactionStatus;
import org.hibernate.Session;

import util.model.openbank.BalanceTypes;
import util.tx.findByIdExptn_CantFindData;

import java.math.BigDecimal;

import static cfg.Containr.sessionFactory;
// static cfg.AppConfig.sessionFactory;
import static service.CmsBiz.toBigDcmTwoDot;
import static util.acc.AccUti.getAccId4ylwlt;
import static util.tx.HbntUtil.*;
import static util.misc.util2026.getFilenameFrmLocalTimeString;
@Data
//@Service
//@Lazy
public class AccSvs4invstAcc  {

    public static Object crdt(Transaction txx ) throws Exception, findByIdExptn_CantFindData {




        System.out.println("\n\n\n===========增加盈利钱包余额");



        String uname = txx.owner;
        BigDecimal amt = txx.amount;

        Session session=sessionFactory.getCurrentSession();

//       Account acc=  curLockAcc.get();//nml wlt
//       if(acc==null)
//           acc=TransDto1.lockAccObj;

       var ylwltAccId= getAccId4ylwlt(uname);
        Account  acc=findByHerbinateLockForUpdtV2(Account.class,ylwltAccId,session);

        BigDecimal nowAmt =acc.getInterim_Available_Balance();

        BigDecimal newBls = nowAmt.add(amt);
        acc.interim_Available_Balance = toBigDcmTwoDot(newBls);
        acc.setInterimBookedBalance(acc.getInterimBookedBalance().add(amt));
        mergex(acc, session);



        //-----------add tx lg

        txx.refUniqId= String.valueOf(System.currentTimeMillis());
        txx.status = TransactionStatus.BOOKED;
        txx.setBalanceAmount( acc.interim_Available_Balance);
        txx.setBalanceType(BalanceTypes.interimAvailable);
        persist(txx, sessionFactory.getCurrentSession());


      //  LogBls4YLwlt logBlsYinliWlt = new LogBls4YLwlt(TransDto1,nowAmt, newBls,"增加");
     //   addBlsLog4ylwlt(logBlsYinliWlt, session);
        //  System.out.println("✅endfun updtBlsByAddChrg()");
        return null;
    }

    public static void addBlsLog4ylwlt(LogBls4YLwlt logBlsYinliWlt, Session session) {
        //--------------add logBlsYinliWlt

        logBlsYinliWlt.id = "LogBalanceYinliWlt" + getFilenameFrmLocalTimeString();
     //   logBlsYinliWlt.uname = lgblsDto.uname;
     //   logBlsYinliWlt.changeMode = "增加";
    //    logBlsYinliWlt.changeAmount = lgblsDto.getChangeAmount();

        // addObj(logBlsYinliWlt,saveUrlLogBalanceYinliWlt);
        persist(logBlsYinliWlt, session);
    }


}

//package apiWltYinli;

package service.YLwltSvs;

import entityx.wlt.LogBls4YLwlt;
import entityx.wlt.TransDto;
import lombok.Data;

import model.OpenBankingOBIE.Accounts;
import model.OpenBankingOBIE.CreditDebitIndicator;
import model.OpenBankingOBIE.TransactionStatus;
import model.OpenBankingOBIE.Transactions;
import org.hibernate.Session;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import util.algo.Icall;
import util.tx.findByIdExptn_CantFindData;

import java.math.BigDecimal;

import static handler.wlt.TransHdr.curLockAcc;
import static cfg.AppConfig.sessionFactory;
import static service.CmsBiz.toBigDcmTwoDot;
import static util.acc.AccUti.getAccId4ylwlt;
import static util.algo.GetUti.getUuid;
import static util.tx.HbntUtil.*;
import static util.misc.util2026.getFilenameFrmLocalTimeString;
@Data
@Service
@Lazy
public class AddMoney2YLWltService implements Icall<TransDto,Object> {

    public Object main(TransDto TransDto1 ) throws Exception, findByIdExptn_CantFindData {




        System.out.println("\n\n\n===========增加盈利钱包余额");



        String uname = TransDto1.uname;
        BigDecimal amt = TransDto1.changeAmount;

        Session session=sessionFactory.getCurrentSession();

       Accounts acc=  curLockAcc.get();//nml wlt
       if(acc==null)
           acc=TransDto1.lockAccObj;

       var ylwltAccId= getAccId4ylwlt(uname);
       acc=findByHerbinate(Accounts.class,ylwltAccId,session);

        BigDecimal nowAmt =acc.getAvailableBalance();
              //  getFieldAsBigDecimal(acc, "balance", 0);

        BigDecimal newBls = nowAmt.add(amt);
        acc.availableBalance = toBigDcmTwoDot(newBls);
        mergeByHbnt(acc, session);



        //-----------add tx lg
        Transactions txx=new Transactions();
        txx.transactionId="add2ylwlt"+getUuid();
        txx.creditDebitIndicator= CreditDebitIndicator.CREDIT;
        txx.accountId=  acc.accountId;
        txx.uname= TransDto1.uname;
        txx.amount= TransDto1.getChangeAmount();
        txx.refUniqId= String.valueOf(System.currentTimeMillis());
        txx.transactionStatus= TransactionStatus.BOOKED;
        persistByHibernate(txx, sessionFactory.getCurrentSession());


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
        persistByHibernate(logBlsYinliWlt, session);
    }


}

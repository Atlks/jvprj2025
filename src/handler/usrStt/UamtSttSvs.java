package handler.usrStt;

import jakarta.validation.constraints.NotNull;
import model.OpenBankingOBIE.Transaction;
import model.usr.UsrExtAmtStats;

import static cfg.Containr.sessionFactory;
import static util.tx.HbntUtil.mergex;

public class UamtSttSvs {

    public static void updtUsrRpt4rechg(@NotNull Transaction tx) {
        System.out.println("fun updtUsrRpt4rechg()");
        UsrExtAmtStats usrStats=new UsrExtAmtStats();
        usrStats.uname=tx.owner;
        usrStats.setTotalDeposit(usrStats.getTotalDeposit().add(tx.amount));
        mergex(usrStats,sessionFactory.getCurrentSession());
    }
}

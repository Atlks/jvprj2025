package handler.acc;

import handler.statmt.CrudFun;
import model.OpenBankingOBIE.Account;
import model.OpenBankingOBIE.Balance;
import util.model.openbank.BalanceTypes;
import util.tx.findByIdExptn_CantFindData;

import java.math.BigDecimal;

// static handler.acc.AccService.updtAccSetAvdblsNBkbls;
import static handler.balance.BlsSvs.getBlsid;
import static util.tx.HbntUtil.*;

public class AccDao {
    @CrudFun
    static void updateBlsSetAmt(String blsid,BigDecimal amount) throws findByIdExptn_CantFindData {
        try{
            findByHerbinateLockForUpdtV2(Balance.class, blsid);
        } catch (findByIdExptn_CantFindData e) {

        }
        Balance bls=findByHerbinateLockForUpdtV2(Balance.class, blsid);
      //  BigDecimal amount = BigDecimal.valueOf(0);
        bls.setAmount(amount);
        bls.setAmount(amount);
        mergex(bls);

    }
    @CrudFun
    public static void subAvdBlsUpdtAcc(Account acc1, BigDecimal amt) throws findByIdExptn_CantFindData {
        BigDecimal avdBls = acc1.interim_Available_Balance;
        BigDecimal newAvdBls = avdBls.subtract(amt);

        acc1.setInterim_Available_Balance(newAvdBls);

        mergex(acc1);

        subAmtUpdtBls(acc1.accountId, BalanceTypes.interimAvailable,amt);

    }

    @CrudFun
    public static void subAvdBlsBkBlsUpdtAcc(Account acc1, BigDecimal amt) throws findByIdExptn_CantFindData {
        BigDecimal avdBls = acc1.interim_Available_Balance;
        BigDecimal newAvdBls = avdBls.subtract(amt);

        acc1.setInterim_Available_Balance(newAvdBls);
        acc1.setInterimBookedBalance(acc1.getInterimBookedBalance().subtract(amt));
        mergex(acc1);

        subAmtUpdtBls(acc1.accountId, BalanceTypes.interimAvailable,amt);
        subAmtUpdtBls(acc1.accountId, BalanceTypes.interimBooked,amt);

    }


    @CrudFun
    static void updateBlsSetAmtZero(String blsid) throws findByIdExptn_CantFindData {
        Balance bls=findByHerbinateLockForUpdtV2(Balance.class, blsid);
        bls.setAmount(BigDecimal.valueOf(0));
        bls.setAmount(BigDecimal.valueOf(0));
        mergex(bls);

    }

    @CrudFun
    public static void addAmt2acc(Account acc1, BigDecimal adjAmt) {
        System.out.println("fun addAmt2acc");
        BigDecimal avdBls = acc1.interim_Available_Balance;
        BigDecimal newAvdBls = avdBls.add(adjAmt);
        // logTag = "增加";

        BigDecimal newBkBls = acc1.InterimBookedBalance.add(adjAmt);

        updtAccSetAvdblsNBkbls(acc1, newAvdBls, newBkBls);
        System.out.println("endfun addAmt2acc");
    }
    @CrudFun
    public static void updtAccSetAvdblsNBkbls(Account acc1, BigDecimal newAvdBls, BigDecimal newBkBls) {
        acc1.setInterim_Available_Balance(newAvdBls);
        acc1.setInterimBookedBalance(newBkBls);
        mergex(acc1);
    }
    @CrudFun
    public static void subAmt2acc(Account acc1, BigDecimal adjAmt) {
        BigDecimal avdBls = acc1.interim_Available_Balance;
        BigDecimal newAvdBls = avdBls.subtract(adjAmt);
        // logTag = "增加";

        BigDecimal newBkbls = acc1.InterimBookedBalance.subtract(adjAmt);
        updtAccSetAvdblsNBkbls(acc1, newAvdBls, newBkbls);
    }
    @CrudFun
    public static void addAmtUpdtAcc(Account acc1, BigDecimal amt) {
        BigDecimal avdBls = acc1.interim_Available_Balance;
        BigDecimal newAvdBls = avdBls.add(amt);


        BigDecimal newBkbls = acc1.InterimBookedBalance.add(amt);
        updtAccSetAvdblsNBkbls(acc1, newAvdBls, newBkbls);
    }
    @CrudFun
    public static void subAmtUpdtBls(String accountId, BalanceTypes balanceTypes, BigDecimal amt) throws findByIdExptn_CantFindData {
        System.out.println("fun subAmtFrmBls(accountId="+accountId+",blstype="+ balanceTypes+", amt="+amt+")");
        String blsid= getBlsid(accountId,balanceTypes);
        Balance bls=findById(Balance.class,blsid);
        bls.setAmount(bls.getAmount().subtract(amt));
        mergex(bls);
        System.out.println("endfun subAmtFrmBls");
    }
}

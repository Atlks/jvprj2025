package handler.balance;

import model.OpenBankingOBIE.Account;
import model.OpenBankingOBIE.Balance;
import model.OpenBankingOBIE.CreditDebitIndicator;
import org.jetbrains.annotations.NotNull;
import util.model.openbank.BalanceTypes;
import util.tx.findByIdExptn_CantFindData;

import java.math.BigDecimal;

import static util.model.openbank.BalanceTypes.interimAvailable;
import static util.model.openbank.BalanceTypes.interimBooked;
import static util.tx.HbntUtil.*;
import static util.tx.HbntUtil.persistByHibernate;

public class BlsSvs {


   @Deprecated
    @NotNull
    public static String getBlsid(String accountId, BalanceTypes interimAvailable) {
        return accountId + "_" +
                interimAvailable;
    }
    /**
     *
     * @param acc1   (accid,acctype)
     * @param adjAmt
     * @param interimAvailable
     * @throws findByIdExptn_CantFindData
     */
    public static void addBal(Account acc1, BigDecimal adjAmt, BalanceTypes interimAvailable) throws findByIdExptn_CantFindData {
        String accountId=acc1.getAccountId();
        String blsid=
                getBlsid(interimAvailable, accountId);
        iniBal(acc1,interimAvailable);
        Balance bls=findByHerbinateLockForUpdtV2(Balance.class, blsid);
        bls.setAccountId(acc1.getAccountId());
        bls.setType(interimAvailable.name());
        bls.setAmount(bls.getAmount().add(adjAmt));
        bls.setAccSubType(acc1.getAccountSubType());
        bls.setOwner(acc1.getAccountOwner());
        bls.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
        bls.setAccSnapshot(acc1);
        mergeByHbnt(bls);
    }

    public static void iniBlss(Account acc1) {
        // String accountId = acc1.accountId;
      //  String blsid=
                getBlsid(interimAvailable, acc1.accountId);
        iniBal(acc1,interimAvailable);
        iniBal( acc1,interimBooked);
        iniBal(acc1, BalanceTypes.frz);

    }

//    @Deprecated
//    public static void iniBlss(String accountId) {
//
//       // String accountId = acc1.accountId;
//        String blsid=
//                getBlsid(interimAvailable, accountId);
//        iniBal(blsid, accountId,interimAvailable);
//        iniBal( getBlsid( (interimBooked), accountId), accountId,interimAvailable);
//        iniBal(accountId, BalanceTypes.frz);
//    }

    public static void subBal(Account acc1, BigDecimal adjAmt, BalanceTypes balanceTypes) throws findByIdExptn_CantFindData {

        String accountId=acc1.getAccountId();
        String blsid=
                getBlsid(balanceTypes, accountId);
        iniBal(acc1,balanceTypes);
        Balance bls=findByHerbinateLockForUpdtV2(Balance.class, blsid);
        bls.setAccountId(acc1.getAccountId());
        bls.setType(balanceTypes.name());
        bls.setAmount(bls.getAmount().subtract(adjAmt));
        bls.setAccSubType(acc1.getAccountSubType());
        bls.setOwner(acc1.getAccountOwner());
        bls.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
        mergeByHbnt(bls);
    }
//    public static void iniBal(  String accountId, BalanceTypes interimAvailable)
//    {
//        String blsid=
//                getBlsid(interimAvailable,   accountId);
//        iniBal(blsid,accountId,interimAvailable);
//    }

    public static void iniBal(Account acc, BalanceTypes interimAvailable)  {
      String blsid=  getBlsid(acc.getAccountId(),interimAvailable);
        try {
            Balance bls= findById(Balance.class, blsid);
        } catch (findByIdExptn_CantFindData e) {
            Balance bls =new Balance( acc,interimAvailable);
            persistByHibernate(bls);

        }
    }

//    public static void iniBal(String blsid, String accountId, BalanceTypes interimAvailable)  {
//        try {
//            Balance bls=findByHerbinate(Balance.class, blsid);
//        } catch (findByIdExptn_CantFindData e) {
//            Balance bls =new Balance(blsid);
//            bls.setAccountId(accountId);
//            bls.setType(interimAvailable.name());
//            persistByHibernate(bls);
//
//        }
//    }

    @NotNull
    public static String getBlsid(BalanceTypes interimAvailable, String accountId) {
        return accountId + "_" +
                interimAvailable;
    }

}

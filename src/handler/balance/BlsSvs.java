package handler.balance;

import handler.ivstAcc.dto.QueryDto;
import handler.statmt.CrudFun;
import jakarta.ws.rs.Path;
import model.OpenBankingOBIE.Account;
import model.OpenBankingOBIE.AccountSubType;
import model.OpenBankingOBIE.Balance;
import model.OpenBankingOBIE.CreditDebitIndicator;
import org.jetbrains.annotations.NotNull;
import util.model.openbank.BalanceTypes;
import util.tx.HbntUtil;
import util.tx.findByIdExptn_CantFindData;

import java.math.BigDecimal;

import static util.acc.AccUti.getAccid;
import static util.model.openbank.BalanceTypes.interimAvailable;
import static util.model.openbank.BalanceTypes.interimBooked;
import static util.tx.HbntUtil.*;
import static util.tx.HbntUtil.persist;

public class BlsSvs {


    // /apiv1/accounts/balances?AccountId=uuu_agtcms
    @Path("/apiv1/accounts/xxx_agtcms/balances")
    public static Object acc_balances_xxx_agtcms(QueryDto dto) throws findByIdExptn_CantFindData {
        String accountId=getAccid(dto.uname, AccountSubType.agtCms);
        Account acc=findById(Account.class,accountId);
        return  acc;
    }

   @Deprecated
    @NotNull
    public static String getBlsid(String accountId, BalanceTypes interimAvailable) {
        return accountId + "_" +
                interimAvailable;
    }

    @CrudFun
    /**
     *
     * @param acc1   (accid,acctype)
     * @param adjAmt
     * @param blstype
     * @throws findByIdExptn_CantFindData
     */
    public static Balance addAmt2BalWhrAccNType(BigDecimal adjAmt,Account acc1,  BalanceTypes blstype) throws findByIdExptn_CantFindData {
        System.out.println(" fun addAmt2BalWhrAccNType(blstype="+blstype.name());
        String accountId=acc1.getAccountId();
        String blsid=
                getBlsid(blstype, accountId);
        iniBal(acc1,blstype);
        Balance bls=findByHerbinateLockForUpdtV2(Balance.class, blsid);
        BigDecimal newAmt = bls.getAmount().add(adjAmt);
        CreditDebitIndicator idctr = CreditDebitIndicator.CREDIT;

        Balance balance = updtBlsSetAmt_Type_Acc_idctr(bls, newAmt, acc1, blstype, idctr);
        System.out.println("endfun addAmt2BalWhrAccNType");
        return balance;
    }

    @NotNull
    private static Balance updtBlsSetAmt_Type_Acc_idctr(Balance bls,BigDecimal newAmt, Account acc1, BalanceTypes balanceTypes,   CreditDebitIndicator idctr) {
        System.out.println("fun updtBlsSetAmt_Type_Acc_idctr (newAmt="+newAmt+",blstype="+balanceTypes);
        bls.setAccountId(acc1.getAccountId());
        bls.setType(balanceTypes.name());
        bls.setAmount(newAmt);
        bls.setAccSubType(acc1.getAccountSubType());
        bls.setOwner(acc1.getOwner());
        bls.setCreditDebitIndicator(idctr);
      //  bls.setAccSnapshot(acc1);
        Balance mergex = mergex(bls);
        System.out.println("endfun updtBlsSetAmt_Type_Acc_idctr ");
        return mergex;
    }

    public static void iniBlss(Account acc1) {
        System.out.println("fun iniblsss");
        // String accountId = acc1.accountId;
      //  String blsid=
                getBlsid(interimAvailable, acc1.accountId);
        iniBal(acc1,interimAvailable);
        iniBal( acc1,interimBooked);
        iniBal(acc1, BalanceTypes.frz);
        System.out.println("endfun iniblsss");
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

    public static Balance subAmt2BalWhrAcc_type(BigDecimal adjAmt, Account acc1, BalanceTypes balanceTypes) throws findByIdExptn_CantFindData {

        String accountId=acc1.getAccountId();
        String blsid=
                getBlsid(balanceTypes, accountId);
        iniBal(acc1,balanceTypes);
        Balance bls=findByHerbinateLockForUpdtV2(Balance.class, blsid);
        BigDecimal newAmt = bls.getAmount().subtract(adjAmt);

        updtBlsSetAmt_Type_Acc_idctr(bls,newAmt,acc1,balanceTypes,CreditDebitIndicator.CREDIT);

        return bls;
    }
//    public static void iniBal(  String accountId, BalanceTypes interimAvailable)
//    {
//        String blsid=
//                getBlsid(interimAvailable,   accountId);
//        iniBal(blsid,accountId,interimAvailable);
//    }

    public static void iniBal(Account acc, BalanceTypes type)  {
        System.out.println("fun inibal(type="+type.name());
      String blsid=  getBlsid(acc.getAccountId(),type);
        try {
            Balance bls= findById(Balance.class, blsid);
        } catch (findByIdExptn_CantFindData e) {
            Balance bls =new Balance( acc,type);
            HbntUtil.persist(bls);

        }
        System.out.println("endfun inibal()");
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

package handler.wlt;

import handler.wlt.dto.AdjstDto;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;

import model.OpenBankingOBIE.*;
import org.hibernate.Session;
import util.model.common.ApiResponse;
import entityx.wlt.LogBls;
//import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.Path;
import util.model.openbank.BalanceTypes;
import util.tx.findByIdExptn_CantFindData;

import java.math.BigDecimal;

import static cfg.Containr.sessionFactory;
import static com.alibaba.fastjson2.util.TypeUtils.toBigDecimal;
import static handler.acc.IniAcc.iniTwoWlt;
import static handler.balance.BlsSvs.*;
import static service.CmsBiz.toBigDcmTwoDot;
import static util.acc.AccUti.getAccId;
import static util.algo.GetUti.getUuid;
import static util.model.openbank.BalanceTypes.interimAvailable;
import static util.model.openbank.BalanceTypes.interimBooked;
import static util.tx.HbntUtil.*;
import static util.tx.dbutil.addObj;
import static util.misc.util2026.*;

/**
 * 资金调整
 *  {@literal http://localhost:8889/wlt/adjust?adjustType=%2B&changeAmount=99}
 */


@Path("/admin/wlt/adjust")

//@Parameter(name = "adjustType")    //+ -  Increase Decrease
//@Parameter(name = "changeAmount")
@RolesAllowed({"admin", "Operator"})
@PermitAll
public class AdjustHdr{


    public Object handleRequest(AdjstDto adjstDto) throws Throwable {

        iniTwoWlt(adjstDto.uname);

String accid=getAccId(adjstDto.accountSubType,adjstDto.uname);

        Session session = sessionFactory.getCurrentSession();
        Account acc1 = findByHerbinateLockForUpdtV2(Account.class, accid ,session);
        Transaction tx=new Transaction();

        BigDecimal avdBls = acc1.interim_Available_Balance;

        String blsid=
                getBlsid(interimAvailable,  acc1. accountId);
        iniBal(blsid,acc1.accountId,interimAvailable);
        iniBal( getBlsid( (interimBooked),  (acc1. accountId)),acc1.accountId,interimAvailable);

        //def is add
        BigDecimal newAvdBls = avdBls;
        var logTag = "";
        BigDecimal adjAmt = BigDecimal.valueOf(adjstDto.adjustAmount);
        if (adjstDto.transactionCode.toLowerCase().equals(TransactionCode.adjst_dbt.name().toLowerCase())) {

            
            Transaction tx2=new Transaction();
            tx2.creditDebitIndicator= CreditDebitIndicator.DEBIT;
            tx2.setAmount(adjAmt);
            tx2.transactionCode=TransactionCode.adjst_dbt.name();
            subAmt2accWzlog(acc1,tx2);
        } else if (adjstDto.transactionCode.toLowerCase().equals(TransactionCode.adjst_crdt.name().toLowerCase())) {

            addAmt2accWzLog(acc1,adjAmt,TransactionCode.adjst_crdt);
            return new ApiResponse(acc1);

        } else if (adjstDto.transactionCode.toLowerCase().equals(TransactionCode.adjst_frz.name().toLowerCase())){



            Transaction tx2=new Transaction();
            tx2.creditDebitIndicator= CreditDebitIndicator.DEBIT;
            tx2.setAmount(adjAmt);
            tx2.transactionCode=TransactionCode.adjst_frz.name();
            frzAmt2accWzlog(acc1,tx2);

        } else if (adjstDto.transactionCode.toLowerCase().equals(TransactionCode.adjst_unfrz.name().toLowerCase())){


            Transaction tx2=new Transaction();
            tx2.creditDebitIndicator= CreditDebitIndicator.CREDIT;
            tx2.setAmount(adjAmt);
            tx2.transactionCode=TransactionCode.adjst_frz.name();
            unfrzAmt2accWzlog(acc1,tx2);
        }


//        if (newAvdBls.equals(avdBls) || adjstDto.adjustType.equals(""))
//            throw new ErrAdjstTypeEx("");

 //       acc1.setInterim_Available_Balance(newAvdBls);
        mergeByHbnt(acc1, session);



        //addTx

        tx.transactionId=getUuid();
        tx.accountOwner =adjstDto.uname;
        tx.accountId= accid.toString();
        tx.transactionCode=  adjstDto.transactionCode;
        tx.amount=toBigDecimal( adjstDto.adjustAmount);
        tx.status =TransactionStatus.BOOKED;

        persistByHibernate(tx,session);


        //add balanceLog

        LogBls logBalance = new LogBls();
        logBalance.id = "LogBalance" + getFilenameFrmLocalTimeString();
        logBalance.uname = acc1.accountId;

        logBalance.changeAmount = BigDecimal.valueOf(adjstDto.getAdjustAmount());
        logBalance.amtBefore = toBigDcmTwoDot(avdBls);
        logBalance.newBalance = toBigDcmTwoDot(newAvdBls);
        logBalance.refUniqId = String.valueOf(System.currentTimeMillis());
        logBalance.adjustType = adjstDto.transactionCode;
        logBalance.changeMode = logTag;
       persistByHibernate(logBalance, session);


        return new ApiResponse(acc1);
    }


    private void  unfrzAmt2accWzlog(Account acc1, Transaction tx) throws findByIdExptn_CantFindData {
        //-----------unfrz acc
        BigDecimal adjAmt=tx.getAmount();

        acc1.setFrozenAmountVld(acc1.frozenAmount.subtract(adjAmt));
        acc1.setInterim_Available_Balance(acc1.interim_Available_Balance.add(adjAmt));

        mergeByHbnt(acc1);




        //sub bls
        addBal(acc1, adjAmt, interimAvailable);
        subBal(acc1, adjAmt,BalanceTypes.frz);



        //add tx


        tx.transactionId=getUuid();
        tx.accountOwner =acc1.accountOwner;
        tx.accountId=acc1.accountId;

        tx.amount=toBigDecimal(adjAmt);
        tx.status =TransactionStatus.BOOKED;
        tx.setBalanceAmount(acc1.getInterim_Available_Balance());
        tx.setBalanceType(interimAvailable.name() );
        tx.setBalanceCreditDebitIndicator(CreditDebitIndicator.CREDIT.name());

        persistByHibernate(tx);
    }


    private void frzAmt2accWzlog(Account acc1, Transaction tx) throws findByIdExptn_CantFindData {
        //-----------frz acc
        BigDecimal adjAmt=tx.getAmount();

        acc1.frozenAmount= acc1.frozenAmount.add(adjAmt);
        acc1.setInterim_Available_Balance(acc1.interim_Available_Balance.subtract(adjAmt));
         mergeByHbnt(acc1);




        //sub bls
        subBal(acc1, adjAmt, interimAvailable);
        addBal(acc1, adjAmt,BalanceTypes.frz);



        //add tx

        tx.creditDebitIndicator= CreditDebitIndicator.DEBIT;
        tx.transactionId=getUuid();
        tx.accountOwner =acc1.accountOwner;
        tx.accountId=acc1.accountId;

        tx.amount=toBigDecimal(adjAmt);
        tx.status =TransactionStatus.BOOKED;
        tx.setBalanceAmount(acc1.getInterim_Available_Balance());
        tx.setBalanceType(interimAvailable.name() );
        tx.setBalanceCreditDebitIndicator(CreditDebitIndicator.CREDIT.name());

        persistByHibernate(tx);
    }



    private void subAmt2accWzlog(Account acc1, Transaction tx) throws findByIdExptn_CantFindData {
        //-----------sub acc
        BigDecimal adjAmt=tx.getAmount();
        subAmt2acc(acc1, adjAmt);


        //sub bls
        subBal(acc1, adjAmt, interimAvailable);
        subBal(acc1, adjAmt, BalanceTypes.interimBooked);


        //add tx

        tx.creditDebitIndicator= CreditDebitIndicator.DEBIT;
        tx.transactionId=getUuid();
        tx.accountOwner =acc1.accountOwner;
        tx.accountId=acc1.accountId;

        tx.amount=toBigDecimal(adjAmt);
        tx.status =TransactionStatus.BOOKED;
        tx.setBalanceAmount(acc1.getInterim_Available_Balance());
        tx.setBalanceType(interimAvailable.name() );
        tx.setBalanceCreditDebitIndicator(CreditDebitIndicator.CREDIT.name());

        persistByHibernate(tx);
    }


    private void addAmt2accWzLog(Account acc1, BigDecimal adjAmt, TransactionCode txCod) throws findByIdExptn_CantFindData {

        //-----------add acc
        addAmt2acc(acc1, adjAmt);

        //------add bls
        BalanceTypes interimAvailable = BalanceTypes.interimAvailable;
        String accountId = acc1.accountId;
        addBal(acc1, adjAmt, interimAvailable);
        addBal(acc1, adjAmt, BalanceTypes.interimBooked);



        //-----addtx
        Transaction tx=new Transaction();
        tx.creditDebitIndicator= CreditDebitIndicator.CREDIT;
        tx.transactionId=getUuid();
        tx.accountOwner =acc1.accountOwner;
        tx.accountId=acc1.accountId;
        tx.transactionCode=  txCod.name();
        tx.amount=toBigDecimal(adjAmt);
        tx.status =TransactionStatus.BOOKED;
        tx.setBalanceAmount(acc1.getInterim_Available_Balance());
        tx.setBalanceType(interimAvailable.name());
        tx.setBalanceCreditDebitIndicator(CreditDebitIndicator.CREDIT.name());

        persistByHibernate(tx);
    }

    private static void addAmt2acc(Account acc1, BigDecimal adjAmt) {
        BigDecimal avdBls = acc1.interim_Available_Balance;
        BigDecimal  newAvdBls = avdBls.add(adjAmt);
        // logTag = "增加";
        acc1.setInterim_Available_Balance(newAvdBls);
        acc1.setInterimBookedBalance(acc1.InterimBookedBalance.add(adjAmt)) ;

        mergeByHbnt(acc1);
    }


    private static void subAmt2acc(Account acc1, BigDecimal adjAmt) {
        BigDecimal avdBls = acc1.interim_Available_Balance;
        BigDecimal  newAvdBls = avdBls.subtract(adjAmt);
        // logTag = "增加";
        acc1.setInterim_Available_Balance(newAvdBls);
        acc1.setInterimBookedBalance(acc1.InterimBookedBalance.subtract(adjAmt)) ;

        mergeByHbnt(acc1);
    }











//    public static void main(String[] args) throws Exception {
//      MyCfg.iniCfgFrmCfgfile();
//        Map<String, String> queryParams=new HashMap<>();
//        queryParams.put("adjst","add");  //sub
//        queryParams.put("amt","9");  //sub
//        updtBls("007","add", BigDecimal.valueOf(9),queryParams);
//    }
//
//    private static void updtBls(String uname, String adjstOp, BigDecimal amt, Map<String, String> queryParams) throws Exception {
//
//        //add blance
//        SortedMap<String, Object> objU = getObjIni(uname, saveDirUsrs);
//        if (objU.get("id") == null) {
//            objU.put("id", uname);
//            objU.put("uname", uname);
//        }
//
//
//    }


}

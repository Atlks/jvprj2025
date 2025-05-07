package handler.wlt;

import handler.wlt.dto.AdjstDto;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;

import model.OpenBankingOBIE.*;
import org.hibernate.Session;
import entityx.ApiResponse;
import entityx.wlt.LogBls;
//import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.Path;
import util.excptn.BalanceNegativeException;

import java.math.BigDecimal;

import static cfg.Containr.sessionFactory;
import static com.alibaba.fastjson2.util.TypeUtils.toBigDecimal;
import static service.CmsBiz.toBigDcmTwoDot;
import static util.acc.AccUti.getAccId;
import static util.algo.GetUti.getUuid;
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

String accid=getAccId(adjstDto.accountSubType,adjstDto.uname);
        Session session = sessionFactory.getCurrentSession();
        Account acc1 = findByHerbinateLockForUpdtV2(Account.class, accid ,session);
        Transaction tx=new Transaction();

        BigDecimal avdBls = acc1.InterimAvailableBalance;


        //def is add
        BigDecimal newBls = avdBls;
        var logTag = "";
        BigDecimal subAmt = BigDecimal.valueOf(adjstDto.adjustAmount);
        if (adjstDto.transactionCode.toUpperCase().equals(TransactionCode.AdjustmentDebit.name())) {
            newBls = avdBls.subtract(subAmt);
            if (newBls.compareTo(BigDecimal.ZERO) < 0) {
                throw new BalanceNegativeException("余额不能为负数");
            }
            logTag = "减少";
            acc1.InterimBookedBalance = acc1.InterimBookedBalance.subtract(subAmt);
            tx.creditDebitIndicator= CreditDebitIndicator.DEBIT;
        } else if (adjstDto.transactionCode.toUpperCase().equals(TransactionCode.AdjustmentCredit.name())) {
            newBls = avdBls.add(subAmt);
            logTag = "增加";
            acc1.InterimBookedBalance = acc1.InterimBookedBalance.add(subAmt);
            tx.creditDebitIndicator= CreditDebitIndicator.CREDIT;
        } else if (adjstDto.transactionCode.toLowerCase().equals(TransactionCode.adjst_frz.name())){
            acc1.frozenAmount= acc1.frozenAmount.add(subAmt);
            acc1.InterimBookedBalance = acc1.InterimBookedBalance.subtract(subAmt);
            tx.creditDebitIndicator= CreditDebitIndicator.DEBIT;

        } else if (adjstDto.transactionCode.toLowerCase().equals(TransactionCode.adjust_unfrz.name())){
            acc1.frozenAmount= acc1.frozenAmount.subtract(subAmt);
            acc1.InterimBookedBalance = acc1.InterimBookedBalance.add(subAmt);
            tx.creditDebitIndicator= CreditDebitIndicator.CREDIT;
        }


//        if (newBls.equals(avdBls) || adjstDto.adjustType.equals(""))
//            throw new ErrAdjstTypeEx("");

        acc1.setInterimAvailableBalance(newBls);
        mergeByHbnt(acc1, session);



        //addTx

        tx.transactionId=getUuid();
        tx.accountOwner =adjstDto.uname;
        tx.accountId= accid.toString();
        tx.transactionCode= TransactionCode.fromCode( adjstDto.transactionCode);
        tx.amount=toBigDecimal( adjstDto.adjustAmount);
        tx.transactionStatus=TransactionStatus.BOOKED;
        persistByHibernate(tx,session);


        //add balanceLog

        LogBls logBalance = new LogBls();
        logBalance.id = "LogBalance" + getFilenameFrmLocalTimeString();
        logBalance.uname = acc1.accountId;

        logBalance.changeAmount = BigDecimal.valueOf(adjstDto.getAdjustAmount());
        logBalance.amtBefore = toBigDcmTwoDot(avdBls);
        logBalance.newBalance = toBigDcmTwoDot(newBls);
        logBalance.refUniqId = String.valueOf(System.currentTimeMillis());
        logBalance.adjustType = adjstDto.transactionCode;
        logBalance.changeMode = logTag;
       persistByHibernate(logBalance, session);


        return new ApiResponse(acc1);
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

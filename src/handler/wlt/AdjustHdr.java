package handler.wlt;

import handler.acc.AccService;
import handler.acc.DecBalDto;
import handler.wlt.dto.AdjstDto;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;

import jakarta.validation.constraints.DecimalMin;
import model.OpenBankingOBIE.*;
import org.hibernate.Session;
import util.algo.RunnableThrowing;
import util.fp.SerializableFun;
import util.model.common.ApiResponse;
import entityx.wlt.LogBls;
//import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.Path;
import util.wkflow.ChoiceStep;
import util.wkflow.ConditionBranch;
import util.wkflow.NmlStep;
import util.wkflow.ProcessWkfl;

import java.math.BigDecimal;
import java.util.function.Function;

import static com.alibaba.fastjson2.util.TypeUtils.toBigDecimal;
import static handler.acc.AccService.*;
import static handler.acc.IniAcc.iniTwoWlt;

import static handler.balance.BlsSvs.*;
import static service.CmsBiz.toBigDcmTwoDot;
import static util.acc.AccUti.getAccId;
import static util.algo.CallUtil.call;
import static util.tx.HbntUtil.*;
import static util.tx.dbutil.addObj;
import static util.misc.util2026.*;


/**
 * 资金调整Process
 * {@literal http://localhost:8889/wlt/adjust?adjustType=%2B&changeAmount=99}
 */


@Path("/apiv1/admin/wlt/adjust")


@RolesAllowed({"admin", "Operator"})
@PermitAll
public class AdjustHdr {


    public Object handleRequest(AdjstDto adjstDto) throws Throwable {

        iniTwoWlt(adjstDto.uname);

        //------prcs context
        String accid = getAccId(adjstDto.accountSubType, adjstDto.uname);
        Account acc1 = findByHerbinateLockForUpdtV2(Account.class, accid);
        iniBlss(acc1);
        @DecimalMin(value = "0.00", inclusive = true, message = "余额不能为负数") BigDecimal adjAmt = adjstDto.adjustAmount;


        //-------------add bal
        ChoiceStep<TransactionCode> chooseStep = new ChoiceStep<>();

        chooseStep.addCase(new ConditionBranch(TransactionCode.adjst_crdt, () -> {

            DepositDto dto=new DepositDto();
            dto.accid=accid;
            dto.amt=adjstDto.getAdjustAmount();
            dto.type=TransactionCode.adjst_crdt;
            call(AccService::CrdBal,dto);
         //   deposit(dto);

        }));

        chooseStep.addCase(new ConditionBranch(TransactionCode.payment_rechg, () -> {

            DepositDto dto=new DepositDto();
            dto.accid=accid;
            dto.amt=adjstDto.getAdjustAmount();
            dto.type=TransactionCode.payment_rechg;
            call(AccService::CrdBal,dto);
            //   deposit(dto);

        }));


        //-------------sub amt
        ConditionBranch ct = new ConditionBranch();
        ct.key = adjstDto.transactionCode;
        ct.valueToMatch = TransactionCode.adjst_dbt;
        ct.act = () -> {

            DecBalDto dto=new DecBalDto();
            dto.accid=accid;
            dto.amt=adjstDto.getAdjustAmount();
            dto.type=TransactionCode.adjst_crdt;
            SerializableFun<DecBalDto, Object> dcrsBalFun = AccService::adjustBalanceDecrease;
            call(dcrsBalFun,dto);
          //  adjustBalanceDecrease(acc1, tx2);

        };
        chooseStep.addCase(ct);


//--------------frz
        RunnableThrowing throwingRunnable = () -> {
            Transaction tx2 = new Transaction();

            tx2.setAmount(adjAmt);
            tx2.transactionCode = TransactionCode.adjst_frz.name();

            frzAmt2accWzlog(acc1, tx2);
        };
        chooseStep.addCase(new ConditionBranch(TransactionCode.adjst_frz, throwingRunnable));


        //------------unfze
        chooseStep.addCase(new ConditionBranch(TransactionCode.adjst_unfrz, () -> {
            Transaction tx2 = new Transaction();

            tx2.setAmount(adjAmt);
            tx2.transactionCode = TransactionCode.adjst_unfrz.name();
            unfrzAmt2accWzlog(acc1, tx2);
        }));



        //----run
        Function<TransactionCode, Boolean> cdtnChkr = (TransactionCode chkRitVal) -> {
            return adjstDto.transactionCode.toLowerCase().equals(chkRitVal.name().toLowerCase());
        };
        chooseStep.setCdtnChkr(cdtnChkr);
      //  chooseStep.run();

        NmlStep ns=new NmlStep();
        RunnableThrowing runnablex1=()->{
                    System.out.println("nml step..."); ;
                };
        ns.taskList.add(runnablex1);

        ProcessWkfl prcs=new ProcessWkfl();
        prcs.getAllSteps().add(chooseStep);
        prcs.getAllSteps().add(ns);
        prcs.run();


        //add balanceLog
//        Session session = sessionFactory.getCurrentSession();
//        addLog(adjstDto, acc1, avdBls, newAvdBls, logTag, session);


        return new ApiResponse(acc1);
    }



    private static void addLog(AdjstDto adjstDto, Account acc1, BigDecimal avdBls, BigDecimal newAvdBls, String logTag, Session session) {
        LogBls logBalance = new LogBls();
        logBalance.id = "LogBalance" + getFilenameFrmLocalTimeString();
        logBalance.uname = acc1.accountId;

        logBalance.changeAmount = adjstDto.getAdjustAmount();
        logBalance.amtBefore = toBigDcmTwoDot(avdBls);
        logBalance.newBalance = toBigDcmTwoDot(newAvdBls);
        logBalance.refUniqId = String.valueOf(System.currentTimeMillis());
        logBalance.adjustType = adjstDto.transactionCode;
        logBalance.changeMode = logTag;
        persist(logBalance, session);
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

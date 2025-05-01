package handler.wlt;

import handler.wlt.dto.AdjstDto;
import jakarta.annotation.security.RolesAllowed;

import model.OpenBankingOBIE.*;
import org.hibernate.Session;
import util.algo.Tag;
import util.annos.JwtParam;
import util.ex.ErrAdjstTypeEx;
import entityx.ApiResponse;
import entityx.wlt.LogBls;
//import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.LockModeType;
import jakarta.ws.rs.Path;
import org.springframework.web.bind.annotation.RestController;
import util.algo.Icall;

import java.math.BigDecimal;

import static cfg.AppConfig.sessionFactory;
import static com.alibaba.fastjson2.util.TypeUtils.toBigDecimal;
import static service.CmsBiz.toBigDcmTwoDot;
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

public class AdjustHdr{


    public Object handleRequest(AdjstDto adjstDto) throws Throwable {

var accid=getAccId(adjstDto.AccountSubType,adjstDto.uname);
        Session session = sessionFactory.getCurrentSession();
        Accounts objU = findByHbntDep(Accounts.class, accid, LockModeType.PESSIMISTIC_WRITE, session);

        BigDecimal nowAmt = objU.availableBalance;
        //def is add
        BigDecimal newBls = nowAmt;
        var logTag = "";
        if (adjstDto.TransactionCode.toUpperCase().equals(TransactionCodes.DEBT.name())) {
            newBls = nowAmt.subtract(BigDecimal.valueOf(adjstDto.adjustAmount));
            logTag = "减少";
        } else if (adjstDto.TransactionCode.toUpperCase().equals(TransactionCodes.CRED.name())) {
            newBls = nowAmt.add(BigDecimal.valueOf(adjstDto.adjustAmount));
            logTag = "增加";
        }


//        if (newBls.equals(nowAmt) || adjstDto.adjustType.equals(""))
//            throw new ErrAdjstTypeEx("");

        objU.setAvailableBalance(newBls);
        mergeByHbnt(objU, session);



        //addTx
        Transactions tx=new Transactions();
        tx.transactionId=getUuid();
        tx.accountId= accid.toString();
        tx.creditDebitIndicator= CreditDebitIndicator.CREDIT;
        tx.transactionCode=TransactionCodes.fromCode( adjstDto.TransactionCode);
        tx.amount=toBigDecimal( adjstDto.adjustAmount);
        persistByHibernate(tx,session);


        //add balanceLog

        LogBls logBalance = new LogBls();
        logBalance.id = "LogBalance" + getFilenameFrmLocalTimeString();
        logBalance.uname = objU.accountId;

        logBalance.changeAmount = BigDecimal.valueOf(adjstDto.getAdjustAmount());
        logBalance.amtBefore = toBigDcmTwoDot(nowAmt);
        logBalance.newBalance = toBigDcmTwoDot(newBls);
        logBalance.refUniqId = String.valueOf(System.currentTimeMillis());
        logBalance.adjustType = adjstDto.TransactionCode;
        logBalance.changeMode = logTag;
       persistByHibernate(logBalance, session);


        return new ApiResponse(objU);
    }

    private Object getAccId(String accountSubType, String uname) {
        if(accountSubType== AccountSubType.EMoney.name())
            return  uname;
        else
            return uname+"_"+accountSubType;

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

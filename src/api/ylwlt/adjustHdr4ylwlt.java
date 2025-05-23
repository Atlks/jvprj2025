package api.ylwlt;

import jakarta.annotation.security.RolesAllowed;

import model.OpenBankingOBIE.Account;
import util.algo.Tag;
import util.annos.JwtParam;
import util.ex.ErrAdjstTypeEx;
import entityx.wlt.LogBls4YLwlt;
import entityx.wlt.TransDto;
//import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.LockModeType;
import jakarta.ws.rs.Path;
// org.springframework.web.bind.annotation.RestController;
import util.algo.Icall;

import java.math.BigDecimal;

// static cfg.AppConfig.sessionFactory;

import static cfg.Containr.sessionFactory;
import static service.CmsBiz.toBigDcmTwoDot;
import static util.acc.AccUti.getAccId4ylwlt;
import static util.misc.util2026.getFilenameFrmLocalTimeString;
import static util.tx.HbntUtil.*;

/**
 * 资金调整
 * http://localhost:8889/AddOrdBetHdr?bettxt=龙湖和
 */

   // 默认返回 JSON，不需要额外加 @ResponseBody。
@Tag(name = "ylwlt", description = "")
@Path("/admin/ylwlt/adjust")
@JwtParam(name = "uname")
//@Parameter(name = "adjustType", description = "")    //+ -  Increase Decrease
//@Parameter(name = "changeAmount")
@RolesAllowed({"admin", "Operator"})
@Deprecated   //use adjustHdr
public class adjustHdr4ylwlt implements Icall<TransDto, Object> {

    @Override
    public Object main(TransDto TransDto1) throws Throwable {


        Account objU = findByHbntDep(Account.class, getAccId4ylwlt(TransDto1.uname) , LockModeType.PESSIMISTIC_WRITE, sessionFactory.getCurrentSession());

        BigDecimal nowAmt = objU.interim_Available_Balance;
        //def is add
        BigDecimal newBls = nowAmt;
        var logTag = "";
        if (TransDto1.adjustType.equals("-")) {
            newBls = nowAmt.subtract(TransDto1.getAmount());
            logTag = "减少";
        } else if (TransDto1.adjustType.equals("+")) {
            newBls = nowAmt.add(TransDto1.getAmount());
            logTag = "增加";
        }


        if (newBls.equals(nowAmt) || TransDto1.adjustType.equals(""))
            throw new ErrAdjstTypeEx("");

        objU.setInterim_Available_Balance(newBls);
        mergex(objU, sessionFactory.getCurrentSession());

        //add balanceLog

        LogBls4YLwlt logBalance = new LogBls4YLwlt();
        logBalance.id = "LogBalance4ylwlt_" + getFilenameFrmLocalTimeString();
        logBalance.uname = objU.owner;

        logBalance.changeAmount = TransDto1.getAmount();
        logBalance.amtBefore = toBigDcmTwoDot(nowAmt);
        logBalance.newBalance = toBigDcmTwoDot(newBls);
        logBalance.refUniqId = String.valueOf(System.currentTimeMillis());
        logBalance.adjustType = TransDto1.adjustType;
        logBalance.changeMode = logTag;
       persist(logBalance,sessionFactory.getCurrentSession());


        return objU;
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

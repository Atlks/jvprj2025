package handler.wlt;

import handler.wlt.dto.AdjstDto;
import jakarta.annotation.security.RolesAllowed;
import model.wlt.Accounts;
import util.annos.JwtParam;
import util.annos.Parameter;
import util.ex.ErrAdjstTypeEx;
import entityx.ApiResponse;
import entityx.wlt.LogBls;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.LockModeType;
import jakarta.ws.rs.Path;
import org.springframework.web.bind.annotation.RestController;
import util.algo.Icall;

import java.math.BigDecimal;

import static cfg.AppConfig.sessionFactory;
import static com.alibaba.fastjson2.util.TypeUtils.toBigDecimal;
import static service.CmsBiz.toBigDcmTwoDot;
import static util.tx.HbntUtil.*;
import static util.tx.dbutil.addObj;
import static util.misc.util2026.*;

/**
 * 资金调整
 *  {@literal http://localhost:8889/wlt/adjust?adjustType=%2B&changeAmount=99}
 */

@RestController   // 默认返回 JSON，不需要额外加 @ResponseBody。
@Tag(name = "wlt 钱包")
@Path("/admin/wlt/adjust")
@JwtParam(name = "uname")
@Parameter(name = "adjustType")    //+ -  Increase Decrease
@Parameter(name = "changeAmount")
@RolesAllowed({"admin", "Operator"})

public class AdjustHdr implements Icall<AdjstDto, Object> {

    @Override
    public Object main(AdjstDto TransDto1) throws Throwable {


        Accounts objU = findByHbntDep(Accounts.class, TransDto1.memberAccount, LockModeType.PESSIMISTIC_WRITE, sessionFactory.getCurrentSession());

        BigDecimal nowAmt = objU.availableBalance;
        //def is add
        BigDecimal newBls = nowAmt;
        var logTag = "";
        if (TransDto1.adjustType.equals("-")) {
            newBls = nowAmt.subtract(BigDecimal.valueOf(TransDto1.adjustAmount));
            logTag = "减少";
        } else if (TransDto1.adjustType.equals("+")) {
            newBls = nowAmt.add(BigDecimal.valueOf(TransDto1.adjustAmount));
            logTag = "增加";
        }


        if (newBls.equals(nowAmt) || TransDto1.adjustType.equals(""))
            throw new ErrAdjstTypeEx("");

        objU.setAvailableBalance(newBls);
        mergeByHbnt(objU, sessionFactory.getCurrentSession());

        //add balanceLog

        LogBls logBalance = new LogBls();
        logBalance.id = "LogBalance" + getFilenameFrmLocalTimeString();
        logBalance.uname = objU.AccountId;

        logBalance.changeAmount = BigDecimal.valueOf(TransDto1.getAdjustAmount());
        logBalance.amtBefore = toBigDcmTwoDot(nowAmt);
        logBalance.newBalance = toBigDcmTwoDot(newBls);
        logBalance.refUniqId = String.valueOf(System.currentTimeMillis());
        logBalance.adjustType = TransDto1.adjustType;
        logBalance.changeMode = logTag;
       persistByHibernate(logBalance,sessionFactory.getCurrentSession());


        return new ApiResponse(objU);
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

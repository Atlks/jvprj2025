package api.wlt;

import annos.JwtParam;
import annos.Parameter;
import entityx.LogBls;
import entityx.TransDto;
import entityx.Usr;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.LockModeType;
import jakarta.ws.rs.Path;
import org.springframework.web.bind.annotation.RestController;
import util.algo.Icall;
import util.auth.IsEmptyEx;

import java.math.BigDecimal;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static api.wlt.RechargeCallbackHdr.saveUrlLogBalance;
import static cfg.AppConfig.sessionFactory;
import static com.alibaba.fastjson2.util.TypeUtils.toBigDecimal;
import static service.CmsBiz.toBigDcmTwoDot;
import static util.tx.HbntUtil.*;
import static util.tx.dbutil.addObj;
import static util.tx.dbutil.getObjIni;
import static util.misc.util2026.*;

/**
 * 资金调整
 * http://localhost:8889/AddOrdBetHdr?bettxt=龙湖和
 */

@RestController   // 默认返回 JSON，不需要额外加 @ResponseBody。
@Tag(name = "wlt 钱包")
@Path("/wlt/adjust")
@JwtParam(name = "uname")
@Parameter(name = "adjustType")    //+ -  Increase Decrease
@Parameter(name = "changeAmount")


public class adjustHdr implements Icall<TransDto, Object> {

    @Override
    public Object call(TransDto TransDto1) throws Throwable {


        Usr objU = findByHbntDep(Usr.class, TransDto1.uname, LockModeType.PESSIMISTIC_WRITE, sessionFactory.getCurrentSession());

        BigDecimal nowAmt = objU.getBalance();
        //def is add
        BigDecimal newBls = nowAmt;
        var logTag = "";
        if (TransDto1.adjustType.equals("-")) {
            newBls = nowAmt.subtract(TransDto1.getChangeAmount());
            logTag = "减少";
        } else if (TransDto1.adjustType.equals("+")) {
            newBls = nowAmt.add(TransDto1.getChangeAmount());
            logTag = "增加";
        }


        if (newBls.equals(nowAmt) || TransDto1.adjustType.equals(""))
            throw new ErrAdjstTypeEx("");

        objU.setBalance(newBls);
        mergeByHbnt(objU, sessionFactory.getCurrentSession());

        //add balanceLog

        LogBls logBalance = new LogBls();
        logBalance.id = "LogBalance" + getFilenameFrmLocalTimeString();
        logBalance.uname = objU.uname;

        logBalance.changeAmount = TransDto1.getChangeAmount();
        logBalance.amtBefore = toBigDcmTwoDot(nowAmt);
        logBalance.newBalance = toBigDcmTwoDot(newBls);
        logBalance.refUniqId = String.valueOf(System.currentTimeMillis());
        logBalance.adjustType = TransDto1.adjustType;
        logBalance.changeMode = logTag;
       persistByHibernate(logBalance,sessionFactory.getCurrentSession());


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

package api.wlt;

import annos.JwtParam;
import annos.Parameter;
import entityx.TransDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.Path;
import org.springframework.web.bind.annotation.RestController;
import util.algo.Icall;
import util.auth.AuthService;
import util.auth.IsEmptyEx;

import java.math.BigDecimal;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static api.wlt.RechargeCallbackHdr.saveUrlLogBalance;
import static com.alibaba.fastjson2.util.TypeUtils.toBigDecimal;
import static util.algo.ToXX.parseQueryParams;
import static util.tx.dbutil.addObj;
import static util.tx.dbutil.getObjIni;
import static util.misc.util2026.*;

/** 资金调整
 * http://localhost:8889/AddOrdBetHdr?bettxt=龙湖和
 */

@RestController   // 默认返回 JSON，不需要额外加 @ResponseBody。
@Tag(name = "wlt 钱包")
@Path("/wlt/adjust")
@JwtParam(name = "uname")
@Parameter(name="adjustType")    //+ -  Increase Decrease
@Parameter(name="amt")


public class UpdtBlsHdr implements Icall<TransDto, String> {

    @Override
    public String call(TransDto TransDto1) throws Exception, IsEmptyEx {


        BigDecimal nowAmt= getFieldAsBigDecimal(objU,"balance",0);
        //def is add
        BigDecimal newBls=nowAmt.add(amt);
        if(adjstOp.equals("sub"))
            newBls=nowAmt.subtract(amt);
        objU.put("balance",newBls);
        addObj(objU,saveDirUsrs);

        //add balanceLog
        SortedMap<String, Object> logBalance=new TreeMap<>();
        logBalance.put("id","LogBalance"+getFilenameFrmLocalTimeString());
        logBalance.put("uname",uname);
        logBalance.put("change","增加");
        if(adjstOp.equals("sub"))
            logBalance.put("change","减少");
        logBalance.put("amt",amt);
        logBalance.put("amtBefore",nowAmt);
        logBalance.put("amtAfter",newBls);
        addObj(logBalance,saveUrlLogBalance);


        return uname;
    }
//    public static void main(String[] args) throws Exception {
//      MyCfg.iniCfgFrmCfgfile();
//        Map<String, String> queryParams=new HashMap<>();
//        queryParams.put("adjst","add");  //sub
//        queryParams.put("amt","9");  //sub
//        updtBls("007","add", BigDecimal.valueOf(9),queryParams);
//    }

    private static void updtBls(String uname,String adjstOp,BigDecimal amt,  Map<String, String> queryParams ) throws Exception {

        //add blance
        SortedMap<String, Object> objU = getObjIni(uname, saveDirUsrs);
        if(objU.get("id")==null)
        {
            objU.put("id",uname);
            objU.put("uname",uname);
        }



    }




}

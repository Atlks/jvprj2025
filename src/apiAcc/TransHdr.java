package apiAcc;

import apiWltYinli.LogBlsLogYLwlt;
import apis.BaseHdr;
import apiUsr.Usr;
import com.sun.net.httpserver.HttpExchange;
import jakarta.persistence.LockModeType;
import org.hibernate.Session;
import utilBiz.OrmUtilBiz;

import java.math.BigDecimal;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static apiUsr.RegHandler.saveDirUsrs;
import static com.alibaba.fastjson2.util.TypeUtils.toBigDecimal;

import static util.HbntUtil.*;
import static util.Util2025.encodeJson;
import static util.util2026.*;
import static apiCms.CmsBiz.toBigDcmTwoDot;

/**
 * 从本机钱包转账到盈利钱包
 * http://localhost:8889/AddOrdBetHdr?bettxt=龙湖和
 */
public class TransHdr extends BaseHdr {

    /**
     * 配合 SELECT ... FOR UPDATE 进行行级锁定：
     * 如果你的查询会影响后续的更新操作，并且不希望其他事务修改这些数据，你可以使用事务 + SELECT ... FOR UPDATE：
     * sql
     * Copy
     * Edit
     * START TRANSACTION;
     * SELECT balance FROM accounts WHERE user_id = 1 FOR UPDATE;
     * -- 此时，其他事务无法修改此行数据
     * UPDATE accounts SET balance = balance - 100 WHERE user_id = 1;
     * COMMIT;
     * 这种方式 常用于 银行转账、库存扣减 等需要保证数据一致性的操作。
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        //  System.out.println(com.mysql.cj.jdbc.Driver);
        iniCfgFrmCfgfile();
        LogBls lgbls = new LogBls();
        lgbls.changeAmount = BigDecimal.valueOf(100.5);
        lgbls.uname = "008";
        transToYinliWlt(lgbls);
    }

    private static void transToYinliWlt(LogBls lgblsDto) throws Exception, BalanceNotEnghou {


        try(  Session session = OrmUtilBiz. openSession(saveDirUsrs)){


        //todo start tx
        session.beginTransaction();

        // 获取对象并加悲观锁
//        User user = session.find(User.class, 1, LockModeType.PESSIMISTIC_WRITE);
        //add blance
        String uname = lgblsDto.uname;
        Usr objU =findByHbnt(Usr.class, lgblsDto.uname, LockModeType.PESSIMISTIC_WRITE,session);
        //       getObjById(uname, saveDirUsrs,Usr.class);
        if (objU.id == null) {
            objU.id = uname;
            objU.uname = uname;
        }


        //  放在一起一快存储，解决了十五问题事务。。。
        BigDecimal nowAmt = getFieldAsBigDecimal(objU, "balance", 0);
        if (lgblsDto.getChangeAmount().compareTo(nowAmt) > 0) {
            SortedMap<String, Object> m = new TreeMap<>();
            m.put("method", "transToYinliWlt()");
            m.put("prm", "amt=" + lgblsDto.getChangeAmount() + ",uname=" + uname);
            m.put("nowAmtBls", nowAmt);
            throw new BalanceNotEnghou(encodeJson(m));
        }

        BigDecimal amt = lgblsDto.getChangeAmount();
        BigDecimal newBls = nowAmt.subtract(toBigDecimal(amt));
        objU.balance = newBls;

        BigDecimal nowAmt2 = getFieldAsBigDecimal(objU, "balanceYinliwlt", 0);
        BigDecimal newBls2 = nowAmt2.add(toBigDecimal(amt));
        objU.balanceYinliwlt = newBls2;
        //  updtObj(objU,saveDirUsrs);
        mergeByHbnt(objU,session);

        //------------add balanceLog
        LogBls logBalance = new LogBls();
        logBalance.id = "LogBalance" + getFilenameFrmLocalTimeString();
        logBalance.uname = uname;

        logBalance.changeAmount = lgblsDto.getChangeAmount();
        logBalance.amtBefore = toBigDcmTwoDot(nowAmt);
        logBalance.newBalance = toBigDcmTwoDot(newBls);

        logBalance.changeMode = "减去";
        System.out.println(" add balanceLog ");
        //  addObj(logBalance,saveUrlLogBalance);
         persistByHbnt(logBalance,session);


        //--------------add logBlsYinliWlt
        LogBlsLogYLwlt logBlsYinliWlt = new LogBlsLogYLwlt();
        logBlsYinliWlt.id = "LogBalanceYinliWlt" + getFilenameFrmLocalTimeString();
        logBlsYinliWlt.uname = uname;
        logBlsYinliWlt.changeMode = "增加";
        logBlsYinliWlt.changeAmount = lgblsDto.getChangeAmount();
        logBlsYinliWlt.amtBefore = nowAmt2;
        logBlsYinliWlt.newBalance = newBls2;
        // addObj(logBlsYinliWlt,saveUrlLogBalanceYinliWlt);
        persistByHbnt(logBlsYinliWlt,session);

        session.getTransaction().commit();
        //adjst yinliwlt balnce
        }

    }




    @Override
    public void handle2(HttpExchange exchange) throws Exception {


        if (isNotLogined(exchange)) {
            //need login
            wrtResp(exchange, "needLogin");
            return;
        }

        //blk login ed
        String uname = getcookie("uname", exchange);
        Map<String, String> queryParams = parseQueryParams(exchange.getRequestURI());
        LogBls lgblsDto = new LogBls();
        lgblsDto.changeAmount = new BigDecimal(queryParams.get("amt"));
        lgblsDto.uname = uname;
        transToYinliWlt(lgblsDto);
        wrtResp(exchange, "ok");


    }


}

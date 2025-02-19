package apiAcc;

import entityx.LogBlsLogYLwlt;
import apis.BaseHdr;
import entityx.LogBls;
import entityx.TransDto;
import entityx.Usr;
import com.sun.net.httpserver.HttpExchange;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import utilBiz.OrmUtilBiz;

import java.math.BigDecimal;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static com.alibaba.fastjson2.util.TypeUtils.toBigDecimal;

import static util.HbntUtil.*;
import static util.ToXX.parseQueryParams;
import static util.TransactMng.beginTransaction;
import static util.TransactMng.commitTransaction;
import static util.Util2025.encodeJson;
import static util.Util2025.encodeJsonObj;
import static util.util2026.*;
import static apiCms.CmsBiz.toBigDcmTwoDot;

/**
 * 从本机钱包转账到盈利钱包
 * http://localhost:8889/AddOrdBetHdr?changeAmount=8
 */
//@Component
public class TransHdr extends BaseHdr {
    // 实现 Serializable 接口
    public static final long serialVersionUID = 1L; // 推荐
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
        lgbls.uname = "009";
        //   transToYinliWlt(lgbls);
    }

    public void transToYinliWlt(TransDto lgblsDto) throws Exception, BalanceNotEnghou {
      //  System.out.println("▶\uFE0Ffun " + getCurrentMethodName() + "(lgblsDto=" + encodeJsonObj(lgblsDto));

        Session session = sessionFactory.getCurrentSession();


        //todo start tx
        beginTransaction(session);

        // 获取对象并加悲观锁
//        User user = session.find(User.class, 1, LockModeType.PESSIMISTIC_WRITE);
        //add blance
        String uname = lgblsDto.uname;
        Usr objU = findByHbnt(Usr.class, lgblsDto.uname, LockModeType.PESSIMISTIC_WRITE, session);
        //       getObjById(uname, saveDirUsrs,Usr.class);
        if (objU.id == null) {
            objU.id = uname;
            objU.uname = uname;
        }

        System.out.println("\n\n\n===========减去钱包余额");

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
        mergeByHbnt(objU, session);

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
        persistByHbnt(logBalance, session);


        System.out.println("\n\n\n===========增加盈利钱包余额");
        //--------------add logBlsYinliWlt
        LogBlsLogYLwlt logBlsYinliWlt = new LogBlsLogYLwlt();
        logBlsYinliWlt.id = "LogBalanceYinliWlt" + getFilenameFrmLocalTimeString();
        logBlsYinliWlt.uname = uname;
        logBlsYinliWlt.changeMode = "增加";
        logBlsYinliWlt.changeAmount = lgblsDto.getChangeAmount();
        logBlsYinliWlt.amtBefore = nowAmt2;
        logBlsYinliWlt.newBalance = newBls2;
        // addObj(logBlsYinliWlt,saveUrlLogBalanceYinliWlt);
        persistByHbnt(logBlsYinliWlt, session);


        //adjst yinliwlt balnce


        System.out.println("✅endfun " + getCurrentMethodName());

    }

    @Transactional
    @Override
    public void handle2(HttpExchange exchange) throws Exception {


        //blk login ed
        String uname = getcookie("uname", exchange);
        Map<String, String> queryParams = parseQueryParams(exchange.getRequestURI());
        TransDto lgblsDto = new TransDto();
        lgblsDto.changeAmount = new BigDecimal(queryParams.get("changeAmount"));
        lgblsDto.uname = uname;
        Session session=sessionFactory.getCurrentSession();
        beginTransaction(session);
        transToYinliWlt(lgblsDto);

        commitTransaction(session);
     //   session.getTransaction().commit();
        wrtResp(exchange, "ok");


    }


}

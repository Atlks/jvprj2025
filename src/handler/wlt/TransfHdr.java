package handler.wlt;


import model.OpenBankingOBIE.*;
import org.jetbrains.annotations.NotNull;
import service.YLwltSvs.AccSvs4invstAcc;
import util.algo.Tag;
import util.annos.CookieParam;
import util.annos.注入;
//import cfg.Operation;
import entityx.wlt.TransDto;

//import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;

import jakarta.ws.rs.Path;

import util.algo.Icall;
import service.Trans2YLwltService;

// static cfg.AppConfig.sessionFactory;
import static cfg.Containr.sessionFactory;
import static com.alibaba.fastjson2.util.TypeUtils.toBigDecimal;

import static handler.acc.AccService.subBalFrmWlt;
import static handler.acc.IniAcc.newIvstWltIfNotExist;
import static util.acc.AccUti.getAccid;
import static util.algo.GetUti.getUuid;
import static util.tx.HbntUtil.findByHbntDep;
// static util.proxy.SprUtil.injectAll4spr;

/**
 * 从本机钱包转账到盈利钱包
 * http://localhost:8889/Trans?changeAmount=8
 */
   // 默认返回 JSON，不需要额外加 @ResponseBody。
@Tag(name = "wlt 钱包")
@Path("/wlt/Trans")
//@Operation(summary = "转账操作", example = "/Trans?changeAmount=8")
//@Parameter(name = "uname", description = "用户名（in cookie）", required = true)
//@Parameter(name = "changeAmount", description = "转账金额", required = true)
@CookieParam(name = "uname",description = "用户名",decryKey="a1235678")

public class TransfHdr {
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
     * @throws Exception
     */
//    public static void main(String[] args) throws Exception {
//        //  System.out.println(com.mysql.cj.jdbc.Driver);
//        MyCfg.iniCfgFrmCfgfile();
//        LogBls lgbls = new LogBls();
//        lgbls.changeAmount = BigDecimal.valueOf(100.5);
//        lgbls.uname = "009";
//        //   transToYinliWlt(lgbls);
//    }


    //
    Trans2YLwltService Trans2YLwltService1;

    @注入

    //@Qualifier("RdsFromWltService")
    public Icall RdsFromWltService1;

    @注入

    //@Qualifier("AddMoney2YLWltService")
    public Icall AddMoney2YLWltService1;

    public static ThreadLocal<Account> curLockAcc = new ThreadLocal<>();

    @Transactional

    public Object handleRequest(TransDto lgblsDto) throws Throwable {
        newIvstWltIfNotExist(lgblsDto.uname);

      //  Icall RdsFromWltService1=getObj("RdsFromWltService");
     //   Icall AddMoney2YLWltService1=getObj("AddMoney2YLWltService");
        // 获取对象并加悲观锁

        //add blance   bcs uname frm cookie
        //  lgblsDto.uname=decryptDES( lgblsDto.uname,Key_a1235678);

        String uname = lgblsDto.uname;
        String accid=getAccid(AccountSubType.EMoney.name(),uname);
        Account objU = findByHbntDep(Account.class, accid, LockModeType.PESSIMISTIC_WRITE, sessionFactory.getCurrentSession());

//        if (objU.id == null) {
//            objU.id = uname;
//            objU.uname = uname;
//        }
        curLockAcc.set(objU);


        //subbal frm wlt
        subBalFrmWlt(lgblsDto);


           //crdt to ivst acc
        lgblsDto.amount =lgblsDto.amount.multiply(toBigDecimal("0.9"));
        Transaction tx=new Transaction("trsf2ivstAcc_"+getUuid(), getAccid4ivstAcc(uname),uname, CreditDebitIndicator.CREDIT, lgblsDto.amount);
        tx.transactionCode= TransactionCode.InternalTransfers_exchg.name();
                tx.status= TransactionStatus.BOOKED;
                tx.ChargeAmount=lgblsDto.amount.multiply(toBigDecimal("0.1"));

        AccSvs4invstAcc.crdt(tx);

//        Icall is = Trans2YLwltService1;
//        ((Trans2YLwltService) is).handle(lgblsDto);

        return "ok";
    }

    @NotNull
    private static String getAccid4ivstAcc(String uname) {
        return getAccid(AccountSubType.GeneralInvestment.name(), uname);
    }

    private void injectAll4spr(TransfHdr transHdr) {
    }


}

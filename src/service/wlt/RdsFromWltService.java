package service.wlt;

import jakarta.validation.constraints.NotNull;
import model.wlt.Wallet;
import util.ex.BalanceNotEnghou;
import entityx.wlt.LogBls;
import entityx.wlt.TransDto;
import entityx.usr.Usr;
import util.algo.Icall;

import java.math.BigDecimal;

import static handler.wlt.TransHdr.curLockAcc;
import static cfg.AppConfig.sessionFactory;
import static com.alibaba.fastjson2.util.TypeUtils.toBigDecimal;
import static service.CmsBiz.toBigDcmTwoDot;
import static util.tx.HbntUtil.*;
import static util.misc.util2026.*;

/**
 * 减去钱包余额服务
 */
public class RdsFromWltService  implements Icall<TransDto, Object> {
    /**
     * @param arg
     * @return
     * @throws Exception
     */
    @Override
    @NotNull
    public @NotNull Object main(@NotNull TransDto TransDto88) throws Exception {





        System.out.println("\n\n\n===========减去钱包余额");

        //  放在一起一快存储，解决了十五问题事务。。。
        Wallet objU=  curLockAcc.get();
        if(objU==null)
            objU=TransDto88.lockAccObj;

        BigDecimal nowAmt =objU.availableBalance;
        if (TransDto88.getChangeAmount().compareTo(nowAmt) > 0) {
            BalanceNotEnghou ex = new BalanceNotEnghou("余额不足");
            ex.fun =this.getClass().getName()+"." + getCurrentMethodName();
            ex.funPrm =  TransDto88;
            ex.info="nowAmtBls="+nowAmt;
            throw  ex;
        }

        BigDecimal amt = TransDto88.getChangeAmount();
        BigDecimal newBls = nowAmt.subtract(toBigDecimal(amt));
        objU.availableBalance = newBls;

        mergeByHbnt(objU, sessionFactory.getCurrentSession());

        //------------add balanceLog
        LogBls logBalance = new LogBls();
        logBalance.id = "LogBalance" + getFilenameFrmLocalTimeString();
        logBalance.uname = objU.userId;

        logBalance.changeAmount = TransDto88.getChangeAmount();
        logBalance.amtBefore = toBigDcmTwoDot(nowAmt);
        logBalance.newBalance = toBigDcmTwoDot(newBls);
        logBalance.refUniqId= String.valueOf(System.currentTimeMillis());
        logBalance.changeMode = "减去";
        System.out.println(" add balanceLog ");
        //  addObj(logBalance,saveUrlLogBalance);
        persistByHibernate(logBalance, sessionFactory.getCurrentSession());
        return objU;
    }
}

package service.YLwltSvs;

import util.ex.BalanceNotEnghou;
import entityx.LogBls4YLwlt;
import entityx.TransDto;
import entityx.Usr;
import org.hibernate.Session;
import util.algo.Icall;

import java.math.BigDecimal;

import static api.wlt.TransHdr.curLockAcc;
import static cfg.AppConfig.sessionFactory;
import static com.alibaba.fastjson2.util.TypeUtils.toBigDecimal;
import static service.YLwltSvs.AddMoney2YLWltService.addBlsLog4ylwlt;
import static util.tx.HbntUtil.mergeByHbnt;
import static util.misc.util2026.getCurrentMethodName;

/**
 * 减去钱包余额服务
 */
public class RdsFromYLlllWltService implements Icall<TransDto, Object> {
    /**
     * @param TransDto88
     * @return
     * @throws Exception
     */
    @Override
    public Object call(TransDto TransDto88) throws Exception {


        //--------------chk bls
        System.out.println("\n\n\n===========检测余额");
        Usr objU=  curLockAcc.get();
        if(objU==null)
            objU=TransDto88.lockAccObj;
        BigDecimal nowAmt2 = objU.balanceYinliwlt;

        if (TransDto88.getChangeAmount().compareTo(nowAmt2) > 0) {
            BalanceNotEnghou ex = new BalanceNotEnghou("余额不足");
            ex.fun = this.getClass().getName() + "." + getCurrentMethodName();
            ex.funPrm = TransDto88;
            ex.info = "nowAmtBls=" + nowAmt2;
            throw ex;
        }


        System.out.println("\n\n\n===========减去盈利钱包余额");
        //  放在一起一快存储，解决了十五问题事务。。。
        BigDecimal amt = TransDto88.getChangeAmount();
        BigDecimal nowAmt=objU.getBalanceYinliwlt();
        BigDecimal newBls = nowAmt.subtract(toBigDecimal(amt));
        objU.balance = newBls;
        Session currentSession = sessionFactory.getCurrentSession();
        mergeByHbnt(objU, currentSession);

        //------------add balanceLog
        System.out.println("\n\n\n===========余额流水");
        LogBls4YLwlt logBlsYinliWlt = new LogBls4YLwlt(TransDto88,nowAmt, newBls,"减去");
        addBlsLog4ylwlt(logBlsYinliWlt, currentSession);
        return null;
    }
}

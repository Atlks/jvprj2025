package service.YLwltSvs;


import model.OpenBankingOBIE.Account;
import util.ex.BalanceNotEnghou;
import entityx.wlt.LogBls4YLwlt;
import entityx.wlt.TransDto;
import org.hibernate.Session;
import util.algo.Icall;

import java.math.BigDecimal;

import static handler.wlt.TransfHdr.curLockAcc;
import static cfg.Containr.sessionFactory;
import static com.alibaba.fastjson2.util.TypeUtils.toBigDecimal;
import static service.YLwltSvs.AccSvs4invstAcc.addBlsLog4ylwlt;
import static util.tx.HbntUtil.mergeByHbnt;

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
    public Object main(TransDto TransDto88) throws Exception {


        //--------------chk bls
        System.out.println("\n\n\n===========检测余额");
        Account objU=  curLockAcc.get();
        if(objU==null)
            objU=TransDto88.lockAccObj;
        BigDecimal nowAmt2 = objU.interim_Available_Balance;

        if (TransDto88.getAmount().compareTo(nowAmt2) > 0) {
            throw new BalanceNotEnghou("余额不足"+"nowAmtBls=" + nowAmt2);
//            ex.fun = this.getClass().getName() + "." + getCurrentMethodName();
//            ex.funPrm = TransDto88;
//            ex.info = "nowAmtBls=" + nowAmt2;
//            throw ex;
        }


        System.out.println("\n\n\n===========减去盈利钱包余额");
        //  放在一起一快存储，解决了十五问题事务。。。
        BigDecimal amt = TransDto88.getAmount();
        BigDecimal nowAmt=objU.getInterim_Available_Balance();
        BigDecimal newBls = nowAmt.subtract(toBigDecimal(amt));
        objU.interim_Available_Balance = newBls;
        Session currentSession = sessionFactory.getCurrentSession();
        mergeByHbnt(objU, currentSession);

        //------------add balanceLog
        System.out.println("\n\n\n===========余额流水");
        LogBls4YLwlt logBlsYinliWlt = new LogBls4YLwlt(TransDto88,nowAmt, newBls,"减去");
        addBlsLog4ylwlt(logBlsYinliWlt, currentSession);
        return null;
    }
}

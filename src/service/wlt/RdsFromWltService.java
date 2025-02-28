package service.wlt;

import biz.BalanceNotEnghou;
import entityx.LogBls;
import entityx.ReChgOrd;
import entityx.TransDto;
import entityx.Usr;
import jakarta.persistence.LockModeType;
import org.hibernate.Session;
import util.Icall;

import java.math.BigDecimal;
import java.util.SortedMap;
import java.util.TreeMap;

import static apiAcc.TransHdr.curLockAcc;
import static cfg.AppConfig.sessionFactory;
import static com.alibaba.fastjson2.util.TypeUtils.toBigDecimal;
import static service.CmsBiz.toBigDcmTwoDot;
import static util.HbntUtil.*;
import static util.Util2025.encodeJson;
import static util.util2026.getFieldAsBigDecimal;
import static util.util2026.getFilenameFrmLocalTimeString;

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
    public Object call(TransDto lgblsDto) throws Exception {





        System.out.println("\n\n\n===========减去钱包余额");

        //  放在一起一快存储，解决了十五问题事务。。。
        Usr objU=  curLockAcc.get();
        BigDecimal nowAmt =objU.balance;
        if (lgblsDto.getChangeAmount().compareTo(nowAmt) > 0) {
            SortedMap<String, Object> m = new TreeMap<>();
            m.put("method", "transToYinliWlt()");
            m.put("prm", "amt=" + lgblsDto.getChangeAmount() + ",uname=" + objU.uname);
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
        mergeByHbnt(objU, sessionFactory.getCurrentSession());

        //------------add balanceLog
        LogBls logBalance = new LogBls();
        logBalance.id = "LogBalance" + getFilenameFrmLocalTimeString();
        logBalance.uname = objU.uname;

        logBalance.changeAmount = lgblsDto.getChangeAmount();
        logBalance.amtBefore = toBigDcmTwoDot(nowAmt);
        logBalance.newBalance = toBigDcmTwoDot(newBls);

        logBalance.changeMode = "减去";
        System.out.println(" add balanceLog ");
        //  addObj(logBalance,saveUrlLogBalance);
        persistByHibernate(logBalance, sessionFactory.getCurrentSession());
        return null;
    }
}

package service.wlt;
import static cfg.AppConfig.sessionFactory;

import annos.Parameter;
import entityx.LogBls;
import entityx.TransDto;
import entityx.Usr;
import jdk.jfr.Name;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import util.algo.Icall;

import java.math.BigDecimal;

import static service.CmsBiz.toBigDcmTwoDot;
import static util.tx.HbntUtil.*;
import static util.misc.util2026.getFieldAsBigDecimal;
import static util.misc.util2026.getFilenameFrmLocalTimeString;


/**
 *  增加钱包余额服务
 *
 *  先添加日志可防止重复添加，在更新余额即可
 *  referenceId 关联充值订单，防止重复加钱，格式使用urlqrystr模式 ,序列化oo了
 * 防并发处理，确保 同一个 rechargeId 只处理一次
 *
 *   AddMoneyToWltService
 *  @author at

 *   @return 计算结果
 *   @throws Exception
 *  @code
 */
@Data
@Component
@Lazy
@Parameter(name = "amt", description = "金额")
@Parameter(name = "uname",value = "$curuser")
@Parameter(name = "lockAccObj")
@NoArgsConstructor
public class AddMoneyToWltService   implements Icall<TransDto, Object> {
    public AddMoneyToWltService(BigDecimal amt,String uname,Usr lockAccObj) {
    }

    @Name("add")
    public Object main2(TransDto args) throws Exception{

        return null;
    }
    public Object main(TransDto TransDto88 ) throws Exception {


        String uname = TransDto88.uname;
        BigDecimal amt = TransDto88.getAmt();
        Session session=sessionFactory.getCurrentSession();
        Usr    objU=TransDto88.lockAccObj;
        BigDecimal nowAmt =objU.getBalance();
        BigDecimal newBls = nowAmt.add(amt);


        //==================add balanceLog
        LogBls logBalance = new LogBls();
        logBalance.id = "LogBalance" + getFilenameFrmLocalTimeString();
        logBalance.uname = uname;
        logBalance.changeTime = System.currentTimeMillis();
        logBalance.changeType = "充值";  //充值增加
        logBalance.changeMode = "增加";
        logBalance.changeAmount = amt;
        logBalance.amtBefore = toBigDcmTwoDot(nowAmt);
        logBalance.newBalance = toBigDcmTwoDot(newBls);
        logBalance.refUniqId=TransDto88.refUniqId;
        System.out.println(" add balanceLog ");
        persistByHibernate(logBalance, session);




        //=================updt
        objU.balance = toBigDcmTwoDot(newBls);
        mergeByHbnt(objU, session);


        
        
        mm2();
        System.out.println("1217");

        //  System.out.println("✅endfun updtBlsByAddChrg()");
        return  objU;
    }

    public void mm2() {
    }


}

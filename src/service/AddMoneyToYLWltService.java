//package apiWltYinli;

package service;

import entityx.LogBls;
import entityx.ReChgOrd;
import entityx.Usr;
import lombok.Data;
import org.hibernate.Session;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import util.Iservice;

import java.math.BigDecimal;

import static cfg.AppConfig.sessionFactory;
import static service.CmsBiz.toBigDcmTwoDot;
import static util.HbntUtil.*;
import static util.util2026.getFieldAsBigDecimal;
import static util.util2026.getFilenameFrmLocalTimeString;
@Data
@Component
@Lazy
public class AddMoneyToYLWltService   implements Iservice<ReChgOrd> {

    public   void doAct(ReChgOrd objChrg ) throws Exception {
        //  printLn("\n▶️fun updtBlsByAddChrg(", BLUE);
        //    printLn("objChrg= " + encodeJson(objChrg), GREEN);
        //    System.out.println(")");
        //  printlnx();
        //   System.out.println("\r\n ▶fun updtBlsByAddChrg(objChrg= "+encodeJson(objChrg));

        String uname = objChrg.uname;
        BigDecimal amt = objChrg.getAmt();

        Session session=sessionFactory.getCurrentSession();

        Usr objU = findByHbnt(Usr.class, uname, session);
        if (objU.id == null) {
            objU.id = uname;
            objU.uname = uname;
        }

        BigDecimal nowAmt = getFieldAsBigDecimal(objU, "balance", 0);

        BigDecimal newBls = nowAmt.add(amt);
        objU.balance = toBigDcmTwoDot(newBls);
        mergeByHbnt(objU, session);

        //add balanceLog
        LogBls logBalance = new LogBls();
        logBalance.id = "LogBalance" + getFilenameFrmLocalTimeString();
        logBalance.uname = uname;
        logBalance.changeTime = System.currentTimeMillis();
        logBalance.changeType = "充值";  //充值增加
        logBalance.changeMode = "增加";
        logBalance.changeAmount = amt;
        logBalance.amtBefore = toBigDcmTwoDot(nowAmt);
        logBalance.newBalance = toBigDcmTwoDot(newBls);
        System.out.println(" add balanceLog ");
        persistByHibernate(logBalance, session);

        //  System.out.println("✅endfun updtBlsByAddChrg()");
    }



}

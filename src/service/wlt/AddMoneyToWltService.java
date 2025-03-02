package service.wlt;
import static cfg.AppConfig.sessionFactory;

import entityx.LogBls;
import entityx.ReChgOrd;
import entityx.TransDto;
import entityx.Usr;
import jdk.jfr.Name;
import lombok.Data;
import org.hibernate.Session;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import util.Icall;

import java.math.BigDecimal;

import static service.CmsBiz.toBigDcmTwoDot;
import static util.HbntUtil.*;
import static util.util2026.getFieldAsBigDecimal;
import static util.util2026.getFilenameFrmLocalTimeString;
@Data
@Component
@Lazy
public class AddMoneyToWltService   implements Icall<TransDto, Object> {

    @Name("add")
    public Object call2(TransDto args) throws Exception{

        return null;
    }
    public Object call(TransDto TransDto88 ) throws Exception {
        //  printLn("\n▶️fun updtBlsByAddChrg(", BLUE);
        //    printLn("objChrg= " + encodeJson(objChrg), GREEN);
        //    System.out.println(")");
        //  printlnx();
        //   System.out.println("\r\n ▶fun updtBlsByAddChrg(objChrg= "+encodeJson(objChrg));

        String uname = TransDto88.uname;
        BigDecimal amt = TransDto88.getAmt();

        Session session=sessionFactory.getCurrentSession();


        Usr    objU=TransDto88.lockAccObj;

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
        
        
        mm2();
        System.out.println("1217");

        //  System.out.println("✅endfun updtBlsByAddChrg()");
        return null;
    }

    public void mm2() {
    }


}

//package apiWltYinli;

package service.YLwltSvs;

import entityx.usr.Usr;
import entityx.wlt.LogBls4YLwlt;
import entityx.wlt.TransDto;
import lombok.Data;
import org.hibernate.Session;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import util.algo.Icall;

import java.math.BigDecimal;

import static handler.wlt.TransHdr.curLockAcc;
import static cfg.AppConfig.sessionFactory;
import static service.CmsBiz.toBigDcmTwoDot;
import static util.tx.HbntUtil.*;
import static util.misc.util2026.getFilenameFrmLocalTimeString;
@Data
@Service
@Lazy
public class AddMoney2YLWltService implements Icall<TransDto,Object> {

    public Object main(TransDto TransDto1 ) throws Exception {




        System.out.println("\n\n\n===========增加盈利钱包余额");



        String uname = TransDto1.uname;
        BigDecimal amt = TransDto1.changeAmount;

        Session session=sessionFactory.getCurrentSession();

       Usr objU=  curLockAcc.get();
       if(objU==null)
           objU=TransDto1.lockAccObj;

        BigDecimal nowAmt =objU.getBalanceYinliwlt();
              //  getFieldAsBigDecimal(objU, "balance", 0);

        BigDecimal newBls = nowAmt.add(amt);
        objU.balanceYinliwlt = toBigDcmTwoDot(newBls);
        mergeByHbnt(objU, session);

        LogBls4YLwlt logBlsYinliWlt = new LogBls4YLwlt(TransDto1,nowAmt, newBls,"增加");
        addBlsLog4ylwlt(logBlsYinliWlt, session);
        //  System.out.println("✅endfun updtBlsByAddChrg()");
        return null;
    }

    public static void addBlsLog4ylwlt(LogBls4YLwlt logBlsYinliWlt, Session session) {
        //--------------add logBlsYinliWlt

        logBlsYinliWlt.id = "LogBalanceYinliWlt" + getFilenameFrmLocalTimeString();
     //   logBlsYinliWlt.uname = lgblsDto.uname;
     //   logBlsYinliWlt.changeMode = "增加";
    //    logBlsYinliWlt.changeAmount = lgblsDto.getChangeAmount();

        // addObj(logBlsYinliWlt,saveUrlLogBalanceYinliWlt);
        persistByHibernate(logBlsYinliWlt, session);
    }


}

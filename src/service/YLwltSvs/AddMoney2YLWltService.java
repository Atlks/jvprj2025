//package apiWltYinli;

package service.YLwltSvs;

import entityx.*;
import lombok.Data;
import org.hibernate.Session;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import util.Icall;

import java.math.BigDecimal;

import static apiAcc.TransHdr.curLockAcc;
import static cfg.AppConfig.sessionFactory;
import static service.CmsBiz.toBigDcmTwoDot;
import static util.HbntUtil.*;
import static util.util2026.getFieldAsBigDecimal;
import static util.util2026.getFilenameFrmLocalTimeString;
@Data
@Service
@Lazy
public class AddMoney2YLWltService implements Icall<TransDto,Object> {

    public Object call(TransDto lgblsDto ) throws Exception {




        System.out.println("\n\n\n===========增加盈利钱包余额");



        String uname = lgblsDto.uname;
        BigDecimal amt = lgblsDto.changeAmount;

        Session session=sessionFactory.getCurrentSession();

       Usr objU=  curLockAcc.get();

        BigDecimal nowAmt =objU.getBalanceYinliwlt();
              //  getFieldAsBigDecimal(objU, "balance", 0);

        BigDecimal newBls = nowAmt.add(amt);
        objU.balanceYinliwlt = toBigDcmTwoDot(newBls);
        mergeByHbnt(objU, session);


        //--------------add logBlsYinliWlt
        LogBlsLogYLwlt logBlsYinliWlt = new LogBlsLogYLwlt();
        logBlsYinliWlt.id = "LogBalanceYinliWlt" + getFilenameFrmLocalTimeString();
        logBlsYinliWlt.uname = uname;
        logBlsYinliWlt.changeMode = "增加";
        logBlsYinliWlt.changeAmount = lgblsDto.getChangeAmount();
        logBlsYinliWlt.amtBefore = nowAmt;
        logBlsYinliWlt.newBalance = newBls;
        // addObj(logBlsYinliWlt,saveUrlLogBalanceYinliWlt);
        persistByHibernate(logBlsYinliWlt, session);
        //  System.out.println("✅endfun updtBlsByAddChrg()");
        return null;
    }



}

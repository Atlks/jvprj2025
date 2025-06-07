package handler.cms;

import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Path;
import model.OpenBankingOBIE.*;
import model.obieErrCode.InsufficientFunds;
import org.hibernate.Session;


import java.math.BigDecimal;

import static cfg.Containr.sessionFactory;
import static util.acc.AccUti.getAccId;
import static util.acc.AccUti.getAccId4ylwlt;
import static util.algo.GetUti.getUuid;
import static util.tx.HbntUtil.*;

//   cms/WthdrCmsHdl

public class WthdrCmsHdl {
//@EventListener

    @Path("/apiv1/cms/WthdrCmsHdl")
    public Object handleRequest(@NotNull WthdCmsRqdto dto) throws Throwable {
        //总体流程图
        //[佣金账户] -减-> 金额 ——> 记录交易（支出）-->[盈利钱包账户] +加-> 金额 ——> 记录交易（收入）

        //---------sub agt cms acc
        //5. 获取代理佣金账户（AgtCms）
        String agtAccId=getAccId(AccountSubType.agtCms.name(), dto.uname);
        Account agtAcc= findById(Account.class, agtAccId);
        //6. 从佣金账户扣除提现金额
        BigDecimal amt = agtAcc.interim_Available_Balance;
        if(amt.compareTo(BigDecimal.ZERO)<=0){
           throw new InsufficientFunds("Amount is negative");
        }
        agtAcc.interim_Available_Balance = agtAcc.interim_Available_Balance.subtract(amt);
        agtAcc.setInterimBookedBalance(agtAcc.getInterimBookedBalance().subtract(amt));
        agtAcc.setAvlbBalcCrdtDbtIndctr(CreditDebitIndicator.DEBIT);
        mergex(agtAcc);
        //7. 记录一笔支出交易（佣金账户扣款）
        Transaction tx2=new Transaction("wthdrCms_"+getUuid(),agtAccId,dto.uname, CreditDebitIndicator.DEBIT,amt);
        tx2.setTransactionCode(TransactionCode.Service_Cms_wthdr);
        tx2.setStatus(TransactionStatus.BOOKED);
        persist(tx2);


        //---add to ylwlt
        //2. 获取用户盈利钱包账户（YLWLT）
        String accId4ylwlt = getAccId4ylwlt(dto.uname);
        Account agtAccYlwlt=  findById(Account.class ,accId4ylwlt);

        //------------------add amt 2 bal to Ylwlt
        //3. 将提现金额加入钱包账户余额
        agtAccYlwlt.interim_Available_Balance = agtAccYlwlt.interim_Available_Balance.add(amt);
        agtAccYlwlt.setInterimBookedBalance(agtAccYlwlt.getInterimBookedBalance().add(amt));
        mergex(agtAccYlwlt);
        //4. 记录一笔收入交易（钱包到账记录）
        Transaction tx=new Transaction("wthdrCmsAdd_"+getUuid(),accId4ylwlt,dto.uname, CreditDebitIndicator.CREDIT,amt);
        tx.setStatus(TransactionStatus.BOOKED);
        tx.setTransactionCode(TransactionCode.Service_Cms_wthdr);
        persist(tx);






        return  "ok";

    }

}
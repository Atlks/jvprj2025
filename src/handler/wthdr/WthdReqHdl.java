package handler.wthdr;

import cfg.AppConfig;
import entityx.usr.WithdrawDto;
import jakarta.ws.rs.core.Context;
import model.OpenBankingOBIE.AccountType;
import model.OpenBankingOBIE.Accounts;
import model.OpenBankingOBIE.CreditDebitIndicator;
import model.OpenBankingOBIE.Transactions;


import util.ex.BalanceNotEnghou;
import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;
import util.tx.findByIdExptn_CantFindData;

import java.math.BigDecimal;

import static cfg.AppConfig.sessionFactory;
import static cfg.MyCfg.iniContnr;
import static service.CmsBiz.toBigDcmTwoDot;
import static util.algo.GetUti.getUuid;
import static util.auth.AuthUtil.getCurrentUser;
import static util.log.ColorLogger.RED_bright;
import static util.log.ColorLogger.colorStr;
import static util.misc.util2026.copyProps;
import static util.misc.util2026.getCurrentMethodName;
import static util.tx.HbntUtil.*;
import static util.tx.TransactMng.commitTsact;
import static util.tx.TransactMng.openSessionBgnTransact;

/**
 * 提现申请    /wthdr/WthdReqHdl
 */
public class WthdReqHdl implements RequestHandler<WithdrawDto, ApiGatewayResponse> {
    /**

     * @param context
     * @return
     * @throws Throwable
     */
    @Override
    public ApiGatewayResponse handleRequest(WithdrawDto dtoWithdrawDto, Context context) throws Throwable {

        System.out.println("\r\n\n\n=============⚡⚡bizfun  " + colorStr("检测余额", RED_bright));
        dtoWithdrawDto.setUserId(getCurrentUser());
        String uname = getCurrentUser();
        Accounts YLwlt11;
        String uname1 = dtoWithdrawDto.uname;
        String acc_id = uname + "_" + AccountType.YlWlt;
        try{

            YLwlt11 = findByHerbinateLockForUpdtV2(Accounts.class, acc_id, sessionFactory.getCurrentSession());
        } catch (findByIdExptn_CantFindData e) {
            iniYlwlt(uname1);
            YLwlt11 = findByHerbinateLockForUpdtV2(Accounts.class, acc_id, sessionFactory.getCurrentSession());
        }


        BigDecimal nowAmt2 = YLwlt11.availableBalance;

        if (dtoWithdrawDto.getAmount().compareTo(nowAmt2) > 0) {
            BalanceNotEnghou ex = new BalanceNotEnghou("余额不足");
            ex.fun = this.getClass().getName() + "." + getCurrentMethodName();
            ex.funPrm = dtoWithdrawDto;
            ex.info = "nowAmtBls=" + nowAmt2;
            throw ex;
        }


        Transactions ord=new Transactions();
        copyProps(dtoWithdrawDto,ord);
        ord.transactionId="wthdr_"+getUuid();
        ord.creditDebitIndicator= CreditDebitIndicator.DEBIT;

        ord.amount =dtoWithdrawDto.amount;

        ord.id =ord.transactionId;
        ord.uname= uname1;
        ord.accountId=acc_id;
        Object ord1 = persistByHibernate(ord, sessionFactory.getCurrentSession());




        //=======================减少盈利钱包的有效余额,增加冻结金额
        //   //adjst yinliwlt balnce
        //----------------------sub blsAvld   blsFreez++
        String mthBiz = colorStr("减少盈利钱包的有效余额,增加冻结金额", RED_bright);
        System.out.println("\r\n\n\n=============⚡⚡bizfun  " + mthBiz);
        BigDecimal newBls2 = nowAmt2.subtract(dtoWithdrawDto.getAmount());
        YLwlt11.availableBalance = toBigDcmTwoDot(newBls2);

        BigDecimal nowAmtFreez = toBigDcmTwoDot(YLwlt11.frozenAmount);
        YLwlt11.frozenAmount = toBigDcmTwoDot(nowAmtFreez.add(dtoWithdrawDto.getAmount()));
        Accounts usr = mergeByHbnt(YLwlt11, sessionFactory.getCurrentSession());
        return new ApiGatewayResponse(
                ord1);


    }

    public static void iniYlwltIfNotExist(String uname1) {

        try{
            var wlt=findByHerbinate(Accounts.class, uname1+"_"+AccountType.YlWlt, sessionFactory.getCurrentSession());
        } catch (findByIdExptn_CantFindData e) {

            iniYlwlt(  uname1);
        }
    }

    public static void iniYlwlt(String uname1) {

        Accounts yLwlt=new Accounts(uname1+"_"+AccountType.YlWlt);
      //  yLwlt.userId= uname1;
        yLwlt.uname=uname1;
        persistByHibernate(yLwlt,sessionFactory.getCurrentSession());

    }


    public static void main(String[] args) throws Throwable {
        new AppConfig().sessionFactory();//ini sessFctr
        //ini contnr 4cfg,, svrs
        iniContnr();

        //============aop trans begn
        openSessionBgnTransact();
        iniYlwlt("666");
        //  persistByHibernate(o, AppConfig.sessionFactory.getCurrentSession());


        commitTsact();
    }
}

package handler.wthdr;

import cfg.MainStart;
import entityx.usr.WithdrawDto;
import entityx.usr.WithdrawalPassword;
import jakarta.ws.rs.Path;
import model.OpenBankingOBIE.TransactionCode;
import util.algo.EncodeUtil;
import util.model.Context;
import model.OpenBankingOBIE.Account;
import model.OpenBankingOBIE.CreditDebitIndicator;
import model.OpenBankingOBIE.Transaction;


import util.ex.BalanceNotEnghou;
import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;
import util.tx.findByIdExptn_CantFindData;

import java.math.BigDecimal;

// static cfg.AppConfig.sessionFactory;
import static cfg.Containr.sessionFactory;
import static cfg.MainStart.iniContnr;
import static handler.acc.IniAcc.iniIvstAcc;
import static service.CmsBiz.toBigDcmTwoDot;
import static util.acc.AccUti.getAccId4ylwlt;
import static util.algo.GetUti.getUuid;
import static util.auth.AuthUtil.getCurrentUser;
import static util.log.ColorLogger.RED_bright;
import static util.log.ColorLogger.colorStr;
import static util.misc.util2026.copyProps;
import static util.tx.HbntUtil.*;
import static util.tx.TransactMng.commitx;
import static util.tx.TransactMng.beginx;

/**
 * 提现申请    /wthdr/WthdReqHdl
 */
@Path("/apiv1/wthdr/WthdReqHdl")
public class WthdReqHdl implements RequestHandler<WithdrawDto, ApiGatewayResponse> {
    /**

     * @param context
     * @return
     * @throws Throwable
     */
    @Override
    public ApiGatewayResponse handleRequest(WithdrawDto dtoWithdrawDto, Context context) throws Throwable {

        //校验密码
        WithdrawalPassword wp = findById(WithdrawalPassword.class, dtoWithdrawDto.getUname(), sessionFactory.getCurrentSession());
        if (wp == null || wp.getEncryptedPassword() == null) {
            throw new RuntimeException("提现密码未设置");
        }

        // 用户输入的密码
        String inputPwd = dtoWithdrawDto.getPwd(); // 例如前端传来的密码

        // 对输入密码进行 MD5 加密（与你存储方式保持一致）
        String encodedInputPwd = EncodeUtil.encodeMd5(inputPwd);
        // 比对加密后的密码是否一致
        if (!encodedInputPwd.equalsIgnoreCase(wp.getEncryptedPassword())) {
            throw new RuntimeException("提现密码错误");
        }

        System.out.println("\r\n\n\n=============⚡⚡bizfun  " + colorStr("检测余额", RED_bright));
        dtoWithdrawDto.setUserId(getCurrentUser());
        String uname = getCurrentUser();
        Account YLwlt11;
        String uname1 = dtoWithdrawDto.uname;
        String acc_id = getAccId4ylwlt(uname1);
        try{

            YLwlt11 = findByHerbinateLockForUpdtV2(Account.class, acc_id, sessionFactory.getCurrentSession());
        } catch (findByIdExptn_CantFindData e) {
            iniIvstAcc(uname1);
            YLwlt11 = findByHerbinateLockForUpdtV2(Account.class, acc_id, sessionFactory.getCurrentSession());
        }


        BigDecimal nowAmt2 = YLwlt11.interim_Available_Balance;

        if (dtoWithdrawDto.getAmount().compareTo(nowAmt2) > 0) {
             throw  new BalanceNotEnghou("余额不足"+"nowAmtBls=" + nowAmt2);
//            ex.fun = this.getClass().getName() + "." + getCurrentMethodName();
//            ex.funPrm = dtoWithdrawDto;
//            ex.info = "nowAmtBls=" + nowAmt2;
//            throw ex;
        }


        Transaction tx1=new Transaction();
        copyProps(dtoWithdrawDto,tx1);
        tx1=new Transaction("wthdr_"+getUuid(),acc_id,uname1, CreditDebitIndicator.DEBIT,dtoWithdrawDto.amount);
        tx1.transactionId="wthdr_"+getUuid();
        tx1.creditDebitIndicator= CreditDebitIndicator.DEBIT;

        tx1.amount =dtoWithdrawDto.amount;

        tx1.id =tx1.transactionId;
        tx1.owner = uname1;
        tx1.accountId=acc_id;
        tx1.setTransactionCode(TransactionCode.Payment_wthdr);
        Object ord1 = persist(tx1, sessionFactory.getCurrentSession());




        //=======================减少盈利钱包的有效余额,增加冻结金额
        //   //adjst yinliwlt balnce
        //----------------------sub blsAvld   blsFreez++
        String mthBiz = colorStr("减少盈利钱包的有效余额,增加冻结金额", RED_bright);
        System.out.println("\r\n\n\n=============⚡⚡bizfun  " + mthBiz);
        BigDecimal newBls2 = nowAmt2.subtract(dtoWithdrawDto.getAmount());
        YLwlt11.interim_Available_Balance = toBigDcmTwoDot(newBls2);

        BigDecimal nowAmtFreez = toBigDcmTwoDot(YLwlt11.frozenAmount);
        YLwlt11.frozenAmount = toBigDcmTwoDot(nowAmtFreez.add(dtoWithdrawDto.getAmount()));
        Account usr = mergex(YLwlt11, sessionFactory.getCurrentSession());
        return new ApiGatewayResponse(
                ord1);


    }




    public static void main(String[] args) throws Throwable {
        new MainStart().sessionFactory();//ini sessFctr
        //ini contnr 4cfg,, svrs
        iniContnr();

        //============aop trans begn
        beginx();
        iniIvstAcc("666");
        //  persistByHibernate(o, AppConfig.sessionFactory.getCurrentSession());


        commitx();
    }
}

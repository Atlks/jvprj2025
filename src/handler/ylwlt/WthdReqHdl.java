package handler.ylwlt;

import entityx.usr.WithdrawDto;
import jakarta.persistence.LockModeType;
import jakarta.ws.rs.core.Context;
import model.pay.WthdrOrdRcd;
import model.wlt.YLwlt;
import util.ex.BalanceNotEnghou;
import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;

import java.math.BigDecimal;

import static cfg.AppConfig.sessionFactory;
import static util.auth.AuthUtil.getCurrentUser;
import static util.log.ColorLogger.RED_bright;
import static util.log.ColorLogger.colorStr;
import static util.misc.util2026.copyProps;
import static util.misc.util2026.getCurrentMethodName;
import static util.tx.HbntUtil.findByHbntDep;
import static util.tx.HbntUtil.persistByHibernate;

/**
 * 提现申请    /ylwlt/WthdReqHdl
 */
public class WthdReqHdl implements RequestHandler<WithdrawDto, ApiGatewayResponse> {
    /**
     * @param param
     * @param context
     * @return
     * @throws Throwable
     */
    @Override
    public ApiGatewayResponse handleRequest(WithdrawDto dtoWithdrawDto, Context context) throws Throwable {

        System.out.println("\r\n\n\n=============⚡⚡bizfun  " + colorStr("检测余额", RED_bright));
        dtoWithdrawDto.setUserId(getCurrentUser());
        String uname = getCurrentUser();


        YLwlt objU = findByHbntDep(YLwlt.class, uname, LockModeType.PESSIMISTIC_WRITE, sessionFactory.getCurrentSession());

        BigDecimal nowAmt2 = objU.availableBalance;

        if (dtoWithdrawDto.getAmount().compareTo(nowAmt2) > 0) {
            BalanceNotEnghou ex = new BalanceNotEnghou("余额不足");
            ex.fun = this.getClass().getName() + "." + getCurrentMethodName();
            ex.funPrm = dtoWithdrawDto;
            ex.info = "nowAmtBls=" + nowAmt2;
            throw ex;
        }


        WthdrOrdRcd ord=new WthdrOrdRcd();
        copyProps(dtoWithdrawDto,ord);
        ord.amt=dtoWithdrawDto.amount;
        ord.endToEndId=ord.id;
        ord.uname=dtoWithdrawDto.uname;
        return new ApiGatewayResponse( persistByHibernate(ord, sessionFactory.getCurrentSession()));


    }
}

package handler.rechg;


import handler.statmt.CrudFun;
import model.OpenBankingOBIE.*;
import model.obieErrCode.FieldInvalidEx;
import model.usr.Usr;
import util.algo.Tag;
import util.annos.CookieParam;

import static cfg.Containr.sessionFactory;
import static handler.acc.AccDao.subAmtUpdtBls;
// static handler.acc.AccService.subAmtUpdtBls;
// static handler.acc.AccService.updtAccSetAvdblsNBkbls;
import static java.time.LocalTime.now;


//import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;


import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import lombok.extern.slf4j.Slf4j;
// org.springframework.stereotype.Component;
import util.algo.Icall;
import util.evt.RchgEvt;
import util.evtdrv.EvtHlpr;
import util.model.openbank.BalanceTypes;
import util.serverless.ApiGatewayResponse;
import util.tx.findByIdExptn_CantFindData;

import java.math.BigDecimal;

import static util.acc.AccUti.getAccid;
import static util.auth.AuthUtil.getCurrentUser;
import static util.evtdrv.EvtHlpr.publishEvent;
import static util.tx.HbntUtil.*;
import static util.tx.dbutil.addObj;
import static util.misc.util2026.*;


@Slf4j


public class RchgHdr {


    /**
     * http://localhost:8889/RechargeHdr?amt=888
     *
     * @return
     * @throws Exception
     */

    @Path("/apiv1/pay/recharge")
    //@DeclareRoles({"ADMIN", "USER"})

    @Transactional
    @RolesAllowed({"", "USER"})  // 只有 ADMIN 和 USER 角色可以访问
    public Object rchg(@BeanParam RechgDto dto) throws Exception, findByIdExptn_CantFindData, FieldInvalidEx {
        System.out.println("handle2.sessfac=" + sessionFactory);
        System.out.println("regchg hrl.hadler3()");
        //blk login ed
        //---------------------add rechg tx
        Transaction ts = new Transaction();
        ts.accountId = getAccid(AccountSubType.EMoney, dto.owner);
        ts.setAmountVldChk(dto.amount);
        ts.id = "ordChrg" + getFilenameFrmLocalTimeString();
        ts.transactionId = ts.id;
        ts.creditDebitIndicator = CreditDebitIndicator.CREDIT;
        ts.setTransactionCode(TransactionCode.payment_rechg);
        //amt alreay have in dto

        // ts.timestamp = System.currentTimeMillis();
        ts.addressLine = dto.addressLine;
        ts.owner = getCurrentUser();

ts.setVipLevAftrDpst(dto.vipLevAftrDpst);
        Usr u = findById(Usr.class, ts.owner);

        ts.ownerParent = u.invtr;
        ts.setReceipt_image(dto.getReceipt_image());


        Object ts1 = persist(ts, sessionFactory.getCurrentSession());

        //----------------------sub acc avdBls,add frz bls
        //   Account acc=findById(Account.class,getAccid(AccountSubType.EMoney,dto.owner));
        //  subAvdBlsUpdtAcc(acc,dto.amount);
        try {
            //  EvtHlpr. publishEvent(RchgEvt.evtlist4rchg, ts);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return new ApiGatewayResponse(ts1);
        //   wrtResp(exchange, encodeJson(r));


    }


//    @Override
//    public void handle(HttpExchange exchange) throws IOException {
//        try {
//            handlex(exchange);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

//    /**
//     * @param exchange
//     * @throws Throwable
//     */
//    @Override
//    protected void handle2(HttpExchange exchange) throws Throwable {
//
//    }

    /**
     * handle() 直接调用 handlex()，没有经过代理。
     * 解决办法
     *
     * 在 handle() 方法中，通过 this 转换成接口再调用 handlex()，确保它走代理：
     *
     * @Override
     * public void handle(HttpExchange exchange) throws IOException {
     *     try {
     *         ((HttpHandlerX) Proxy.newProxyInstance(
     *             this.getClass().getClassLoader(),
     *             this.getClass().getInterfaces(),
     *             new JdkDynamicProxySmpl(this)
     *         )).handlex(exchange);
     *     } catch (Exception e) {
     *         throw new RuntimeException(e);
     */


    //    public AddOrdChargeHdr() {
//    }


//    @RolesAllowed({"", "USER"})  // 只有 ADMIN 和 USER 角色可以访问
//    public Object addChgOrd(@NotNull(message = "ordDto is required")  ChgOrd ord) throws Exception {
//        log.info("Processing addChgOrd...");
//        String now = String.valueOf(now());
//        // 使用 try-with-resources 自动关闭
//        //try(  Session session = OrmUtilBiz. openSession(saveUrlOrdChrg)) {
//        //  om.jdbcurl=saveDirUsrs;
//        //todo start tx
//        Session session = sessionFactory.getCurrentSession();
//
//      return  persistByHibernate(ord, session);
//
//        //   }
//        //    addObj(ord, saveUrlOrdChrg,OrdChrg.class);
//    }

//    public static void main(String[] args) throws Exception {
//        iniCfgFrmCfgfile();
//        // drvMap.put("com.mysql.cj.jdbc.Driver", "org.h2.Driver");
//        Map<String, Object> queryParams = new HashMap<>();
//        queryParams.put("amt", new BigDecimal("888"));
//
//        OrdChrg ord = new OrdChrg();
//        ord.uname = "009";
//        ord.amt = new BigDecimal("888");
//        ord.timestamp = System.currentTimeMillis();
//        ord.id = "ordChrg" + getFilenameFrmLocalTimeString();
//        System.out.println("ordid=" + ord.id
//        );
//        //   addOrdChg(ord);
//    }

}

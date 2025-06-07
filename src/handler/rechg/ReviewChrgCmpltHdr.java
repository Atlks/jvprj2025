package handler.rechg;

import cfg.IniCfg;
import entityx.wlt.TransDto;
import handler.agt.RchgEvtHdl;
import handler.rechg.dto.ReviewChrgRqdto;
import model.OpenBankingOBIE.Account;
import model.OpenBankingOBIE.AccountSubType;
import model.OpenBankingOBIE.TransactionStatus;
import model.OpenBankingOBIE.Transaction;


import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import orgx.uti.context.ThreadContext;
import util.model.Context;

import org.hibernate.Session;

import service.wlt.AccService;
import util.algo.Icall;
import util.excptn.AreadyProcessedEx;
import util.model.openbank.BalanceTypes;
import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import static cfg.Containr.sessionFactory;
import static handler.acc.IniAcc.addAccEmnyIfNotExst;
import static handler.balance.BlsSvs.addAmt2BalWhrAccNType;
import static util.acc.AccUti.getAccid;
import static util.log.ColorLogger.RED_bright;
import static util.log.ColorLogger.colorStr;
import static util.misc.util2026.copyProps;
import static util.misc.util2026.getField2025;
import static util.model.openbank.BalanceTypes.interimAvailable;
import static util.tx.HbntUtil.*;
import static handler.secury.SecUti.*;

/**
 * 审核通过充值。。处理规范
 * 
 * 所有资金相关接口，加上幂等控制（避免重复扣款
 *  推荐前端传入 idempotencyKey  ,幂等控制   接口加全局幂等锁（可选）
 * 基本的事务控制
 * 使用 SELECT ... FOR UPDATE 方式锁住这笔订单记录

 * 把“设置状态为 BOOKED”放在加钱后面，或者加钱成功之后再设置状态。
 * addMoneyToWltService1 也要幂等
 * 枚举合法转移状态，确保  PENDING → BOOKED ✅
 * 
 * 
 * <p>
 * <p>
 * //   http://localhost:8889/ReviewChrgPassHdr?ord_id=
 */

@Path("/apiv1/admin/wlt/ReviewChrgPassHdr")
@PermitAll
public class ReviewChrgCmpltHdr implements RequestHandler<ReviewChrgRqdto, ApiGatewayResponse> {

    static boolean ovrtTEst = false;

    /**
     * 
     * @param reqdto
     * @param context
     * @return
     * @throws Throwable
     */
    @Override
    public ApiGatewayResponse handleRequest(ReviewChrgRqdto reqdto, Context context) throws Throwable {

        if (isExistIdptKey(reqdto.IdempotencyKey)) 
            throw new AreadyProcessedEx("");;



        //------------blk chge regch stat=accp
        String mthBiz = colorStr("设置订单状态=完成", RED_bright);
        System.out.println("\r\n\n\n=============⚡⚡bizfun  " + mthBiz);
        Session session = sessionFactory.getCurrentSession();
          // 加悲观锁，锁定这一笔记录（确保幂等 + 并发安全）
        var trx1 = findByHerbinateLockForUpdtV2(Transaction.class, reqdto.transactionId, session);
        // System.out.println("\r\n----blk updt chg ord set stat=ok");
        //  is proceed??幂等判断在操作最前面
        if (trx1.status.equals(TransactionStatus.BOOKED)
                || trx1.status.equals(TransactionStatus.REJECTED)) {
            System.out.println("alread cpmlt ord,id=" + trx1.id);
            if (ovrtTEst) {
            } else {
                throw new AreadyProcessedEx("该充值已审核");
            }
        }
       




        //----=============add blance n log  ..blk
        // 注意，这一步必须是幂等或可重试
        String mthBiz2=colorStr("主钱包加钱",RED_bright);
        System.out.println("\r\n\n\n=============⚡⚡bizfun "+mthBiz2);
        String uname = trx1.owner;
        TransDto transDto=new TransDto();
        copyProps(trx1,transDto);
        transDto.setAmount(trx1.amount);;
        transDto.refUniqId="reqid="+trx1.id;
        addAccEmnyIfNotExst( trx1.owner, session);
        Account lockAcc4updt = findByHerbinateLockForUpdtV2(Account.class, getAccid(AccountSubType.EMoney.name(), trx1.owner) , session);
        transDto.lockAccObj= lockAcc4updt;
        AccService.crdtFd(transDto);   //here add bls
        //  System.out.println("\n\r\n---------endblk  kmplt chrg");
        addAmt2BalWhrAccNType(trx1.amount, lockAcc4updt, interimAvailable);
        addAmt2BalWhrAccNType(trx1.amount, lockAcc4updt, BalanceTypes.interimBooked);


        //==============stp2...chg tx stat
         //chk stat is not pndg,,, throw ex
         if (trx1.status.equals(TransactionStatus.PENDING))
         trx1.setStatus( TransactionStatus.BOOKED);
         trx1.setReviewer(ThreadContext.currAdmin.get());
         trx1.setReviewDateTime(OffsetDateTime.now());
     mergex(trx1, session);


     //===============add  idptkey
     addIdptKey(reqdto.IdempotencyKey);
       new RchgEvtHdl().handleRequest(trx1);
        return new ApiGatewayResponse(trx1);
    }

   
       




    //注解告诉 JSON 序列化库跳过该字段。

    // @Inject("addMoneyToWltService")
    //@Qualifier("AddMoneyToWltService")  // 使用类名自动转换
    public Icall addMoneyToWltService1;   //=new AddMoneyToWltService();
    /**
     * @param uname
     * @return
     */
    public static String encodeSqlAsLikeMatchParam(String uname) {
        return "'% " + encodeSqlPrm4safe(uname) + " %'";

    }


    public static String encodeSqlPrm4safe(String uname) {
        return uname.replaceAll("'", "''");
    }

    public static String encodeSqlPrmAsStr(String uname) {
        return "'" + encodeSqlPrm4safe(uname) + "'";
    }

    public static void main(String[] args) throws Exception {
        IniCfg.iniContnr4cfgfile();
        Map<String, String> queryParams = Map.of(
                "uname", "007",
                "key2", "value2"
        );
//        var list1 = qryuser(queryParams);
//        System.out.println(encodeJson(list1));
    }

//    @Override
//    public void handle2(HttpExchange exchange) throws Exception {
//
//
//        //blk login ed
//        // qryuser(exchange);
//        Map<String, String> queryParams = parseQueryParams(exchange.getRequestURI());
//        long pagesize= Long.parseLong(queryParams.getOrDefault("pagesize","10"));
//        long page=Long.parseLong(queryParams.getOrDefault("page","1"));
//        String uname = getcookie("uname", exchange);
//        var list1 = qryOrdChrg(uname, queryParams,10,1);
//        wrtResp(exchange, encodeJson(list1));
//    }

//    private static Object qryOrdChrg(String uname, Map<String, String> queryParams,long pagesize,long page) throws Exception {
//        var expression = "";
//
//        if (isSqldb(saveDirUsrs)) {
//            return qryOrdChrgSql(uname, queryParams, (int) pagesize,page);
//        } else if (saveDirUsrs.startsWith("lucene:")) {
//            return null;
//            //   return qryuserLucene(queryParams);
//        } else {
//            //json doc ,ini ,redis ,lucene
//            return qryuserIni(queryParams);
//        }

    /// /        org.hibernate.Session session = OrmUtilBiz.openSession(saveDirUsrs);
    /// /
    /// /        //todo start tx
    /// /        session.beginTransaction();
    /// /        Usr u=  session.find(Usr.class,uname);
    /// /        session.getTransaction().commit();
//    }


//    static PageResult<SortedMap<String, Object>> qryOrdChrgSql(String uname, Map<String, String> queryParams, int pageSize, long pageNumber) throws Exception {
//
//        var sqlNoOrd = "select * from OrdChrg where  uname =:uname ";//for count
//        var sql=sqlNoOrd+" order by timestamp desc ";
//        Map<String, Object> sqlprmMap= Map.of( "sql",sql,   "uname",uname);
//        System.out.println( encodeJson(sqlprmMap));
//
//
//        Session session = OrmUtilBiz. openSession(saveUrlOrdChrg);
//        List<ReChgOrd> lst = (List<ReChgOrd>) nativeQueryGetResultList( sql, sqlprmMap, (int) pageNumber,pageSize, session );
//        //    var list1 = getSortedMapsBypages( sql,pageSize, pageNumber);
//        // 1️⃣ 计算总记录数
//        return getPageResultByCntsql( sqlNoOrd,sqlprmMap,lst,pageSize);
//    }
    private static List<SortedMap<String, Object>> qryuserIni(Map<String, String> queryParams) {
        String uname = (String) getField2025(queryParams, "uname", "");
        var expression = "";
        if (!uname.equals("")) {
            //
            // 转义正则表达式特殊字符
            String escapedUname = uname.replaceAll("([\\\\+*?\\[\\](){}|.^$])", "\\\\$1");
            // 使用转义后的uname变量
            expression = "#this['uname'] matches '.*" + escapedUname + ".*'";
        }
        var list1 = 0;//findObjs(saveDirUsrs , expression);
        return null;
    }


}

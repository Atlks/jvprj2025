package handler.wthdr;

import cfg.IniCfg;
import handler.rechg.dto.ReviewChrgRqdto;
import jakarta.annotation.security.PermitAll;
import jakarta.persistence.LockModeType;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;

import model.OpenBankingOBIE.Account;
import model.OpenBankingOBIE.Transaction;
import model.OpenBankingOBIE.TransactionStatus;

import org.hibernate.Session;

import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import static cfg.Containr.sessionFactory;
//import static handler.wthdr.ReviewWthdrReqOrdPassHdr.getYlAccId;
import static service.CmsBiz.toBigDcmTwoDot;
import static util.log.ColorLogger.RED_bright;
import static util.log.ColorLogger.colorStr;
import static util.misc.util2026.getField2025;
import static util.tx.HbntUtil.*;
//   http://localhost:8889/QueryOrdChrgHdr

/**
 * 审核充值--拒绝
 *
 *

 */

@Path("/admin/wthdr/ReviewWthdrReqOrdRejectHdr")
@PermitAll
public class ReviewWthdrReqOrdRejectHdr implements RequestHandler<ReviewChrgRqdto, ApiGatewayResponse> {
    /**
     * @param reqdto
     * @param context
     * @return
     * @throws Throwable
     */
    @Override
    public ApiGatewayResponse handleRequest(ReviewChrgRqdto reqdto, Context context) throws Throwable {


        Session session = sessionFactory.getCurrentSession();
        var tx= findById(Transaction.class,reqdto.transactionId, session);

        //mideng chk
        if(tx.status.equals(TransactionStatus.REJECTED)){
            throw new AlreadyProcessedEx("  AlreadyProcessed ord,id="+tx.id);
        }


         tx.setStatus((TransactionStatus.REJECTED));
         mergeByHbnt(tx, session);



        var  mthBiz = colorStr("减少盈利钱包的冻结金额,back to 有效余额", RED_bright);
        System.out.println("\r\n\n\n=============⚡⚡bizfun  " + mthBiz);
        Account acc1 = findByHbntDep(Account.class,( tx.accountId), LockModeType.PESSIMISTIC_WRITE, sessionFactory.getCurrentSession());
        BigDecimal bls = acc1.interim_Available_Balance;
        BigDecimal bls2 = bls.add(tx.amount);
        BigDecimal beforeAmt=acc1.interim_Available_Balance.add(tx.amount);
        //  objU.availableBalance = toBigDcmTwoDot(bls2);

        BigDecimal nowAmtFreez = toBigDcmTwoDot(acc1.frozenAmount);
        acc1.frozenAmount = acc1.frozenAmount.subtract(tx.amount);
        acc1.interim_Available_Balance = acc1.interim_Available_Balance.add(tx.amount) ;
       // acc1.setInterimBookedBalance(;
        Account acc = mergeByHbnt(acc1, session);
        return new ApiGatewayResponse(acc);
    }


    /**
     *
     * @param uname
     * @return
     */
    public static String encodeSqlAsLikeMatchParam(String uname) {
       return   "'% "+ encodeSqlPrm4safe(uname)+" %'";

    }



    public static String encodeSqlPrm4safe(String uname) {
        return  uname.replaceAll("'","''");
    }

    public static String encodeSqlPrmAsStr(String uname) {
        return   "'"+ encodeSqlPrm4safe(uname)+"'";
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
////        org.hibernate.Session session = OrmUtilBiz.openSession(saveDirUsrs);
////
////        //todo start tx
////        session.beginTransaction();
////        Usr u=  session.find(Usr.class,uname);
////        session.getTransaction().commit();
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

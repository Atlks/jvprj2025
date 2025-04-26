package handler.rechg;

import cfg.MyCfg;
import entityx.wlt.TransDto;
import handler.rechg.dto.ReviewChrgRqdto;
import model.OpenBankingOBIE.Accounts;
import model.OpenBankingOBIE.TransactionStatus;
import model.OpenBankingOBIE.Transactions;


import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RestController;
import util.algo.Icall;
import util.excptn.AreadyProcessedEx;
import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;
import util.tx.findByIdExptn_CantFindData;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import static cfg.Containr.sessionFactory;
import static util.log.ColorLogger.RED_bright;
import static util.log.ColorLogger.colorStr;
import static util.misc.util2026.copyProps;
import static util.misc.util2026.getField2025;
import static util.tx.HbntUtil.*;


/**
 * 审核通过充值。。处理规范
 * 基本的事务控制
 * 使用 SELECT ... FOR UPDATE 方式锁住这笔订单记录
 * 推荐前端传入 idempotencyKey  ,幂等控制   接口加全局幂等锁（可选）
 * 把“设置状态为 BOOKED”放在加钱后面，或者加钱成功之后再设置状态。
 * addMoneyToWltService1 也要幂等
 * 枚举合法转移状态，确保  PENDING → BOOKED ✅
 * 
 * 
 * <p>
 * <p>
 * //   http://localhost:8889/ReviewChrgPassHdr?ord_id=
 */
@RestController
@Path("/admin/wlt/ReviewChrgPassHdr")
@PermitAll
public class ReviewChrgPassHdr implements RequestHandler<ReviewChrgRqdto, ApiGatewayResponse> {

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


        //------------blk chge regch stat=accp
        String mthBiz = colorStr("设置订单状态=完成", RED_bright);
        System.out.println("\r\n\n\n=============⚡⚡bizfun  " + mthBiz);
        Session session = sessionFactory.getCurrentSession();
          // 加悲观锁，锁定这一笔记录（确保幂等 + 并发安全）
        var trx1 = findByHerbinateLockForUpdtV2(Transactions.class, reqdto.transactionId, session);
        // System.out.println("\r\n----blk updt chg ord set stat=ok");
        //  is proceed??幂等判断在操作最前面
        if (trx1.transactionStatus.equals(TransactionStatus.BOOKED)
                || trx1.transactionStatus.equals(TransactionStatus.REJECTED)) {
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
        String uname = trx1.uname;
        TransDto transDto=new TransDto();
        copyProps(trx1,transDto);
        transDto.amt=trx1.amount;
        transDto.refUniqId="reqid="+trx1.id;
        addWltIfNotExst( trx1.uname, session);
        transDto.lockAccObj= findByHerbinateLockForUpdtV2(Accounts.class, trx1.uname, session);
        addMoneyToWltService1.main(transDto);
        //  System.out.println("\n\r\n---------endblk  kmplt chrg");



        //==============stp2...chg tx stat
         //chk stat is not pndg,,, throw ex
         if (trx1.transactionStatus.equals(TransactionStatus.PENDING))
         trx1.setTransactionStatus( TransactionStatus.BOOKED);
     mergeByHbnt(trx1, session);
        return new ApiGatewayResponse(trx1);
    }

    public static void addWltIfNotExst(String uname, Session session) throws findByIdExptn_CantFindData {
        try{
            var wlt=findByHerbinate(Accounts.class, uname, session);
        } catch (findByIdExptn_CantFindData e) {
            //ini wlt
            Accounts wlt=new Accounts();
            wlt.accountId = uname;
            persistByHibernate(wlt, session);
          //  transDto.lockAccObj=findByHerbinate(Wallet.class, uname, session);
        }
    }


    //注解告诉 JSON 序列化库跳过该字段。
    @Lazy
    @Autowired()
    // @Inject("addMoneyToWltService")
    @Qualifier("AddMoneyToWltService")  // 使用类名自动转换
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
        MyCfg.iniContnr4cfgfile();
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

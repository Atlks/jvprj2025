package handler.rechg;

import cfg.MyCfg;
import entityx.wlt.TransDto;
import handler.dto.ReviewChrgPassRqdto;
import handler.rechg.dto.AreadyProcessedEx;
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
 * 审核通过充值
 * <p>
 * <p>
 * //   http://localhost:8889/ReviewChrgPassHdr?ord_id=
 */
@RestController
@Path("/admin/wlt/ReviewChrgPassHdr")
@PermitAll
public class ReviewChrgPassHdr implements RequestHandler<ReviewChrgPassRqdto, ApiGatewayResponse> {

    static boolean ovrtTEst = false;

    /**
     * @param reqdto
     * @param context
     * @return
     * @throws Throwable
     */
    @Override
    public ApiGatewayResponse handleRequest(ReviewChrgPassRqdto reqdto, Context context) throws Throwable {


        //------------blk chge regch stat=accp
        String mthBiz = colorStr("设置订单状态=完成", RED_bright);
        System.out.println("\r\n\n\n=============⚡⚡bizfun  " + mthBiz);
        Session session = sessionFactory.getCurrentSession();
        var objChrg = findByHerbinate(Transactions.class, reqdto.transactionId, session);
        // System.out.println("\r\n----blk updt chg ord set stat=ok");
        //  is proceed??
        if (objChrg.transactionStatus.equals(TransactionStatus.BOOKED)
                || objChrg.transactionStatus.equals(TransactionStatus.REJECTED)) {
            System.out.println("alread cpmlt ord,id=" + objChrg.id);
            if (ovrtTEst) {
            } else {
                throw new AreadyProcessedEx("");
            }
        }
        //chk stat is not pndg,,, throw ex
        if (objChrg.transactionStatus.equals(TransactionStatus.PENDING))
            objChrg.setTransactionStatus( TransactionStatus.BOOKED);
        mergeByHbnt(objChrg, session);




        //----=============add blance n log  ..blk
        String mthBiz2=colorStr("主钱包加钱",RED_bright);
        System.out.println("\r\n\n\n=============⚡⚡bizfun "+mthBiz2);
        String uname = objChrg.uname;
        TransDto transDto=new TransDto();
        copyProps(objChrg,transDto);
        transDto.amt=objChrg.amount;
        transDto.refUniqId="reqid="+objChrg.id;
        iniWltIfNotExst( objChrg.uname, session);
        transDto.lockAccObj=findByHerbinate(Accounts.class, objChrg.uname, session);


        addMoneyToWltService1.main(transDto);
        //  System.out.println("\n\r\n---------endblk  kmplt chrg");


        return new ApiGatewayResponse(objChrg);
    }

    public static void iniWltIfNotExst(String uname, Session session) throws findByIdExptn_CantFindData {
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

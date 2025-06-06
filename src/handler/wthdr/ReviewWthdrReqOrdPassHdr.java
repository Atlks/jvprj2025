package handler.wthdr;

import cfg.IniCfg;
import entityx.wlt.TransDto;
import handler.rechg.dto.ReviewChrgRqdto;
import jakarta.annotation.security.PermitAll;
import jakarta.persistence.LockModeType;
import jakarta.ws.rs.Path;
import util.model.Context;

import model.OpenBankingOBIE.Account;
import model.OpenBankingOBIE.Transaction;
import model.OpenBankingOBIE.TransactionStatus;


import org.hibernate.Session;

import util.algo.Icall;
import util.excptn.AreadyProcessedEx;
import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;
import util.tx.findByIdExptn_CantFindData;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import static cfg.Containr.sessionFactory;
import static service.CmsBiz.toBigDcmTwoDot;
import static util.acc.AccUti.getAccId4ylwlt;
import static util.log.ColorLogger.RED_bright;
import static util.log.ColorLogger.colorStr;
import static util.misc.util2026.copyProps;
import static util.misc.util2026.getField2025;
import static util.tx.HbntUtil.*;
import static handler.secury.SecUti.*;

/**
 * 审核通过充值
 * <p>
 * <p>
 * //   http://localhost:8889/ReviewChrgPassHdr?ord_id=
 */

@Path("/apiv1/admin/wlt/ReviewWthdrReqOrdPassHdr")
@PermitAll
public class ReviewWthdrReqOrdPassHdr implements RequestHandler<ReviewChrgRqdto, ApiGatewayResponse> {

    static boolean ovrtTEst = false;

    /**
     * @param reqdto
     * @param context
     * @return
     * @throws Throwable
     */
    @Override
    public ApiGatewayResponse handleRequest(ReviewChrgRqdto reqdto, Context context) throws Throwable {

        //========mideng chk
        if (isExistIdptKey(reqdto.IdempotencyKey)) 
            throw new AreadyProcessedEx("");;


        //------------blk chge regch stat=accp
        String mthBiz = colorStr("设置订单状态=完成", RED_bright);
        System.out.println("\r\n\n\n=============⚡⚡bizfun  " + mthBiz);
        Session session = sessionFactory.getCurrentSession();
        Transaction objOrd = findById(Transaction.class, reqdto.transactionId, session);
        // System.out.println("\r\n----blk updt chg ord set stat=ok");
        //  is proceed??
        if (objOrd.status.equals(TransactionStatus.BOOKED)
                || objOrd.status.equals(TransactionStatus.REJECTED)) {
            System.out.println("alread cpmlt ord,id=" + objOrd.id);
            if (ovrtTEst) {
            } else {
                throw new AreadyProcessedEx("");
            }
        }
        //chk stat is not pndg,,, throw ex
        //
        if (objOrd.status.equals(TransactionStatus.PENDING))
            objOrd.setStatus((TransactionStatus.BOOKED));
        mergex(objOrd, session);




        //----=============rds blance n log  ..blk
        String mthBiz2=colorStr("yl钱包减去钱",RED_bright);
        System.out.println("\r\n\n\n=============⚡⚡bizfun "+mthBiz2);
        String uname = objOrd.owner;
        TransDto transDto=new TransDto();
        copyProps(objOrd,transDto);
        transDto.setAmount(objOrd.amount);;
        transDto.refUniqId="reqid="+objOrd.id;
        iniWlt( objOrd.owner, session);
        transDto.lockYlwltObj= findById(Account.class, getAccId4ylwlt(objOrd.owner) , session);
      //  addMoneyToWltService1.main(transDto);
        //  System.out.println("\n\r\n---------endblk  kmplt chrg");

        //=======================减少盈利钱包的有效余额,增加冻结金额
        //   //adjst yinliwlt balnce
        //----------------------sub blsAvld   blsFreez++
          mthBiz = colorStr("减少盈利钱包的有效余额,增加冻结金额", RED_bright);
        System.out.println("\r\n\n\n=============⚡⚡bizfun  " + mthBiz);
        Account objU = findByHbntDep(Account.class, getAccId4ylwlt(uname), LockModeType.PESSIMISTIC_WRITE, sessionFactory.getCurrentSession());
        BigDecimal nowAmt2 = objU.interim_Available_Balance;
        BigDecimal newBls2 = nowAmt2.subtract(objOrd.amount);
        BigDecimal beforeAmt=objU.interim_Available_Balance.add(objOrd.amount);
      //  objU.availableBalance = toBigDcmTwoDot(newBls2);

        BigDecimal nowAmtFreez = toBigDcmTwoDot(objU.frozenAmount);
        objU.frozenAmount = objU.frozenAmount.subtract(objOrd.amount);
        Account usr = mergex(objU, session);



        //取款体现后  日志的变化  冻结金额 ，有效金额变化。。。
        System.out.println("\r\n\n\n=============⚡⚡bizfun  " + colorStr("余额变化了流水", RED_bright));
        //------------add balanceLog
//        LogBls4YLwlt logBlsYinliWlt = new LogBls4YLwlt(objOrd.uname,beforeAmt,  objU.availableBalance,"减去");
//        logBlsYinliWlt.refUniqId=reqdto.transactionId;
//          logBlsYinliWlt.adjustType="减去";
//        addBlsLog4ylwlt(logBlsYinliWlt, session);


        return new ApiGatewayResponse(objOrd);
    }



    public static void iniWlt(String uname, Session session) throws findByIdExptn_CantFindData {
        try{
            var wlt= findById(Account.class, uname, session);
        } catch (findByIdExptn_CantFindData e) {
            //ini wlt
            Account wlt=new Account();
            wlt.accountId = uname;
            mergex(wlt, session);
          //  transDto.lockAccObj=findByHerbinate(Wallet.class, uname, session);
        }
    }


    //注解告诉 JSON 序列化库跳过该字段。
//    @Lazy
//    ()
//    // @Inject("addMoneyToWltService")
//    @Qualifier("AddMoneyToWltService")  // 使用类名自动转换
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

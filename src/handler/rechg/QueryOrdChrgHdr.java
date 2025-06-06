package handler.rechg;

import cfg.IniCfg;
import model.OpenBankingOBIE.CreditDebitIndicator;
import model.OpenBankingOBIE.Transaction;
import util.annos.NoDftParam;
import entityx.wlt.QryRechgOrdReqDto;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import util.model.Context;

import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;

import java.util.*;


import static cfg.Containr.sessionFactory;
import static util.Oosql.SlctQry.toValStr;
import static util.algo.EncodeUtil.encodeSqlAsLikeMatchParam;
import static util.algo.GetUti.getTablename;
import static util.algo.ToXX.toSnake;
import static util.tx.Pagging.getPageResultByHbntV4;


/**
 *
 *
 * qru cdtn
 *
 * //   http://localhost:8889/admin/wlt/QueryOrdChrgHdr
 *
 订单号              会员账号          标签                    VIP等级          上级代理             充值金额           截图附件              审核状态            审核人                    充值时间                         审核时间
 */

@Path("/apiv1/admin/wlt/QueryOrdChrgHdr")
@PermitAll
@NoDftParam
//@RolesAllowed({"admin","op"})
public class QueryOrdChrgHdr  implements RequestHandler<QryRechgOrdReqDto, ApiGatewayResponse> {
    /**
     * @param reqdto
     * @param context
     * @return
     * @throws Throwable
     */
    @Override
    public ApiGatewayResponse handleRequest(QryRechgOrdReqDto reqdto, Context context) throws Throwable {

        String tablename = getTablename(Transaction.class);
        String fldIdctr=toSnake(Transaction.Fields.creditDebitIndicator);
        var sqlNoOrd = "select * from "+ tablename +" where "
                +fldIdctr+"="+
                toValStr(CreditDebitIndicator.CREDIT.name()) ;
               // ='credit' ";//for count    where  uname =:uname
        HashMap<String, Object> sqlprmMap = new HashMap<>();
        if(reqdto.uname!="")
        {  sqlNoOrd=sqlNoOrd+ " and  "+toSnake(Transaction.Fields.owner)+" like "+ encodeSqlAsLikeMatchParam(reqdto.uname);
          //  sqlprmMap.put("uname",)
        }

        var sql=sqlNoOrd+" order by timestamp desc ";
      //  Map<String, Object> sqlprmMap= Map.of( "sql",sql,   "uname",reqdto.uname);
     //   System.out.println( encodeJson(sqlprmMap));



        var list1 = getPageResultByHbntV4(sql, sqlprmMap, reqdto, sessionFactory.getCurrentSession(), Transaction.class);

        return new ApiGatewayResponse(list1);
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




//    private static List<SortedMap<String, Object>> qryuserIni(Map<String, String> queryParams) {
//        String uname = (String) getField2025(queryParams, "uname", "");
//        var expression = "";
//        if (!uname.equals("")) {
//            //
//            // 转义正则表达式特殊字符
//            String escapedUname = uname.replaceAll("([\\\\+*?\\[\\](){}|.^$])", "\\\\$1");
//            // 使用转义后的uname变量
//            expression = "#this['uname'] matches '.*" + escapedUname + ".*'";
//        }
//        var list1 = 0;//findObjs(saveDirUsrs , expression);
//        return null;
//    }



}

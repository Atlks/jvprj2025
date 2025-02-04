package apiOrdBet;

import apis.BaseHdr;
import com.sun.net.httpserver.HttpExchange;
import org.hibernate.Session;
import util.HttpExchangeImp;
import utilBiz.OrmUtilBiz;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//import static apiUsr.QueryUsrHdr.qryuserLucene;


import static apiAcc.AddOrdChargeHdr.saveUrlOrdChrg;
import static java.time.LocalTime.now;
import static util.OrdUtil.orderBySqlOrderMode;
import static util.Qry.convertSqlToSpEL;
import static util.ToXX.toObjFrmMap;
import static util.Util2025.encodeJson;
import static util.Util2025.encodeJsonObj;
import static util.dbutil.*;
import static util.util2026.*;

/**   查询规则，参数使用类型化，不要新定义dto，直接使用实体代替
 * http://localhost:8889/QryOrdBetHdr
 */
public class QryOrdBetHdr extends BaseHdr {

    public static String saveUrlOrdBet = "";

    @Override
    public void handle2( HttpExchange exchange) throws Exception {




        //blk login ed
        String uname = getcookie("uname", exchange);
        Map<String, String> queryParams = parseQueryParams(exchange.getRequestURI());
        OrdBet prm=toObjFrmMap(queryParams,OrdBet.class);
        prm.uname=uname;

        var list1 = qryOrdBet(uname,prm);
        wrtResp(exchange, encodeJson(list1));


    }




    private Object qryOrdBet(String uname, OrdBet queryParams) throws Exception {

        var expression = "";
    //    String uname = queryParams.uname;

        if (isSqldb(saveUrlOrdBet)  ) {
            return qryOrdBetSql(queryParams);

        } else {
            //json doc ,ini ,redis ,lucene
            return qryOrdBetSql(queryParams);
          //  return qryOrdBetIni(queryParams);
        }
//        //    addMapx("spdbfun",QryOrdBetHdr::qryOrdBetIni);
//        HashMap<String, Function<Map<String, String>, Object>> mapFuns = new HashMap<>();
//        mapFuns.put("sqldbFun", QryOrdBetHdr::qryOrdBetSql);
//    //    mapFuns.put("luceneFun", QryOrdBetHdr::qryOrdBetIni);
//        mapFuns.put("arrFun", QryOrdBetHdr::qryOrdBetIni);
  //      return execQry(saveUrlOrdBet, mapFuns);

      //  return null;
    }





    private static Object qryOrdBetSql(OrdBet queryParams) throws Exception {


        var sql = "select * from ordbet where uname =:uname order by timestamp desc " ;
        Map<String, Object> sqlprmMap= Map.of( "sql",sql,   "uname",queryParams.uname);
        System.out.println( encodeJson(sqlprmMap));
        System.out.println("spel="+convertSqlToSpEL(sql));
        Session session = OrmUtilBiz. openSession(saveUrlOrdChrg);
        List<OrdBet> lst = nativeQueryGetResultList( sql, sqlprmMap, session,OrdBet.class );
        //    var list1 = getSortedMapsBypages( sql,pageSize, pageNumber);
        // 1️⃣ 计算总记录数
        return getPageResult( lst,queryParams.pagesize,queryParams.page);


    }


    private static Object qryOrdBetIni(OrdBet queryParams) {

        String uname = (String) getField2025(queryParams, "uname", "");
        var expression = "";
        if (!uname.equals("")) {
            //
            // 转义正则表达式特殊字符
            String escapedUname = uname.replaceAll("([\\\\+*?\\[\\](){}|.^$])", "\\\\$1");
            // 使用转义后的uname变量
            expression = "#this['uname'] matches '.*" + escapedUname + ".*'";
        }
        var list1 = 0;// = findObjs(saveUrlOrdBet, expression);
        return list1;
    }


}

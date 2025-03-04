package api.bet;

import biz.BaseHdr;
import com.sun.net.httpserver.HttpExchange;
import entityx.OrdBet;
import entityx.Usr;
import org.hibernate.Session;
import util.auth.IsEmptyEx;
import utilBiz.OrmUtilBiz;

import java.util.Map;

//import static api.usr.QueryUsrHdr.qryuserLucene;


import static api.wlt.RechargeHdr.saveUrlOrdChrg;
import static java.time.LocalTime.now;
import static util.Pagging.getPageResultByHbntV2;
import static util.ToXX.toObjFrmQrystr;
import static util.Util2025.encodeJson;
import static util.util2026.*;

/**   查询规则，参数使用类型化，不要新定义dto，直接使用实体代替
 * http://localhost:8889/QryOrdBetHdr
 */
public class QryOrdBetHdr extends BaseHdr<Usr, Usr> {

    public static String saveUrlOrdBet = "";

    @Override
    public void handle2( HttpExchange exchange) throws Exception, IsEmptyEx {

        //blk login ed
        String uname = getcookie("uname", exchange);
        OrdBet qryDto4page=toObjFrmQrystr(exchange,OrdBet.class);

        var sql = "select * from ordbet where uname =:uname order by timestamp desc " ;
        Map<String, Object> sqlprmMap= Map.of( "sql",sql,   "uname",uname);
        System.out.println( encodeJson(sqlprmMap));
   //     System.out.println("spel="+convertSqlToSpEL(sql));
        Session session = OrmUtilBiz. openSession(saveUrlOrdChrg);
        var list1 = getPageResultByHbntV2(sql, sqlprmMap, qryDto4page.page, qryDto4page.pagesize,session);
        //,OrdBet.class
        wrtResp(exchange, encodeJson(list1));

    }




//    private static Object qryOrdBetIni(OrdBet queryParams) {
//
//        String uname = (String) getField2025(queryParams, "uname", "");
//        var expression = "";
//        if (!uname.equals("")) {
//            //
//            // 转义正则表达式特殊字符
//            String escapedUname = uname.replaceAll("([\\\\+*?\\[\\](){}|.^$])", "\\\\$1");
//            // 使用转义后的uname变量
//            expression = "#this['uname'] matches '.*" + escapedUname + ".*'";
//        }
//        var list1 = 0;// = findObjs(saveUrlOrdBet, expression);
//        return list1;
//    }


}

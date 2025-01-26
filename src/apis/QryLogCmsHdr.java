package apis;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;

//import static util.Fltr.filterWithSpEL;
import static util.Fltr.fltr2501;
import static util.Util2025.encodeJson;
import static util.dbutil.*;
import static util.util2026.*;
import static yonjin.Cms.saveUrlLogCms;

/**  app use ,so must hav uname
 * http://localhost:8889/QryLogCmsHdr
 */
public class QryLogCmsHdr extends BaseHdr {

 //saveUrlLogCms

    @Override
    public void handle2(HttpExchange exchange) throws Exception {


        if (isNotLogined(exchange)) {
            //need login
            wrtResp(exchange, "needLogin需要登录");
            return;
        }

        //blk login ed
        String uname = getcookie("uname", exchange);
        Map<String, String> queryParams = parseQueryParams(exchange.getRequestURI());


        var list1 = QryLogCms(queryParams);
        wrtResp(exchange, encodeJson(list1));


    }

    private Object QryLogCms(Map<String, String> queryParams) throws Exception {

        var expression = "";
        String uname = queryParams.get("uname");


        //    addMapx("spdbfun",QryOrdBetHdr::qryOrdBetIni);
//        HashMap<String, Function<Map<String, String>, Object>> mapFuns = new HashMap<>();
//        mapFuns.put("sqldbFun", QryLogCmsHdr::qryOrdBetSql);
//    //    mapFuns.put("luceneFun", QryOrdBetHdr::qryOrdBetIni);
//        mapFuns.put("arrFun", queryParams1 -> QryLogCmsIni(queryParams1, uname));
        if (isSqldb(saveUrlLogCms)) {
            return  QryLogCmsSql(queryParams,uname);
        }else
            return  QryLogCmsIni(queryParams,uname);

    }


    private static Object QryLogCmsSql(Map<String, String> queryParams, String uname) throws Exception {
        var sql="select * from logCms where uname='"+uname+"'";
        return qrySql(sql,saveUrlLogCms);
    }


    private static Object QryLogCmsIni(Map<String, String> queryParams, String uname) {

        // 定义过滤条件：只保留 符合条件的的记录
        Predicate<SortedMap<String, Object>> filter1 = map -> {
            String unm = (String) map.get("uname");
            if (unm.equals(uname) )
                return true;
            return false;
        };

        var list1 = findObjs(saveUrlLogCms, filter1);
        return list1;
    }


}

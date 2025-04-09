package api.cms;

import biz.BaseHdr;
import cfg.MyCfg;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import entityx.LogCms;
import entityx.Usr;
import org.hibernate.Session;
import util.misc.HttpExchangeImp;
import entityx.PageResult;
import util.excptn.OrmUtilBiz;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.function.Predicate;

//import static util.Fltr.filterWithSpEL;

import static biz.Containr.saveUrlOrdChrg;
import static util.algo.Fltr.fltr2501;
import static util.algo.ToXX.parseQueryParams;
import static util.algo.ToXX.toObjFrmMap;
import static util.misc.Util2025.encodeJson;
import static util.tx.dbutil.*;
import static util.misc.util2026.*;
import static service.CmsBiz.saveUrlLogCms;

/**
 * app use ,so must hav uname
 * http://localhost:8889/QryLogCmsHdr
 */
public class QryLogCmsHdr extends BaseHdr<Usr, Usr> {

    //saveUrlLogCms

    @Override
    public void handle2(HttpExchange exchange) throws Exception {


        //blk login ed
        String uname = getcookie("uname", exchange);
        Map<String, String> queryParams = parseQueryParams(exchange.getRequestURI());
        LogCms qryDto=toObjFrmMap(queryParams,LogCms.class);
        qryDto.uname=uname;

        var list1 = QryLogCms(qryDto);
        wrtResp(exchange, encodeJson(list1));


    }

    public static void main(String[] args) throws Exception {

        MyCfg.iniContnr4cfgfile();
        HttpExchangeImp he = new HttpExchangeImp();
        // he.setAttribute();
        he.setRequestURI("http://localhost:8889/QryLogCmsHdr");
        String ResponseBodyoutputStreamF = "output2025.txt";
        Map<String, String> reqhdr = new HashMap<>();
        reqhdr.put("Cookie", "uname=007");


        he.setRequestHeaders(reqhdr);
        he.setResponseHeaders(new Headers());
        OutputStream outputStream = new FileOutputStream(ResponseBodyoutputStreamF); // 创建一个输出流
        he.setResponseBody(outputStream);
        // setcookie("uname","007",he);
        new QryLogCmsHdr().handle2(he);
    }

    private Object QryLogCms(LogCms qryDto) throws Exception {

        var expression = "";
        String uname = qryDto.uname;


        //    addMapx("spdbfun",QryOrdBetHdr::qryOrdBetIni);
//        HashMap<String, Function<Map<String, String>, Object>> mapFuns = new HashMap<>();
//        mapFuns.put("sqldbFun", QryLogCmsHdr::qryOrdBetSql);
//    //    mapFuns.put("luceneFun", QryOrdBetHdr::qryOrdBetIni);
//        mapFuns.put("arrFun", queryParams1 -> QryLogCmsIni(queryParams1, uname));
        if (isSqldb(saveUrlLogCms)) {
            return QryLogCmsSql(qryDto);
        } else
            return QryLogCmsIni(qryDto);

    }


    private static Object QryLogCmsSql(LogCms qryDto) throws Exception {
        var sql = "select * from logCms where uname=:uname";

        Map<String, Object> sqlprmMap = Map.of("sql", sql, "uname", qryDto.uname);
        System.out.println(encodeJson(sqlprmMap));

        Session session = OrmUtilBiz.openSession(saveUrlOrdChrg);
        PageResult<?> rzt = getPageResultByHbnt(sql, sqlprmMap, qryDto.page, qryDto.pagesize, session);
        //    var list1 = getSortedMapsBypages( sql,pageSize, pageNumber);
        // 1️⃣ 计算总记录数
        // return getPageResult( sqlNoOrd,sqlprmMap,lst,pageSize);
        return rzt;
        //qrySql(sql,saveUrlLogCms);
    }


    private static Object QryLogCmsIni(LogCms qryDto) {

        // 定义过滤条件：只保留 符合条件的的记录
        Predicate<SortedMap<String, Object>> filter1 = map -> {
            String unm = (String) map.get("uname");
            if (unm.equals(qryDto.uname))
                return true;
            return false;
        };

        var list1 = findObjs(saveUrlLogCms, filter1);
        return list1;
    }


}

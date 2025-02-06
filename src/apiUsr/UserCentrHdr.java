package apiUsr;

import apis.BaseHdr;
import com.sun.net.httpserver.HttpExchange;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import utilBiz.OrmUtilBiz;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import static apiAcc.AddOrdChargeHdr.saveUrlOrdChrg;
import static apiUsr.RegHandler.saveDirUsrs;
import static util.ToXX.parseQueryParams;
import static util.Util2025.encodeJson;
import static util.dbutil.*;
import static util.util2026.*;

public class UserCentrHdr extends BaseHdr {

    public static void main(String[] args) throws Exception {
        iniCfgFrmCfgfile();
        Map<String, String> queryParams = Map.of(
                "uname", "007",
                "key2", "value2"
        );
        String uname = "";
        var list1 = findUser(uname, queryParams);
        System.out.println(encodeJson(list1));
    }

    @Override
    public void handle2(HttpExchange exchange) throws Exception {


        String uname = getcookie("uname", exchange);


        //blk login ed
        // qryuser(exchange);
        Map<String, String> queryParams = parseQueryParams(exchange.getRequestURI());
        var list1 = findUser(uname, queryParams);
        wrtResp(exchange, encodeJson(list1));
    }

    private static Object findUser(String uname, Map<String, String> queryParams) throws Exception {
        var expression = "";
        // String uname =getFieldAsStrFrmMap( queryParams,"uname");

        if (isSqldb(saveDirUsrs)) {
            return qryuserSql(uname, queryParams);
        } else if (saveDirUsrs.startsWith("lucene:")) {
            return null;
            //   return qryuserLucene(queryParams);
        } else {
            //json doc ,ini ,redis ,lucene
            return qryuserIni(queryParams);
        }
//        org.hibernate.Session session = OrmUtilBiz.openSession(saveDirUsrs);
//
//        //todo start tx
//        session.beginTransaction();
//        Usr u=  session.find(Usr.class,uname);
//        session.getTransaction().commit();
    }

   ;
    static Object qryuserSql(String uname, Map<String, String> queryParams) throws Exception {
        Map<String, Object> prmMap = Map.of(
                "fun", getCurrentMethodName(), "uname", uname,
                "queryParams", queryParams
        );
        System.out.println(encodeJson(prmMap));
        currFunPrms4dbg.set(prmMap);


        var sql = "select * from usr where uname=:uname   ";
        Map<String, Object> sqlprmMap = Map.of("sql", sql, "uname", uname);
        System.out.println(encodeJson(sqlprmMap));


        Session session = OrmUtilBiz.openSession(saveUrlOrdChrg);
        NativeQuery<Usr> nativeQuery = session.createNativeQuery(sql, Usr.class);
        setPrmts4sql(sqlprmMap, nativeQuery);
        Usr u = nativeQuery.getSingleResult();


        //       .setParameter("age", 18);


        return u;
    }

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

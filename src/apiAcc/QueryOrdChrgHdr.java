package apiAcc;

import apis.BaseHdr;
import com.sun.net.httpserver.HttpExchange;
import util.PageResult;
import utilBiz.OrmUtilBiz;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import static apiUsr.RegHandler.saveDirUsrs;
import static util.Util2025.encodeJson;
import static util.dbutil.*;
import static util.util2026.*;
//   http://localhost:8889/QueryOrdChrgHdr
public class QueryOrdChrgHdr extends BaseHdr {

    public static void main(String[] args) throws Exception {
        iniCfgFrmCfgfile();
        Map<String, String> queryParams = Map.of(
                "uname", "007",
                "key2", "value2"
        );
//        var list1 = qryuser(queryParams);
//        System.out.println(encodeJson(list1));
    }

    @Override
    public void handle2(HttpExchange exchange) throws Exception {


        //blk login ed
        // qryuser(exchange);
        Map<String, String> queryParams = parseQueryParams(exchange.getRequestURI());
        long pagesize= Long.parseLong(queryParams.getOrDefault("pagesize","10"));
        long page=Long.parseLong(queryParams.getOrDefault("page","1"));
        String uname = getcookie("uname", exchange);
        var list1 = qryOrdChrg(uname, queryParams,10,1);
        wrtResp(exchange, encodeJson(list1));
    }

    private static Object qryOrdChrg(String uname, Map<String, String> queryParams,long pagesize,long page) throws Exception {
        var expression = "";

        if (isSqldb(saveDirUsrs)) {
            return qryOrdChrgSql(uname, queryParams,pagesize,page);
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


    static PageResult<SortedMap<String, Object>> qryOrdChrgSql(String uname, Map<String, String> queryParams, long pageSize, long pageNumber) throws Exception {

        var sql = "select * from OrdChrg where  uname ='" + uname + "'";
        System.out.println(sql);
        var list1 = getSortedMapsBypages( sql,pageSize, pageNumber);
        // 1️⃣ 计算总记录数
        return getPageResult( sql,list1,pageSize);
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

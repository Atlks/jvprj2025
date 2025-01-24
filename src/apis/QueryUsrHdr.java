package apis;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import static biz.BaseBiz.saveDir;
import static util.Util2025.encodeJson;
import static util.dbutil.*;
import static util.dbutil.getSortedMaps;
import static util.util2026.*;

public class QueryUsrHdr extends BaseHdr {

    public static void main(String[] args) throws IOException {

        Map<String, String> queryParams = Map.of(
                "uname", "u2",
                "key2", "value2"
        );
        var list1 = qryuser(queryParams);
        System.out.println(encodeJson(list1));
    }

    @Override
    public void handle2(HttpExchange exchange) throws Exception {


        String uname = getcookie("uname", exchange);
        if (uname.equals("")) {
            //need login
            wrtResp(exchange, "needLogin");
            return;
        }

        //blk login ed
        // qryuser(exchange);
        Map<String, String> queryParams = parseQueryParams(exchange.getRequestURI());
        var list1 = qryuser(queryParams);
        wrtResp(exchange, encodeJson(list1));
    }

    private static List<SortedMap<String, Object>> qryuser(Map<String, String> queryParams) throws IOException {
        var expression = "";
        String uname = queryParams.get("uname");
        if (saveDir.startsWith("jdbc:mysql") || saveDir.startsWith("jdbc:sqlite")) {
            return getSortedMapsSql(queryParams);
        } else if (saveDir.startsWith("lucene:")) {
            return getSortedMapsLucene(queryParams);
        } else {
            //json doc ,ini ,redis ,lucene
            return getSortedMapsIni(queryParams);
        }
        //   return new ArrayList<SortedMap<String, Object>>();
    }

    private static List<SortedMap<String, Object>> getSortedMapsLucene(Map<String, String> queryParams) {


    }

    private static List<SortedMap<String, Object>> getSortedMapsSql(Map<String, String> queryParams) {
        String uname = queryParams.get("uname");
        var expression = "";

        //addObjMysql(obj, collName, saveDir);
        if (!uname.equals("")) {
            expression = "uname like '%{uname}%'";
        }
        var sql = "select * from usrs where 1=1 and " + expression;
        var list1 = qrySql(sql, saveDir);
        return (List<SortedMap<String, Object>>) list1;
    }

    private static List<SortedMap<String, Object>> getSortedMapsIni(Map<String, String> queryParams) {
        String uname = queryParams.get("uname");
        var expression = "";
        if (!uname.equals("")) {
            //
            // 转义正则表达式特殊字符
            String escapedUname = uname.replaceAll("([\\\\+*?\\[\\](){}|.^$])", "\\\\$1");
            // 使用转义后的uname变量
            expression = "#this['uname'] matches '.*" + escapedUname + ".*'";
        }
        var list1 = getObjs(saveDir + "usrs", expression);
        return list1;
    }


}

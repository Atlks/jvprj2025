package apis;

import com.sun.net.httpserver.HttpExchange;
//import org.apache.lucene.index.DirectoryReader;
//import org.apache.lucene.index.Term;
//import org.apache.lucene.search.*;
//import org.apache.lucene.store.Directory;
//import org.apache.lucene.store.FSDirectory;
//import static util.UtilLucene.toListMap;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;


import static apis.RegHandler.saveDirUsrs;
import static util.Util2025.encodeJson;

import static util.dbutil.*;

import static util.util2026.*;

public class QueryUsrHdr extends BaseHdr {

    public static void main(String[] args) throws Exception {

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
        uname="ttt";
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

    private static List<SortedMap<String, Object>> qryuser(Map<String, String> queryParams) throws Exception {
        var expression = "";
        String uname = queryParams.get("uname");
        if (saveDirUsrs.startsWith("jdbc:mysql") || saveDirUsrs.startsWith("jdbc:sqlite")) {
            return qryuserSql(queryParams);
        } else if (saveDirUsrs.startsWith("lucene:")) {
            return  null;
         //   return qryuserLucene(queryParams);
        } else {
            //json doc ,ini ,redis ,lucene
            return qryuserIni(queryParams);
        }
        //   return new ArrayList<SortedMap<String, Object>>();
    }




    static List<SortedMap<String, Object>> qryuserSql(Map<String, String> queryParams) throws Exception {
        String uname = queryParams.get("uname");
        var expression = "";

        //addObjMysql(obj, collName, saveDir);
        if (!uname.equals("")) {
            expression = "uname like '%{uname}%'";
        }
        var sql = "select * from usrs where 1=1 and " + expression;
        var list1 = qrySql(sql, saveDirUsrs);
        return (List<SortedMap<String, Object>>) list1;
    }

    private static List<SortedMap<String, Object>> qryuserIni(Map<String, String> queryParams) {
        String uname = (String) getField2025(queryParams,"uname","");
        var expression = "";
        if (!uname.equals("")) {
            //
            // 转义正则表达式特殊字符
            String escapedUname = uname.replaceAll("([\\\\+*?\\[\\](){}|.^$])", "\\\\$1");
            // 使用转义后的uname变量
            expression = "#this['uname'] matches '.*" + escapedUname + ".*'";
        }
        var list1 =0 ;//findObjs(saveDirUsrs , expression);
        return null;
    }


}

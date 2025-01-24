package apis;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import static biz.BaseBiz.saveDir;
import static util.Util2025.encodeJson;
import static util.dbutil.getObjsDocdb;
import static util.util2026.*;

public class QueryUsrHdr extends BaseHdr {

    public static void main(String[] args) throws IOException {

        Map<String, String> queryParams = Map.of(
                "uname", "u23",
                "key2", "value2"
        );
        var list1 =   qryuser(queryParams);
        System.out.println( encodeJson(list1));
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
        var list1 =   qryuser(queryParams);
        wrtResp(exchange, encodeJson(list1));
    }

    private static List<SortedMap<String, Object>> qryuser(Map<String, String> queryParams) throws IOException {
        var expression = "";

        if (saveDir.startsWith("jdbc:mysql")) {
            //addObjMysql(obj, collName, saveDir);
        } else {
            //json doc
            String uname = queryParams.get( "uname");
            if (!uname.equals("")) {
                //
                // 转义正则表达式特殊字符
                String escapedUname = uname.replaceAll("([\\\\+*?\\[\\](){}|.^$])", "\\\\$1");
                // 使用转义后的uname变量
                expression = "#this['uname'] matches '.*" + escapedUname + ".*'";
            }
            var list1 = getObjsDocdb(saveDir + "usrs", expression);
            return list1;
        }
        return new ArrayList<>();
    }


}

package apis;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static apis.QueryUsrHdr.qryuserLucene;


import static java.time.LocalTime.now;
import static util.Util2025.encodeJson;
import static util.dbutil.execQry;
import static util.dbutil.findObjs;
import static util.util2026.*;

/**
 * http://localhost:8889/QryOrdBetHdr
 */
public class QryOrdBetHdr extends BaseHdr {

    public static String saveUrlOrdBet = "";

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



        var list1 = qryOrdBet(queryParams);
        wrtResp(exchange, encodeJson(list1));


    }

    private Object qryOrdBet(Map<String, String> queryParams) throws IOException {

        var expression = "";
        String uname = queryParams.get("uname");


    //    addMapx("spdbfun",QryOrdBetHdr::qryOrdBetIni);
        HashMap<String,Function<Map<String, String>,Object> >   mapFuns=new HashMap<>();
        mapFuns.put("sqldbFun",QryOrdBetHdr::qryOrdBetSql);
        mapFuns.put("luceneFun",QryOrdBetHdr::qryOrdBetIni);
        mapFuns.put("arrFun",QryOrdBetHdr::qryOrdBetIni);
        execQry(saveUrlOrdBet,mapFuns);

    }




    private static Object qryOrdBetSql(Map<String, String> queryParams) {
        return null;
    }


    private static Object qryOrdBetIni(Map<String, String> queryParams) {

        String uname = (String) getField2025(queryParams,"uname","");
        var expression = "";
        if (!uname.equals("")) {
            //
            // 转义正则表达式特殊字符
            String escapedUname = uname.replaceAll("([\\\\+*?\\[\\](){}|.^$])", "\\\\$1");
            // 使用转义后的uname变量
            expression = "#this['uname'] matches '.*" + escapedUname + ".*'";
        }
        var list1 = findObjs(saveUrlOrdBet , expression);
        return list1;
    }





}

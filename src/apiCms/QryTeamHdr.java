package apiCms;

import biz.BaseHdr;
import com.sun.net.httpserver.HttpExchange;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static util.ToXX.parseQueryParams;
import static util.Util2025.encodeJson;
import static util.dbutil.*;
import static util.util2026.*;
import static util.util2026.wrtResp;
//http://localhost:8889/QryTeamHdr
public class QryTeamHdr  extends BaseHdr {
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
        queryParams.put("uname",uname);

        var list1 = qryTeam(uname,queryParams);
        wrtResp(exchange, encodeJson(list1));

    }

    private Object qryTeam(String uname, Map<String, String> queryParams) {

        var expression = "";
       // String uname = queryParams.get("uname");


        //    addMapx("spdbfun",QryOrdBetHdr::qryOrdBetIni);
        HashMap<String, Function<Map<String, String>, Object>> mapFuns = new HashMap<>();
      //  mapFuns.put("sqldbFun", QryOrdBetHdr::qryOrdBetSql);
      //  mapFuns.put("luceneFun", QryOrdBetHdr::qryOrdBetIni);
        mapFuns.put("arrFun", QryTeamHdr::qryTeamIni);
        return execQry2501(saveDirUsrs, mapFuns,queryParams);
    }


    private static Object qryTeamIni(Map<String, String> queryParams) {
        String uname = (String) getField2025(queryParams, "uname", "");
        var expression = "";
        if (!uname.equals("")) {
            //
            // 转义正则表达式特殊字符
            String escapedUname = uname.replaceAll("([\\\\+*?\\[\\](){}|.^$])", "\\\\$1");
            // 使用转义后的uname变量
            expression = "#this['invtr'] =='" + escapedUname + "'";
        }
        //#this['invtr'] ='007'
        var list1 =0;// findObjs(saveDirUsrs, expression);
        return null;
    }
}

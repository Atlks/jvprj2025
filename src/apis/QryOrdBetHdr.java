package apis;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.Map;

import static apis.QueryUsrHdr.qryuserLucene;
import static apis.QueryUsrHdr.qryuserSql;
import static biz.BaseBiz.saveDirUsrs;
import static biz.BaseBiz.saveUrlOrdBet;
import static java.time.LocalTime.now;
import static util.Util2025.encodeJson;
import static util.dbutil.addObj;
import static util.dbutil.findObjs;
import static util.util2026.*;

/**
 * http://localhost:8889/QryOrdBetHdr
 */
public class QryOrdBetHdr extends BaseHdr {
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
        if (saveDirUsrs.startsWith("jdbc:mysql") || saveDirUsrs.startsWith("jdbc:sqlite")) {
            return qryuserSql(queryParams);
        } else if (saveDirUsrs.startsWith("lucene:")) {

            return qryuserLucene(queryParams);
        } else {
            //json doc ,ini ,redis ,lucene
            return qryOrdBetIni(queryParams);
        }
    }

    private Object qryOrdBetIni(Map<String, String> queryParams) {

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

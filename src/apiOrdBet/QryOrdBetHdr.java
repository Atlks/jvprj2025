package apiOrdBet;

import apis.BaseHdr;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.function.Function;

//import static apiUsr.QueryUsrHdr.qryuserLucene;



import static apiUsr.RegHandler.saveDirUsrs;
import static java.time.LocalTime.now;
import static util.ToXX.toObjFrmMap;
import static util.Util2025.encodeJson;
import static util.dbutil.execQry;
import static util.dbutil.qrySql;
import static util.util2026.*;

/**
 * http://localhost:8889/QryOrdBetHdr
 */
public class QryOrdBetHdr extends BaseHdr {

    public static String saveUrlOrdBet = "";

    @Override
    public void handle2( HttpExchange exchange) throws Exception {


        if (isNotLogined(exchange)) {
            //need login
            wrtResp(exchange, "needLogin需要登录");
            return;
        }

        //blk login ed
        String uname = getcookie("uname", exchange);
        Map<String, String> queryParams = parseQueryParams(exchange.getRequestURI());
        queryParams4ordbet prm=toObjFrmMap(queryParams,queryParams4ordbet.class);

        var list1 = qryOrdBet(prm);
        wrtResp(exchange, encodeJson(list1));


    }

    public static void main(String[] args) throws Exception {
       iniCfgFrmCfgfile();;
        queryParams4ordbet queryParams=new queryParams4ordbet();
        queryParams.uname="008";
        System.out.println(encodeJson( qryOrdBetSql(queryParams)));
    }

    private Object qryOrdBet(queryParams4ordbet queryParams) throws Exception {

        var expression = "";
        String uname = queryParams.uname;

        if (isSqldb(saveUrlOrdBet)  ) {
            return qryOrdBetSql(queryParams);

        } else {
            //json doc ,ini ,redis ,lucene
           // return qryOrdBetIni(queryParams);
        }
//        //    addMapx("spdbfun",QryOrdBetHdr::qryOrdBetIni);
//        HashMap<String, Function<Map<String, String>, Object>> mapFuns = new HashMap<>();
//        mapFuns.put("sqldbFun", QryOrdBetHdr::qryOrdBetSql);
//    //    mapFuns.put("luceneFun", QryOrdBetHdr::qryOrdBetIni);
//        mapFuns.put("arrFun", QryOrdBetHdr::qryOrdBetIni);
  //      return execQry(saveUrlOrdBet, mapFuns);

        return null;
    }


    private static Object qryOrdBetSql(queryParams4ordbet queryParams) throws Exception {

        String expression="";
        if (!queryParams.uname.equals("")) {
            String uname=queryParams.uname;
            expression = "uname like '%"+uname+"%'";
        }
        var sql = "select * from ordbet where 1=1 and " + expression;
        var list1 = qrySql(sql, saveUrlOrdBet);
        return   list1;

    }


    private static Object qryOrdBetIni(Map<String, String> queryParams) {

        String uname = (String) getField2025(queryParams, "uname", "");
        var expression = "";
        if (!uname.equals("")) {
            //
            // 转义正则表达式特殊字符
            String escapedUname = uname.replaceAll("([\\\\+*?\\[\\](){}|.^$])", "\\\\$1");
            // 使用转义后的uname变量
            expression = "#this['uname'] matches '.*" + escapedUname + ".*'";
        }
        var list1 = 0;// = findObjs(saveUrlOrdBet, expression);
        return list1;
    }


}

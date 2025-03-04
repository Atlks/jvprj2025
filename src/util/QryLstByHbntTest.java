package util;

import entityx.OrdBet;
import org.hibernate.Session;
import util.HttpExchangeImp;
import util.excptn.OrmUtilBiz;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static api.wlt.RechargeHdr.saveUrlOrdChrg;
import static cfg.MyCfg.iniCfgFrmCfgfile;
import static util.tx.Qry.convertSqlToSpEL;
import static util.Util2025.encodeJson;
import static util.Util2025.encodeJsonObj;
import static util.dbutil.nativeQueryGetResultList;

public class QryLstByHbntTest {

    public static void main(String[] args) throws Exception {
        iniCfgFrmCfgfile();;
        OrdBet queryParams=new OrdBet();
        queryParams.uname="008";
        //    System.out.println(encodeJson( qryOrdBetSql(queryParams)));


        HttpExchangeImp he = new HttpExchangeImp("http://localhost:8889/QryLogCmsHdr", "uname=007","output2025.txt");

        //   new QryOrdBetHdr().handle2(he);
        OrdBet prm=new OrdBet();prm.uname="009";
        System.out.println(encodeJsonObj(  qryOrdBetSql4t(prm)));   ;
    }


    private static Object qryOrdBetSql4t(OrdBet queryParams) throws Exception {

        //   order by timestamp desc
        var sql = "select * from ordbet where  uname =:uname    order by timestamp desc" ;
        Map<String, Object> sqlprmMap= Map.of( "sql",sql,   "uname",queryParams.uname);
        System.out.println( encodeJson(sqlprmMap));
        System.out.println("spel="+convertSqlToSpEL(sql));
        Session session = OrmUtilBiz. openSession(saveUrlOrdChrg);
        List<OrdBet> lst = nativeQueryGetResultList( sql, sqlprmMap, session,OrdBet.class );


        //    var list1 = getSortedMapsBypages( sql,pageSize, pageNumber);
        // 1️⃣ 计算总记录数
        return lst;


    }


    /**
     * 截取 SQL 排序部分的内容
     * @param sql SQL 查询语句
     * @return 排序部分，若没有排序部分则返回空字符串   返回 order by xxxx
     */
    public static String getOrderbyFromSql(String sql) {
        // 正则表达式用于匹配 ORDER BY 后面的内容
        // 正则表达式用于匹配完整的 ORDER BY 部分
        String regex = "(?i)(order by\\s+.*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(sql);

        if (matcher.find()) {
            return matcher.group(1).trim(); // 返回排序部分
        } else {
            return ""; // 如果没有 ORDER BY 部分，返回空字符串
        }
    }

}

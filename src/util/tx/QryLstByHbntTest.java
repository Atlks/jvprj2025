package util.tx;

import entityx.BetOrd;
import org.hibernate.Session;
import util.misc.HttpExchangeImp;
import util.excptn.OrmUtilBiz;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static api.wlt.RechargeHdr.saveUrlOrdChrg;
import static cfg.MyCfg.iniCfgFrmCfgfile;
import static util.tx.Qry.convertSqlToSpEL;
import static util.misc.Util2025.encodeJson;
import static util.misc.Util2025.encodeJsonObj;
import static util.tx.dbutil.nativeQueryGetResultList;

public class QryLstByHbntTest {

    public static void main(String[] args) throws Exception {
        iniCfgFrmCfgfile();;
        BetOrd queryParams=new BetOrd();
        queryParams.uname="008";
        //    System.out.println(encodeJson( qryOrdBetSql(queryParams)));


        HttpExchangeImp he = new HttpExchangeImp("http://localhost:8889/QryLogCmsHdr", "uname=007","output2025.txt");

        //   new QryOrdBetHdr().handle2(he);
        BetOrd prm=new BetOrd();prm.uname="009";
        System.out.println(encodeJsonObj(  qryOrdBetSql4t(prm)));   ;
    }


    private static Object qryOrdBetSql4t(BetOrd queryParams) throws Exception {

        //   order by timestamp desc
        var sql = "select * from ordbet where  uname =:uname    order by timestamp desc" ;
        Map<String, Object> sqlprmMap= Map.of( "sql",sql,   "uname",queryParams.uname);
        System.out.println( encodeJson(sqlprmMap));
        System.out.println("spel="+convertSqlToSpEL(sql));
        Session session = OrmUtilBiz. openSession(saveUrlOrdChrg);
        List<BetOrd> lst = nativeQueryGetResultList( sql, sqlprmMap, session, BetOrd.class );


        //    var list1 = getSortedMapsBypages( sql,pageSize, pageNumber);
        // 1️⃣ 计算总记录数
        return lst;


    }




}

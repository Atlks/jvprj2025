package api.usr;

import biz.BaseHdr;
import cfg.MyCfg;
import com.sun.net.httpserver.HttpExchange;
import entityx.Usr;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//import org.apache.lucene.index.DirectoryReader;
//import org.apache.lucene.index.Term;
//import org.apache.lucene.search.*;
//import org.apache.lucene.store.Directory;
//import org.apache.lucene.store.FSDirectory;
//import static util.UtilLucene.toListMap;
import java.util.*;


import static util.ToXX.parseQueryParams;
import static util.Util2025.encodeJson;

import static util.dbutil.*;

import static util.util2026.*;

@Component
public class QueryUsrHdr extends BaseHdr<Usr, Usr> {

    @Autowired
    public QueryUsrHdr(SessionFactory sessionFactory1){
        this.sessionFactory = sessionFactory1;
        super.sessionFactory=sessionFactory1;
    }
    public static void main(String[] args) throws Exception {
        MyCfg.iniCfgFrmCfgfile();
        Map<String, String> queryParams = Map.of(
                "uname", "007",
                "key2", "value2"
        );
        var list1 = qryuser(queryParams);
        System.out.println(encodeJson(list1));
    }

    @Override
    public void handle2(HttpExchange exchange) throws Exception {


        String uname = getcookie("uname", exchange);
        uname="ttt";


        //blk login ed
        // qryuser(exchange);
        Map<String, String> queryParams = parseQueryParams(exchange.getRequestURI());
        var list1 = qryuser(queryParams);
        wrtResp(exchange, encodeJson(list1));
    }

    private static List<SortedMap<String, Object>> qryuser(Map<String, String> queryParams) throws Exception {
        var expression = "";
        String uname =getFieldAsStrFrmMap( queryParams,"uname");

        if (isSqldb(saveDirUsrs)  ) {
            return qryuserSql(queryParams);
        } else if (saveDirUsrs.startsWith("lucene:")) {
            return  null;
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

    private static String getFieldAsStrFrmMap(Map<String, String> queryParams, String uname) {
      return  queryParams.getOrDefault(uname,"");
    }


    static List<SortedMap<String, Object>> qryuserSql(Map<String, String> queryParams) throws Exception {
        String uname = queryParams.getOrDefault("uname","");
        var expression = "";

        //addObjMysql(obj, collName, saveDir);
        if (!uname.equals("")) {
            expression = " and uname like '%"+uname+"%'";
        }
        var sql = "select * from usr where 1=1  " + expression;
        System.out.println(sql);
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

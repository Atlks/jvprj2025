package api.usr;

import entityx.usr.ReqDtoQryUsr;
import model.usr.Usr;
import jakarta.annotation.security.PermitAll;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import util.algo.Icall;
import util.annos.Paths;
//import org.apache.lucene.index.DirectoryReader;
//import org.apache.lucene.index.Term;
//import org.apache.lucene.search.*;
//import org.apache.lucene.store.Directory;
//import org.apache.lucene.store.FSDirectory;
//import static util.UtilLucene.toListMap;
import java.util.*;


//import static cfg.Containr.sessionFactory;
import static cfg.Containr.sessionFactory;
import static util.algo.ToXX.toSnake;
import static util.model.common.ApiResponse.createResponse;
import static util.algo.EncodeUtil.encodeParamSql;
import static util.algo.NullUtil.isBlank;

import static util.oo.SqlUti.orderby;
import static util.tx.Pagging.getPageResultByHbntV3;


//组合了  和 @ResponseBody，表示该类是 REST API 控制器，所有方法的返回值默认序列化为 JSON 或 XML。
@PermitAll
@Paths({"/admin/qryUsrApi", "/admin/qryUsr"})
//   http://localhost:8889/admin/qryUsr?uname=008&page=1&pagesize=100
@NoArgsConstructor
@Data

public class QueryUsrHdrApi implements Icall<ReqDtoQryUsr, Object> {

    public Object main(ReqDtoQryUsr reqdto) throws Exception {

        String crtTimeStmp = toSnake(Usr.Fields.crtTimeStmp);
        var uNameLikeConditon = "";
        if (!isBlank(reqdto.unameKeyword))
            uNameLikeConditon = "where  uname like '%" + encodeParamSql(reqdto.unameKeyword) + "%'";
        var sql = "select * from usr " + uNameLikeConditon

                + orderby(crtTimeStmp + " desc ");
        System.out.println(sql);

        Session session = sessionFactory.getCurrentSession();
        var list1 = getPageResultByHbntV3(sql, new HashMap<>(), reqdto, session);

        return createResponse(list1);

    }




//
//    private static List<SortedMap<String, Object>> qryuser(Map<String, String> queryParams) throws Exception {
//        var expression = "";
//        String uname =getFieldAsStrFrmMap( queryParams,"uname");
//
//        if (isSqldb(saveDirUsrs)  ) {
//            return qryuserSql(queryParams);
//        } else if (saveDirUsrs.startsWith("lucene:")) {
//            return  null;
//            //   return qryuserLucene(queryParams);
//        } else {
//            //json doc ,ini ,redis ,lucene
//            return qryuserIni(queryParams);
//        }

    /// /        org.hibernate.Session session = OrmUtilBiz.openSession(saveDirUsrs);
    /// /
    /// /        //todo start tx
    /// /        session.beginTransaction();
    /// /        Usr u=  session.find(Usr.class,uname);
    /// /        session.getTransaction().commit();
//    }
    private static String getFieldAsStrFrmMap(Map<String, String> queryParams, String uname) {
        return queryParams.getOrDefault(uname, "");
    }


//    static List<SortedMap<String, Object>> qryuserSql(Map<String, String> queryParams) throws Exception {
//        String uname = queryParams.getOrDefault("uname", "");
//        var expression = "";
//
//        //addObjMysql(obj, collName, saveDir);
//        if (!uname.equals("")) {
//            expression = " and uname like '%" + uname + "%'";
//        }
//        var sql = "select * from usr where 1=1  " + expression;
//        System.out.println(sql);
//        var list1 = qrySql(sql, saveDirUsrs);
//        return (List<SortedMap<String, Object>>) list1;
//    }

//    private static List<SortedMap<String, Object>> qryuserIni(Map<String, String> queryParams) {
//        String uname = (String) getField2025(queryParams, "uname", "");
//        var expression = "";
//        if (!uname.equals("")) {
//            //
//            // 转义正则表达式特殊字符
//            String escapedUname = uname.replaceAll("([\\\\+*?\\[\\](){}|.^$])", "\\\\$1");
//            // 使用转义后的uname变量
//            expression = "#this['uname'] matches '.*" + escapedUname + ".*'";
//        }
//        var list1 = 0;//findObjs(saveDirUsrs , expression);
//        return list1;
//    }


}

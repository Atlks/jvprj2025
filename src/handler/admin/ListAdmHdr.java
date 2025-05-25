package handler.admin;

import handler.admin.dto.QryAdmDto;
import model.admin.Admin;
import entityx.usr.NonDto;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Session;


import util.algo.Icall;
import util.annos.Paths;
import util.serverless.ApiGatewayResponse;

import java.util.HashMap;
import java.util.Map;

import static cfg.Containr.sessionFactory;
import static util.algo.EncodeUtil.encodeSqlPrm;
import static util.algo.GetUti.getTableName;
import static util.algo.NullUtil.isBlank;
import static util.algo.ToXX.toSnake;
import static util.oo.SqlUti.fromWzSlct;
import static util.oo.SqlUti.orderByDesc;
import static util.tx.dbutil.nativeQueryGetResultList;


//组合了  和 @ResponseBody，表示该类是 REST API 控制器，所有方法的返回值默认序列化为 JSON 或 XML。
@PermitAll
@Paths({"/admin/listAdm","admin/ListAdmHdr"})
///admin/ListAdmHdr
//   http://localhost:8889/admin/listAdm
@NoArgsConstructor
@Data

public class ListAdmHdr  {

    public Object handleRequest(QryAdmDto reqdto) throws Exception {

//        var uNameLikeConditon = "";

        var sql =
                fromWzSlct(Admin.class);

        if (!isBlank(reqdto.uname))
        {
            sql+= " where  "+addCondt(Admin.Fields.username ," like", reqdto.uname );

        }

              sql+=  orderByDesc(Admin.Fields.createdAt);
               // order by createdAt desc  ";
        System.out.println(sql);

        Session session = sessionFactory.getCurrentSession();
        var list1 = nativeQueryGetResultList(sql, new HashMap<>(), session, Admin.class);



       return  (list1);
    }

    private String addCondt(String fld, String op, String val) {
        if(op.trim().toLowerCase().equals("like"))
            val="'%"+encodeSqlPrm(val) +"%'";
        fld=toSnake(fld);
        return fld+" "+op+" "+val;
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

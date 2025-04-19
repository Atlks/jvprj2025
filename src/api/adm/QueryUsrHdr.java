package api.adm;

import util.annos.NoDftParam;
import util.annos.RequireAuth;
import entityx.usr.ReqDtoQryUsr;
import jakarta.ws.rs.Path;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import util.algo.Icall;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import static cfg.AppConfig.sessionFactory;
import static ztest.htmlTppltl.rend;
import static util.algo.EncodeUtil.encodeParamSql;
import static util.algo.NullUtil.isBlank;
import static util.tx.Pagging.getPageResultByHbntV3;

@Controller


//组合了 @Controller 和 @ResponseBody，表示该类是 REST API 控制器，所有方法的返回值默认序列化为 JSON 或 XML。
//@PermitAll
@Path("/admin/qryUsr")
//   http://localhost:8889/admin/qryUsr?uname=008&page=1&pagesize=100
@NoArgsConstructor
@Data
@Component
@RequireAuth(role="admin",authFun= AuthFun4admin.class)
@NoDftParam
public class QueryUsrHdr implements Icall<ReqDtoQryUsr, Object> {

    public Object main(ReqDtoQryUsr reqdto) throws Exception {

        var uNameLikeConditon = "";
        if (!isBlank(reqdto.unameKeyword))
            uNameLikeConditon = "where  uname like '%" + encodeParamSql(reqdto.unameKeyword) + "%'";
        var sql = "select * from usr " + uNameLikeConditon + " order by crtTimeStmp desc  ";
        System.out.println(sql);

        Session session = sessionFactory.getCurrentSession();
        var list1 = getPageResultByHbntV3(sql, new HashMap<>(), reqdto, session);


        Context context = new Context();
        context.setVariable("users", list1.records);
        String tmpleFileName = "adm/usr/listUsr";


        return  ( rend(tmpleFileName, context ));
     //   return createResponse(list1);

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

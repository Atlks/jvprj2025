package api.bet;

import biz.BaseHdr;
import biz.Response;
import entityx.BetOrd;
import entityx.TransDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.Path;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import util.algo.Icall;
import util.excptn.OrmUtilBiz;

import java.util.List;
import java.util.Map;

//import static api.usr.QueryUsrHdr.qryuserLucene;


import static api.wlt.RechargeHdr.saveUrlOrdChrg;
import static biz.Response.createResponse;
import static cfg.AppConfig.sessionFactory;
import static java.time.LocalTime.now;
import static util.algo.EncodeUtil.encodeParamSql;
import static util.tx.Pagging.getPageResultByHbntV2;
import static util.algo.ToXX.toObjFrmQrystr;
import static util.misc.Util2025.encodeJson;
import static util.misc.util2026.*;
import static util.tx.dbutil.setPrmts4sql;

/**   查询规则，参数使用类型化，不要新定义dto，直接使用实体代替
 * http://localhost:8889/list_bets
 */
@RestController
@Tag(name = "bet")
@Path("/list_bets")
@annos.JwtParam(name = "uname")

@NoArgsConstructor
public class ListBetsHdr  implements Icall<QryDto4bets, Object>  {

    public static String saveUrlOrdBet = "";

    @Override
    public Object  call( QryDto4bets dto) throws Exception {

        //blk login ed
      //  String uname = getcookie("uname", exchange);
     //   BetOrd qryDto4page=toObjFrmQrystr(exchange, BetOrd.class);

        var sql = "select * from ordbet where uname ="+encodeParamSql(dto.uname)+" order by timestamp desc " ;
        System.out.println( sql);
//        Map<String, Object> sqlprmMap= Map.of( "sql",sql,   "uname",dto.uname);
//        System.out.println( encodeJson(sqlprmMap));
   //     System.out.println("spel="+convertSqlToSpEL(sql));
        Session session =  sessionFactory.getCurrentSession();
        var list1 =getListBySql(sql,session);
                //getPageResultByHbntV2(sql,  Map.of(), dto.page, dto.pagesize,session);

        return  createResponse(list1);
    }




//    private static Object qryOrdBetIni(OrdBet queryParams) {
//
//        String uname = (String) getField2025(queryParams, "uname", "");
//        var expression = "";
//        if (!uname.equals("")) {
//            //
//            // 转义正则表达式特殊字符
//            String escapedUname = uname.replaceAll("([\\\\+*?\\[\\](){}|.^$])", "\\\\$1");
//            // 使用转义后的uname变量
//            expression = "#this['uname'] matches '.*" + escapedUname + ".*'";
//        }
//        var list1 = 0;// = findObjs(saveUrlOrdBet, expression);
//        return list1;
//    }


}

package api.bet;

import cfg.MyCfg;
import com.sun.net.httpserver.HttpExchange;
import entityx.OrdBet;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CookieValue;
import util.HttpExchangeImp;
import util.Icall;

import java.sql.SQLException;
import java.util.Map;


import static cfg.AppConfig.sessionFactory;
import static java.time.LocalTime.now;
import static util.HbntUtil.persistByHibernate;
import static util.ToXX.parseQueryParams;
import static util.ToXX.toObjFrmMap;
import static util.Util2025.encodeJson;
import static util.dbutil.addObj;
import static util.util2026.*;


/**
 * http://localhost:8889/BetHdr?bettxt=龙湖和
 */
@Component
@NoArgsConstructor
public class BetHdr   implements Icall<HttpExchange,Object> {



//    @Autowired
//    private SessionFactory sessionFactory;
//@Path("/BetHdr")
@QueryParam("bettxt")
@CookieParam("uname")

@Tag(name = "bet")
@Path("/BetHdr")
@Operation(method = "get", summary = "投注", description = "")
@Parameter(name ="bettxt" , description = "", required = true, example = "123",in=ParameterIn.QUERY)
@Parameter(name="uname",description = "用户名（从 Cookie 获取）",required = true ,in = ParameterIn.COOKIE)



    @Override
    public Object call(@CookieValue(name="uname") HttpExchange exchange) throws Exception {




        //blk login ed
        String uname = getcookie("uname", exchange);
        Map<String, String> queryParams = parseQueryParams(exchange.getRequestURI());
        OrdBet ord=toObjFrmMap(queryParams,OrdBet.class);
//        String now = String.valueOf(now());
//        queryParams.put("datetime_utc", now);
//        queryParams.put("datetime_local", getLocalTimeString());
//        queryParams.put("timezone", now);
        ord.timestamp=System.currentTimeMillis();
        ord.uname=uname;
        ord.id="ordBet"+getFilenameFrmLocalTimeString();

        wrtResp(exchange, encodeJson( addOrdBet(ord)));


    return null;
}

    public Object addOrdBet(OrdBet ord) throws SQLException {
        Session session =sessionFactory.getCurrentSession();
        //  om.jdbcurl=saveDirUsrs;
        //todo start tx

         return persistByHibernate(ord,session);


    }


    public static void main(String[] args) throws Exception {

        MyCfg.iniCfgFrmCfgfile();;
        OrdBet ord=new OrdBet();
//        String now = String.valueOf(now());
//        queryParams.put("datetime_utc", now);
//        queryParams.put("datetime_local", getLocalTimeString());
//        queryParams.put("timezone", now);
        ord.timestamp=System.currentTimeMillis();
        ord.uname="007";
        ord.bettxt="龙湖和";
        ord.id="ordBet"+getFilenameFrmLocalTimeString();
     //   addObj(ord,saveUrlOrdBet,OrdBet.class);

        HttpExchangeImp he = new HttpExchangeImp("http://localhost:8889/QryLogCmsHdr?bettxt=龙湖和", "uname=009","output2025.txt");

     //   new AddOrdBetHdr().handle2(he);
    }

    /**
     * @param dto
     * @return
     * @throws Exception
     */

    public Object handlex(Object dto) throws Exception {
        return null;
    }


}

package apiAcc;

import apiOrdBet.OrdBet;
import apis.BaseHdr;
import com.sun.net.httpserver.HttpExchange;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static java.time.LocalTime.now;
import org.hibernate.Session;
import utilBiz.OrmUtilBiz;

import static util.HbntUtil.openSession;
import static util.HbntUtil.persistByHbnt;
import static util.ToXX.parseQueryParams;
import static util.dbutil.addObj;
import static util.util2026.*;

/**
 * http://localhost:8889/AddOrdChargeHdr?amt=888
 */
public class AddOrdChargeHdr extends BaseHdr {
    public static String saveUrlOrdChrg;

    @Override
    public void handle2(HttpExchange exchange) throws Exception {



        //blk login ed
        String uname = getcookie("uname", exchange);
        Map<String, String> queryParams = parseQueryParams(exchange.getRequestURI());


        OrdChrg ord = new OrdChrg();
        ord.uname = uname;
        ord.amt = new BigDecimal(queryParams.get("amt"));
        ord.timestamp = System.currentTimeMillis();
        ord.id = "ordChrg" + getFilenameFrmLocalTimeString();
        addOrdChg(ord);
        wrtResp(exchange, "ok");


    }


    private static void addOrdChg(OrdChrg ord) throws Exception {
        String now = String.valueOf(now());
        // 使用 try-with-resources 自动关闭
      try(  Session session = OrmUtilBiz. openSession(saveUrlOrdChrg)) {
          //  om.jdbcurl=saveDirUsrs;
          //todo start tx
          session.beginTransaction();
          persistByHbnt(ord,session);
          session.getTransaction().commit();
      }
    //    addObj(ord, saveUrlOrdChrg,OrdChrg.class);
    }

    public static void main(String[] args) throws Exception {
        iniCfgFrmCfgfile();
       // drvMap.put("com.mysql.cj.jdbc.Driver", "org.h2.Driver");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("amt", new BigDecimal("888"));

        OrdChrg ord = new OrdChrg();
        ord.uname = "009";
        ord.amt = new BigDecimal("888");
        ord.timestamp = System.currentTimeMillis();
        ord.id = "ordChrg" + getFilenameFrmLocalTimeString();
        System.out.println("ordid=" + ord.id
        );
        addOrdChg(ord);
    }


}

package apiOrdBet;

import apis.BaseHdr;
import com.alibaba.fastjson2.JSON;
import com.sun.net.httpserver.HttpExchange;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.HttpExchangeImp;
import utilBiz.OrmUtilBiz;

import java.sql.SQLException;
import java.util.Map;


import static apiOrdBet.QryOrdBetHdr.saveUrlOrdBet;
import static java.time.LocalTime.now;
import static util.ToXX.parseQueryParams;
import static util.ToXX.toObjFrmMap;
import static util.dbutil.addObj;
import static util.util2026.*;

/**
 * http://localhost:8889/AddOrdBetHdr?bettxt=龙湖和
 */
@Component
public class AddOrdBetHdr extends BaseHdr {

    public AddOrdBetHdr(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        super.sessionFactory= sessionFactory;
    }

//    @Autowired
//    private SessionFactory sessionFactory;

    @Override
    public void handle2(HttpExchange exchange) throws Exception {




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
        addOrdBet(ord);
        wrtResp(exchange, "ok");


    }

    public void addOrdBet(OrdBet ord) throws SQLException {
        Session session =sessionFactory.getCurrentSession();
        //  om.jdbcurl=saveDirUsrs;
        //todo start tx
        session.beginTransaction();
        session.persist(ord);
        session.getTransaction().commit();

    }


    public static void main(String[] args) throws Exception {

        iniCfgFrmCfgfile();;
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

}

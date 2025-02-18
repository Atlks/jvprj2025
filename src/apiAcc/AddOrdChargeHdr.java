package apiAcc;

import apis.BaseHdr;
import com.sun.net.httpserver.HttpExchange;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static java.time.LocalTime.now;

import entityx.OrdChrg;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static util.HbntUtil.persistByHbnt;
import static util.ToXX.parseQueryParams;
import static util.dbutil.addObj;
import static util.util2026.*;

/**
 * http://localhost:8889/AddOrdChargeHdr?amt=888
 */
@Component
public class AddOrdChargeHdr extends BaseHdr {
    public static String saveUrlOrdChrg;

    //    public AddOrdChargeHdr() {
//    }
    @Autowired
    public SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public AddOrdChargeHdr(SessionFactory sess) {
        this.sessionFactory = sess;
    }

    @Override
    public void handle2(HttpExchange exchange) throws Exception {


        System.out.println("handle2.sessfac="+sessionFactory);
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

    private void addOrdChg(OrdChrg ord) throws Exception {
        String now = String.valueOf(now());
        // 使用 try-with-resources 自动关闭
        //try(  Session session = OrmUtilBiz. openSession(saveUrlOrdChrg)) {
        //  om.jdbcurl=saveDirUsrs;
        //todo start tx
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        persistByHbnt(ord, session);
        session.getTransaction().commit();
        //   }
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
        //   addOrdChg(ord);
    }


}

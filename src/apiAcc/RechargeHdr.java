package apiAcc;

import biz.BaseHdr;
import biz.HttpHandlerX;
import com.sun.net.httpserver.HttpExchange;

import java.math.BigDecimal;
import java.util.Map;

import static java.time.LocalTime.now;

import entityx.ChgOrd;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static util.HbntUtil.persistByHbnt;
import static util.ToXX.parseQueryParams;
import static util.dbutil.addObj;
import static util.util2026.*;
@Slf4j
/**
 * http://localhost:8889/AddOrdChargeHdr?amt=888
 */
@DeclareRoles({"ADMIN", "USER"})
@RolesAllowed({"", "USER"})
@Component
public class RechargeHdr extends BaseHdr implements HttpHandlerX {
    public static String saveUrlOrdChrg;

    //    public AddOrdChargeHdr() {
//    }
    @Autowired
    public SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public RechargeHdr(SessionFactory sess) {
        this.sessionFactory = sess;
    }

    public RechargeHdr() {
    }


    @RolesAllowed({"", "USER"})  // 只有 ADMIN 和 USER 角色可以访问
    private void addChgOrd(@NotNull(message = "ordDto is required")  ChgOrd ord) throws Exception {
        log.info("Processing addChgOrd...");
        String now = String.valueOf(now());
        // 使用 try-with-resources 自动关闭
        //try(  Session session = OrmUtilBiz. openSession(saveUrlOrdChrg)) {
        //  om.jdbcurl=saveDirUsrs;
        //todo start tx
        Session session = sessionFactory.getCurrentSession();

        persistByHbnt(ord, session);

        //   }
        //    addObj(ord, saveUrlOrdChrg,OrdChrg.class);
    }

//    public static void main(String[] args) throws Exception {
//        iniCfgFrmCfgfile();
//        // drvMap.put("com.mysql.cj.jdbc.Driver", "org.h2.Driver");
//        Map<String, Object> queryParams = new HashMap<>();
//        queryParams.put("amt", new BigDecimal("888"));
//
//        OrdChrg ord = new OrdChrg();
//        ord.uname = "009";
//        ord.amt = new BigDecimal("888");
//        ord.timestamp = System.currentTimeMillis();
//        ord.id = "ordChrg" + getFilenameFrmLocalTimeString();
//        System.out.println("ordid=" + ord.id
//        );
//        //   addOrdChg(ord);
//    }


    /** http://localhost:8889/RechargeHdr?amt=888
     * @param exchange
     * @throws Exception
     */
    @Override
     @GET
    @Path("/RechargeHdr")
    @QueryParam("amt")
    @CookieParam("uname")
    @Transactional
    public void handle2(HttpExchange exchange) throws Exception {
        System.out.println("handle2.sessfac=" + sessionFactory);
        //blk login ed
        String uname = getcookie("uname", exchange);
        Map<String, String> queryParams = parseQueryParams(exchange.getRequestURI());


        ChgOrd ord = new ChgOrd();
        ord.uname = uname;
        ord.amt = new BigDecimal(queryParams.get("amt"));
        ord.timestamp = System.currentTimeMillis();
        ord.id = "ordChrg" + getFilenameFrmLocalTimeString();


            addChgOrd(ord);
            wrtResp(exchange, "ok");


    }

    /**
     * @param exchange
     * @throws Exception
     */
    @Override
    public void handlex(HttpExchange exchange) throws Exception {

    }


//    @Override
//    public void handle(HttpExchange exchange) throws IOException {
//        try {
//            handlex(exchange);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

//    /**
//     * @param exchange
//     * @throws Throwable
//     */
//    @Override
//    protected void handle2(HttpExchange exchange) throws Throwable {
//
//    }

    /**
     * handle() 直接调用 handlex()，没有经过代理。
     * 解决办法
     *
     * 在 handle() 方法中，通过 this 转换成接口再调用 handlex()，确保它走代理：
     *
     * @Override
     * public void handle(HttpExchange exchange) throws IOException {
     *     try {
     *         ((HttpHandlerX) Proxy.newProxyInstance(
     *             this.getClass().getClassLoader(),
     *             this.getClass().getInterfaces(),
     *             new JdkDynamicProxySmpl(this)
     *         )).handlex(exchange);
     *     } catch (Exception e) {
     *         throw new RuntimeException(e);
     */
}

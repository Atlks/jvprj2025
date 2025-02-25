//RegHandler.java

package apiUsr;

import biz.BaseHdr;
import biz.existUserEx;
import com.sun.net.httpserver.HttpHandler;
import entityx.Usr;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import util.HbntUtil;


import static util.HbntUtil.persistByHibernate;
import static util.Util2025.encodeJson;
import static util.util2026.*;

@Component  // 让 Spring 自动管理这个 Bean

//  http://localhost:8889/reg?uname=008&pwd=000&invtr=007
@RestController
@RequestMapping("/reg")
@Tag(name = "用户管理", description = "用户相关操作")
@PermitAll
public class RegHandler extends BaseHdr<Usr, Usr> implements HttpHandler {

    //  @Inject // 可选，PicoContainer 其实不依赖 @Inject，但能增加可读性
    public RegHandler(SessionFactory sessionFactory1) {
        this.sessionFactory = sessionFactory1;
        super.sessionFactory = sessionFactory1;
    }

    //  @Bean
    public RegHandler() { // 确保它是 Solon 代理的 Bean
        System.out.println("RegHandler 已创建");
    }

    /**
     *
     * @throws Exception
     */

    //如何标识swagger文档。。
    //我的访问url类似  http://localhost:8889/reg?uname=008&pwd=000&invtr=007


    // 会自动把 ?name=John&age=30 转换成 UserQueryDTO 对象！
//    public void handle2(
//            @ModelAttribute
//            HttpExchange exchange) throws Exception, existUserEx {
//
//        //u dto
//        Usr u = toDto(exchange,Usr.class);
//
//
//    }
    @Path("/reg")
    @Tag(name = "usr")
    @Operation(summary = "注册用户的方法reg", description = "注册用户的方法dscrp。。。。")
    @RequestMapping("/reg")
    @Parameter(name = "uname", description = "用户名", required = true)
    @Parameter(name = "pwd", description = "密码", required = true)
    @Parameter(name = "uname", description = "邀请人", required = false)
    @PermitAll
    @Validated
    @Override
    public Object handle3(  @ModelAttribute Usr dtoU) throws Exception {
        System.out.println("reghdl.hd3(" + encodeJson(dtoU));
        dtoU.id = dtoU.uname;
        if (existUser(dtoU) && (!ovrwtest)) {
            var e = new existUserEx("存在用户",getCurrentMethodName(),dtoU);
            throw e;
        }
        persistByHibernate(  dtoU, sessionFactory);
        return dtoU;
    }




    public static boolean ovrwtest = false;


    //@Autowired
//    org.hibernate.Session session;
    public boolean existUser(Usr user) throws Exception {
//        org.hibernate.Session session = OrmUtilBiz.openSession(saveDirUsrs);
        //  om.jdbcurl=saveDirUsrs;

        Session session = sessionFactory.getCurrentSession();
        Usr jo = session.find(Usr.class, user.uname);
        if (jo == null)
            return false;
        // 空安全处理，直接操作结果
        if (jo.uname.equals("")) {
            return false;
        } else
            return true;
    }


//    public record User(String id, String uname, String pwd, int age, String invtr) {
//
//        // record 自动生成构造函数、getters、equals、hashCode 和 toString 方法
//    }

    public boolean existUser(String uname) throws Exception {

        //    Usr jo = getObjById(uname, saveDirUsrs, Usr.class);

//        org.hibernate.Session session = OrmUtilBiz.openSession(saveDirUsrs);
        //  om.jdbcurl=saveDirUsrs;
        //todo start tx
        // session.beginTransaction();
        Session session = sessionFactory.getCurrentSession();
        Usr jo = session.find(Usr.class, uname);
        if (jo == null)
            return false;
        // 空安全处理，直接操作结果
        if (jo.uname.equals("")) {
            return false;
        } else
            return true;
    }
}

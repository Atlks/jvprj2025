//RegHandler.java

package api.usr;

import core.IRegHandler;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.springframework.beans.factory.annotation.Qualifier;
import service.auth.ISAM;
import util.ex.existUserEx;
import entityx.Usr;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import static cfg.AppConfig.sessionFactory;
import static util.proxy.AopUtil.ivk4log;
import static util.tx.HbntUtil.persistByHibernate;


/**
 * 注册 用户
 * // @param uname
 * // @param pwd
 */
@Component  // 让 Spring 自动管理这个 Bean

//  http://localhost:8889/reg?uname=008&pwd=000&invtr=007
@RestController
@Path("/reg")
@RequestMapping("/reg")
@Tag(name = "用户管理", description = "用户相关操作")
@annos.Parameter(name = "uname")
@annos.Parameter(name = "pwd")
@PermitAll
@NoArgsConstructor
// @Produces / @Consumes：指定返回和接收的数据格式（如 application/json）
public class RegHandler implements IRegHandler {

    public RegHandler(String uname, String pwd) {
    }

    public static String saveDirUsrs;

    /**
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
//    @Path("/reg")
//    @Tag(name = "usr")
//    @Operation(summary = "注册用户的方法reg", description = "注册用户的方法dscrp。。。。")
//    @Parameter(name = "uname", description = "用户名", required = true)
//    @Parameter(name = "pwd", description = "密码", required = true)
//    @Parameter(name = "uname", description = "邀请人", required = false)
//    @PermitAll
//    @Validated
//    @Override
//    public Object call(@BeanParam RegDto dtoReg) throws Throwable {
//
//    }



    public   void addU(RegDto dtoReg) {
        Usr u=new Usr(dtoReg.uname);
        persistByHibernate( u, sessionFactory.getCurrentSession());
    }



    @Inject
    @Qualifier(SAM4regLgn)
    @Named(SAM4regLgn)
    public ISAM sam;


    public static boolean ovrwtest = false;


    //@Autowired
//    org.hibernate.Session session;
    @Override
    public boolean chkExistUser(RegDto user) throws existUserEx {
//        org.hibernate.Session session = OrmUtilBiz.openSession(saveDirUsrs);
        //  om.jdbcurl=saveDirUsrs;

        Session session = sessionFactory.getCurrentSession();
        Usr jo = session.find(Usr.class, user.uname);
        if (jo == null)
            return false;
            // 空安全处理，直接操作结果

        else
           throw new existUserEx("uname("+user.uname+")");
    }


//    public record User(String id, String uname, String pwd, int age, String invtr) {
//
//        // record 自动生成构造函数、getters、equals、hashCode 和 toString 方法
//    }

//    @Override
//    public boolean existUser(String uname) throws Exception {
//
//        //    Usr jo = getObjById(uname, saveDirUsrs, Usr.class);
//
////        org.hibernate.Session session = OrmUtilBiz.openSession(saveDirUsrs);
//        //  om.jdbcurl=saveDirUsrs;
//        //todo start tx
//        // session.beginTransaction();
//        Session session = sessionFactory.getCurrentSession();
//        Usr jo = session.find(Usr.class, uname);
//        if (jo == null)
//            return false;
//        // 空安全处理，直接操作结果
//        if (jo.uname.equals("")) {
//            return false;
//        } else
//            return true;
//    }


}

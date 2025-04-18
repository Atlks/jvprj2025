//RegHandler.java

package api.adm;

import entityx.admin.Admin;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import util.algo.Icall;
import util.ex.existUserEx;

import static cfg.AppConfig.sessionFactory;
import static util.algo.EncodeUtil.encodeMd5;
import static util.misc.Util2025.encodeJson;
import static util.tx.HbntUtil.persistByHibernate;


/**
 * 注册 用户
 * // @param uname
 * // @param pwd
 */
@Component  // 让 Spring 自动管理这个 Bean

//  http://localhost:8889/adm/add?username=000&password=000&key=
@Controller
@Path("/adm/add")
@RequestMapping("/adm/add")
@Tag(name = "用户管理", description = "用户相关操作")
@PermitAll
@NoArgsConstructor
// @Produces / @Consumes：指定返回和接收的数据格式（如 application/json）
public class AddAdminHdr implements Icall<Admin, Object> {

    public AddAdminHdr(String uname, String pwd) {
    }

    public static String saveDirUsrs;





    //@Autowired
//    org.hibernate.Session session;

    public boolean chkExistUser(Admin user) throws existUserEx {
//        org.hibernate.Session session = OrmUtilBiz.openSession(saveDirUsrs);
        //  om.jdbcurl=saveDirUsrs;
        System.out.println(encodeJson(user));
        Session session = sessionFactory.getCurrentSession();
        Admin jo = session.find(Admin.class, user.username);
        if (jo == null)
            return false;
            // 空安全处理，直接操作结果

        else
           throw new existUserEx("uname("+user.username+")");
    }

    /**
     * @param arg
     * @return
     * @throws Throwable
     */
    @Override
    public Object main(Admin arg) throws Throwable {
        chkExistUser(arg);
        Admin u=new Admin();
        u.username=arg.username;
        u.setPassword(encodeMd5(arg.getPassword()));
        persistByHibernate( u, sessionFactory.getCurrentSession());
        return "ok";
    }




}

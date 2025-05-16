//RegHandler.java

package handler.admin;

import model.admin.Admin;
//import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import lombok.NoArgsConstructor;
import org.hibernate.Session;



import util.algo.Tag;
import util.annos.Paths;
import util.ex.existUserEx;

import static cfg.Containr.sessionFactory;
import static util.algo.EncodeUtil.encodeMd5;
import static util.misc.Util2025.encodeJson;
import static util.tx.HbntUtil.persist;


/**
 * 注册 用户
 * // @param uname
 * // @param pwd
 */
  // 让 Spring 自动管理这个 Bean

//  http://localhost:8889/adm/add?username=000&password=000&key=

@Path("/admin/AddAdminHdr")
//@RequestMapping()
@Paths({"/adm/add"})
@Tag(name = "用户管理", description = "用户相关操作")
@PermitAll
@NoArgsConstructor
// @Produces / @Consumes：指定返回和接收的数据格式（如 application/json）
public class AddAdminHdr {
    /**
     * @param arg
     * @return
     * @throws Throwable
     */

    public Object handleRequest(Admin arg) throws Throwable {
        chkExistUser(arg);
        Admin u=new Admin();
        u.username =arg.username;
        u.setPassword(encodeMd5(arg.getPassword()));
        persist( u, sessionFactory.getCurrentSession());
        return "ok";
    }



    public AddAdminHdr(String uname, String pwd) {
    }

    public static String saveDirUsrs;





    //
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
           throw new existUserEx("uname("+user.username +")");
    }





}

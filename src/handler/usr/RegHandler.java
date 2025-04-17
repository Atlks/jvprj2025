//RegHandler.java

package handler.usr;

import core.IRegHandler;
import entityx.Usr;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.springframework.web.bind.annotation.RestController;
import util.ex.existUserEx;
import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;

import static biz.Containr.sam4regLgn;
import static cfg.AppConfig.sessionFactory;
import static util.misc.Util2025.encodeJson;
import static util.proxy.AopUtil.ivk4log;
import static util.tx.HbntUtil.persistByHibernate;


/**
 * 注册 用户
 * // @param uname
 * // @param pwd
 */
//@Component  // 让 Spring 自动管理这个 Bean

//  http://localhost:8889/reg?uname=008&pwd=000&invtr=007
@RestController
@Path("/reg")
@Tag(name = "用户管理", description = "用户相关操作")
@annos.Parameter(name = "uname")
@annos.Parameter(name = "pwd")
@PermitAll
@NoArgsConstructor
// @Produces / @Consumes：指定返回和接收的数据格式（如 application/json）

//implements RequestHandler<Map<String,Object>, ApiGatewayResponse> {
public class RegHandler implements RequestHandler<RegDto, ApiGatewayResponse>,IRegHandler {
    /**
     * @param dtoReg
     * @param context
     * @return
     */
    @Override
    public ApiGatewayResponse handleRequest(RegDto dtoReg, Context context) throws Throwable {
        System.out.println("RegHandler.handleRequest(" + encodeJson(dtoReg));
        ivk4log("chkExistUser", () -> {
            return chkExistUser(dtoReg);
        });
        //add u
        addU(dtoReg);
        //  storekey(dtoReg);
        sam4regLgn.storeKey(dtoReg.uname, dtoReg.pwd);
        return new ApiGatewayResponse(dtoReg);
    }

    public RegHandler(String uname, String pwd) {
    }



    public   void addU(RegDto dtoReg) {
        Usr u=new Usr(dtoReg.uname);
        persistByHibernate( u, sessionFactory.getCurrentSession());
    }


//
//    @Inject
//    @Qualifier(SAM4regLgn)
//    @Named(SAM4regLgn)
//    public ISAM sam;


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





}

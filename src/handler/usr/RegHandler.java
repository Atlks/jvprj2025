//RegHandler.java

package handler.usr;

import core.IRegHandler;
import model.usr.Usr;
//import io.swagger.v3.oas.annotations.tags.Tag;
import handler.usr.dto.RegDto;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import util.algo.Tag;
import util.ex.existUserEx;

//import static cfg.Containr.evtlist4reg;
import java.util.UUID;

import static handler.acc.IniAcc.iniTwoWlt;
import static util.evt.RegEvt.evtlist4reg;
import static cfg.Containr.sam4regLgn;
import static cfg.Containr.sessionFactory;

import static util.algo.CopyUti.copyProp;
import static util.evtdrv.EvtHlpr.publishEvent;
import static util.misc.Util2025.encodeJson;
import static util.proxy.AopUtil.ivk4log;
import static util.tx.HbntUtil.persist;


/**
 * 注册 用户
 * // @param uname
 * // @param pwd
 */
//  // 让 Spring 自动管理这个 Bean

//  http://localhost:8889/reg?uname=008&pwd=000&invtr=007

@Path("/apiv1/reg")
@Tag(name = "用户管理", description = "用户相关操作")
//@Parameter(name = "uname")
//@Parameter(name = "pwd")
@PermitAll
@NoArgsConstructor
// @Produces / @Consumes：指定返回和接收的数据格式（如 application/json）

//implements RequestHandler<Map<String,Object>, ApiGatewayResponse> {
public class RegHandler implements IRegHandler {
    /**
     * @param dtoReg

     * @return
     */

    public Object handleRequest(RegDto dtoReg  ) throws Throwable {
        System.out.println("RegHandler.handleRequest(" + encodeJson(dtoReg));
        ivk4log("chkExistUser", () -> {
            return chkExistUser(dtoReg);
        });
        //add u
      Usr u=  addU(dtoReg);
        //  storekey(dtoReg);
        sam4regLgn.storeKey(dtoReg.uname, dtoReg.pwd);
        iniTwoWlt(dtoReg.uname);

        publishEvent(evtlist4reg,u);
        return (dtoReg);
    }

    @PermitAll
    @Path("/apiv1/usr/addTestU")
    public static  Object addTestU() throws Throwable {
        for(int i=0;i<5;i++)
        {
            RegDto dtoReg = new RegDto();
            dtoReg.uname = UUID.randomUUID().toString();
            dtoReg.pwd = UUID.randomUUID().toString();
            dtoReg.setCptch("666");
            new RegHandler().handleRequest(dtoReg);
        }
        return "ok";

    }

    public RegHandler(String uname, String pwd) {
    }



    public Usr addU(RegDto dtoReg) {
        Usr u=new Usr(dtoReg.uname);
        copyProp(dtoReg,u);
        persist( u, sessionFactory.getCurrentSession());
        return u;
    }




//
//    @Inject
//    @Qualifier(SAM4regLgn)
//    @Named(SAM4regLgn)
//    public ISAM sam;


    public static boolean ovrwtest = false;


    //
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

//RegHandler.java

package api.usr;

import entityx.usr.Usr;
import handler.usr.RegDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Path;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import service.auth.ISAM;
import util.algo.Icall;
import util.evtdrv.AnotherEvent;
import util.ex.existUserEx;


import static biz.Containr.evtPublisher;
import static cfg.AppConfig.sessionFactory;
import static util.misc.Util2025.encodeJson;
import static util.proxy.AopUtil.ivk4log;
import static util.tx.HbntUtil.persistByHibernate;


/**
 * 注册 用户
 * // @param uname
 * // @param pwd
 */
@Component  // 让 Spring 自动管理这个 Bean

//  http://localhost:8889/reg2?uname=008&pwd=000&invtr=007
@RestController
@Path("/reg2")

@Tag(name = "用户管理", description = "用户相关操作")
@annos.Parameter(name = "uname")
@annos.Parameter(name = "pwd")
@PermitAll
@NoArgsConstructor
public class RegHandler2 implements Icall<RegDto, Object> {
    public static final String SAM4regLgn = "SAM4regLgn";

    public RegHandler2(String uname, String pwd) {
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
    @Path("/reg2")
    @Tag(name = "usr")
    @Operation(summary = "注册用户的方法reg", description = "注册用户的方法dscrp。。。。")

    @Parameter(name = "uname", description = "用户名", required = true)
    @Parameter(name = "pwd", description = "密码", required = true)
    @Parameter(name = "uname", description = "邀请人", required = false)
    @PermitAll
    @Validated

    public Object main(@BeanParam RegDto dtoReg) throws Throwable {
        System.out.println("reghdl.hd3(" + encodeJson(dtoReg));


        evtPublisher.publishEvent(new ChkUsrExstEvt(dtoReg));
//        ivk4log("existUser", () -> {
//             chkExistUser(dtoReg);
//            return 1;
//        });

        evtPublisher.publishEvent(new StartRegEvt(dtoReg));

//        addU(dtoReg);
//
//        strorKey(dtoReg);
        evtPublisher.publishEvent(new FinishRegEvt(dtoReg));
        return dtoReg;
    }

    @EventListener({StartRegEvt.class, AnotherEvent.class})
    public void strorKey(Object event) {
        RegDto dtoReg = (RegDto) ((StartRegEvt) event).getSource();
        sam.storeKey(dtoReg.uname, dtoReg.pwd);
    }


    @EventListener({StartRegEvt.class, AnotherEvent.class})
    public    void addU(Object event) {
        RegDto dtoReg = (RegDto) ((StartRegEvt) event).getSource();
        Usr u = new Usr(dtoReg.uname);
        persistByHibernate(u, sessionFactory.getCurrentSession());
    }


    @Inject
    @Qualifier(SAM4regLgn)
    public ISAM sam;


    public static boolean ovrwtest = false;


    //@Autowired
//    org.hibernate.Session session;
    @EventListener({ChkUsrExstEvt.class, AnotherEvent.class})
    public boolean chkExistUser(ChkUsrExstEvt event) throws existUserEx {
//        org.hibernate.Session session = OrmUtilBiz.openSession(saveDirUsrs);
        //  om.jdbcurl=saveDirUsrs;
        RegDto dtoReg = (RegDto) ((ChkUsrExstEvt) event).getSource();
        Session session = sessionFactory.getCurrentSession();
        Usr jo = session.find(Usr.class, dtoReg.uname);
        if (jo == null)
            return false;
            // 空安全处理，直接操作结果

        else
            throw new existUserEx("uname(" + dtoReg.uname + ")");
    }


//    public record User(String id, String uname, String pwd, int age, String invtr) {
//
//        // record 自动生成构造函数、getters、equals、hashCode 和 toString 方法
//    }

    public boolean chkExistUser(String uname) throws Exception {

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


    private class FinishRegEvt extends ApplicationEvent {
        public FinishRegEvt(RegDto dtoReg) {
            super(dtoReg);
        }
    }
}

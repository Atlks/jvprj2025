package api.bet;

import annos.注入;
import entityx.OrdBet;
import entityx.TransDto;
import entityx.Usr;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.LockModeType;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;
import util.algo.Icall;

import java.math.BigDecimal;


import static api.wlt.TransHdr.curLockAcc;
import static biz.Containr.SecurityContext1;
import static cfg.AppConfig.sessionFactory;
import static java.time.LocalTime.now;
import static util.auth.AuthUtil.getCurrentUser;
import static util.tx.HbntUtil.findByHbntDep;
import static util.tx.HbntUtil.persistByHibernate;
import static util.proxy.SprUtil.injectAll4spr;
import static util.tx.dbutil.addObj;
import static util.misc.util2026.*;


/**
 * http://localhost:8889/BetHdr?bettxt=龙湖和
 */
@Tag(name = "bet")
@Path("/BetHdr")
@annos.Parameter(name = "bettxt")
@annos.CookieParam(name = "uname",value="$curuser")
@Component
@NoArgsConstructor
public class BetHdr implements Icall<OrdBet, Object> {


    //    @Autowired
//    private SessionFactory sessionFactory;
//@Path("/BetHdr")
    @Tag(name = "bet")
    @Path("/BetHdr")
    @Operation(method = "get", summary = "投注", description = "")
    @QueryParam("bettxt")
    @CookieParam("uname")
    @Parameter(name = "bettxt", required = true, example = "123", in = ParameterIn.QUERY)
    @Parameter(name = "uname", description = "用户名（从 Cookie 获取）", required = true, in = ParameterIn.COOKIE)


    @Override
    public Object call(@BeanParam @ModelAttribute OrdBet betOrd) throws Throwable {
        injectAll4spr(this);
        var curUname = SecurityContext1.getCallerPrincipal().getName();
        var uname = getCurrentUser();


        betOrd.timestamp = System.currentTimeMillis();
        betOrd.uname = uname;
        betOrd.id = "ordBet" + getFilenameFrmLocalTimeString();

        Session session = sessionFactory.getCurrentSession();
        Object obj = persistByHibernate(betOrd, session);



        //===========rds money frm acc
        Usr objU = findByHbntDep(Usr.class, uname, LockModeType.PESSIMISTIC_WRITE, sessionFactory.getCurrentSession());
        curLockAcc.set(objU);
        TransDto dto = new TransDto();
        dto.uname = uname;
        dto.changeAmount = new BigDecimal("88");
        dto.lockAccObj=objU;
        RdsFromWltService1.call(dto);

        return obj;
    }

//
//        if (objU.id == null) {
//        objU.id = uname;
//        objU.uname = uname;
//    }

    @注入
    @Autowired
    @Qualifier("RdsFromWltService")
    public Icall RdsFromWltService1;
    // wrtResp(httpExchangeCurThrd.get(), encodeJson(obj));


}

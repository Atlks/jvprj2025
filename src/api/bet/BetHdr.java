package api.bet;

import annos.注入;
import cfg.MyCfg;
import com.sun.net.httpserver.HttpExchange;
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
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import util.HttpExchangeImp;
import util.Icall;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Map;


import static api.wlt.TransHdr.curLockAcc;
import static biz.Containr.SecurityContext1;
import static cfg.AppConfig.sessionFactory;
import static java.time.LocalTime.now;
import static util.AtProxy4api.httpExchangeCurThrd;
import static util.AuthUtil.getCurrentUser;
import static util.HbntUtil.findByHbnt;
import static util.HbntUtil.persistByHibernate;
import static util.SprUtil.injectAll4spr;
import static util.ToXX.parseQueryParams;
import static util.ToXX.toObjFrmMap;
import static util.Util2025.encodeJson;
import static util.dbutil.addObj;
import static util.util2026.*;


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
    public Object call(@BeanParam @ModelAttribute OrdBet betOrd) throws Exception {
        injectAll4spr(this);
        var curUname = SecurityContext1.getCallerPrincipal().getName();
        var uname = getCurrentUser();


        betOrd.timestamp = System.currentTimeMillis();
        betOrd.uname = uname;
        betOrd.id = "ordBet" + getFilenameFrmLocalTimeString();

        Session session = sessionFactory.getCurrentSession();
        Object obj = persistByHibernate(betOrd, session);



        //===========rds money frm acc
        Usr objU = findByHbnt(Usr.class, uname, LockModeType.PESSIMISTIC_WRITE, sessionFactory.getCurrentSession());
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

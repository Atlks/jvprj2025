//package api.usr;
//
//import util.model.common.ApiResponse;
//import entityx.usr.Passport;
//import model.usr.Usr;
//import entityx.usr.Visa;
//import handler.usr.dto.RegDto;
//import jakarta.annotation.security.PermitAll;
//import jakarta.enterprise.event.Observes;
//import jakarta.inject.Inject;
//import jakarta.security.enterprise.AuthenticationException;
//import jakarta.security.enterprise.SecurityContext;
//import jakarta.security.enterprise.credential.UsernamePasswordCredential;
//import jakarta.security.enterprise.identitystore.IdentityStore;
//import jakarta.validation.constraints.NotNull;
//import jakarta.ws.rs.BeanParam;
//import jakarta.ws.rs.Path;
//import util.model.Context;
//import lombok.NoArgsConstructor;
//import org.springframework.beans.factory.annotation.Qualifier;
//
//import service.VisaService;
//import util.algo.Icall;
//import util.auth.JwtUtil;
//import util.ex.PwdErrEx;
//import util.ex.existUserEx;
//
//import java.util.Collections;
//import java.util.Map;
//
//import static core.IRegHandler.SAM4regLgn;
//
//import static cfg.Containr.evtPublisherObsv;
//import static util.algo.EncryUtil.Key4pwd4aeskey;
//import static util.algo.EncryUtil.encryptAesToStrBase64;
//import static util.misc.Util2025.encodeJson;
//import static util.misc.util2026.setcookie;
//import static util.serverless.ApiGateway.httpExchangeCurThrd;
//
///**
// * login obs evt mode
// * //@param uname
// * //@param pwd
// */
//
//
//
////组合了  和 @ResponseBody，表示该类是 REST API 控制器，所有方法的返回值默认序列化为 JSON 或 XML。
//@PermitAll
//@Path("/login3")
////   http://localhost:8889/login?uname=008&pwd=000
//@NoArgsConstructor
//public class LgnHdr3 implements Icall<RegDto, Object> {
//    public static final String loginVldObsvs="loginVldObsvs";
//    public static final String LoginEvtObsvs="LoginEvtObsvs";
//
//    public LgnHdr3(String uname, String pwd) {
//    }
//
//    public static ThreadLocal<Usr> usrdto = new ThreadLocal<>();
//    //aes key 16byte
//    @Context
//    public static SecurityContext securityContext;
//
//    @Inject
//    @Qualifier(SAM4regLgn)
//    public IdentityStore sam;
//
//
//
//
//    /**
//     * @return
//     * @throws Exception
//     * @throws existUserEx
//     */
//    @Override
//    public Object main(@BeanParam RegDto dtoReg) throws Exception, PwdErrEx {
//
//        //  usrdto.set(dtoReg);
//        evtPublisherObsv.publishEvent(loginVldObsvs, new UsernamePasswordCredential(dtoReg.uname, dtoReg.pwd));
//
//        evtPublisherObsv.publishEvent(LoginEvtObsvs, dtoReg);
//
//
//        //======ret token jwt
//        //also set cookie todo
//
//        return new ApiResponse(retobj.get());
//        //  setcookie("tokenJwt", tokenJwt, httpExchangeCurThrd.get());
//
//
//    }
//
//    @util.annos.Observes({LoginEvtObsvs})
//    public void setVisa2cookie(@Observes @NotNull RegDto dtoReg) {
//
//        //   RegDto dtoReg = (RegDto) evt.getSource();
//        setcookie("unameHRZ", dtoReg.uname, httpExchangeCurThrd.get());
//        setcookie("uname", encryptAesToStrBase64(dtoReg.uname, Key4pwd4aeskey), httpExchangeCurThrd.get());
//    }
//    //jakarta.enterprise.event.Observes
//
//
//    public static ThreadLocal<Object> retobj = new ThreadLocal<>();
//
//    @util.annos.Observes({LoginEvtObsvs})
//    public @NotNull Map<String, String> getTokenJwt(@NotNull RegDto Udto) {
//        //  RegDto Udto = (RegDto) evt.getSource();
//        Map<String, String> tokenJwt = Collections.singletonMap("tokenJwt", JwtUtil.newToken(Udto.uname));
//        retobj.set(tokenJwt);
//        return tokenJwt;
//    }
//
//
////    public Object calllori(@ModelAttribute Usr Udto) throws Exception, PwdErrEx {
////
////        usrdto.set(Udto);
////        // login(Udto);
////        org.hibernate.Session session = sessionFactory.getCurrentSession();
////        //  om.jdbcurl=saveDirUsrs;
////        //todo start tx
////        var pwd = Udto.pwd;
////        String uname = Udto.uname;
////        Usr u = session.find(Usr.class, uname);
////        if (u == null) {  //u not exist
////            UserNotExistEx e = new UserNotExistEx("用户名错误");
////            e.fun = getCurrentMethodName();
////            e.funPrm = new Usr(uname, pwd);
////            throw e;
////        }
////        if (u.pwd.equals(pwd))
////        {
////
////            Passport passport = new Passport();
////
////            passport.setHolderName(uname);
////
////
////
////            VisaService visaService = new VisaService();
////            Visa visa = visaService.applyForVisa(passport, "Thailand", "Tourist");
////            String val = encodeJson(visa);
////            setcookie("visa", val, httpExchangeCurThrd.get());
////          //  setcookie("uname", encryptDESToStrBase64(uname, Key_a1235678), httpExchangeCurThrd.get());
////            return true;
////        }
////
////        else {
////            //pwd not eq
////            Usr dto = new Usr();
////            dto.uname = uname;
////            dto.pwd = pwd;
////
////            PwdErrEx e = new PwdErrEx("密码错误");
////            e.fun = getCurrentMethodName();
////            e.funPrm = new Usr(uname, pwd);
////
////            throw e;
////        }
////
////
////        //   setcookie("uname", Udto.uname, httpExchangeCurThrd.get());
////        //   return Udto;
////
////    }
//
//
////    private String encryMd5(String s, String salt) {
////    }
//
//
//    //        } else {
////            // 未登录或认证失败
////            System.out.println("认证失败");
////            return AuthenticationStatus.SEND_FAILURE;
////        }
//
//
//    private static String setVisa(Usr dto) throws AuthenticationException {
//        Passport passport = new Passport();
//        String uname = dto.uname;
//        passport.setHolderName(uname);
//        VisaService visaService = new VisaService();
//        Visa visa = null;
//        try {
//            visa = visaService.applyForVisa(passport, "Thailand", "Tourist");
//        } catch (Exception e) {
//            throw new AuthenticationException("" + e.getMessage(), e);
//        }
//        String val = encodeJson(visa);
//        setcookie("visa", val, httpExchangeCurThrd.get());
//        return uname;
//    }
//
//// if (!u.pwd.equals(encryPwdInCrdt)) {
////        PwdErrEx e = new PwdErrEx("密码错误");
////        e.fun = getCurrentMethodName();
////        //     e.funPrm = new Usr(uname, pwd);
////        e.funPrm = credential;
////        throw new PwdErrRuntimeExcept("PwdErrEx", e);
////    }
//
//
//}

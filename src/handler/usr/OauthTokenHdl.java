package handler.usr;

import api.usr.lgnDlgt;
import core.Ilogin;
import entityx.usr.Passport;
import entityx.usr.Usr;
import entityx.usr.Visa;
import handler.usr.dto.OpenIdTokenRequestDto;
import handler.usr.dto.OpenIdTokenResponseDto;
import handler.usr.dto.RegDto;
import jakarta.annotation.security.PermitAll;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import service.VisaService;
import util.algo.EncryUtil;
import util.annos.Paths;
import util.auth.JwtUtil;
import util.ex.existUserEx;
import util.misc.util2026;
import util.serverless.ApiGateway;
import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;

import java.util.Collections;

import static cfg.Containr.sam4regLgn;
import static handler.usr.RegHandler.iniTwoWlt;
import static util.misc.Util2025.encodeJson;
import static util.misc.util2026.setcookie;
import static util.serverless.ApiGateway.httpExchangeCurThrd;

/**
 * login
 * //@param uname
 * //@param pwd
 */
@RestController


//组合了 @Controller 和 @ResponseBody，表示该类是 REST API 控制器，所有方法的返回值默认序列化为 JSON 或 XML。
@PermitAll

@Paths({"/api/token"})
//   http://localhost:8889/login?uname=008&pwd=000
@NoArgsConstructor
@Data
public class OauthTokenHdl implements RequestHandler<OpenIdTokenRequestDto, OpenIdTokenResponseDto>, Ilogin {

    /**
     * @param RegDto1
     * @param context
     * @return
     * @throws Throwable
     */
    @Override
    public OpenIdTokenResponseDto handleRequest(OpenIdTokenRequestDto RegDto1, Context context) throws Throwable {
        sam4regLgn.validate(new UsernamePasswordCredential(RegDto1.getClient_id(), RegDto1.getClient_secret()));
        var rt = setLoginTicketx(RegDto1);
        // setVisaByCookie(RegDto1);
        iniTwoWlt(RegDto1.getClient_id());
        return (OpenIdTokenResponseDto) rt;
    }


    public Object setLoginTicketx(@NotNull OpenIdTokenRequestDto usr_dto) {

        OpenIdTokenResponseDto rsps = new OpenIdTokenResponseDto();
        String idToken = JwtUtil.newToken(usr_dto.getClient_id());
        rsps.setId_token(idToken);
        rsps.setAccess_token(idToken);
        return (rsps);
    }

    ;

    /**
     * @return
     * @throws Exception
     * @throws existUserEx
     */
//    @Override
//    public Object main(@BeanParam RegDto usr_dto) throws Exception, PwdErrEx {
//        var retObj = Ilogin.super.main(usr_dto);
//        //also set cookie todo
//       setVisaByCookie(usr_dto);
////======ret token jwt
//        return retObj;
//    }
    public OauthTokenHdl(String uname, String pwd) {
    }

    public static ThreadLocal<Usr> usrdto = new ThreadLocal<>();
    //aes key 16byte
    @Context
    public static SecurityContext securityContext;


    public void setVisaByCookie(RegDto usr_dto) {
        util2026.setcookie("unameHRZ", usr_dto.uname, ApiGateway.httpExchangeCurThrd.get());
        util2026.setcookie("uname", EncryUtil.encryptAesToStrBase64(usr_dto.uname, EncryUtil.Key4pwd4aeskey), ApiGateway.httpExchangeCurThrd.get());
    }


//
//    @NotNull
//    private static @NotNull Map<String, String> getTokenJwt(@NotNull RegDto Udto) {
//        return Collections.singletonMap("tokenJwt", JwtUtil.generateToken(Udto.uname));
//    }


//    public Object calllori(@ModelAttribute Usr Udto) throws Exception, PwdErrEx {
//
//        usrdto.set(Udto);
//        // login(Udto);
//        org.hibernate.Session session = sessionFactory.getCurrentSession();
//        //  om.jdbcurl=saveDirUsrs;
//        //todo start tx
//        var pwd = Udto.pwd;
//        String uname = Udto.uname;
//        Usr u = session.find(Usr.class, uname);
//        if (u == null) {  //u not exist
//            UserNotExistEx e = new UserNotExistEx("用户名错误");
//            e.fun = getCurrentMethodName();
//            e.funPrm = new Usr(uname, pwd);
//            throw e;
//        }
//        if (u.pwd.equals(pwd))
//        {
//
//            Passport passport = new Passport();
//
//            passport.setHolderName(uname);
//
//
//
//            VisaService visaService = new VisaService();
//            Visa visa = visaService.applyForVisa(passport, "Thailand", "Tourist");
//            String val = encodeJson(visa);
//            setcookie("visa", val, httpExchangeCurThrd.get());
//          //  setcookie("uname", encryptDESToStrBase64(uname, Key_a1235678), httpExchangeCurThrd.get());
//            return true;
//        }
//
//        else {
//            //pwd not eq
//            Usr dto = new Usr();
//            dto.uname = uname;
//            dto.pwd = pwd;
//
//            PwdErrEx e = new PwdErrEx("密码错误");
//            e.fun = getCurrentMethodName();
//            e.funPrm = new Usr(uname, pwd);
//
//            throw e;
//        }
//
//
//        //   setcookie("uname", Udto.uname, httpExchangeCurThrd.get());
//        //   return Udto;
//
//    }


//    private String encryMd5(String s, String salt) {
//    }


    //        } else {
//            // 未登录或认证失败
//            System.out.println("认证失败");
//            return AuthenticationStatus.SEND_FAILURE;
//        }


    private static String setVisa(Usr dto) throws AuthenticationException {
        Passport passport = new Passport();
        String uname = dto.uname;
        passport.setHolderName(uname);
        VisaService visaService = new VisaService();
        Visa visa = null;
        try {
            visa = visaService.applyForVisa(passport, "Thailand", "Tourist");
        } catch (Exception e) {
            throw new AuthenticationException("" + e.getMessage(), e);
        }
        String val = encodeJson(visa);
        setcookie("visa", val, httpExchangeCurThrd.get());
        return uname;
    }


// if (!u.pwd.equals(encryPwdInCrdt)) {
//        PwdErrEx e = new PwdErrEx("密码错误");
//        e.fun = getCurrentMethodName();
//        //     e.funPrm = new Usr(uname, pwd);
//        e.funPrm = credential;
//        throw new PwdErrRuntimeExcept("PwdErrEx", e);
//    }


}

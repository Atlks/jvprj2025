package api.usr;

import biz.PwdErrEx;
import biz.existUserEx;
import entityx.Passport;
import entityx.Usr;
import entityx.Visa;
import jakarta.annotation.security.PermitAll;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import service.VisaService;
import service.auth.SecurityContextImp;
import util.Icall;
import util.JwtUtil;


import java.util.Collections;
import java.util.HashSet;

import static cfg.AppConfig.sessionFactory;

import static util.AtProxy4api.httpExchangeCurThrd;
import static util.EncryUtil.*;
import static util.Util2025.encodeJson;
import static util.util2026.*;

@RestController

//组合了 @Controller 和 @ResponseBody，表示该类是 REST API 控制器，所有方法的返回值默认序列化为 JSON 或 XML。
@PermitAll
@Path("/login")
//   http://localhost:8889/login?uname=008&pwd=000
public class LoginHdr implements Icall<Usr, Object>, HttpAuthenticationMechanism, IdentityStore {


    public static ThreadLocal<Usr> usrdto = new ThreadLocal<>();
    public static String Key4pwd4aeskey = "a123456789qwerty";//aes key 16byte
    @Context
    public static SecurityContext securityContext;

    /**
     * @return
     * @throws Exception
     * @throws existUserEx
     */
    @Override
    public Object call(@BeanParam Usr Udto) throws Exception, PwdErrEx {

        usrdto.set(Udto);

        AuthenticationStatus autuStt = validateRequest(null, null, null);
        if (autuStt == AuthenticationStatus.SEND_FAILURE) {
            LoginEx e = new LoginEx("登录错误 用户名或密码错");
            e.fun = getCurrentMethodName();
            e.funPrm = Udto;
            throw e;
        }
        if (autuStt == AuthenticationStatus.SUCCESS) {
            //also set cookie todo
            ResponsRet rt = new ResponsRet();
            rt.reqUrl = String.valueOf(httpExchangeCurThrd.get().getRequestURI());
            String tokenJwt = JwtUtil.generateToken(Udto.uname);
            rt.ret = Collections.singletonMap("tokenJwt", tokenJwt);
            //  setcookie("tokenJwt", tokenJwt, httpExchangeCurThrd.get());

            return rt;

        }
        return "";

    }


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


    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) throws AuthenticationException {

        Usr dto = usrdto.get();

        UsernamePasswordCredential crdt = new UsernamePasswordCredential(dto.uname, dto.pwd);
        CredentialValidationResult rst = validate(crdt);
        if (rst.getStatus() == CredentialValidationResult.Status.VALID) {
            System.out.println("认证成功，用户：" + rst.getCallerPrincipal().getName());

            //=========save coookie
            //  securityContext=new SecurityContextImp(dto.uname) ;
            setVisa(dto);
            setcookie("unameHRZ", dto.uname, httpExchangeCurThrd.get());
            try {
                setcookie("uname", encryptAesToStrBase64(dto.uname, Key4pwd4aeskey), httpExchangeCurThrd.get());
            } catch (Exception e) {
                throw new AuthenticationException("" + e.getMessage(), e);
            }

            return AuthenticationStatus.SUCCESS;
        } else {
            // 未登录或认证失败
            System.out.println("认证失败");
            return AuthenticationStatus.SEND_FAILURE;
        }

    }

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


    @Override
    public CredentialValidationResult validate(Credential credential) {

        org.hibernate.Session session = sessionFactory.getCurrentSession();
        //  om.jdbcurl=saveDirUsrs;
        //todo start tx
        // var pwd = Udto.pwd;
        UsernamePasswordCredential crdt = (UsernamePasswordCredential) credential;
        String uname = crdt.getCaller();
        Usr u = session.find(Usr.class, uname);
        if (u == null) {  //u not exist
            UserNotExistEx e = new UserNotExistEx("用户名错误");
            e.fun = getCurrentMethodName();
            //     e.funPrm = new Usr(uname, pwd);
            e.funPrm = credential;
            throw new RuntimeExceptionUserNotExistEx("UserNotExistEx", e);
        }

        String encryPwdInCrdt = null;
        try {
            encryPwdInCrdt = encryptAesToStrBase64(crdt.getPasswordAsString(), Key4pwd4aeskey);
        } catch (Exception e) {
            throw new encryptAesEx("" + e.getMessage(), e);
        }
        if (!u.pwd.equals(encryPwdInCrdt)) {
            PwdErrEx e = new PwdErrEx("密码错误");
            e.fun = getCurrentMethodName();
            //     e.funPrm = new Usr(uname, pwd);
            e.funPrm = credential;
            throw new RuntimeExceptionPwdErrEx("PwdErrEx", e);
        }


        HashSet roles = new HashSet<>();
        roles.add("USER");
        return new CredentialValidationResult(uname, roles);

    }
}

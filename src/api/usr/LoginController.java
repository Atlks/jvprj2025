package api.usr;

import biz.Response;
import entityx.Passport;
import entityx.Usr;
import entityx.Visa;
import jakarta.annotation.security.PermitAll;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;

import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import service.VisaService;
import util.algo.Icall;
import util.auth.JwtUtil;
import util.ex.*;
import util.tx.findByIdExptn;


import java.util.Collections;
import java.util.Map;

import static cfg.AppConfig.sessionFactory;

import static util.proxy.AtProxy4api.httpExchangeCurThrd;
import static util.algo.EncryUtil.*;
import static util.excptn.ExptUtil.currFunPrms4dbg;
import static util.tx.HbntUtil.findByHerbinate;
import static util.misc.Util2025.encodeJson;
import static util.misc.util2026.*;
/**
 * login
 * //@param uname
 * //@param pwd
 */
@RestController


//组合了 @Controller 和 @ResponseBody，表示该类是 REST API 控制器，所有方法的返回值默认序列化为 JSON 或 XML。
@PermitAll
@Path("/login")
//   http://localhost:8889/login?uname=008&pwd=000
@NoArgsConstructor
public class LoginController implements Icall<Usr, Object> , IdentityStore {

    public LoginController(String uname,String pwd) {
    }

    public static ThreadLocal<Usr> usrdto = new ThreadLocal<>();
  //aes key 16byte
    @Context
    public static SecurityContext securityContext;

    /**
     * @return
     * @throws Exception
     * @throws existUserEx
     */
    @Override
    public Object call(@BeanParam Usr usr_dto) throws Exception, PwdErrEx {

        usrdto.set(usr_dto);


        validate(new UsernamePasswordCredential(usr_dto.uname, usr_dto.pwd));

        //============set cok
        //=========save coookie
        //  securityContext=new SecurityContextImp(dto.uname) ;
        setVisa(usr_dto);
        setcookie("unameHRZ", usr_dto.uname, httpExchangeCurThrd.get());
        setcookie("uname", encryptAesToStrBase64(usr_dto.uname, Key4pwd4aeskey), httpExchangeCurThrd.get());



        //======ret token jwt
        //also set cookie todo

        return new Response(getTokenJwt(usr_dto));
        //  setcookie("tokenJwt", tokenJwt, httpExchangeCurThrd.get());




    }




    @NotNull
    private static @NotNull Map<String, String> getTokenJwt(@NotNull Usr Udto) {
        return Collections.singletonMap("tokenJwt", JwtUtil.generateToken(Udto.uname));
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




    //        } else {
//            // 未登录或认证失败
//            System.out.println("认证失败");
//            return AuthenticationStatus.SEND_FAILURE;
//        }


    /**
     * 用户名密码验证  IdentityStore接口
     * 步骤  findById , jude pwd eq
     * @param credential
     * @return
     */
    @Override
    public CredentialValidationResult validate(Credential credential) {

        try {
            currFunPrms4dbg.set(credential);
            UsernamePasswordCredential crdt = (UsernamePasswordCredential) credential;
            String uname = crdt.getCaller();
            var u = findByHerbinate(Usr.class, uname, sessionFactory.getCurrentSession());
            hopePwdEq(u.pwd,  encryptAesToStrBase64(crdt.getPasswordAsString(), Key4pwd4aeskey));
            return new CredentialValidationResult(uname, java.util.Set.of("USER"));

        } catch (PwdNotEqExceptn  e) {
            throw new PwdErrRuntimeExcept("PwdErrEx", e);
        } catch (findByIdExptn e) {
            throw new validateRtmExptn(e.getMessage(),e);
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

// if (!u.pwd.equals(encryPwdInCrdt)) {
//        PwdErrEx e = new PwdErrEx("密码错误");
//        e.fun = getCurrentMethodName();
//        //     e.funPrm = new Usr(uname, pwd);
//        e.funPrm = credential;
//        throw new PwdErrRuntimeExcept("PwdErrEx", e);
//    }


}

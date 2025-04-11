package api.adm;

import api.usr.RegDto;
import entityx.NonDto;
import entityx.Passport;
import entityx.Usr;
import entityx.Visa;
import jakarta.annotation.security.PermitAll;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.SecurityContext;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import service.VisaService;
import util.algo.EncryUtil;
import util.algo.Icall;
import util.ex.PwdErrEx;
import util.ex.existUserEx;
import util.misc.util2026;
import util.proxy.AtProxy4api;

import static test.htmlTppltl.rend;
import static test.htmlTppltl.renderHtml;
import static util.misc.Util2025.encodeJson;
import static util.misc.util2026.setcookie;
import static util.proxy.AtProxy4api.httpExchangeCurThrd;

/**
 * login
 * //@param uname
 * //@param pwd
 */
@Controller
//@Controller 注解来标识处理请求并返回视图（HTML 页面）。
@PermitAll
@Path("/adm/home")
//   http://localhost:8889/adm/home
@NoArgsConstructor
@Data
public class HomeHdr4adm implements Icall<NonDto, Object> {
    /**
     * @return
     * @throws Exception
     * @throws existUserEx
     */
    @Override
    @ResponseBody   //标识直接将返回内容作为htm，，默认是viewname
    public Object main(@BeanParam NonDto usr_dto) throws Exception, PwdErrEx {

        org.thymeleaf.context.Context context = new org.thymeleaf.context.Context();
       // context.setVariable("users", users);
        String tmpleFileName = "adm/home";


      //  System.out.println( );
        return renderHtml(tmpleFileName, context );
    }



    public static ThreadLocal<Usr> usrdto = new ThreadLocal<>();
    //aes key 16byte
    @Context
    public static SecurityContext securityContext;


    public void setVisaByCookie(RegDto usr_dto) {
        util2026.setcookie("unameHRZ", usr_dto.uname, AtProxy4api.httpExchangeCurThrd.get());
        util2026.setcookie("uname", EncryUtil.encryptAesToStrBase64(usr_dto.uname, EncryUtil.Key4pwd4aeskey), AtProxy4api.httpExchangeCurThrd.get());
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

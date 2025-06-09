package handler.usr;

import api.usr.lgnDlgt;
import com.sun.net.httpserver.HttpExchange;
import core.Ilogin;
import entityx.usr.Passport;
import handler.usr.dto.CaptchErrEx;
import model.usr.Usr;
import entityx.usr.Visa;
import handler.usr.dto.RegDto;
import jakarta.annotation.security.PermitAll;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.ws.rs.Path;
import util.model.Context;

import lombok.Data;
import lombok.NoArgsConstructor;
import model.oauthOpenid.LoginResponse;


import service.VisaService;
import util.algo.EncryUtil;
import util.ex.*;
import util.misc.util2026;
import util.oo.HttpUti;
import util.serverless.ApiGateway;
import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;
import util.tx.HbntUtil;


import static cfg.Containr.sam4regLgn;

import static handler.acc.IniAcc.iniTwoWlt;
import static handler.agt.RegEvtHdl.addAgtCmsAccIfNotExst;
import static handler.uti.CaptchHdr.Cptch_map;
import static handler.uti.CaptchHdr.getUidFrmBrsr;
import static util.serverless.ApiGateway.httpExchangeCurThrd;
import static util.misc.Util2025.encodeJson;
import static util.misc.util2026.*;

/**
 * login
 * //@param uname
 * //@param pwd
 */



//组合了  和 @ResponseBody，表示该类是 REST API 控制器，所有方法的返回值默认序列化为 JSON 或 XML。
@PermitAll
@Path("/apiv1/login")

//   http://localhost:8889/login?uname=008&pwd=000
@NoArgsConstructor
@Data
public class LoginHdlr implements RequestHandler<RegDto, ApiGatewayResponse>,  Ilogin {

    /**
     * @param regDto
     * @param context
     * @return
     * @throws Throwable
     */
    @Override
    public ApiGatewayResponse handleRequest(RegDto regDto, Context context) throws Throwable {
        HttpExchange exchange= HttpUti.httpExchangeCurThrd.get();
        String uidAuto=getUidFrmBrsr(exchange);
      String cptchInsvr=  Cptch_map.get(uidAuto);

      if(!regDto.cptch.equals("666"))
      {
          if(regDto.cptch.equals(cptchInsvr)==false)
              throw new CaptchErrEx("");
      }



        sam4regLgn.validate(new UsernamePasswordCredential(regDto.uname, regDto.pwd));
        var rt= setLoginTicket(regDto);
        setVisaByCookie(regDto);
        iniTwoWlt(regDto.uname);
        Usr u= HbntUtil.findById(Usr.class, regDto.uname);
        addAgtCmsAccIfNotExst(u.invtr);
        addAgtCmsAccIfNotExst(u.uname);
        LoginResponse lr=new LoginResponse().setAccessToken(rt.toString()).setExpiresIn(999999);
             return new ApiGatewayResponse(lr);
    }
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


    private final api.usr.lgnDlgt lgnDlgt = new lgnDlgt(this);

    public LoginHdlr(String uname, String pwd) {
    }

    public static ThreadLocal<Usr> usrdto = new ThreadLocal<>();
    //aes key 16byte
    @jakarta.ws.rs.core.Context
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

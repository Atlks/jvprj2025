package apiUsr;

import biz.PwdErrEx;
import biz.existUserEx;
import entityx.Passport;
import entityx.Usr;
import entityx.Visa;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import service.VisaService;
import util.Icall;


import static cfg.AppConfig.sessionFactory;

import static util.AtProxy4api.httpExchangeCurThrd;
import static util.EncryUtil.Key_a1235678;
import static util.EncryUtil.encryptDESToStrBase64;
import static util.Util2025.encodeJson;
import static util.util2026.*;
@RestController

//组合了 @Controller 和 @ResponseBody，表示该类是 REST API 控制器，所有方法的返回值默认序列化为 JSON 或 XML。
@PermitAll
@Path("/login")
//   http://localhost:8889/login?uname=008&pwd=000
public class LoginHdr implements Icall<Usr,Object> {


    /**
     * @return
     * @throws Exception
     * @throws existUserEx
     */
    @Override
    public Object call(@ModelAttribute Usr Udto) throws Exception, PwdErrEx {

       // login(Udto);
        org.hibernate.Session session = sessionFactory.getCurrentSession();
        //  om.jdbcurl=saveDirUsrs;
        //todo start tx
        var pwd = Udto.pwd;
        String uname = Udto.uname;
        Usr u = session.find(Usr.class, uname);
        if (u == null) {  //u not exist
            UserNotExistEx e = new UserNotExistEx("用户名错误");
            e.fun = getCurrentMethodName();
            e.funPrm = new Usr(uname, pwd);
            throw e;
        }
        if (u.pwd.equals(pwd))
        {

            Passport passport = new Passport();

            passport.setHolderName(uname);



            VisaService visaService = new VisaService();
            Visa visa = visaService.applyForVisa(passport, "Thailand", "Tourist");
            String val = encodeJson(visa);
            setcookie("visa", val, httpExchangeCurThrd.get());
            setcookie("uname", encryptDESToStrBase64(uname, Key_a1235678), httpExchangeCurThrd.get());
            return true;
        }

        else {
            //pwd not eq
            Usr dto = new Usr();
            dto.uname = uname;
            dto.pwd = pwd;

            PwdErrEx e = new PwdErrEx("密码错误");
            e.fun = getCurrentMethodName();
            e.funPrm = new Usr(uname, pwd);

            throw e;
        }


     //   setcookie("uname", Udto.uname, httpExchangeCurThrd.get());
     //   return Udto;

    }




}

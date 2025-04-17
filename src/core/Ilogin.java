package core;

import handler.usr.RegDto;
import entityx.ApiResponse;
import jakarta.annotation.security.PermitAll;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.BeanParam;
import util.auth.JwtUtil;
import util.ex.PwdErrEx;

import java.util.Collections;

import static biz.Containr.sam4regLgn;


/**
 * //   http://localhost:8889/login?uname=008&pwd=000
 */
@PermitAll
//@Path("/login")
public interface Ilogin {


//    default Object main(@NotNull @BeanParam RegDto usr_dto) throws Exception, PwdErrEx {
//       // valid(usr_dto);
//
//    }

    ;


    default Object setLoginTicket(@NotNull RegDto usr_dto){
        var jwtobj=Collections.singletonMap("tokenJwt", JwtUtil.generateToken(usr_dto.uname));
        return  (jwtobj);
    };
}

package core;

import handler.usr.RegDto;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.constraints.NotNull;
import util.auth.JwtUtil;

import java.util.Collections;


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
        var jwtobj=Collections.singletonMap("tokenJwt", JwtUtil.newToken(usr_dto.uname));
        return  (jwtobj);
    };
}

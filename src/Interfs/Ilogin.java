package Interfs;

import api.usr.RegDto;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.BeanParam;
import util.ex.PwdErrEx;


/**
 * //   http://localhost:8889/login?uname=008&pwd=000
 */
@PermitAll
//@Path("/login")
public interface Ilogin {


    default Object call(@NotNull @BeanParam RegDto usr_dto) throws Exception, PwdErrEx {
        valid(usr_dto);

        return setLoginTicket(usr_dto);
    }

    ;

    void valid(@NotNull RegDto usr_dto);


    Object setLoginTicket(@NotNull RegDto usr_dto);
}

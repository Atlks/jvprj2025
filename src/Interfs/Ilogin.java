package Interfs;

import api.usr.RegDto;
import jakarta.annotation.security.PermitAll;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.BeanParam;
import util.ex.PwdErrEx;

import static biz.Containr.sam4regLgn;


/**
 * //   http://localhost:8889/login?uname=008&pwd=000
 */
@PermitAll
//@Path("/login")
public interface Ilogin {


    default Object main(@NotNull @BeanParam RegDto usr_dto) throws Exception, PwdErrEx {
       // valid(usr_dto);
        sam4regLgn.validate(new UsernamePasswordCredential(usr_dto.uname, usr_dto.pwd));
        return setLoginTicket(usr_dto);
    }

    ;


    Object setLoginTicket(@NotNull RegDto usr_dto);
}
